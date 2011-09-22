package fi.koku.services.entity.customer.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Electronic contact info entity.
 * 
 * @author aspluma
 */
@Entity
@Table(name = "electronic_contact_info")
public class ElectronicContactInfo {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable=false)
  private String type;
  
  @Column(nullable=false)
  private String contact;
  
  @ManyToOne
  @JoinColumn(name="customer_id")
  private Customer customer;

  
  public ElectronicContactInfo() {
  }

  public Long getId() {
    return id;
  }

  protected void setId(Long id) {
    this.id = id;
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

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setElectronicContactInfo(ElectronicContactInfo electronicContactInfo) {
    setType(electronicContactInfo.getType());
    setContact(electronicContactInfo.getContact());
  }
  
}
