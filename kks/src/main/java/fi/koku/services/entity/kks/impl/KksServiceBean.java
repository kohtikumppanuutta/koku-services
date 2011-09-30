package fi.koku.services.entity.kks.impl;

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

  @Override
  public List<KksTag> getTags(List<String> tagIds, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    Log.logRead("", "kks.metadata.tags", audit, "Read tag metadata");
    return serviceDAO.getTags(tagIds);
  }

  @Override
  public List<KksCollectionClass> getCollectionClasses(fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    Log.logRead("", "kks.metadata.collections", audit, "Read KKS metadata");
    return serviceDAO.getCollectionClasses();
  }

  @Override
  public List<KksCollection> getCollections(String customer, String scope,
      fi.koku.services.entity.kks.v1.AuditInfoType audit) {

    Log.logRead(customer, "kks.collections", audit, "Read person collections");
    // TODO: Scope mukaan, ehkä lazy init kirjausten hakemiselle
    return serviceDAO.getCollections(customer);
  }

  @Override
  public void update(KksCollection collection, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    KksCollectionClass c = serviceDAO.getCollectionClass(collection.getCollectionClass());
    Log.logUpdate(collection.getCustomer(), c.getTypeCode(), audit, "Updating person collection " + collection.getId());
    serviceDAO.update(collection);
  }

  @Override
  public KksCollection get(String collectionId, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    KksCollection c = serviceDAO.getCollection(collectionId);
    KksCollectionClass cc = serviceDAO.getCollectionClass(c.getCollectionClass());
    Log.logRead(c.getCustomer(), cc.getTypeCode(), audit, "Read person collections");
    return c;
  }

  @Override
  public List<KksCollection> search(KksQueryCriteria criteria, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    Log.logQuery(criteria.getPic(), "kks.collection.query", audit, "Quering person collections with criteria"
        + criteria.getTagNames());
    return serviceDAO.query(criteria);
  }

  @Override
  public Long add(KksCollectionCreation creation, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    KksCollectionClass cc = serviceDAO.getCollectionClass(Integer.parseInt(creation.getCollectionId()));
    Log.logCreate(creation.getCustomer(), cc.getTypeCode(), audit,
        "Creating collection type " + creation.getCollectionId() + " with name " + creation.getName());
    return serviceDAO.insertCollection(creation);
  }

  @Override
  public void removeEntryValue(String customer, Long id, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    Log.logDelete(customer, "" + id, audit, "Removing entry " + id);
    serviceDAO.deleteValue(id);
  }

  @Override
  public void updateCollectionStatus(String customer, String collection, String status,
      fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    Log.logUpdate(customer, collection, audit, "Updating collection status to " + status);
    serviceDAO.updateCollectionStatus(collection, status);
  }

  @Override
  public Long addEntry(KksEntryCreation creation, fi.koku.services.entity.kks.v1.AuditInfoType audit) {

    if (creation.getId() == null) {
      Log.logCreate(creation.getPic(), "" + creation.getCollectionId(), audit,
          "Adding entry type " + creation.getEntryClassId() + " to collection " + creation.getCollectionId());
    } else {
      Log.logUpdate(creation.getPic(), "" + creation.getCollectionId(), audit, "Updating entry " + creation.getId()
          + " in collection " + creation.getCollectionId());
    }
    return serviceDAO.insertEntry(creation);
  }

  @Override
  public Long version(KksCollectionCreation creation, fi.koku.services.entity.kks.v1.AuditInfoType audit) {
    Log.logCreate(creation.getCustomer(), "" + creation.getCollectionId(), audit,
        "Creating new collection version from " + creation.getCollectionId() + " with name " + creation.getName());
    return serviceDAO.copyAndInsert(creation);
  }

}
