/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.kks.impl;

/**
 * Wrapper class for collection creation
 * 
 * @author Ixonos / tuomape
 * 
 */
public class KksCollectionCreation {

  private String creator;
  private String customer;
  private String collectionId;
  private String name;
  private boolean empty;

  public KksCollectionCreation() {

  }

  public KksCollectionCreation(String creator, String customer, String collectionId, String name, boolean empty) {
    this.creator = creator;
    this.customer = customer;
    this.collectionId = collectionId;
    this.name = name;
    this.empty = empty;
  }

  public String getCreator() {
    return creator;
  }

  public void setCreator(String creator) {
    this.creator = creator;
  }

  public String getCustomer() {
    return customer;
  }

  public void setCustomer(String customer) {
    this.customer = customer;
  }

  public String getCollectionId() {
    return collectionId;
  }

  public void setCollectionId(String collectionId) {
    this.collectionId = collectionId;
  }

  public String getName() {
    return name;
  }

  public void setName(String name) {
    this.name = name;
  }

  public boolean isEmpty() {
    return empty;
  }

  public void setEmpty(boolean empty) {
    this.empty = empty;
  }

}
