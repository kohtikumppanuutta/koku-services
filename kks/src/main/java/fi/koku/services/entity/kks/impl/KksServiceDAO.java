package fi.koku.services.entity.kks.impl;

import java.util.List;

import javax.ejb.Local;

/**
 * Interface for KKS data access
 * 
 * @author Ixonos / tuomape
 */
@Local
public interface KksServiceDAO {

  /**
   * Gets collections for the customer
   * 
   * @param pic
   *          of the customer
   * @return customer collections
   */
  List<KksCollection> getCollections(String pic);

  /**
   * Gets collection
   * 
   * @param id
   *          of the collection
   * @return collection or NULL if collection is not found
   */
  KksCollection getCollection(String id);

  /**
   * Inserts collection
   * 
   * @param creation
   *          parameters
   * @return created collection
   */
  Long insertCollection(KksCollectionCreation creation);

  /**
   * Inserts entry
   * 
   * @param creation
   *          parameters
   * @return entry id
   */
  Long insertEntry(KksEntryCreation creation);

  /**
   * Deletes entry
   * 
   * @param id
   *          of the deleted entry
   */
  void deleteValue(Long id);

  /**
   * Updates collection status
   * 
   * @param collection
   *          that is updated
   * @param status
   *          new status
   */
  void updateCollectionStatus(String collection, String status);

  /**
   * Gets tags
   * 
   * @param tagIds
   * @return tags
   */
  List<KksTag> getTags(List<String> tagIds);

  /**
   * Gets KKS collection classes
   * 
   * @return collection classes
   */
  List<KksCollectionClass> getCollectionClasses();

  /**
   * Gets collection class
   * 
   * @param id
   * @return
   */
  KksCollectionClass getCollectionClass(int id);

  /**
   * Queries entries
   * 
   * @param criteria
   *          of the query
   * @return list of collections containing the matching entries
   */
  List<KksCollection> query(KksQueryCriteria criteria);

  /**
   * Updates collection
   * 
   * @param collection
   */
  void update(KksCollection collection);

  /**
   * Copies and insert new version from given source
   * 
   * @param source
   *          for the version
   * @return id of the new version
   */
  Long copyAndInsert(KksCollectionCreation source);

}
