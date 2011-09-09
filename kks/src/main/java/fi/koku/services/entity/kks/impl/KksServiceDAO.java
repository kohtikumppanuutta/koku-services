package fi.koku.services.entity.kks.impl;

import java.util.List;

public interface KksServiceDAO {

  KksCollectionClass getCollectionClass(String collectionClass);

  List<KksTag> getTags(List<String> tagIds);

  List<KksEntryClass> getEntryClasses(List<String> entryClassIds);

}
