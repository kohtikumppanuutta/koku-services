package fi.koku.services.entity.kks.impl;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity for entry class (entry metadata)
 * 
 * @author Ixonos / tuomape
 * 
 */
@Entity
@NamedQueries({
    @NamedQuery(name = KksEntryClass.NAMED_QUERY_GET_ALL_ENTRY_CLASSES, query = "SELECT DISTINCT k FROM KksEntryClass k LEFT OUTER JOIN FETCH k.tags"),
    @NamedQuery(name = KksEntryClass.NAMED_QUERY_GET_ENTRY_CLASSES_FOR_GROUPS, query = "SELECT DISTINCT k FROM KksEntryClass k WHERE k.groupId IN (:ids)") })
@Table(name = "kks_entry_class")
public class KksEntryClass implements Serializable {

  public static final String NAMED_QUERY_GET_ALL_ENTRY_CLASSES = "getAllEntryClasses";
  public static final String NAMED_QUERY_GET_ENTRY_CLASSES_FOR_GROUPS = "getAllEntryClassesForGroups";

  private static final long serialVersionUID = 4568034252182971307L;

  @Id
  @GeneratedValue
  private Integer id;

  @Column(name = "entry_class_id")
  private int entryClassId;

  @Column(name = "sort_order", nullable = false)
  private int sortOrder;

  @Column(nullable = false)
  private String name;

  @Column
  private String description;

  @Column(name = "multi_value", nullable = false)
  private boolean multiValue;

  @Column(name = "data_type", nullable = false)
  private String dataType;

  @Column(name = "value_spaces")
  private String valueSpaces;

  @Column(name = "entry_group")
  private Integer groupId;

  // @ManyToOne
  // @JoinColumn(name = "entry_group")
  // private KksGroup group;

  @ManyToMany(fetch = FetchType.EAGER)
  @JoinTable(name = "kks_entry_class_tags", joinColumns = @JoinColumn(name = "entry_class_id", referencedColumnName = "entry_class_id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
  private List<KksTag> tags;

  public KksEntryClass() {
    tags = new ArrayList<KksTag>();
  }

  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  // public KksGroup getGroup() {
  // return group;
  // }
  //
  // public void setGroup(KksGroup croup) {
  // this.group = croup;
  // }

  public List<KksTag> getTags() {
    return tags;
  }

  public void setTags(List<KksTag> tags) {
    this.tags = tags;
  }

  public int getEntryClassId() {
    return entryClassId;
  }

  public void setEntryClassId(int entryClassId) {
    this.entryClassId = entryClassId;
  }

  public Integer getGroupId() {
    return groupId;
  }

  public void setGroupId(Integer groupId) {
    this.groupId = groupId;
  }

}
