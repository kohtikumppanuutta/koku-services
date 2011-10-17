package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fi.koku.calendar.CalendarUtil;
import fi.koku.services.entity.tiva.v1.Consent;
import fi.koku.services.entity.tiva.v1.ConsentStatus;
import fi.koku.services.utility.log.v1.LogEntriesType;
import fi.koku.services.utility.log.v1.LogEntryType;

/**
 * KKS service implementation class
 * 
 * @author Ixonos / tuomape
 * 
 */
@Stateless
public class KksServiceBean implements KksService {

  @EJB
  private KksServiceDAO serviceDAO;

  @EJB
  private Authorization authorization;

  private Log log;

  public KksServiceBean() {
    log = new Log();
  }

  @Override
  public List<KksTag> getTags(List<String> tagIds, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    log.logRead("", "kks.metadata.tags", audit.getUserId(), "Read tag metadata");
    return serviceDAO.getTags(tagIds);
  }

  @Override
  public List<KksCollectionClass> getCollectionClasses(fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    log.logRead("", "kks.metadata.collections", audit.getUserId(), "Read KKS metadata");
    return serviceDAO.getCollectionClasses();
  }

  @Override
  public List<KksCollection> getCollections(String customer, String scope,
      fi.koku.services.entity.kks.v1.AuditInfoType audit) {

    boolean parent = authorization.isParent(audit.getUserId(), customer);

    if (parent) {
      List<KksCollection> tmp = serviceDAO.getChildCollectionsForParent(customer);
      setExtraInfoAndLogCollections(customer, audit, null, null, tmp);
      return tmp;
    }

    List<KksCollectionClass> classes = serviceDAO.getCollectionClassesWithOutContent();
    Map<Integer, KksCollectionClass> collectionClasses = getCollectionClassMap(classes);
    Map<String, List<Consent>> consentMap = authorization.getConsentMap(customer, audit.getUserId(), classes);

    Set<String> consents = getConsentSet(consentMap);

    List<String> registrys = authorization.getAuthorizedRegistryNames(audit.getUserId());
    List<KksCollection> tmp = serviceDAO.getAuthorizedCollections(customer, audit.getUserId(), registrys, consents);

    setExtraInfoAndLogCollections(customer, audit, collectionClasses, consentMap, tmp);

    return tmp;
  }

  private void setExtraInfoAndLogCollections(String customer, fi.koku.services.entity.kks.v1.AuditInfoType audit,
      Map<Integer, KksCollectionClass> collectionClasses, Map<String, List<Consent>> consentMap, List<KksCollection> tmp) {
    StringBuilder sb = new StringBuilder("Read collections: ");

    for (int i = 0; i < tmp.size(); i++) {
      KksCollection c = tmp.get(i);
      sb.append(c.getName());
      if ((i + 1) < tmp.size()) {
        sb.append(",");
      }
      c.setEntries(new ArrayList<KksEntry>());

      if (consentMap != null && consentMap.size() > 0) {
        setConsentStatus(collectionClasses, consentMap, c);
      }

    }
    log.logRead(customer, "kks.collections", audit.getUserId(), sb.toString());
  }

  private Map<Integer, KksCollectionClass> getCollectionClassMap(List<KksCollectionClass> classes) {
    Map<Integer, KksCollectionClass> collectionClasses = new HashMap<Integer, KksCollectionClass>();
    for (KksCollectionClass c : classes) {
      collectionClasses.put(c.getId(), c);
    }
    return collectionClasses;
  }

  private Set<String> getConsentSet(Map<String, List<Consent>> consentMap) {
    Set<String> set = new HashSet<String>();

    if (consentMap.size() > 0) {
      for (String key : consentMap.keySet()) {
        for (Consent c : consentMap.get(key)) {
          if (ConsentStatus.VALID.equals(c.getStatus())) {
            set.add(key);
          }
        }
      }
    }
    return set;
  }

  private void setConsentStatus(Map<Integer, KksCollectionClass> collectionClasses,
      Map<String, List<Consent>> consents, KksCollection c) {
    List<Consent> consentList = consents.get(collectionClasses.get(c.getCollectionClass()).getConsentType());

    if (consentList != null && consentList.size() > 0) {
      for (Consent con : consentList) {

        if (ConsentStatus.VALID.equals(con.getStatus())) {
          c.setConsentRequested(true);
          c.setUserConsentStatus(ConsentStatus.VALID.toString());
        } else if (ConsentStatus.PARTIALLY_GIVEN.equals(con.getStatus())) {
          c.setConsentRequested(true);
          c.setUserConsentStatus(con.getStatus().toString());
        } else if (ConsentStatus.OPEN.equals(con.getStatus())) {
          c.setConsentRequested(false);
        } else {
          c.setConsentRequested(false);
          c.setUserConsentStatus(ConsentStatus.DECLINED.toString());
        }
      }
    }
  }

  @Override
  public void update(KksCollection collection, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    KksCollectionClass c = serviceDAO.getCollectionClass(collection.getCollectionClass());
    LogEntriesType logEntries = new LogEntriesType();
    LogEntryType lt = getCollectionUpdateLogEntry(collection.getCustomer(), c.getTypeCode(), audit.getUserId(),
        "Updating person collection (" + collection.getId() + ") " + collection.getName());
    logEntries.getLogEntry().add(lt);
    serviceDAO.update(audit.getUserId(), collection, log, logEntries);
    log.logEntries(logEntries, audit.getUserId());
  }

  private LogEntryType getCollectionUpdateLogEntry(String customer, String type, String user, String message) {
    LogEntryType l = new LogEntryType();
    l.setClientSystemId(Log.CLIENT_ID);
    l.setCustomerPic(customer);
    l.setDataItemId(Log.CLIENT_ID);
    l.setDataItemType(type);
    l.setMessage(message);
    l.setOperation(Log.UPDATE);
    l.setTimestamp(CalendarUtil.getXmlDate(new Date()));
    l.setUserPic(customer);
    return l;
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
      log.logRead(customer, cc.getTypeCode(), audit.getUserId(), "Read person collection " + name);
    } else {

      log.logRead(customer, cc.getTypeCode(), audit.getUserId(), "Unauthorized read attempt to person collection "
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

    log.logQuery(criteria.getPic(), "kks.collection.query", audit.getUserId(), "Quering collections " + sb.toString()
        + " with criteria" + criteria.getTagNames());

    return tmp;
  }

  @Override
  public Long add(KksCollectionCreation creation, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    KksCollectionClass cc = serviceDAO.getCollectionClass(Integer.parseInt(creation.getCollectionId()));
    Long id = serviceDAO.insertCollection(creation);
    log.logCreate(creation.getCustomer(), cc.getTypeCode(), audit.getUserId(),
        "Created collection type " + creation.getCollectionId() + " with name " + creation.getName());
    return id;
  }

  @Override
  public void removeEntryValue(String collectionId, String customer, Long id,
      fi.koku.services.entity.kks.v1.AuditInfoType audit) {

    KksCollection c = serviceDAO.getCollection(collectionId);
    KksCollectionClass cc = serviceDAO.getCollectionClass(c.getCollectionClass());

    log.logDelete(customer, cc.getTypeCode(), audit.getUserId(),
        "Removing entry " + id + " from collection (" + c.getId() + ") " + c.getName());
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
