/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (http://www.ixonos.com/).
 *
 */
package fi.koku.services.entity.kks.impl;

import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Local;

import fi.koku.services.utility.log.v1.LogEntriesType;

/**
 * Interface for KKS data access
 * 
 * @author Ixonos / tuomape
 */
@Local
public interface KksServiceDAO {

  /**
   * Gets customer (pic) collections <b>without</b> checking authorization (for
   * customers parent).
   * 
   * @param pic
   *          of the customer
   * @return customer collections
   */
  List<KksCollection> getChildCollectionsForParent(String pic);

  /**
   * Gets customer (pic) collections where user is authorized to see the
   * collection
   * 
   * @param pic
   * @param user
   * @param registers
   *          that are allowed for the user
   * @param consents
   *          that are valid for the user
   * @return collections that are authorized for the user
   */
  List<KksCollection> getAuthorizedCollections(String pic, String user, List<String> registers, Set<String> consents);

  /**
   * Gets customer (pic) collections where user is the creator of the collection
   * 
   * @param pic
   * @param user
   * @return collections that are authorized for the user
   */
  List<KksCollection> getAuthorizedCollections(String pic, String user);

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
   * @param user
   * @return entry id
   */
  Long insertEntry(String user, KksEntryCreation creation);

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
   * @param user
   * @param collection
   *          that is updated
   * @param status
   *          new status
   */
  void updateCollectionStatus(String user, String collection, String status);

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
   * Gets KKS collection classes without content (groups, entry classes)
   * 
   * @return collection classes
   */
  List<KksCollectionClass> getCollectionClassesWithOutContent();

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
   * @param user
   * @param criteria
   *          of the query
   * @return list of collections containing the matching entries
   */
  List<KksCollection> query(String user, KksQueryCriteria criteria);

  /**
   * Updates collection
   * 
   * @param collection
   * @param user
   * @param log
   *          for the update
   * @param logEntries
   *          for logging
   */
  void update(String user, KksCollection collection, Log log, LogEntriesType logEntries);

  /**
   * Copies and insert new version from given source
   * 
   * @param source
   *          for the version
   * @param user
   * @return id of the new version
   */
  Long copyAndInsert(String user, KksCollectionCreation source);

  /**
   * Gets registers that are used in given collection class
   * 
   * @param classId
   * @return collection registers
   */
  List<String> getCollectionClassRegisters(int classId);

  /**
   * Gets groups that are used in given collection class
   * 
   * @param classId
   * @return collection groups
   */
  List<KksGroup> getCollectionClassGroups(int classId);

  /**
   * Gets entry classes for given groups
   * 
   * @param groupIds
   * @return entry classes for given groups
   */
  List<KksEntryClass> getEntryClassesForGroups(Set<Integer> groupIds);

  /**
   * Gets entry class registers in given collection class
   * 
   * @param classId
   * @return entry class registers
   */
  Map<Integer, String> getEntryClassRegistriesForCollectionClass(int classId);

}
