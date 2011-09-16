package fi.koku.services.entity.customer.impl;

import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Address entity.
 * 
 * @author Ixonos / aspluma
 */
@Entity
@Table(name = "address")
public class Address {
  @Id
  @GeneratedValue
  private Long id;

  private String type;
  
  @Column(name="street_address")
  private String streetAddress;

  @Column(name="postal_district")
  private String postalDistrict;

  @Column(name="postal_code")
  private String postalCode;

  @Column(name="po_box")
  private String poBox;

  @Column(name="country_code")
  private String countryCode;

  @Column(name="valid_from")
  @Temporal(TemporalType.DATE)
  private Date validFrom;

  @Column(name="valid_to")
  @Temporal(TemporalType.DATE)
  private Date validTo;

  @ManyToOne
  @JoinColumn(name="customer_id")
  private Customer customer;

  
  public Address() {
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

  public String getStreetAddress() {
    return streetAddress;
  }

  public void setStreetAddress(String streetAddress) {
    this.streetAddress = streetAddress;
  }

  public String getPostalDistrict() {
    return postalDistrict;
  }

  public void setPostalDistrict(String postalDistrict) {
    this.postalDistrict = postalDistrict;
  }

  public String getPostalCode() {
    return postalCode;
  }

  public void setPostalCode(String postalCode) {
    this.postalCode = postalCode;
  }

  public String getPoBox() {
    return poBox;
  }

  public void setPoBox(String poBox) {
    this.poBox = poBox;
  }

  public String getCountryCode() {
    return countryCode;
  }

  public void setCountryCode(String countryCode) {
    this.countryCode = countryCode;
  }

  public Date getValidFrom() {
    return validFrom;
  }

  public void setValidFrom(Date validFrom) {
    this.validFrom = validFrom;
  }

  public Date getValidTo() {
    return validTo;
  }

  public void setValidTo(Date validTo) {
    this.validTo = validTo;
  }

  public Customer getCustomer() {
    return customer;
  }

  public void setCustomer(Customer customer) {
    this.customer = customer;
  }

}
