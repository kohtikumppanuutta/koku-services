package fi.koku.services.entity.kks.impl;

import java.math.BigInteger;
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

import fi.koku.services.entity.kks.v1.KksEntryValueType;

@Stateless
public class KksServiceDAOBean implements KksServiceDAO {

  @PersistenceContext
  private EntityManager em;

  public KksServiceDAOBean() {
  }

  public List<KksCollectionClass> getCollectionClasses() {
    Query q = em.createNamedQuery(KksCollectionClass.NAMED_QUERY_GET_ALL_COLLECTION_CLASSES);

    @SuppressWarnings("unchecked")
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

  private Map<Integer, List<KksGroup>> getRootGroupsMap() {
    Query q = em.createNamedQuery(KksGroup.NAMED_QUERY_GET_ALL_GROUPS);

    @SuppressWarnings("unchecked")
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
  public List<KksCollection> getCollections(String pic) {
    Query q = em.createNamedQuery(KksCollection.NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_PIC);
    q.setParameter("pic", pic);

    @SuppressWarnings("unchecked")
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
  public List<KksTag> getTags(List<String> tagIds) {
    Query q = em.createNamedQuery(KksTag.NAMED_QUERY_GET_TAGS_BY_IDS);

    List<Integer> tmp = new ArrayList<Integer>();

    for (String s : tagIds) {
      tmp.add(Integer.parseInt(s));
    }

    q.setParameter("ids", tmp);

    @SuppressWarnings("unchecked")
    List<KksTag> list = (List<KksTag>) q.getResultList();
    return list;
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
  public Long insertEntry(Long id, String pic, String creator, Date modified, Long collectionId, KksEntryValueType value) {

    if (id == null) {
      KksEntry e = new KksEntry();
      e.setCustomer(pic);
      e.setModified(modified);
      e.setKksCollection(em.find(KksCollection.class, collectionId));
      e.setCreator(creator);
      KksValue v = new KksValue();
      v.setValue(value.getValue());
      List<KksValue> tmp = new ArrayList<KksValue>();
      tmp.add(v);
      e.setValues(tmp);
      em.persist(e);
      return e.getId();

    } else {
      KksEntry e = em.find(KksEntry.class, id);
      e.setCustomer(pic);
      e.setCreator(creator);
      e.setModified(modified);

      KksValue v = value.getId() == null ? new KksValue() : em.find(KksValue.class, Long.parseLong(value.getId()));
      v.setValue(value.getValue());
      List<KksValue> tmp = new ArrayList<KksValue>();
      tmp.add(v);
      e.setValues(tmp);
      em.merge(e);
      return e.getId();
    }
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

  @Override
  public List<KksCollection> queryCollections(String pic, List<String> tagNames) {

    if (tagNames.size() == 0) {
      return getCollections(pic);
    }

    StringBuilder tmp = new StringBuilder();

    for (int i = 0; i < tagNames.size(); i++) {
      String t = tagNames.get(i);
      tmp.append(" name LIKE '%");
      tmp.append(t);
      tmp.append("%' ");
      if ((i + 1) < tagNames.size()) {
        tmp.append("AND");
      }
    }

    StringBuilder qs = new StringBuilder();
    qs.append("SELECT DISTINCT collection_id FROM kks_entry WHERE customer = ");
    qs.append(pic);
    qs.append(" AND id IN( SELECT DISTINCT entry_class_id FROM kks_entry_class_tags WHERE entry_class_id IN (SELECT tag_id FROM kks_tag WHERE");
    qs.append(tmp.toString());
    qs.append("));");

    @SuppressWarnings("unchecked")
    List<BigInteger> collectionIds = em.createNativeQuery(qs.toString()).getResultList();

    List<Long> collectionIdsList = new ArrayList<Long>();

    for (BigInteger b : collectionIds) {
      collectionIdsList.add(b.longValue());
    }

    if (collectionIds == null || collectionIds.size() == 0) {
      return new ArrayList<KksCollection>();
    }

    Query q = em.createNamedQuery(KksCollection.NAMED_QUERY_GET_COLLECTIONS_BY_IDS);

    q.setParameter("ids", collectionIdsList);

    @SuppressWarnings("unchecked")
    List<KksCollection> collections = (List<KksCollection>) q.getResultList();
    return collections;
  }

  @Override
  public void update(KksCollection collection) {

    KksCollection tmp = em.find(KksCollection.class, collection.getId());

    syncFields(collection, tmp);

    tmp.setNextVersion(collection.getNextVersion());
    tmp.setPrevVersion(collection.getPrevVersion());
    tmp.setStatus(collection.getStatus());
    tmp.setName(collection.getName());
    tmp.setCreated(collection.getCreated());
    tmp.setCreator(collection.getCreator());
    tmp.setDescription(collection.getDescription());

    em.merge(tmp);
  }

  private void syncFields(KksCollection collection, KksCollection tmp) {
    Map<Long, KksEntry> newEntries = new HashMap<Long, KksEntry>();
    Map<Long, KksEntry> oldEntries = new HashMap<Long, KksEntry>();

    Map<Long, KksValue> newValues = new HashMap<Long, KksValue>();
    Map<Long, KksValue> oldValues = new HashMap<Long, KksValue>();

    Map<Integer, KksTag> newTags = new HashMap<Integer, KksTag>();
    Map<Integer, KksTag> oldTags = new HashMap<Integer, KksTag>();

    createEntriesMap(collection, newEntries, newValues, newTags);
    createEntriesMap(tmp, oldEntries, oldValues, oldTags);

    combineEntries(collection, tmp, oldEntries, oldValues, oldTags);
    deleteRemovedFields(tmp, newEntries, newValues, newTags);
  }

  private void deleteRemovedFields(KksCollection tmp, Map<Long, KksEntry> newEntries, Map<Long, KksValue> newValues,
      Map<Integer, KksTag> newTags) {
    List<KksEntry> eList = new ArrayList<KksEntry>(tmp.getEntries());

    List<Long> removableEntries = new ArrayList<Long>();
    List<Long> removableValues = new ArrayList<Long>();
    List<Integer> removableTags = new ArrayList<Integer>();

    generateRemovableIds(tmp, newEntries, newValues, newTags, eList, removableEntries, removableValues, removableTags);

    if (removableValues.size() > 0) {
      em.createNamedQuery(KksValue.NAMED_QUERY_DELETE_VALUES_BY_IDS).setParameter("ids", removableValues)
          .executeUpdate();
      em.flush();
    }

    if (removableEntries.size() > 0) {
      em.createNamedQuery(KksEntry.NAMED_QUERY_DELETE_ENTRIES_BY_IDS).setParameter("ids", removableEntries)
          .executeUpdate();
      em.flush();
    }

    // TODO: Tag deleting

  }

  private void generateRemovableIds(KksCollection tmp, Map<Long, KksEntry> newEntries, Map<Long, KksValue> newValues,
      Map<Integer, KksTag> newTags, List<KksEntry> eList, List<Long> removableEntries, List<Long> removableValues,
      List<Integer> removableTags) {
    if (eList != null) {
      for (KksEntry e : eList) {
        if (newEntries.get(e.getId()) == null) {
          tmp.removeKksEntry(e);
          removableEntries.add(e.getId());

          if (e.getValues() != null) {
            for (KksValue v : e.getValues()) {
              removableValues.add(v.getId());
            }
          }

          if (e.getTags() != null) {
            for (KksTag t : e.getTags()) {
              removableTags.add(t.getId());
            }
          }
        } else {

          if (e.getValues() != null) {
            List<KksValue> vList = new ArrayList<KksValue>(e.getValues());
            for (KksValue v : vList) {
              if (v.getId() != null && newValues.get(v.getId()) == null) {
                e.removeKksValue(v);
                removableValues.add(v.getId());
              }
            }
          }

          if (e.getTags() != null) {
            List<KksTag> tList = new ArrayList<KksTag>(e.getTags());
            for (KksTag t : tList) {
              if (newTags.get(t.getId()) == null) {
                e.removeKksTag(t);
                removableTags.add(t.getId());
              }
            }
          }
        }
      }
    }
  }

  private void combineEntries(KksCollection collection, KksCollection tmp, Map<Long, KksEntry> oldEntries,
      Map<Long, KksValue> oldValues, Map<Integer, KksTag> oldTags) {
    for (KksEntry e : collection.getEntries()) {
      KksEntry old = oldEntries.get(e.getId());

      if (old == null) {
        e.setKksCollection(tmp);
        tmp.addKksEntry(e);
      } else {
        old.setCreator(e.getCreator());
        old.setCustomer(e.getCustomer());
        old.setModified(e.getModified());
        old.setCustomer(e.getCustomer());

        if (e.getValues() != null) {
          for (KksValue v : e.getValues()) {
            KksValue oldV = oldValues.get(v.getId());

            if (oldV == null) {
              v.setEntry(old);
              old.addKksValue(v);
            } else {
              oldV.setValue(v.getValue());
            }
          }
        }

        if (e.getTags() != null) {
          for (KksTag t : e.getTags()) {
            KksTag oldT = oldTags.get(t.getId());

            if (oldT == null) {
              old.addKksTag(t);
            }
          }
        }

      }
    }
  }

  private void createEntriesMap(KksCollection collection, Map<Long, KksEntry> entries, Map<Long, KksValue> values,
      Map<Integer, KksTag> tags) {

    if (collection.getEntries() != null) {
      for (KksEntry entry : collection.getEntries()) {
        entries.put(entry.getId(), entry);

        if (entry.getValues() != null) {
          for (KksValue value : entry.getValues()) {
            values.put(value.getId(), value);
          }
        }

        if (entry.getTags() != null) {
          for (KksTag value : entry.getTags()) {
            tags.put(value.getId(), value);
          }
        }
      }
    }
  }

  @Override
  public Long insertAndCopy(String creator, String customer, String collectionId, String name, boolean empty) {
    KksCollection old = getCollection(collectionId);

    KksCollection newVersion = new KksCollection();
    newVersion.setName(name);
    newVersion.setCreator(creator);
    newVersion.setCreated(new Date());
    newVersion.setCustomer(customer);
    newVersion.setPrevVersion("" + old.getId());
    newVersion.setStatus("ACTIVE");

    if (!empty) {

      for (KksEntry e : old.getEntries()) {
        KksEntry newE = new KksEntry();
        newE.setCreator(e.getCreator());
        newE.setCustomer(customer);
        newE.setEntryClassId(e.getEntryClassId());
        newE.setModified(e.getModified());
        newVersion.addKksEntry(newE);

        for (KksValue value : e.getValues()) {
          KksValue newVal = new KksValue();
          newVal.setEntry(newE);
          newVal.setValue(value.getValue());
          newE.addKksValue(newVal);
        }

        for (KksTag tag : e.getTags()) {
          // todo tags
        }
      }
    }

    em.persist(newVersion);

    old.setNextVersion("" + newVersion.getId());
    em.merge(old);

    return newVersion.getId();
  }
}
