package fi.koku.services.entity.kks.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fi.koku.services.entity.kks.v1.KksEntryValueType;

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
  public List<KksTag> getTags(List<String> tagIds) {
    return serviceDAO.getTags(tagIds);
  }

  @Override
  public List<KksCollectionClass> getCollectionClasses() {
    return serviceDAO.getCollectionClasses();
  }

  @Override
  public List<KksCollection> getCollections(String customer, String scope) {

    // TODO: Scope mukaan, ehkä lazy init kirjausten hakemiselle
    return serviceDAO.getCollections(customer);
  }

  @Override
  public void update(KksCollection collection) {
    serviceDAO.update(collection);
  }

  @Override
  public KksCollection get(String collectionId) {

    return serviceDAO.getCollection(collectionId);
  }

  @Override
  public List<KksCollection> getCollections(String customer, List<String> tagNames) {
    return serviceDAO.queryCollections(customer, tagNames);
  }

  @Override
  public KksCollection add(String creator, String customer, String collectionClassId, String name) {

    return serviceDAO.insertCollection(name, collectionClassId, customer, creator);
  }

  @Override
  public void removeEntry(Long id) {
    serviceDAO.deleteEntry(id);
  }

  @Override
  public void updateCollectionStatus(String collection, String status) {
    serviceDAO.updateCollectionStatus(collection, status);
  }

  @Override
  public Long addEntry(String id, String pic, String creator, Date modified, String collectionId,
      KksEntryValueType value) {
    return serviceDAO.insertEntry(id == null ? null : Long.parseLong(id), pic, creator, modified,
        collectionId == null ? null : Long.parseLong(collectionId), value);
  }

  @Override
  public Long version(String creator, String customer, String collectionId, String name, boolean empty) {
    return serviceDAO.insertAndCopy(creator, customer, collectionId, name, empty);
  }
}
