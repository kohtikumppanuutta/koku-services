/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (http://www.ixonos.com/).
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
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.Version;

/**
 * Entity for single KKS entry
 * 
 * @author Ixonos / tuomape
 * 
 */
@Entity
@NamedQueries({
    @NamedQuery(name = KksEntry.NAMED_QUERY_GET_ENTRIES_BY_IDS, query = "FROM KksEntry k WHERE k.customer =:customer AND k.id IN (:ids)"),
    @NamedQuery(name = KksEntry.NAMED_QUERY_GET_ENTRIES_BY_IDS_WITH_COLLECTIONS, query = "FROM KksEntry k WHERE k.customer =:customer AND k.id IN (:ids) AND k.kksCollection.id IN (:cIds)"),
    @NamedQuery(name = KksEntry.NAMED_QUERY_DELETE_ENTRIES_BY_IDS, query = "DELETE FROM KksEntry k WHERE k.id IN (:ids)"),
    @NamedQuery(name = KksEntry.NAMED_QUERY_GET_ENTRY_BY_CLASS_AND_COLLECTION, query = "FROM KksEntry k WHERE k.entryClassId =:entryClassId AND k.kksCollection.id =:collectionId") })
@Table(name = "kks_entry")
public class KksEntry implements Serializable {

  public static final String NAMED_QUERY_GET_ENTRIES_BY_IDS = "getAllEntriesByIds";
  public static final String NAMED_QUERY_GET_ENTRIES_BY_IDS_WITH_COLLECTIONS = "getAllEntriesByIdsWithCollections";
  public static final String NAMED_QUERY_DELETE_ENTRIES_BY_IDS = "deleteAllEntriesByIds";
  public static final String NAMED_QUERY_GET_ENTRY_BY_CLASS_AND_COLLECTION = "getEntryByClassAndCollection";

  private static final long serialVersionUID = -4758238731645701773L;

  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable = false)
  @Temporal(TemporalType.TIMESTAMP)
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

  @Transient
  private List<Integer> tagIds;

  public KksEntry() {
    values = new ArrayList<KksValue>();
    tags = new ArrayList<KksTag>();
    tagIds = new ArrayList<Integer>();
  }

  public KksEntry(KksEntry e, KksCollection c) {
    values = new ArrayList<KksValue>();
    tags = new ArrayList<KksTag>();
    tagIds = new ArrayList<Integer>();
    id = e.getId();

    modified = e.getModified();
    creator = e.getCreator();
    version = e.getVersion();
    entryClassId = e.getEntryClassId();
    customer = e.getCustomer();
    kksCollection = c;

    for (KksValue v : e.getValues()) {
      values.add(new KksValue(v, this));
    }

    for (KksTag t : e.getTags()) {
      tags.add(new KksTag(t));
    }
    tagIds = e.getTagIds();
  }

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

  public void addKksTag(KksTag tag) {
    this.tags.add(tag);
  }

  public void removeKksTag(KksTag tag) {
    List<KksTag> tmp = new ArrayList<KksTag>(tags);

    for (int i = 0; i < tmp.size(); i++) {
      KksTag t = tmp.get(i);
      if (t.getId().equals(tag.getId())) {
        tags.remove(i);
        break;
      }
    }
  }

  public void addKksValue(KksValue val) {
    this.values.add(val);
  }

  public void removeKksValue(KksValue val) {
    List<KksValue> tmp = new ArrayList<KksValue>(values);

    for (int i = 0; i < tmp.size(); i++) {
      KksValue t = tmp.get(i);
      if (t != null && t.getId().equals(val.getId())) {
        values.remove(i);
        break;
      }
    }
  }

  public void addTag(KksTag t) {
    tags.add(t);
  }

  public List<Integer> getTagIds() {
    return tagIds;
  }

  public void setTagIds(List<Integer> tagIds) {
    this.tagIds = tagIds;
  }

  public void clearTags() {
    tags.clear();
  }

  public String toString() {
    StringBuilder sb = new StringBuilder();
    sb.append("id: ").append(getId()).append(" classId: ").append(getEntryClassId()).append(" values: ")
        .append(getValues());
    return sb.toString();
  }

}
