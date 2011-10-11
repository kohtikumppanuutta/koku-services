package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fi.koku.services.entity.tiva.v1.Consent;
import fi.koku.services.entity.tiva.v1.ConsentStatus;

/**
 * KKS service implementation class
 * 
 * @author Ixonos / tuomape
 * 
 */
@Stateless
public class KksServiceBean implements KksService {

  @EJB
  KksServiceDAO serviceDAO;

  @EJB
  Authorization authorization;

  @Override
  public List<KksTag> getTags(List<String> tagIds, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    Log.logRead("", "kks.metadata.tags", audit.getUserId(), "Read tag metadata");
    return serviceDAO.getTags(tagIds);
  }

  @Override
  public List<KksCollectionClass> getCollectionClasses(fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    Log.logRead("", "kks.metadata.collections", audit.getUserId(), "Read KKS metadata");
    return serviceDAO.getCollectionClasses();
  }

  @Override
  public List<KksCollection> getCollections(String customer, String scope,
      fi.koku.services.entity.kks.v1.AuditInfoType audit) {

    boolean parent = authorization.isParent(audit.getUserId(), customer);

    Map<Integer, KksCollectionClass> collectionClasses = new HashMap<Integer, KksCollectionClass>();
    List<KksCollectionClass> classes = serviceDAO.getCollectionClassesWithOutContent();
    for (KksCollectionClass c : classes) {
      collectionClasses.put(c.getId(), c);
    }

    Map<String, List<Consent>> consents = authorization.getConsentMap(customer, audit.getUserId(), classes);

    if (parent) {
      return serviceDAO.getChildCollectionsForParent(customer);
    }

    List<String> registrys = authorization.getAuthorizedRegistryNames(audit.getUserId());
    List<KksCollection> tmp = new ArrayList<KksCollection>();
    if (registrys.size() > 0) {
      tmp = serviceDAO.getAuthorizedCollections(customer, audit.getUserId(), registrys);
    } else {
      tmp = serviceDAO.getAuthorizedCollections(customer, audit.getUserId());
    }

    StringBuilder sb = new StringBuilder("Read collections: ");
    if (tmp != null) {
      for (int i = 0; i < tmp.size(); i++) {
        KksCollection c = tmp.get(i);
        sb.append(c.getName());
        if ((i + 1) < tmp.size()) {
          sb.append(",");
        }
        c.setEntries(new ArrayList<KksEntry>());

        setConsentStatus(collectionClasses, consents, c);

      }
    }
    Log.logRead(customer, "kks.collections", audit.getUserId(), sb.toString());

    return tmp;
  }

  private void setConsentStatus(Map<Integer, KksCollectionClass> collectionClasses,
      Map<String, List<Consent>> consents, KksCollection c) {
    List<Consent> consentList = consents.get(collectionClasses.get(c.getCollectionClass()));

    for (Consent con : consentList) {

      if (ConsentStatus.VALID.equals(con.getStatus())) {
        c.setConsentRequested(true);
        c.setUserConsentStatus(ConsentStatus.VALID.toString());
      } else if (ConsentStatus.PARTIALLY_GIVEN.equals(con.getStatus())) {
        c.setConsentRequested(true);
        c.setUserConsentStatus(con.getStatus().toString());
      } else {
        c.setConsentRequested(false);
      }
    }
  }

  @Override
  public void update(KksCollection collection, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    KksCollectionClass c = serviceDAO.getCollectionClass(collection.getCollectionClass());
    Log.logUpdate(collection.getCustomer(), c.getTypeCode(), audit.getUserId(), "Updating person collection "
        + collection.getId());
    serviceDAO.update(audit.getUserId(), collection);
  }

  @Override
  public KksCollection get(String collectionId, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    KksCollection c = serviceDAO.getCollection(collectionId);
    KksCollectionClass cc = serviceDAO.getCollectionClass(c.getCollectionClass());
    String customer = c.getCustomer();
    String name = c.getName();

    c = authorization.removeUnauthorizedContent(c, cc,
        serviceDAO.getEntryClassRegistriesForCollectionClass(cc.getId()), audit.getUserId());

    if (c != null) {
      Log.logRead(customer, cc.getTypeCode(), audit.getUserId(), "Read person collection " + name);
    } else {

      Log.logRead(customer, cc.getTypeCode(), audit.getUserId(), "Unauthorized read attempt to person collection "
          + name);
    }
    return c;
  }

  @Override
  public List<KksCollection> search(KksQueryCriteria criteria, fi.koku.services.entity.kks.v1.AuditInfoType audit) {

    List<KksCollection> tmp = serviceDAO.query(audit.getUserId(), criteria);
    StringBuilder sb = new StringBuilder();
    if (tmp != null) {
      for (int i = 0; i < tmp.size(); i++) {
        KksCollection c = tmp.get(i);
        sb.append(c.getName());
        if ((i + 1) < tmp.size()) {
          sb.append(",");
        }
      }
    }

    Log.logQuery(criteria.getPic(), "kks.collection.query", audit.getUserId(), "Quering collections " + sb.toString()
        + " with criteria" + criteria.getTagNames());

    return tmp;
  }

  @Override
  public Long add(KksCollectionCreation creation, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    KksCollectionClass cc = serviceDAO.getCollectionClass(Integer.parseInt(creation.getCollectionId()));
    Long id = serviceDAO.insertCollection(creation);
    Log.logCreate(creation.getCustomer(), cc.getTypeCode(), audit.getUserId(),
        "Created collection type " + creation.getCollectionId() + " with name " + creation.getName());
    return id;
  }

  @Override
  public void removeEntryValue(String customer, Long id, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    Log.logDelete(customer, "" + id, audit.getUserId(), "Removing entry " + id);
    serviceDAO.deleteValue(id);
  }

  @Override
  public void updateCollectionStatus(String customer, String collection, String status,
      fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    serviceDAO.updateCollectionStatus(audit.getUserId(), collection, status);
  }

  @Override
  public Long addEntry(KksEntryCreation creation, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    return serviceDAO.insertEntry(audit.getUserId(), creation);
  }

  @Override
  public Long version(KksCollectionCreation creation, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    return serviceDAO.copyAndInsert(audit.getUserId(), creation);
  }

}
