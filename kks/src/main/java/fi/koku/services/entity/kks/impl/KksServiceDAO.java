package fi.koku.services.entity.kks.impl;

import java.util.Date;
import java.util.List;

import javax.ejb.Local;

import fi.koku.services.entity.kks.v1.KksEntryValueType;

@Local
public interface KksServiceDAO {

  List<KksCollection> getCollections(String pic);

  KksCollection getCollection(String id);

  KksCollection insertCollection(String name, String type, String customer, String creator);

  Long insertEntry(Long id, String pic, String creator, Date modified, Long collectionId, KksEntryValueType value);

  void deleteEntry(Long id);

  void updateCollectionStatus(String collection, String status);

  List<KksTag> getTags(List<String> tagIds);

  List<KksCollectionClass> getCollectionClasses();

  List<KksCollection> queryCollections(String pic, List<String> tagNames);

  void update(KksCollection collection);

  Long insertAndCopy(String creator, String customer, String collectionId, String name, boolean empty);

}
