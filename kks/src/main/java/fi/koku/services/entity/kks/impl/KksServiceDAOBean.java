/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.kks.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fi.koku.services.utility.authorizationinfo.v1.model.Registry;
import fi.koku.services.utility.log.v1.LogEntriesType;

/**
 * KKS data access implementation
 * 
 * @author Ixonos / tuomape
 *
 */
@Stateless
public class KksServiceDAOBean implements KksServiceDAO {

  public static final String ACTIVE = "ACTIVE";
  public static final String LOCKED = "LOCKED";
  public static final String COMMENT_FIELD_TAG = "kks.kehitysasia.kommentti";

  @EJB
  private Authorization authorization;

  @PersistenceContext
  private EntityManager em;

  private Log log;

  public KksServiceDAOBean() {
    log = new Log();
  }

  @Override
  public List<KksCollectionClass> getCollectionClasses() {
    Query q = em.createNamedQuery(KksCollectionClass.NAMED_QUERY_GET_ALL_COLLECTION_CLASSES);

    @SuppressWarnings("unchecked")
    List<KksCollectionClass> list = q.getResultList();

    // metadata loading is done manually
    Map<Integer, List<KksGroup>> groups = getRootGroupsMap();
    for (KksCollectionClass c : list) {
      c.setGroups(groups.get(c.getId()));
      Collections.sort(c.getGroups());
    }
    return list;
  }

  @Override
  public List<KksCollectionClass> getCollectionClassesWithOutContent() {
    Query q = em.createNamedQuery(KksCollectionClass.NAMED_QUERY_GET_ALL_COLLECTION_CLASSES);

    @SuppressWarnings("unchecked")
    List<KksCollectionClass> list = q.getResultList();
    return list;
  }

  @Override
  public KksCollectionClass getCollectionClass(int id) {
    return (KksCollectionClass) em.find(KksCollectionClass.class, id);
  }

  /**
   * Creates map from root groups (groups that don't have parent)
   * 
   * @return root group map
   */
  private Map<Integer, List<KksGroup>> getRootGroupsMap() {
    Query groups = em.createNamedQuery(KksGroup.NAMED_QUERY_GET_ALL_GROUPS);

    Query classes = em.createNamedQuery(KksEntryClass.NAMED_QUERY_GET_ALL_ENTRY_CLASSES);
    @SuppressWarnings("unchecked")
    List<KksEntryClass> entries = (List<KksEntryClass>) classes.getResultList();
    Map<Integer, List<KksEntryClass>> entryMap = new HashMap<Integer, List<KksEntryClass>>();

    for (KksEntryClass e : entries) {
      if (entryMap.containsKey(e.getGroupId())) {
        List<KksEntryClass> tmp = entryMap.get(e.getGroupId());
        tmp.add(e);
      } else {
        List<KksEntryClass> tmp = new ArrayList<KksEntryClass>();
        tmp.add(e);
        entryMap.put(e.getGroupId(), tmp);
      }
    }

    @SuppressWarnings("unchecked")
    List<KksGroup> list = (List<KksGroup>) groups.getResultList();

    Map<Integer, KksGroup> map = new LinkedHashMap<Integer, KksGroup>();
    Map<Integer, List<KksGroup>> collectionMap = new HashMap<Integer, List<KksGroup>>();

    for (KksGroup g : list) {
      map.put(g.getGroupId(), g);
      g.setEntryClasses(entryMap.get(g.getGroupId()));
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
  public List<KksCollection> getChildCollectionsForParent(String pic) {
    Query q = em.createNamedQuery(KksCollection.NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_PIC);
    q.setParameter("pic", pic);

    @SuppressWarnings("unchecked")
    List<KksCollection> list = (List<KksCollection>) q.getResultList();
    List<KksCollection> tmp = new ArrayList<KksCollection>();
    
    for ( KksCollection c : list ) {
      tmp.add(new KksCollection(c));
    }
    return tmp;
  }

  @Override
  public List<KksCollection> getAuthorizedCollections(String pic, String user, List<String> registers,
      Set<String> consents) {
    Query q = null;
    if (registers.size() > 0 && consents.size() == 0) {
      q = em
          .createQuery("SELECT c FROM KksCollection c WHERE c.customer =:customer AND (c.creator =:user OR c.collectionClass IN (SELECT DISTINCT g.collectionClassId FROM KksGroup g WHERE g.register IN (:registers)))  ");
      q.setParameter("customer", pic).setParameter("user", user).setParameter("registers", registers);
    } else if (registers.size() > 0 && consents.size() > 0) {
      q = em
          .createQuery("SELECT c FROM KksCollection c WHERE c.customer =:customer AND (c.creator =:user OR c.collectionClass IN (SELECT DISTINCT g.collectionClassId FROM KksGroup g WHERE g.register IN (:registers) OR c.collectionClass IN (SELECT DISTINCT kc.id FROM KksCollectionClass kc WHERE kc.consentType IN(:consents))))");
      q.setParameter("customer", pic).setParameter("user", user).setParameter("registers", registers)
          .setParameter("consents", consents);
    } else if (registers.size() == 0 && consents.size() > 0) {
      q = em
          .createQuery("SELECT c FROM KksCollection c WHERE c.customer =:customer AND (c.creator =:user OR c.collectionClass IN (SELECT DISTINCT kc.id FROM KksCollectionClass kc WHERE kc.consentType IN(:consents))");
      q.setParameter("customer", pic).setParameter("user", user).setParameter("consents", consents);
    } else {
      q = em.createNamedQuery(KksCollection.NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_AND_CREATOR);
      q.setParameter("creator", user).setParameter("customer", pic);
    }

    @SuppressWarnings("unchecked")
    List<KksCollection> list = (List<KksCollection>) q.getResultList();
    List<KksCollection> tmp = new ArrayList<KksCollection>();
    
    for ( KksCollection c : list ) {
      tmp.add(new KksCollection(c));
    }
    return tmp;
  }

  public List<KksCollection> getAuthorizedCollections(String pic, String user) {
    Query q = em.createNamedQuery(KksCollection.NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_AND_CREATOR);
    q.setParameter("creator", user).setParameter("customer", pic);

    @SuppressWarnings("unchecked")
    List<KksCollection> list = (List<KksCollection>) q.getResultList();
    List<KksCollection> tmp = new ArrayList<KksCollection>();
    
    for ( KksCollection c : list ) {
      tmp.add(new KksCollection(c));
    }
    return tmp;
  }

  @Override
  public KksCollection getCollection(String id) {
    return new KksCollection( em.find(KksCollection.class, Long.parseLong(id)));
  }

  @Override
  public Long insertCollection(KksCollectionCreation creation) {
    KksCollection k = new KksCollection();
    k.setCollectionClass(Integer.parseInt(creation.getCollectionId()));
    k.setName(creation.getName());
    k.setCustomer(creation.getCustomer());
    k.setCreated(new Date());
    k.setCreator(creation.getCreator());
    k.setModified(new Date());
    k.setModifier(creation.getCreator());
    k.setStatus(ACTIVE);

    Query q = em.createNamedQuery(KksGroup.NAMED_QUERY_GET_ALL_COLLECTION_CLASS_GROUPS).setParameter("id",
        k.getCollectionClass());

    @SuppressWarnings("unchecked")
    List<KksGroup> tmp = q.getResultList();

    Set<Integer> groups = new HashSet<Integer>();

    for (KksGroup g : tmp) {
      groups.add(g.getGroupId());
    }

    List<KksEntryClass> entryClasses = getEntryClassesForGroups(groups);
    List<KksEntry> entrys = new ArrayList<KksEntry>();
    for (KksEntryClass ec : entryClasses) {

      if (!ec.isMultiValue()) {
        // multivalues are created by user
        KksEntry e = new KksEntry();
        e.setCreator(creation.getCreator());
        e.setCustomer(creation.getCustomer());
        e.setEntryClassId(ec.getEntryClassId());
        e.setKksCollection(k);
        e.setModified(new Date());

        List<KksValue> values = new ArrayList<KksValue>();

        KksValue value = new KksValue();
        value.setEntry(e);
        value.setModified(new Date());
        value.setModifier(creation.getCreator());
        value.setValue("");
        values.add(value);
        e.setValues(values);
        entrys.add(e);
      }
    }

    k.setEntries(entrys);

    em.persist(k);
    return k.getId();
  }

  @Override
  public List<KksTag> getTags(List<String> tagIds) {

    if (tagIds.isEmpty()) {
      return new ArrayList<KksTag>();
    }
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

  /**
   * Maps group to collection classes
   * 
   * @param collectionMap
   * @param group
   */
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
  public Long insertEntry(String user, KksEntryCreation creation) {

    KksCollection collection = em.find(KksCollection.class, creation.getCollectionId());
    KksCollectionClass metadata = getCollectionClass(collection.getCollectionClass());

    if (creation.getId() == null) {
      @SuppressWarnings("unchecked")
      List<KksEntry> list = (List<KksEntry>) em
          .createNamedQuery(KksEntry.NAMED_QUERY_GET_ENTRY_BY_CLASS_AND_COLLECTION)
          .setParameter("entryClassId", creation.getEntryClassId())
          .setParameter("collectionId", creation.getCollectionId()).getResultList();

      if (!list.isEmpty()) {
        return handleExistingEntryValue(user, collection, metadata, creation, list.get(0));
      }

      KksEntry e = new KksEntry();
      e.setCustomer(creation.getPic());
      e.setModified(new Date());
      e.setCreator(creation.getCreator());
      e.setKksCollection(collection);
      e.setEntryClassId(creation.getEntryClassId());
      List<KksValue> tmp = new ArrayList<KksValue>();
      KksValue v = creation.getValue();
      v.setEntry(e);
      v.setModified(new Date());
      v.setModifier(user);
      tmp.add(v);
      e.setValues(tmp);
      em.persist(e);
      em.flush();

      log.logValueAddition(collection.getName(), metadata.getTypeCode(), collection.getCustomer(), user, e,
          creation.getValue());
      insertDefaultTags(e);

      return e.getId();

    } else {
      KksEntry k = em.find(KksEntry.class, creation.getId());
      return handleExistingEntryValue(user, collection, metadata, creation, k);
    }
  }

  private void insertDefaultTags(KksEntry e) {

    @SuppressWarnings("unchecked")
    List<Integer> ids = (List<Integer>) em
        .createNativeQuery("SELECT tag_id FROM kks_entry_class_tags WHERE entry_class_id =?")
        .setParameter(1, e.getEntryClassId()).getResultList();

    insertTags(ids, e.getId());
  }

  private Long handleExistingEntryValue(String user, KksCollection collection, KksCollectionClass metadata,
      KksEntryCreation creation, KksEntry e) {
    e.setCustomer(creation.getPic());
    e.setCreator(user);
    e.setModified(new Date());

    if (e.getValues().size() == 0 || creation.getValue().getId() == null) {
      KksValue v = creation.getValue();
      v.setId(null);
      v.setEntry(e);
      v.setModified(new Date());
      v.setModifier(user);
      em.persist(v);

      log.logValueAddition(collection.getName(), metadata.getTypeCode(), collection.getCustomer(), user, e,
          creation.getValue());
      return e.getId();
    } else {
      for (KksValue v : e.getValues()) {
        if (v.getId().equals(creation.getValue().getId())) {

          log.logValueUpdate(collection.getName(), metadata.getTypeCode(), collection.getCustomer(), user, e, v,
              creation.getValue());

          v.setValue(creation.getValue().getValue());
          v.setModified(new Date());
          v.setModifier(user);
        }
      }
    }
    em.merge(e);
    em.flush();
    return e.getId();
  }

  @Override
  public void deleteValue(Long id) {
    List<Long> ids = new ArrayList<Long>();
    ids.add(id);
    em.createNamedQuery(KksValue.NAMED_QUERY_DELETE_VALUES_BY_IDS).setParameter("ids", ids).executeUpdate();
  }

  @Override
  public void updateCollectionStatus(String user, String collection, String status) {
    KksCollection k = getCollection(collection);
    String old = k.getStatus();
    KksCollectionClass metadata = getCollectionClass(k.getCollectionClass());
    k.setStatus(status);
    em.merge(k);
    log.logUpdate(k.getCustomer(), metadata.getTypeCode(), user, k.getName() + " changed status from " + old + " to "
        + status);
  }

  @Override
  public Map<Integer, String> getEntryClassRegistriesForCollectionClass(int classId) {
    List<KksGroup> groups = getCollectionClassGroups(classId);

    Map<Integer, String> groupRegisters = new HashMap<Integer, String>();
    for (KksGroup g : groups) {
      groupRegisters.put(g.getGroupId(), g.getRegister());
    }

    List<KksEntryClass> entryClasses = getEntryClassesForGroups(groupRegisters.keySet());
    Map<Integer, String> entryRegisters = new HashMap<Integer, String>();

    for (KksEntryClass kec : entryClasses) {
      entryRegisters.put(kec.getEntryClassId(), groupRegisters.get(kec.getGroupId()));
    }

    return entryRegisters;
  }

  @Override
  public List<KksEntryClass> getEntryClassesForGroups(Set<Integer> groupIds) {
    Query q = em.createNamedQuery(KksEntryClass.NAMED_QUERY_GET_ENTRY_CLASSES_FOR_GROUPS).setParameter("ids", groupIds);

    @SuppressWarnings("unchecked")
    List<KksEntryClass> tmp = q.getResultList();
    return tmp;
  }

  @Override
  public List<KksCollection> query(String user, KksQueryCriteria criteria) {

    List<String> tagNames = criteria.getTagNames();
    if (tagNames.size() == 0) {
      return new ArrayList<KksCollection>();
    }

    boolean parent = authorization.isParent(user, criteria.getPic());

    List<String> registrys = null;

    if (!parent) {
      registrys = authorization.getAuthorizedRegistryNames(user);
    }
    List<Long> entryIds = searchTaggedEntries(tagNames, registrys);

    if (entryIds.isEmpty()) {
      return new ArrayList<KksCollection>();
    }

    if (parent) {
      return handleParentQuery(user, criteria, entryIds);
    } else {
      return handleAuthorizedQuery(user, criteria, entryIds);
    }
  }

  private List<KksCollection> handleParentQuery(String user, KksQueryCriteria criteria, List<Long> entryIdsList) {
    Query entryQ = em.createNamedQuery(KksEntry.NAMED_QUERY_GET_ENTRIES_BY_IDS);
    entryQ.setParameter("customer", criteria.getPic()).setParameter("ids", entryIdsList);

    @SuppressWarnings("unchecked")
    List<KksEntry> entries = (List<KksEntry>) entryQ.getResultList();

    if (entries.isEmpty()) {
      return new ArrayList<KksCollection>();
    }

    Set<KksCollection> tmpCollections = createCollectionSet(entries);

    List<KksCollection> collections = new ArrayList<KksCollection>(tmpCollections);
    return collections;
  }

  @Override
  public List<String> getCollectionClassRegisters(int classId) {

    Query q = em.createNamedQuery(KksGroup.NAMED_QUERY_GET_ALL_COLLECTION_CLASS_REGISTERS).setParameter("id", classId);

    @SuppressWarnings("unchecked")
    List<String> tmp = q.getResultList();
    return tmp;
  }

  @Override
  public List<KksGroup> getCollectionClassGroups(int classId) {
    Query q = em.createNamedQuery(KksGroup.NAMED_QUERY_GET_ALL_COLLECTION_CLASS_GROUPS).setParameter("id", classId);

    @SuppressWarnings("unchecked")
    List<KksGroup> tmp = q.getResultList();
    return tmp;
  }

  private List<KksCollection> handleAuthorizedQuery(String user, KksQueryCriteria criteria, List<Long> entryIdsList) {
    List<String> registrys = authorization.getAuthorizedRegistryNames(user);

    if (registrys.size() > 0) {
      Query q = em
          .createQuery("SELECT DISTINCT c.id FROM KksCollection c WHERE c.customer =:customer AND c.collectionClass IN (SELECT DISTINCT g.collectionClassId FROM KksGroup g WHERE g.register IN (:registers))");
      q.setParameter("customer", criteria.getPic()).setParameter("registers", registrys);

      @SuppressWarnings("unchecked")
      List<Integer> tmp = q.getResultList();

      if (tmp.size() > 0) {

        Query q2 = em
            .createNativeQuery("SELECT id FROM kks_entry WHERE id IN(:ids) AND entry_class_id IN( SELECT entry_class_id FROM kks_entry_class WHERE entry_group IN (select distinct  group_id from kks_group where register IN (:registers)))");
        q2.setParameter("ids", entryIdsList).setParameter("registers", registrys);

        Query entryQ = em.createNamedQuery(KksEntry.NAMED_QUERY_GET_ENTRIES_BY_IDS_WITH_COLLECTIONS);
        entryQ.setParameter("customer", criteria.getPic()).setParameter("ids", entryIdsList).setParameter("cIds", tmp);

        @SuppressWarnings("unchecked")
        List<KksEntry> entries = (List<KksEntry>) entryQ.getResultList();

        if (entries.isEmpty()) {
          return new ArrayList<KksCollection>();
        }

        Set<KksCollection> tmpCollections = createCollectionSet(entries);

        List<KksCollection> collections = new ArrayList<KksCollection>(tmpCollections);
        return collections;
      }
    }
    return new ArrayList<KksCollection>();
  }

  /**
   * Creates set of collections from given entry list
   * 
   * @param entries
   *          containing collections
   * @return set of collections used in entries
   */
  private Set<KksCollection> createCollectionSet(List<KksEntry> entries) {
    Set<KksCollection> tmpCollections = new HashSet<KksCollection>();
    Map<Long, List<KksEntry>> entryMap = new HashMap<Long, List<KksEntry>>();

    for (KksEntry e : entries) {
      tmpCollections.add( e.getKksCollection() );

      if (entryMap.containsKey(e.getKksCollection().getId())) {
        List<KksEntry> tmpList = entryMap.get(e.getKksCollection().getId());
        tmpList.add(e);
      } else {
        List<KksEntry> tmpList = new ArrayList<KksEntry>();
        tmpList.add(e);
        entryMap.put(e.getKksCollection().getId(), tmpList);
      }
    }

    Set<KksCollection> collections = new HashSet<KksCollection>();
    for (KksCollection c : tmpCollections) {
      
      KksCollection kc = new KksCollection(c);
      List<KksEntry> entryList = new ArrayList<KksEntry>();
      
      for ( KksEntry e : entryMap.get(c.getId()) ) {
        entryList.add(new KksEntry(e, kc));
      }
      
      kc.setEntries(entryList);   
      collections.add(kc);
    }
    return collections;
  }

  private List<Long> searchTaggedEntries(List<String> tagNames, List<String> registers) {
    StringBuilder tmp = new StringBuilder();

    for (int i = 0; i < tagNames.size(); i++) {
      String t = tagNames.get(i);
      tmp.append(" name LIKE '%");
      tmp.append(t);
      tmp.append("%' ");
      if ((i + 1) < tagNames.size()) {
        tmp.append("OR");
      }
    }

    StringBuilder qs = new StringBuilder();

    qs.append("SELECT DISTINCT entry_id FROM kks_entry_tags WHERE tag_id IN (SELECT tag_id FROM kks_tag WHERE");
    qs.append(tmp.toString());
    qs.append(")");

    Query q = null;
    if (registers != null && registers.size() > 0) {
      qs.append(" AND entry_id IN(SELECT id FROM kks_entry WHERE entry_class_id IN( SELECT entry_class_id FROM kks_entry_class WHERE entry_group IN (select distinct  group_id from kks_group where register IN (:registers))))");

      q = em.createNativeQuery(qs.toString()).setParameter("registers", registers);
    } else {
      q = em.createNativeQuery(qs.toString());
    }

    @SuppressWarnings("unchecked")
    List<BigInteger> entryIds = q.getResultList();

    if (entryIds.size() == 0) {
      return new ArrayList<Long>();
    }

    List<Long> list = new ArrayList<Long>();

    for (BigInteger bi : entryIds) {
      list.add(bi.longValue());
    }

    return list;
  }

  @Override
  public void update(String user, KksCollection collection, Log log, LogEntriesType logEntries) {

    KksCollection tmp = new KksCollection( em.find(KksCollection.class, collection.getId() ));

    syncFields(user, collection, tmp, log, logEntries);

    tmp.setNextVersion(collection.getNextVersion());
    tmp.setPrevVersion(collection.getPrevVersion());
    tmp.setStatus(collection.getStatus());
    tmp.setName(collection.getName());
    tmp.setModifier(collection.getModifier());
    tmp.setModified(new Date());
    tmp.setDescription(collection.getDescription());
    tmp.setVersion(collection.getVersion());
    em.merge(tmp);
    setTags(collection, tmp);
  }

  private void setTagsByEntryClass(KksCollection collection, KksCollection newCollection) {

    List<String> tagIds = new ArrayList<String>();

    Map<Integer, List<Integer>> entryTagMap = new HashMap<Integer, List<Integer>>();

    for (KksEntry e : collection.getEntries()) {

      List<Integer> tmp = new ArrayList<Integer>();
      for (KksTag t : e.getTags()) {
        tmp.add(t.getTagId());
      }

      entryTagMap.put(e.getEntryClassId(), tmp);
    }

    List<KksTag> tags = getTags(tagIds);
    Map<Integer, KksTag> tagMap = new HashMap<Integer, KksTag>();

    for (KksTag t : tags) {
      tagMap.put(t.getTagId(), t);
    }

    for (KksEntry e : newCollection.getEntries()) {
      insertTags(entryTagMap.get(e.getEntryClassId()), e.getId());
    }
  }

  private void setTags(KksCollection collection, KksCollection newCollection) {

    List<String> tagIds = new ArrayList<String>();
    Map<Long, List<Integer>> entryTagMap = new HashMap<Long, List<Integer>>();
    Map<Integer, List<Integer>> entryClassTagMap = new HashMap<Integer, List<Integer>>();

    for (KksEntry e : collection.getEntries()) {

      List<Integer> tmp = new ArrayList<Integer>();
      for (Integer s : e.getTagIds()) {
        tagIds.add(s.toString());
        tmp.add(s);
      }
      entryTagMap.put(e.getId(), e.getTagIds());
      entryClassTagMap.put(e.getEntryClassId(), tmp);
    }

    List<KksTag> tags = getTags(tagIds);
    Map<Integer, KksTag> tagMap = new HashMap<Integer, KksTag>();

    for (KksTag t : tags) {
      tagMap.put(t.getTagId(), t);
    }

    for (KksEntry e : collection.getEntries()) {
      // handle tag deletion & insertion manually
      removeTags(e.getId());

      List<Integer> values = entryTagMap.get(e.getId());
      if (values == null || values.isEmpty()) {
        insertTags(entryClassTagMap.get(e.getEntryClassId()), e.getId());
      } else {
        insertTags(entryTagMap.get(e.getId()), e.getId());
      }
    }
  }

  private void syncFields(String user, KksCollection newCollection, KksCollection oldCollection, Log log,
      LogEntriesType logEntries) {
    Map<String, Registry> registers = authorization.getAuthorizedRegistries(user);
    boolean master = authorization.isParent(user, oldCollection.getCustomer())
        || authorization.hasConsent(oldCollection.getCustomer(), user,
            getCollectionClass(oldCollection.getCollectionClass()).getConsentType());

    List<KksGroup> groups = getCollectionClassGroups(newCollection.getCollectionClass());

    Set<Integer> ids = new HashSet<Integer>();
    for (KksGroup group : groups) {
      ids.add(group.getGroupId());
    }
    List<KksEntryClass> entryClasses = getEntryClassesForGroups(ids);

    CollectionUpdateHelper helper = new CollectionUpdateHelper(user, registers, master, newCollection, oldCollection,
        getCollectionClass(newCollection.getCollectionClass()), groups, entryClasses);
    helper.combineEntries(log, logEntries);
  }

  public void insertTags(List<Integer> tagIds, Long entryId) {

    if (tagIds == null || tagIds.isEmpty()) {
      return;
    }

    StringBuilder qs = new StringBuilder();
    qs.append("INSERT INTO kks_entry_tags (entry_id,tag_id) VALUES ( ?,? ) ");

    for (Integer i : tagIds) {
      em.createNativeQuery(qs.toString()).setParameter(1, entryId).setParameter(2, i).executeUpdate();
    }
  }

  public void removeTags(Long entryId) {
    StringBuilder qs = new StringBuilder();
    qs.append("DELETE FROM kks_entry_tags WHERE entry_id = ?");

    em.createNativeQuery(qs.toString()).setParameter(1, entryId).executeUpdate();
  }

  public void removeTags(List<Long> entries) {
    StringBuilder qs = new StringBuilder();
    qs.append("DELETE FROM kks_entry_tags WHERE entry_id IN (?)");

    StringBuilder tmp = new StringBuilder();

    for (int i = 0; i < entries.size(); i++) {
      tmp.append("" + entries.get(i));

      if ((i + 1) < entries.size()) {
        tmp.append(", ");
      }
    }

    em.createNativeQuery(qs.toString()).setParameter(1, tmp.toString()).executeUpdate();
    em.flush();
  }

  @Override
  public Long copyAndInsert(String user, KksCollectionCreation creation) {
    KksCollection old = getCollection(creation.getCollectionId());
    KksCollectionClass metadata = getCollectionClass(old.getCollectionClass());

    KksCollection newVersion = new KksCollection();
    newVersion.setName(creation.getName());
    newVersion.setCreator(creation.getCreator());
    newVersion.setCreated(new Date());
    newVersion.setModified(new Date());
    newVersion.setModifier(creation.getCreator());
    newVersion.setCustomer(creation.getCustomer());
    newVersion.setPrevVersion("" + old.getId());
    newVersion.setStatus(ACTIVE);
    newVersion.setCollectionClass(old.getCollectionClass());

    if (!creation.isEmpty()) {
      handleVersioning(creation, old, newVersion);
    } else {
      handleEmptyVersioning(user, creation, old, newVersion);
    }

    em.persist(newVersion);

    log.logCreate(old.getCustomer(), metadata.getTypeCode(), user, "Created version " + newVersion.getName() + " from "
        + old.getName());

    setTagsByEntryClass(old, newVersion);
    old.setNextVersion("" + newVersion.getId());
    String oldStatus = old.getStatus();
    old.setStatus(LOCKED);
    em.merge(old);

    log.logUpdate(old.getCustomer(), metadata.getTypeCode(), user, "Changed " + old.getName() + " status from "
        + oldStatus + " to " + LOCKED);

    return newVersion.getId();
  }

  private void handleVersioning(KksCollectionCreation creation, KksCollection old, KksCollection newVersion) {
    for (KksEntry e : old.getEntries()) {

      if (isCopyable(e.getTags())) {
        KksEntry newE = new KksEntry();
        newE.setCreator(e.getCreator());
        newE.setCustomer(creation.getCustomer());
        newE.setEntryClassId(e.getEntryClassId());
        newE.setModified(e.getModified());
        newE.setKksCollection(newVersion);
        newVersion.addKksEntry(newE);

        for (KksValue value : e.getValues()) {
          KksValue newVal = new KksValue();
          newVal.setEntry(newE);
          newVal.setModified(value.getModified());
          newVal.setModifier(value.getModifier());
          newVal.setValue(value.getValue());
          newVal.setEntry(newE);
          newE.addKksValue(newVal);
        }
      }
    }
  }

  private void handleEmptyVersioning(String user, KksCollectionCreation creation, KksCollection old,
      KksCollection newVersion) {
    for (KksEntry e : old.getEntries()) {

      if (isCopyable(e.getTags())) {
        KksEntry newE = new KksEntry();
        newE.setCreator(user);
        newE.setCustomer(creation.getCustomer());
        newE.setEntryClassId(e.getEntryClassId());
        newE.setModified(new Date());
        newE.setKksCollection(newVersion);
        newVersion.addKksEntry(newE);

        for (KksValue value : e.getValues()) {
          KksValue newVal = new KksValue();
          newVal.setEntry(newE);
          newVal.setModified(new Date());
          newVal.setModifier(user);
          newVal.setValue("");
          newVal.setEntry(newE);
          newE.addKksValue(newVal);
        }
      }
    }
  }

  private boolean isCopyable(List<KksTag> tags) {
    if (tags != null && !tags.isEmpty()) {
      for (KksTag t : tags) {
        if (t.getName().equalsIgnoreCase(COMMENT_FIELD_TAG)) {
          return false;
        }
      }
    }
    return true;
  }

}
