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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

/**
 * Entity for kks collection
 * 
 * @author Ixonos / tuomape
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = KksCollection.NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_PIC, query = "FROM KksCollection k WHERE k.customer =:pic") })
@Table(name = "kks_collection")
public class KksCollection implements Serializable {

  public static final String NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_PIC = "getAllCollectionsByPic";

  private static final long serialVersionUID = 8064946506296337381L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  private String name;

  @Column
  private String description;

  @Column(nullable = false)
  private String status;

  @Column(nullable = false)
  @Temporal(TemporalType.DATE)
  private Date created;

  @Column(nullable = false)
  private String creator;

  @Version
  private int version;

  @Column(name = "next_version")
  private String nextVersion;

  @Column(name = "prev_version")
  private String prevVersion;

  @Column(nullable = false)
  private String customer;

  @Column(name = "collection_class_id")
  private int collectionClass;

  @OneToMany(mappedBy = "kksCollection", cascade = CascadeType.ALL)
  private List<KksEntry> entries;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "kks_collection_tags", joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
  private List<KksTag> tags;

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
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

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

  public int getVersion() {
    return version;
  }

  public void setVersion(int version) {
    this.version = version;
  }

  public int getCollectionClass() {
    return collectionClass;
  }

  public void setCollectionClass(int collectionClass) {
    this.collectionClass = collectionClass;
  }

  public List<KksEntry> getEntries() {
    return entries;
  }

  public void setEntries(List<KksEntry> entries) {
    this.entries = entries;
  }

  public String getNextVersion() {
    return nextVersion;
  }

  public void setNextVersion(String nextVersion) {
    this.nextVersion = nextVersion;
  }

  public String getPrevVersion() {
    return prevVersion;
  }

  public void setPrevVersion(String prevVersion) {
    this.prevVersion = prevVersion;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public List<KksTag> getTags() {
    return tags;
  }

  public void setTags(List<KksTag> tags) {
    this.tags = tags;
  }

}
