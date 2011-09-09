package fi.koku.services.entity.kks.impl;

import java.util.List;

import javax.ejb.Stateless;

@Stateless
public class KksServiceDAOBean implements KksServiceDAO {

  @Override
  public KksCollectionClass getCollectionClass(String collectionClass) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<KksTag> getTags(List<String> tagIds) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<KksEntryClass> getEntryClasses(List<String> entryClassIds) {
    // TODO Auto-generated method stub
    return null;
  }

}
