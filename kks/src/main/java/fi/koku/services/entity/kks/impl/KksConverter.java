package fi.koku.services.entity.kks.impl;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import fi.koku.services.entity.kks.v1.EntryValuesType;
import fi.koku.services.entity.kks.v1.KksCollectionClassType;
import fi.koku.services.entity.kks.v1.KksCollectionType;
import fi.koku.services.entity.kks.v1.KksEntriesType;
import fi.koku.services.entity.kks.v1.KksEntryClassType;
import fi.koku.services.entity.kks.v1.KksEntryClassesType;
import fi.koku.services.entity.kks.v1.KksEntryType;
import fi.koku.services.entity.kks.v1.KksGroupType;
import fi.koku.services.entity.kks.v1.KksGroupsType;
import fi.koku.services.entity.kks.v1.KksTagIdsType;
import fi.koku.services.entity.kks.v1.KksTagType;
import fi.koku.services.entity.kks.v1.ValueSpacesType;

/**
 * Converts classes from and to WS types
 * 
 * @author Ixonos / tuomape
 * 
 */
public class KksConverter {

  private KksConverter() {

  }

  public static KksTagType toWsType(KksTag tag) {
    KksTagType tmp = new KksTagType();
    tmp.setId("" + tag.getId());
    tmp.setName(tag.getName());
    tmp.setDescription(tag.getDescription());
    return tmp;
  }

  public static KksTag fromWsType(KksTagType tag) {
    KksTag tmp = new KksTag();
    tmp.setId(Integer.parseInt(tag.getId()));
    tmp.setName(tag.getName());
    tmp.setDescription(tag.getDescription());
    return tmp;
  }

  public static KksCollectionClassType toWsType(KksCollectionClass collectionClass) {

    KksCollectionClassType tmp = new KksCollectionClassType();
    tmp.setId("" + collectionClass.getId());
    tmp.setDescription(collectionClass.getDescription());
    tmp.setName(collectionClass.getName());

    KksGroupsType kksGroupsType = new KksGroupsType();
    for (KksGroup g : collectionClass.getGroups()) {
      kksGroupsType.getKksGroup().add(toWsType(g));
    }
    return tmp;

  }

  public static KksEntryClassType toWsType(KksEntryClass entryClass) {
    KksEntryClassType kksEntryClassType = new KksEntryClassType();
    kksEntryClassType.setId("" + entryClass.getId());
    kksEntryClassType.setDescription(entryClass.getDescription());
    kksEntryClassType.setDataType(entryClass.getDataType());
    kksEntryClassType.setGroupId("" + entryClass.getGroup().getId());
    kksEntryClassType.setMultiValue(entryClass.isMultiValue());
    kksEntryClassType.setName(entryClass.getName());
    kksEntryClassType.setSortOrder(new BigInteger("" + entryClass.getSortOrder()));
    KksTagIdsType kksTagIdsType = new KksTagIdsType();

    for (KksTag tag : entryClass.getTags()) {
      kksTagIdsType.getKksTagId().add("" + tag.getId());
    }

    kksEntryClassType.setKksTagIds(kksTagIdsType);

    ValueSpacesType valueSpacesType = new ValueSpacesType();

    if (entryClass.isMultiValue()) {
      String tmp[] = entryClass.getValueSpaces().split(",");
      for (String s : tmp) {
        valueSpacesType.getValueSpace().add(s);
      }
    } else {
      valueSpacesType.getValueSpace().add("");
    }

    kksEntryClassType.setValueSpaces(valueSpacesType);
    return kksEntryClassType;
  }

  public static KksGroupType toWsType(KksGroup group) {
    KksGroupType kksGroupType = new KksGroupType();
    kksGroupType.setId("" + group.getId());
    kksGroupType.setDescription(group.getDescription());
    kksGroupType.setName(group.getName());
    kksGroupType.setOrder(new BigInteger("" + group.getSortOrder()));
    kksGroupType.setRegister(group.getRegister());

    KksEntryClassesType kksEntryClassesType = new KksEntryClassesType();

    for (KksEntryClass e : group.getEntryClasses()) {
      kksEntryClassesType.getKksEntryClass().add(toWsType(e));
    }

    kksGroupType.setKksEntryClasses(kksEntryClassesType);

    return kksGroupType;
  }

  public static KksCollectionType toWsType(KksCollection collection) {
    KksCollectionType kksCollectionType = new KksCollectionType();
    kksCollectionType.setCollectionClassId("" + collection.getCollectionClass().getId());
    Date created = collection.getCreated();
    Calendar c = new GregorianCalendar();
    c.setTime(created);
    kksCollectionType.setCreated(c);
    kksCollectionType.setCustomerId(collection.getCustomer());
    kksCollectionType.setDescription(collection.getDescription());
    kksCollectionType.setId("" + collection.getId());
    kksCollectionType.setName(collection.getName());
    kksCollectionType.setNewVersion(false);
    kksCollectionType.setNextVersion(collection.getNextVersion());
    kksCollectionType.setPrevVersion(collection.getPrevVersion());
    kksCollectionType.setStatus(collection.getStatus());
    kksCollectionType.setVersion(new BigInteger(collection.getVersion()));
    kksCollectionType.setVersioned(collection.getNextVersion() != null);

    KksEntriesType kksEntriesType = new KksEntriesType();

    for (KksEntry e : collection.getEntries()) {
      kksEntriesType.getEntries().add(toWsType(e));
    }

    kksCollectionType.setKksEntries(kksEntriesType);
    return kksCollectionType;
  }

  public static KksEntryType toWsType(KksEntry entry) {
    KksEntryType kksEntryType = new KksEntryType();
    kksEntryType.setEntryClassId("" + entry.getEntryClassId());
    kksEntryType.setCreator(entry.getCreator());
    kksEntryType.setId("" + entry.getId());

    Calendar c = new GregorianCalendar();
    c.setTime(entry.getModified());
    kksEntryType.setModified(c);
    kksEntryType.setVersion(new BigInteger(entry.getVersion()));

    KksTagIdsType kksTagIdsType = new KksTagIdsType();

    for (Integer tagId : entry.getTagIds()) {
      kksTagIdsType.getKksTagId().add("" + tagId);
    }

    kksEntryType.setKksTagIds(kksTagIdsType);

    EntryValuesType entryValuesType = new EntryValuesType();

    for (KksValue v : entry.getValues()) {
      entryValuesType.getEntryValue().add(v.getValue());
    }

    kksEntryType.setEntryValues(entryValuesType);
    return kksEntryType;

  }

  public static KksCollection fromWsType(KksCollectionType collection) {
    KksCollection kksCollection = new KksCollection();

    kksCollection.setCustomer(collection.getCustomerId());
    kksCollection.setCreated(collection.getCreated().getTime());

    kksCollection.setDescription(collection.getDescription());
    kksCollection.setId(Integer.parseInt(collection.getId()));
    kksCollection.setName(collection.getName());
    kksCollection.setNextVersion(collection.getNextVersion());
    kksCollection.setPrevVersion(collection.getPrevVersion());
    kksCollection.setStatus(collection.getStatus());
    kksCollection.setVersion(collection.getVersion().toString());

    List<KksEntry> tmp = new ArrayList<KksEntry>();
    for (KksEntryType et : collection.getKksEntries().getEntries()) {
      tmp.add(KksConverter.fromWsType(et));
    }

    return kksCollection;
  }

  public static KksEntry fromWsType(KksEntryType entryType) {
    KksEntry entry = new KksEntry();
    entry.setCreator(entryType.getCreator());

    entry.setCustomer(entryType.getCustomerId());
    if (entryType.getId() != null) {
      entry.setId(Integer.parseInt(entryType.getId()));
    }

    entry.setEntryClassId(Integer.parseInt(entryType.getEntryClassId()));
    entry.setId(Integer.parseInt(entryType.getId()));
    entry.setModified(entryType.getModified().getTime());
    entry.setVersion(entryType.getVersion().toString());

    List<KksValue> tmp = new ArrayList<KksValue>();

    for (String value : entryType.getEntryValues().getEntryValue()) {
      KksValue v = new KksValue();
      v.setValue(value);
      tmp.add(v);
    }

    List<Integer> tagIds = new ArrayList<Integer>();
    for (String tagId : entryType.getKksTagIds().getKksTagId()) {
      tagIds.add(Integer.parseInt(tagId));
    }
    entry.setTagIds(tagIds);
    entry.setValues(tmp);
    return entry;
  }
}
