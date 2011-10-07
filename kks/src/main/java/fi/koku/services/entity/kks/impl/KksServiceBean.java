package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

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
    Log.logRead(customer, "kks.collections", audit.getUserId(), "Read person collections");

    boolean parent = authorization.isParent(audit.getUserId(), customer);

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

    if (tmp != null) {
      for (KksCollection c : tmp) {
        c.setEntries(new ArrayList<KksEntry>());
      }
    }

    return tmp;
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

    c = authorization.removeUnauthorizedContent(c, serviceDAO.getEntryClassRegistriesForCollectionClass(cc.getId()),
        audit.getUserId());

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
    Log.logQuery(criteria.getPic(), "kks.collection.query", audit.getUserId(),
        "Quering person collections with criteria" + criteria.getTagNames());
    return serviceDAO.query(audit.getUserId(), criteria);
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
