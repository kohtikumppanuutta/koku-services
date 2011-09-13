package fi.koku.services.entity.customer.impl;

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

  private String type;
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
  
}
