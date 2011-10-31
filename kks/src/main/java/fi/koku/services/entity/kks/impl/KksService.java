/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.kks.impl;

import java.util.List;

import javax.ejb.Local;

import fi.koku.services.entity.kks.v1.AuditInfoType;

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
   * @param audit
   * @return tags
   */
  List<KksTag> getTags(List<String> tagIds, AuditInfoType audit);

  /**
   * Gets KKS collection classes
   * 
   * @return KKS collection classes
   */
  List<KksCollectionClass> getCollectionClasses(AuditInfoType audit);

  /**
   * Gets collections for given customer
   * 
   * @param pic
   *          of customer
   * @param scope
   *          of the collections (all, mininmum)
   * @param audit
   * @return customer collections
   */
  List<KksCollection> getCollections(String pic, String scope, AuditInfoType audit);

  /**
   * Adds new KKS collection
   * 
   * @param creation
   *          parameters
   * @param audit
   * @return created collection ID
   */
  Long add(KksCollectionCreation creation, AuditInfoType audit);

  /**
   * Versions KKS collection
   * 
   * @param creation
   *          paramaters for the version
   * @param audit
   * @return created version ID
   */
  Long version(KksCollectionCreation creation, AuditInfoType audit);

  /**
   * Adds new KKS entry
   * 
   * @param creation
   *          params for the entry
   * @param audit
   * @return created entry ID
   */
  Long addEntry(KksEntryCreation creation, AuditInfoType audit);

  /**
   * Removes KKS entry
   * 
   * @param collectionId
   * @param id
   *          of the removed entry
   * @param audit
   */
  void removeEntryValue(String collectionId, String customer, Long id, AuditInfoType audit);

  /**
   * Updates given collection
   * 
   * @param collection
   * @param audit
   */
  void update(KksCollection collection, AuditInfoType audit);

  /**
   * Updates given collection status
   * 
   * @param collection
   *          which status is updated
   * @param status
   *          new status
   * @param audit
   */
  void updateCollectionStatus(String customer, String collection, String status, AuditInfoType audit);

  /**
   * Gets KKS collection
   * 
   * @param collectionId
   *          of the collection
   * @param audit
   * @return wanted KKS collection or NULL if collection does not exist
   */
  KksCollection get(String collectionId, AuditInfoType audit);

  /**
   * Searches KKS entries
   * 
   * @param criteria
   *          for the search
   * @param audit
   * @return KKS collections containing match entries
   */
  List<KksCollection> search(KksQueryCriteria criteria, AuditInfoType audit);

}
