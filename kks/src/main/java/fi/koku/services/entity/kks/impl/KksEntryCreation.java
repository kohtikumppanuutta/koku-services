/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.kks.impl;

import java.util.Date;

/**
 * Wrapper class for entry level creation
 * 
 * @author Ixonos / tuomape
 * 
 */
public class KksEntryCreation {

  private Long id;
  private String pic;
  private String creator;
  private Date modified;
  private Long collectionId;
  private Integer entryClassId;
  private KksValue value;

  public KksEntryCreation(String id, String pic, String creator, Date modified, String collectionId,
      String entryClassId, KksValue value) {
    this.id = id == null ? null : Long.parseLong(id);
    this.pic = pic;
    this.creator = creator;
    this.modified = modified;
    this.collectionId = collectionId == null ? null : Long.parseLong(collectionId);
    this.entryClassId = entryClassId == null ? null : Integer.parseInt(entryClassId);
    this.value = value;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
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

  public Long getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(Long collectionId) {
    this.collectionId = collectionId;
  }

  public KksValue getValue() {
    return value;
  }

  public void setValue(KksValue value) {
    this.value = value;
  }

  public Integer getEntryClassId() {
    return entryClassId;
  }

  public void setEntryClassId(Integer entryClassId) {
    this.entryClassId = entryClassId;
  }

}
