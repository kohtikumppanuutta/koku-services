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

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

/**
 * Entity for single kks tag
 * 
 * @author Ixonos / tuomape
 * 
 */
@Entity
@NamedQueries({ @NamedQuery(name = KksTag.NAMED_QUERY_GET_TAGS_BY_IDS, query = "FROM KksTag k WHERE k.tagId IN (:ids)") })
@Table(name = "kks_tag")
public class KksTag implements Serializable {

  public static final String NAMED_QUERY_GET_TAGS_BY_IDS = "getAllTagsById";
  public static final String NAMED_QUERY_GET_ALL_TAGS = "getAllTags";

  private static final long serialVersionUID = -6375772555471958534L;

  @Id
  @GeneratedValue
  private Integer id;

  @Column(name = "tag_id")
  private int tagId;

  @Column(unique = true, nullable = false)
  private String name;

  @Column
  private String description;

  public KksTag() {
    
  }
  
  public KksTag( KksTag t ) {
    id = t.getId();
    tagId = t.getTagId();
    name = t.getName();
    description = t.getDescription();
  }
  
  public Integer getId() {
    return id;
  }

  public void setId(Integer id) {
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

  public int getTagId() {
    return tagId;
  }

  public void setTagId(int tagId) {
    this.tagId = tagId;
  }

}
