package fi.koku.services.entity.customer.impl;

import java.util.Date;

/**
 * Address entity.
 * 
 * @author Ixonos / aspluma
 */
public class Address {
  private String type;
  private String streetAddress;
  private String postalDistrict;
  private String postalCode;
  private String poBox;
  private String countryCode;
  private Date validFrom;
  private Date validTo;
  
  public Address() {
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

}
