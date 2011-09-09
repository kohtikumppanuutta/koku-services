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

  List<KksTag> getTags();

  List<KksCollectionClass> getCollectionClasses();

  List<KksCollection> getCollections(String pic, String scope);

  KksCollection add(String customer, String collectionClassId, String name);

  boolean update(KksCollection collection);

  KksCollection get(String collectionId);

  List<KksCollection> getCollections(String pic, List<String> tagIds);

  void injectMetadata(KksCollection collection, String collectionClassId);

}
