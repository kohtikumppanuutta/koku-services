package fi.koku.services.entity.kks.impl;

import java.util.List;

import javax.ejb.Local;

/**
 * Interface for handling KKS related services
 * 
 * @author Ixonos / tuomape
 * 
 */
@Local
public interface KksService {

  /**
   * Gets tags of given ids
   * 
   * @param tagIds
   * @return tags
   */
  List<KksTag> getTags(List<String> tagIds);

  /**
   * Gets KKS collection classes
   * 
   * @return KKS collection classes
   */
  List<KksCollectionClass> getCollectionClasses();

  /**
   * Gets collections for given customer
   * 
   * @param pic
   *          of customer
   * @param scope
   *          of the collections (all, mininmum)
   * @return customer collections
   */
  List<KksCollection> getCollections(String pic, String scope);

  /**
   * Adds new KKS collection
   * 
   * @param creation
   *          parameters
   * @return created collection ID
   */
  Long add(KksCollectionCreation creation);

  /**
   * Versions KKS collection
   * 
   * @param creation
   *          paramaters for the version
   * @return created version ID
   */
  Long version(KksCollectionCreation creation);

  /**
   * Adds new KKS entry
   * 
   * @param creation
   *          params for the entry
   * @return created entry ID
   */
  Long addEntry(KksEntryCreation creation);

  /**
   * Removes KKS entry
   * 
   * @param id
   *          of the removed entry
   */
  void removeEntry(Long id);

  /**
   * Updates given collection
   * 
   * @param collection
   */
  void update(KksCollection collection);

  /**
   * Updates given collection status
   * 
   * @param collection
   *          which status is updated
   * @param status
   *          new status
   */
  void updateCollectionStatus(String collection, String status);

  /**
   * Gets KKS collection
   * 
   * @param collectionId
   *          of the collection
   * @return wanted KKS collection or NULL if collection does not exist
   */
  KksCollection get(String collectionId);

  /**
   * Searches KKS entries
   * 
   * @param criteria
   *          for the search
   * @return KKS collections containing match entries
   */
  List<KksCollection> search(KksQueryCriteria criteria);

}
