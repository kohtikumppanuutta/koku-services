package fi.koku.services.entity.kks.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import fi.koku.services.entity.kks.v1.KksEntryValueType;

/**
 * Interface for handling KKS related services
 * 
 * @author Ixonos / tuomape
 * 
 */
@Local
public interface KksService {

  List<KksTag> getTags(List<String> tagIds);

  List<KksCollectionClass> getCollectionClasses();

  List<KksCollection> getCollections(String pic, String scope);

  KksCollection add(String creator, String customer, String collectionClassId, String name);

  Long version(String creator, String customer, String collectionId, String name, boolean empty);

  Long addEntry(String id, String pic, String creator, Date modified, String collectionId, KksEntryValueType value);

  void removeEntry(Long id);

  void update(KksCollection collection);

  void updateCollectionStatus(String collection, String status);

  KksCollection get(String collectionId);

  List<KksCollection> getCollections(String pic, List<String> tagNames);

}
