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
  public List<KksCollection> search(KksQueryCriteria criteria) {
    return serviceDAO.query(criteria);
  }

  @Override
  public Long add(KksCollectionCreation creation) {

    return serviceDAO.insertCollection(creation);
  }

  @Override
  public void removeEntryValue(Long id) {
    serviceDAO.deleteValue(id);
  }

  @Override
  public void updateCollectionStatus(String collection, String status) {
    serviceDAO.updateCollectionStatus(collection, status);
  }

  @Override
  public Long addEntry(KksEntryCreation creation) {
    return serviceDAO.insertEntry(creation);
  }

  @Override
  public Long version(KksCollectionCreation creation) {
    return serviceDAO.copyAndInsert(creation);
  }
}
