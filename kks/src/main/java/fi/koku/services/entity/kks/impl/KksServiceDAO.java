package fi.koku.services.entity.kks.impl;

import java.util.List;

import javax.ejb.Local;

@Local
public interface KksServiceDAO {

  KksCollectionClass getCollectionClass(String collectionClass);

  List<KksCollection> getCollections(String pic);

  KksCollection getCollection(String id);

  KksCollection insertCollection(String name, String type, String customer, String creator);

  Long insertEntry(KksEntry e);

  void deleteEntry(Long id);

  void updateCollectionStatus(String collection, String status);

  List<KksTag> getTags(List<String> tagIds);

  List<KksEntryClass> getEntryClasses(List<String> entryClassIds);

  List<KksCollectionClass> getCollectionClasses();

}
