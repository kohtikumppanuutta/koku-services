package fi.koku.services.entity.kks.impl;

import java.util.List;

/**
 * Entity for group
 * 
 * @author Ixonos / tuomape
 * 
 */
public class KksGroup {

  private int id;
  private int sortOrder;
  private String name;
  private String description;
  private String register;
  private List<KksGroup> subGroups;
  private List<KksEntryClass> entryClasses;

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

  public String getRegister() {
    return register;
  }

  public void setRegister(String register) {
    this.register = register;
  }

  public List<KksGroup> getSubGroups() {
    return subGroups;
  }

  public void setSubGroups(List<KksGroup> subGroups) {
    this.subGroups = subGroups;
  }

  public List<KksEntryClass> getEntryClasses() {
    return entryClasses;
  }

  public void setEntryClasses(List<KksEntryClass> entryClasses) {
    this.entryClasses = entryClasses;
  }

}
