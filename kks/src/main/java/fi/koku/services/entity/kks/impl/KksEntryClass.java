package fi.koku.services.entity.kks.impl;

import java.util.List;

/**
 * Entity for entry class (entry metadata)
 * 
 * @author Ixonos / tuomape
 * 
 */
public class KksEntryClass {

  private int id;
  private int sortOrder;
  private String name;
  private String description;
  private boolean multiValue;
  private String dataType;
  private String valueSpaces;
  private KksCollectionClass collectionClass;
  private KksGroup croup;
  private List<KksTag> tags;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
  }

  public int getSortOrder() {
    return sortOrder;
  }

  public void setSortOrder(int sortOrder) {
    this.sortOrder = sortOrder;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public String getDescription() {
    return description;
  }

  public void setDescription(String description) {
    this.description = description;
  }

  public boolean isMultiValue() {
    return multiValue;
  }

  public void setMultiValue(boolean multiValue) {
    this.multiValue = multiValue;
  }

  public String getDataType() {
    return dataType;
  }

  public void setDataType(String dataType) {
    this.dataType = dataType;
  }

  public String getValueSpaces() {
    return valueSpaces;
  }

  public void setValueSpaces(String valueSpaces) {
    this.valueSpaces = valueSpaces;
  }

  public KksCollectionClass getCollectionClass() {
    return collectionClass;
  }

  public void setCollectionClass(KksCollectionClass collectionClass) {
    this.collectionClass = collectionClass;
  }

  public KksGroup getCroup() {
    return croup;
  }

  public void setCroup(KksGroup croup) {
    this.croup = croup;
  }

  public List<KksTag> getTags() {
    return tags;
  }

  public void setTags(List<KksTag> tags) {
    this.tags = tags;
  }

}
