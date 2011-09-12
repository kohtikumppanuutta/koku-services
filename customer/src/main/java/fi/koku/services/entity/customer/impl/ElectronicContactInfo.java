package fi.koku.services.entity.customer.impl;

/**
 * Electronic contact info entity.
 * 
 * @author aspluma
 */
public class ElectronicContactInfo {
  private String type;
  private String contact;
  
  public ElectronicContactInfo() {
  }

  public String getType() {
    return type;
  }

  public void setType(String type) {
    this.type = type;
  }

  public String getContact() {
    return contact;
  }

  public void setContact(String contact) {
    this.contact = contact;
  }
  
}
