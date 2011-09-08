package fi.koku.services.entity.kks.impl;

import java.util.List;

/**
 * Entity for collection class (collection metadata)
 * 
 * @author Ixonos / tuomape
 * 
 */
public class KksCollectionClass {

  private int id;
  private String name;
  private String description;
  private List<KksGroup> groups;

  public int getId() {
    return id;
  }

  public void setId(int id) {
    this.id = id;
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

  public List<KksGroup> getGroups() {
    return groups;
  }

  public void setGroups(List<KksGroup> groups) {
    this.groups = groups;
  }

}
