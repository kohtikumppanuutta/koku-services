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
import java.util.Date;
import java.util.List;

import org.springframework.util.StringUtils;

import fi.koku.calendar.CalendarUtil;
import fi.koku.services.entity.kks.v1.EntryValuesType;
import fi.koku.services.entity.kks.v1.KksCollectionClassType;
import fi.koku.services.entity.kks.v1.KksCollectionType;
import fi.koku.services.entity.kks.v1.KksEntriesType;
import fi.koku.services.entity.kks.v1.KksEntryClassType;
import fi.koku.services.entity.kks.v1.KksEntryClassesType;
import fi.koku.services.entity.kks.v1.KksEntryType;
import fi.koku.services.entity.kks.v1.KksEntryValueType;
import fi.koku.services.entity.kks.v1.KksGroupType;
import fi.koku.services.entity.kks.v1.KksGroupsType;
import fi.koku.services.entity.kks.v1.KksTagIdsType;
import fi.koku.services.entity.kks.v1.KksTagType;
import fi.koku.services.entity.kks.v1.KksTagsType;
import fi.koku.services.entity.kks.v1.ValueSpacesType;

/**
 * Converts classes from and to WS types
 * 
 * @author Ixonos / tuomape
 * 
 */
public final class KksConverter {

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
    tmp.setConsentType(collectionClass.getConsentType());
    tmp.setTypeCode(collectionClass.getTypeCode());

    KksGroupsType kksGroupsType = new KksGroupsType();
    for (KksGroup g : collectionClass.getGroups()) {
      kksGroupsType.getKksGroup().add(toWsType(g));
    }
    tmp.setKksGroups(kksGroupsType);
    return tmp;

  }

  public static KksEntryClassType toWsType(KksEntryClass entryClass) {
    KksEntryClassType kksEntryClassType = new KksEntryClassType();
    kksEntryClassType.setId("" + entryClass.getEntryClassId());
    kksEntryClassType.setDescription(entryClass.getDescription());
    kksEntryClassType.setDataType(entryClass.getDataType());
    kksEntryClassType.setGroupId("" + entryClass.getGroupId());
    kksEntryClassType.setMultiValue(entryClass.isMultiValue());
    kksEntryClassType.setName(entryClass.getName());
    kksEntryClassType.setSortOrder(new BigInteger("" + entryClass.getSortOrder()));
    KksTagsType kksTagsType = new KksTagsType();

    if (entryClass.getTags() != null) {
      for (KksTag tag : entryClass.getTags()) {
        KksTagType t = new KksTagType();
        t.setId("" + tag.getTagId());
        t.setName(tag.getName());
        t.setDescription(tag.getDescription());
        kksTagsType.getKksTag().add(t);
      }
    }

    kksEntryClassType.setKksTags(kksTagsType);

    ValueSpacesType valueSpacesType = new ValueSpacesType();

    if (entryClass.getValueSpaces() != null) {
      String tmp[] = entryClass.getValueSpaces().split(",");
      for (String s : tmp) {
        valueSpacesType.getValueSpace().add(s.trim());
      }
    } else {
      valueSpacesType.getValueSpace().add("");
    }

    kksEntryClassType.setValueSpaces(valueSpacesType);
    return kksEntryClassType;
  }

  public static KksGroupType toWsType(KksGroup group) {
    KksGroupType kksGroupType = new KksGroupType();
    kksGroupType.setId("" + group.getGroupId());
    kksGroupType.setDescription(group.getDescription());
    kksGroupType.setName(group.getName());
    kksGroupType.setOrder(new BigInteger("" + group.getSortOrder()));
    kksGroupType.setRegister(group.getRegister());
    kksGroupType.setAccountable(group.getAccountable());

    KksEntryClassesType kksEntryClassesType = new KksEntryClassesType();

    for (KksEntryClass e : group.getEntryClasses()) {
      kksEntryClassesType.getKksEntryClass().add(toWsType(e));
    }

    kksGroupType.setKksEntryClasses(kksEntryClassesType);

    if (group.getSubGroups() != null) {
      KksGroupsType subGroups = new KksGroupsType();

      for (KksGroup sb : group.getSubGroups()) {
        subGroups.getKksGroup().add(toWsType(sb));
      }

      kksGroupType.setSubGroups(subGroups);
    }

    return kksGroupType;
  }

  public static KksCollectionType toWsType(KksCollection collection) {

    if (collection == null) {
      return new KksCollectionType();
    }

    KksCollectionType kksCollectionType = new KksCollectionType();
    kksCollectionType.setCollectionClassId("" + collection.getCollectionClass());

    kksCollectionType.setCreated(CalendarUtil.getXmlDateTime(collection.getCreated()));
    kksCollectionType.setCreator(collection.getCreator());
    kksCollectionType.setCustomerId(collection.getCustomer());
    kksCollectionType.setDescription(collection.getDescription());
    kksCollectionType.setId("" + collection.getId());
    kksCollectionType.setName(collection.getName());
    kksCollectionType.setNewVersion(false);
    kksCollectionType.setNextVersion(collection.getNextVersion());
    kksCollectionType.setPrevVersion(collection.getPrevVersion());
    kksCollectionType.setStatus(collection.getStatus());
    kksCollectionType.setVersion(new BigInteger("" + collection.getVersion()));
    kksCollectionType.setVersioned(collection.getNextVersion() != null);

    if (collection.getModified() != null) {
      kksCollectionType.setModified(CalendarUtil.getXmlDateTime(collection.getModified()));
    }
    kksCollectionType.setModifier(collection.getModifier());
    kksCollectionType.setConsentRequested(collection.isConsentRequested());
    kksCollectionType.setUserConsentStatus(collection.getUserConsentStatus());

    KksEntriesType kksEntriesType = new KksEntriesType();

    if (collection.getEntries() != null) {
      for (KksEntry e : collection.getEntries()) {
        kksEntriesType.getEntries().add(toWsType(e));
      }
    }

    kksCollectionType.setKksEntries(kksEntriesType);
    return kksCollectionType;
  }

  public static KksEntryType toWsType(KksEntry entry) {
    KksEntryType kksEntryType = new KksEntryType();
    kksEntryType.setEntryClassId("" + entry.getEntryClassId());
    kksEntryType.setCreator(entry.getCreator());
    kksEntryType.setId("" + entry.getId());

    kksEntryType.setModified(CalendarUtil.getXmlDateTime(entry.getModified()));
    kksEntryType.setVersion(new BigInteger("" + entry.getVersion()));

    KksTagIdsType kksTagIdsType = new KksTagIdsType();

    for (KksTag tagId : entry.getTags()) {
      kksTagIdsType.getKksTagId().add("" + tagId.getTagId());
    }

    kksEntryType.setKksTagIds(kksTagIdsType);

    EntryValuesType entryValuesType = new EntryValuesType();

    for (KksValue v : entry.getValues()) {
      KksEntryValueType kksEv = new KksEntryValueType();
      kksEv.setId("" + v.getId());
      kksEv.setValue(v.getValue());

      if (v.getModified() != null) {
        kksEv.setModified(CalendarUtil.getXmlDateTime(v.getModified()));
      }
      kksEv.setModifier(v.getModifier());
      entryValuesType.getEntryValue().add(kksEv);
    }

    kksEntryType.setEntryValues(entryValuesType);
    return kksEntryType;

  }

  public static KksCollection fromWsType(KksCollectionType collection) {
    KksCollection kksCollection = new KksCollection();

    kksCollection.setId(Long.valueOf(collection.getId()));
    kksCollection.setCustomer(collection.getCustomerId());
    kksCollection.setCreated(CalendarUtil.getDate(collection.getCreated()));
    kksCollection.setCreator(collection.getCreator());
    kksCollection.setDescription(collection.getDescription());
    kksCollection.setId(Long.parseLong(collection.getId()));
    kksCollection.setName(collection.getName());
    kksCollection.setNextVersion(collection.getNextVersion());
    kksCollection.setPrevVersion(collection.getPrevVersion());
    kksCollection.setStatus(collection.getStatus());
    kksCollection.setCollectionClass(Integer.valueOf(collection.getCollectionClassId()));
    kksCollection.setVersion(collection.getVersion().intValue());
    kksCollection.setModified(collection.getModified() == null ? new Date() : CalendarUtil.getDate(collection.getModified()));
    kksCollection.setModifier(collection.getModifier());

    List<KksEntry> tmp = new ArrayList<KksEntry>();
    for (KksEntryType et : collection.getKksEntries().getEntries()) {
      tmp.add(KksConverter.fromWsType(et, kksCollection));
    }

    kksCollection.setEntries(tmp);

    return kksCollection;
  }

  public static KksEntry fromWsType(KksEntryType entryType, KksCollection collection ) {
    KksEntry entry = new KksEntry();
    entry.setCreator(entryType.getCreator());

    entry.setCustomer(entryType.getCustomerId());
    
    entry.setKksCollection(collection);

    entry.setEntryClassId(Integer.valueOf(entryType.getEntryClassId()));
    entry.setId(parseNullableLong(entryType.getId()));
    entry.setModified(entryType.getModified() == null ? new Date() : CalendarUtil.getDate(entryType.getModified()));
    
    entry.setVersion(entryType.getVersion().intValue());

    List<KksValue> tmp = new ArrayList<KksValue>();

    for (KksEntryValueType value : entryType.getEntryValues().getEntryValue()) {
      KksValue v = new KksValue();
      v.setId(parseNullableLong(value.getId()));
      v.setValue(value.getValue());
      v.setModified(value.getModified() == null ? new Date() : CalendarUtil.getDate(value.getModified()));
      v.setModifier(value.getModifier());
      v.setEntry(entry);
      tmp.add(v);
    }

    List<Integer> tagIds = new ArrayList<Integer>();

    if (entryType.getKksTagIds() != null) {
      for (String tagId : entryType.getKksTagIds().getKksTagId()) {
        tagIds.add(Integer.parseInt(tagId));
      }
      entry.setTagIds(tagIds);
    }
    entry.setValues(tmp);
    return entry;
  }

  public static Integer parseNullableInt(String strInt) {
    if (strInt == null) {
      return null;
    } else if (strInt.equals("")) {
      return null;
    }
    return new Integer(strInt);
  }

  public static Long parseNullableLong(String strInt) {
    try {
      if (strInt == null) {
        return null;
      } else if (strInt.equals("") || strInt.equals("null")) {
        return null;
      }
      return new Long(strInt);
    } catch (Exception e) {
      return null;
    }
  }
}
