package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.koku.services.entity.authorizationinfo.v1.model.Registry;

/**
 * Helper class for collection updating
 * 
 * @author Ixonos / tuomape
 * 
 */
public class CollectionUpdateHelper {
  private Map<String, Registry> registers;
  private Map<Integer, String> groupRegisters;
  private Map<Integer, Integer> entryGroupMap;
  private boolean master;

  private Map<Long, KksEntry> newEntries;
  private Map<Long, KksEntry> oldEntries;
  private Map<Long, KksValue> newValues;
  private Map<Long, KksValue> oldValues;
  private Map<Integer, KksTag> newTags;
  private Map<Integer, KksTag> oldTags;
  private List<Long> removableEntries;
  private List<Long> removableValues;

  private KksCollection newCollection;
  private KksCollection oldCollection;
  private KksCollectionClass metadata;
  private Map<Integer, KksEntryClass> entryMetadata;
  private String user;

  public CollectionUpdateHelper(String user, Map<String, Registry> registers, boolean master, KksCollection newCol,
      KksCollection oldCol, KksCollectionClass metadata, List<KksGroup> groups, List<KksEntryClass> entryClasses) {
    this.user = user;
    this.registers = registers;
    this.master = master;
    this.groupRegisters = new HashMap<Integer, String>();
    this.entryGroupMap = new HashMap<Integer, Integer>();
    this.newEntries = new HashMap<Long, KksEntry>();
    this.oldEntries = new HashMap<Long, KksEntry>();
    this.newValues = new HashMap<Long, KksValue>();
    this.oldValues = new HashMap<Long, KksValue>();
    this.newTags = new HashMap<Integer, KksTag>();
    this.oldTags = new HashMap<Integer, KksTag>();
    this.entryMetadata = new HashMap<Integer, KksEntryClass>();
    this.removableEntries = new ArrayList<Long>();
    this.removableValues = new ArrayList<Long>();
    this.newCollection = newCol;
    this.oldCollection = oldCol;
    this.metadata = metadata;
    initMaps(entryClasses);
    createRegistryMap(groups, entryClasses);
    combineEntries();
    generateRemovableIds();
  }

  private void initMaps(List<KksEntryClass> entryClasses) {

    for (KksEntryClass kec : entryClasses) {
      entryMetadata.put(kec.getEntryClassId(), kec);
    }

    createCollectionMaps(newCollection, newEntries, newValues, newTags);
    createCollectionMaps(oldCollection, oldEntries, oldValues, oldTags);
  }

  private void createRegistryMap(List<KksGroup> groups, List<KksEntryClass> entryClasses) {
    for (KksGroup group : groups) {
      groupRegisters.put(group.getGroupId(), group.getRegister());
    }

    for (KksEntryClass entryClass : entryClasses) {
      entryGroupMap.put(entryClass.getEntryClassId(), entryClass.getGroupId());
    }
  }

  private void createCollectionMaps(KksCollection collection, Map<Long, KksEntry> entries, Map<Long, KksValue> values,
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

  private void combineEntries() {
    for (KksEntry e : newCollection.getEntries()) {
      if (!entryMetadata.get(e.getEntryClassId()).isMultiValue() && hasRightForEntry(e.getEntryClassId())) {
        KksEntry old = getOldEntries().get(e.getId());
        if (old == null) {
          // add new entry to old
          e.setKksCollection(oldCollection);
          e.setModified(new Date());

          if (e.getValues() != null) {
            for (KksValue v : e.getValues()) {
              v.setModified(new Date());
              v.setModifier(e.getCreator());
            }
          }
          Log.logCreate(oldCollection.getCustomer(), metadata.getTypeCode(), user, oldCollection.getName()
              + " added entry class " + e.toString());
          oldCollection.addKksEntry(e);
        } else {
          // modify old
          old.setCreator(e.getCreator());
          old.setCustomer(e.getCustomer());
          old.setModified(new Date());
          old.setVersion(e.getVersion());
          combineValues(e, old);
          combineTags(e, old);
        }
      }
    }
  }

  private void combineTags(KksEntry e, KksEntry old) {
    if (e.getTags() != null) {
      for (KksTag t : e.getTags()) {
        KksTag oldT = getOldTags().get(t.getId());

        if (oldT == null) {
          old.addKksTag(t);
        }
      }
    }
  }

  private void combineValues(KksEntry e, KksEntry old) {
    if (e.getValues() != null) {
      for (KksValue v : e.getValues()) {
        KksValue oldValue = getOldValues().get(v.getId());

        if (oldValue == null) {
          v.setEntry(old);
          old.addKksValue(v);
          Log.logValueAddition(oldCollection.getName(), metadata.getTypeCode(), oldCollection.getCustomer(), user, old,
              v);
        } else {
          Log.logValueUpdate(oldCollection.getName(), metadata.getTypeCode(), oldCollection.getCustomer(), user, old,
              oldValue, v);
          oldValue.setValue(v.getValue());
          oldValue.setModified(new Date());

          if (v.getModifier() == null) {
            System.out.println("TULEE");
          }
          oldValue.setModifier(v.getModifier());
        }
      }
    }
  }

  private void generateRemovableIds() {
    List<KksEntry> entries = new ArrayList<KksEntry>(oldCollection.getEntries());
    if (entries != null) {
      for (KksEntry e : entries) {
        if (hasRightForEntry(e.getEntryClassId())) {
          handlePossibleRemoval(e);
        }
      }
    }
  }

  private void handlePossibleRemoval(KksEntry e) {
    if (e.getValues() != null) {
      List<KksValue> vList = new ArrayList<KksValue>(e.getValues());
      for (KksValue v : vList) {
        if (v.getId() != null && getNewValues().get(v.getId()) == null) {
          e.removeKksValue(v);
          removableValues.add(v.getId());
        }
      }
    }

    if (e.getTags() != null) {
      List<KksTag> tList = new ArrayList<KksTag>(e.getTags());
      for (KksTag t : tList) {
        if (getNewTags().get(t.getId()) == null) {
          e.removeKksTag(t);
        }
      }
    }
  }

  private boolean hasRightForEntry(Integer entryClassId) {
    if (master) {
      return true;
    }
    String groupRegister = groupRegisters.get(entryGroupMap.get(entryClassId));
    return registers.containsKey(groupRegister);
  }

  public Map<String, Registry> getRegisters() {
    return registers;
  }

  public boolean isMaster() {
    return master;
  }

  public Map<Long, KksEntry> getNewEntries() {
    return newEntries;
  }

  public Map<Long, KksEntry> getOldEntries() {
    return oldEntries;
  }

  public Map<Long, KksValue> getNewValues() {
    return newValues;
  }

  public Map<Long, KksValue> getOldValues() {
    return oldValues;
  }

  public Map<Integer, KksTag> getNewTags() {
    return newTags;
  }

  public Map<Integer, KksTag> getOldTags() {
    return oldTags;
  }

  public List<Long> getRemovableEntries() {
    return removableEntries;
  }

  public List<Long> getRemovableValues() {
    return removableValues;
  }

}
