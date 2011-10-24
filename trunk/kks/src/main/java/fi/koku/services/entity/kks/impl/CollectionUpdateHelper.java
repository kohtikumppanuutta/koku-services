package fi.koku.services.entity.kks.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.koku.services.utility.authorizationinfo.v1.model.Registry;
import fi.koku.services.utility.log.v1.LogEntriesType;

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
  private Map<Long, KksEntry> oldEntries;
  private Map<Long, KksValue> oldValues;
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
    this.oldEntries = new HashMap<Long, KksEntry>();
    this.oldValues = new HashMap<Long, KksValue>();
    this.entryMetadata = new HashMap<Integer, KksEntryClass>();
    this.newCollection = newCol;
    this.oldCollection = oldCol;
    this.metadata = metadata;
    initMaps(entryClasses);
    createRegistryMap(groups, entryClasses);
  }

  private void initMaps(List<KksEntryClass> entryClasses) {

    for (KksEntryClass kec : entryClasses) {
      entryMetadata.put(kec.getEntryClassId(), kec);
    }

    createCollectionMaps(oldCollection, oldEntries, oldValues);
  }

  private void createRegistryMap(List<KksGroup> groups, List<KksEntryClass> entryClasses) {
    for (KksGroup group : groups) {
      groupRegisters.put(group.getGroupId(), group.getRegister());
    }

    for (KksEntryClass entryClass : entryClasses) {
      entryGroupMap.put(entryClass.getEntryClassId(), entryClass.getGroupId());
    }
  }

  private void createCollectionMaps(KksCollection collection, Map<Long, KksEntry> entries, Map<Long, KksValue> values) {

    if (collection.getEntries() != null) {
      for (KksEntry entry : collection.getEntries()) {
        entries.put(entry.getId(), entry);

        if (entry.getValues() != null) {
          for (KksValue value : entry.getValues()) {
            values.put(value.getId(), value);
          }
        }

      }
    }
  }

  public void combineEntries(Log log, LogEntriesType logEntries) {
    for (KksEntry e : newCollection.getEntries()) {
      if (!entryMetadata.get(e.getEntryClassId()).isMultiValue() && hasRightForEntry(e.getEntryClassId())) {
        KksEntry old = getOldEntries().get(e.getId());
        if (old != null) {
          // modify old
          old.setCreator(e.getCreator());
          old.setCustomer(e.getCustomer());
          old.setModified(new Date());
          old.setVersion(e.getVersion());
          combineValues(e, old, log, logEntries);
        }
      }
    }
  }

  private void combineValues(KksEntry e, KksEntry old, Log log, LogEntriesType logEntries) {
    if (e.getValues() != null) {
      for (KksValue v : e.getValues()) {
        KksValue oldValue = getOldValues().get(v.getId());

        if (oldValue == null) {
          v.setEntry(old);
          old.addKksValue(v);
          log.logValueUpdate(oldCollection.getName(), metadata.getTypeCode(), oldCollection.getCustomer(), user, old,
              oldValue, v, logEntries);
        } else {
          log.logValueUpdate(oldCollection.getName(), metadata.getTypeCode(), oldCollection.getCustomer(), user, old,
              oldValue, v, logEntries);
          oldValue.setValue(v.getValue());
          oldValue.setModified(new Date());
          oldValue.setModifier(v.getModifier());
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

  public Map<Long, KksEntry> getOldEntries() {
    return oldEntries;
  }

  public Map<Long, KksValue> getOldValues() {
    return oldValues;
  }

}
