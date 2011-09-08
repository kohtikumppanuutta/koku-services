package fi.koku.services.entity.kks.impl;

import java.util.List;

/**
 * Interface for handling KKS related services
 * 
 * @author Ixonos / tuomape
 * 
 */
public interface KksService {

  List<KksTag> getTags();

  List<KksCollectionClass> getCollectionClasses();

  List<KksCollection> getCollections(String pic, String scope);

  boolean add(String collectionClassId, String name);

  boolean update(KksCollection collection);

  KksCollection get(String collectionId);

  List<KksCollection> getCollections(String pic, List<String> tagIds);

}
