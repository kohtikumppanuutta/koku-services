package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

@Stateless
public class KksServiceDAOBean implements KksServiceDAO {

  @PersistenceContext
  private EntityManager em;

  public KksServiceDAOBean() {
  }

  @SuppressWarnings("unchecked")
  public List<KksCollectionClass> getCollectionClasses() {
    Query q = em.createNamedQuery(KksCollectionClass.NAMED_QUERY_GET_ALL_COLLECTION_CLASSES);
    List<KksCollectionClass> list = q.getResultList();

    // due to recursive reference between groups and subgroups, group mapping is
    // hand made
    // if this would have done via annotations, it would have made the
    // insertions harder to make (ie: full path should have been set inside
    // single transaction)
    Map<Integer, List<KksGroup>> groups = getRootGroupsMap();
    for (KksCollectionClass c : list) {
      c.setGroups(groups.get(c.getCollectionClassId()));
      Collections.sort(c.getGroups());
    }

    return list;
  }

  @SuppressWarnings("unchecked")
  private Map<Integer, List<KksGroup>> getRootGroupsMap() {
    Query q = em.createNamedQuery(KksGroup.NAMED_QUERY_GET_ALL_GROUPS);
    List<KksGroup> list = (List<KksGroup>) q.getResultList();
    Map<Integer, KksGroup> map = new LinkedHashMap<Integer, KksGroup>();
    Map<Integer, List<KksGroup>> collectionMap = new HashMap<Integer, List<KksGroup>>();

    for (KksGroup g : list) {
      map.put(g.getGroupId(), g);

      if (g.getParentId() == null) {
        // map only root groups
        mapGroupToCollectionClass(collectionMap, g);
      }
    }

    for (KksGroup g : list) {
      KksGroup parent = map.get(g.getParentId());

      if (parent != null) {
        parent.addSubGroup(g);
        Collections.sort(parent.getSubGroups());
      }
    }

    return collectionMap;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<KksCollection> getCollections(String pic) {
    Query q = em.createNamedQuery(KksCollection.NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_PIC);
    q.setParameter("pic", pic);
    List<KksCollection> list = (List<KksCollection>) q.getResultList();
    return list;
  }

  @Override
  public KksCollection getCollection(String id) {
    return em.find(KksCollection.class, Long.parseLong(id));
  }

  @Override
  public KksCollection insertCollection(String name, String type, String customer, String creator) {
    KksCollection k = new KksCollection();
    k.setCollectionClass(Integer.parseInt(type));
    k.setName(name);
    k.setCustomer(customer);
    k.setCreated(new Date());
    k.setCreator(creator);
    k.setStatus("ACTIVE");
    em.persist(k);
    return k;
  }

  @Override
  public KksCollectionClass getCollectionClass(String collectionClass) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  @SuppressWarnings("unchecked")
  public List<KksTag> getTags(List<String> tagIds) {
    Query q = em.createNamedQuery(KksTag.NAMED_QUERY_GET_TAGS_BY_IDS);

    List<Integer> tmp = new ArrayList<Integer>();

    for (String s : tagIds) {
      tmp.add(Integer.parseInt(s));
    }

    q.setParameter("ids", tmp);
    List<KksTag> list = (List<KksTag>) q.getResultList();
    return list;
  }

  @Override
  public List<KksEntryClass> getEntryClasses(List<String> entryClassIds) {
    // TODO Auto-generated method stub
    return null;
  }

  private void mapGroupToCollectionClass(Map<Integer, List<KksGroup>> collectionMap, KksGroup group) {
    if (collectionMap.containsKey(group.getCollectionClassId())) {
      List<KksGroup> tmp = collectionMap.get(group.getCollectionClassId());
      tmp.add(group);
    } else {
      List<KksGroup> tmp = new ArrayList<KksGroup>();
      tmp.add(group);
      collectionMap.put(group.getCollectionClassId(), tmp);
    }
  }

  @Override
  public Long insertEntry(KksEntry e) {
    em.persist(e);
    return e.getId();
  }

  @Override
  public void deleteEntry(Long id) {
    KksEntry e = em.find(KksEntry.class, id);
    em.remove(e);
  }

  @Override
  public void updateCollectionStatus(String collection, String status) {
    KksCollection k = getCollection(collection);
    k.setStatus(status);
    em.merge(k);
  }
}
