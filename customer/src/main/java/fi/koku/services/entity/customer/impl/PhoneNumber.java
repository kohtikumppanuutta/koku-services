package fi.koku.services.entity.customer.impl;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;


/**
 * Phone number entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
@Table(name = "phone_number")
public class PhoneNumber {
  @Id
  @GeneratedValue
  private Long id;

  @Column(nullable=false)
  private String type;

  @Column(name="class", nullable=false)
  private String numberClass;

  @Column(nullable=false)
  private String number;

  @ManyToOne
  @JoinColumn(name="customer_id", nullable=false)
  private Customer customer;

  
  public PhoneNumber() {
  }
  
  public Long getId() {
    return id;
  }

  protected void setId(Long id) {
    this.id = id;
  }

  public String getNumberClass() {
    return numberClass;
  }

  public void setNumberClass(String numberClass) {
    this.numberClass = numberClass;
  }

  public String getType() {
    return type;
  }
  public void setType(String type) {
    this.type = type;
  }
  public String getNumber() {
    return number;
  }
  public void setNumber(String number) {
    this.number = number;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

  public void setPhoneNumber(PhoneNumber phoneNumber) {
    setType(phoneNumber.getType());
    setNumber(phoneNumber.getNumber());
    setNumberClass(phoneNumber.getNumberClass());
  }

}
