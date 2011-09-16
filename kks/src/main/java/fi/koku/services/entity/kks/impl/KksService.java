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

  List<KksTag> getTags(List<String> tagIds);

  List<KksCollectionClass> getCollectionClasses();

  List<KksCollection> getCollections(String pic, String scope);

  KksCollection add(String creator, String customer, String collectionClassId, String name);

  void removeEntry(Long id);

  boolean update(KksCollection collection);

  void updateCollectionStatus(String collection, String status);

  KksCollection get(String collectionId);

  List<KksCollection> getCollections(String pic, List<String> tagNames);

  void injectMetadata(KksCollection collection, String collectionClassId);

}
