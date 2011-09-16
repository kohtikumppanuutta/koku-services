package fi.koku.services.entity.kks.impl;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Entity for KKS entry
 * 
 * @author Ixonos / tuomape
 * 
 */
@Entity
@Table(name = "kks_entry")
public class KksEntry implements Serializable {

  private static final long serialVersionUID = -4758238731645701773L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private Date modified;

  @Column(nullable = false)
  private String creator;

  @Version
  private int version;

  @Column(name = "entry_class_id", nullable = false)
  private Integer entryClassId;

  @OneToMany(mappedBy = "entry", cascade = CascadeType.ALL)
  private List<KksValue> values;

  @Column(nullable = false)
  private String customer;

  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "collection_id")
  private KksCollection kksCollection;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "kks_entry_tags", joinColumns = @JoinColumn(name = "entry_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
  private List<KksTag> tags;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public Integer getEntryClassId() {
    return entryClassId;
  }

  public void setEntryClassId(Integer entryClassId) {
    this.entryClassId = entryClassId;
  }

  public List<KksValue> getValues() {
    return values;
  }

  public void setValues(List<KksValue> values) {
    this.values = values;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public KksCollection getKksCollection() {
    return kksCollection;
  }

  public void setKksCollection(KksCollection kksCollection) {
    this.kksCollection = kksCollection;
  }

  public List<KksTag> getTags() {
    return tags;
  }

  public void setTags(List<KksTag> tags) {
    this.tags = tags;
  }

}
