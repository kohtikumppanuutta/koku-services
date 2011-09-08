package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.EntityManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * KKS service implementation class
 * 
 * @author Ixonos / tuomape
 * 
 */
public class KksServiceImpl implements KksService {

  private static final Logger LOG = LoggerFactory.getLogger(KksServiceImpl.class);

  private EntityManager em;

  public KksServiceImpl(EntityManager em) {
    this.em = em;
  }

  @Override
  public List<KksTag> getTags() {
    List<KksTag> tmp = new ArrayList<KksTag>();
    KksTag kt = new KksTag();
    kt.setDescription("Tagin kuvaus 2");
    kt.setId(1);
    kt.setName("Tagin nimi 2");
    tmp.add(kt);
    return tmp;
  }

  @Override
  public List<KksCollectionClass> getCollectionClasses() {
    // TODO Auto-generated method stub

    return null;
  }

  @Override
  public List<KksCollection> getCollections(String pic, String scope) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean update(KksCollection collection) {
    // TODO Auto-generated method stub
    return false;
  }

  @Override
  public KksCollection get(String collectionId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<KksCollection> getCollections(String pic, List<String> tagIds) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public boolean add(String collectionClassId, String name) {
    // TODO Auto-generated method stub
    return false;
  }

}
