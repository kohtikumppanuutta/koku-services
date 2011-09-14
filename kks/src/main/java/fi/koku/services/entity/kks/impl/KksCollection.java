package fi.koku.services.entity.kks.impl;

import java.util.Date;
import java.util.List;

/**
 * Entity for kks collection
 * 
 * @author Ixonos / tuomape
 * 
 */
public class KksCollection {

  private int id;
  private String name;
  private String description;
  private String status;
  private Date created;
  private String creator;
  private String version;
  private KksCollectionClass collectionClass;
  private List<KksEntry> entries;
  private String nextVersion;
  private String prevVersion;
  private String customer;

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

  public String getVersion() {
    return version;
  }

  public void setVersion(String version) {
    this.version = version;
  }

  public KksCollectionClass getCollectionClass() {
    return collectionClass;
  }

  public void setCollectionClass(KksCollectionClass collectionClass) {
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

}
