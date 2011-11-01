/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.kks.impl;

import java.io.Serializable;
import java.util.ArrayList;
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
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity for kks collection
 * 
 * @author Ixonos / tuomape
 * 
 */
@Entity
@NamedQueries({
    @NamedQuery(name = KksCollection.NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_PIC, query = "FROM KksCollection k WHERE k.customer =:pic"),
    @NamedQuery(name = KksCollection.NAMED_QUERY_GET_COLLECTIONS_BY_IDS, query = "FROM KksCollection k WHERE k.id IN (:ids)"),
    @NamedQuery(name = KksCollection.NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_AND_CREATOR, query = "FROM KksCollection k WHERE k.creator =:creator AND k.customer =:customer") })
@Table(name = "kks_collection")
public class KksCollection implements Serializable {

  public static final String NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_PIC = "getAllCollectionsByPic";
  public static final String NAMED_QUERY_GET_COLLECTIONS_BY_IDS = "getAllCollectionsByIds";
  public static final String NAMED_QUERY_GET_COLLECTIONS_BY_CUSTOMER_AND_CREATOR = "getAllCollectionsByCustomerAndCreator";

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
  @Temporal(TemporalType.TIMESTAMP)
  private Date created;

  @Column(nullable = false)
  private String creator;

  @Column
  @Temporal(TemporalType.TIMESTAMP)
  private Date modified;

  @Column
  private String modifier;

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

  @OneToMany(fetch = FetchType.LAZY, mappedBy = "kksCollection", cascade = { CascadeType.PERSIST, CascadeType.MERGE })
  private List<KksEntry> entries;

  @ManyToMany(fetch = FetchType.LAZY)
  @JoinTable(name = "kks_collection_tags", joinColumns = @JoinColumn(name = "collection_id", referencedColumnName = "id"), inverseJoinColumns = @JoinColumn(name = "tag_id", referencedColumnName = "tag_id"))
  private List<KksTag> tags;

  @Transient
  private boolean consentRequested;

  @Transient
  private String userConsentStatus;

  public KksCollection() {
    entries = new ArrayList<KksEntry>();
    tags = new ArrayList<KksTag>();
    consentRequested = false;
    userConsentStatus = "";
  }
  
  public KksCollection(KksCollection c) {
    entries = new ArrayList<KksEntry>();
    tags = new ArrayList<KksTag>();
    
    id = c.getId();    
    name = c.getName();
    description = c.getDescription();
    status = c.getStatus();
    created = c.getCreated();
    creator = c.getCreator();
    modified = c.getModified();
    modifier = c.getModifier();    
    version = c.getVersion();
    nextVersion = c.getNextVersion();
    prevVersion = c.getPrevVersion();
    customer = c.getCustomer();
    collectionClass = c.getCollectionClass();
    consentRequested = c.isConsentRequested();
    userConsentStatus = c.getUserConsentStatus();

    for ( KksEntry e : c.getEntries() ) {
      entries.add(new KksEntry(e, this ));
    }
    
    for ( KksTag t : c.getTags() ) {
      tags.add(new KksTag(t));
    }
    
  }

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

  public Date getModified() {
    return modified;
  }

  public void setModified(Date modified) {
    this.modified = modified;
  }

  public String getModifier() {
    return modifier;
  }

  public void setModifier(String modifier) {
    this.modifier = modifier;
  }

  public List<KksTag> getTags() {
    return tags;
  }

  public void setTags(List<KksTag> tags) {
    this.tags = tags;
  }

  public void addKksEntry(KksEntry e) {
    this.entries.add(e);
  }

  public void removeKksEntry(KksEntry e) {
    List<KksEntry> listTmp = new ArrayList<KksEntry>(entries);
    for (int i = 0; i < listTmp.size(); i++) {
      KksEntry tmp = listTmp.get(i);
      if (tmp.getId().equals(e.getId())) {
        entries.remove(i);
        break;
      }
    }
  }

  public boolean isConsentRequested() {
    return consentRequested;
  }

  public void setConsentRequested(boolean consentRequested) {
    this.consentRequested = consentRequested;
  }

  public String getUserConsentStatus() {
    return userConsentStatus;
  }

  public void setUserConsentStatus(String userConsentStatus) {
    this.userConsentStatus = userConsentStatus;
  }

}
