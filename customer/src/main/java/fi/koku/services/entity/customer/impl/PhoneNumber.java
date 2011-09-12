package fi.koku.services.entity.customer.impl;


/**
 * Phone number entity.
 * 
 * @author Ixonos / aspluma
 */
public class PhoneNumber {
  private String numberClass;
  private String type;
  private String number;

  public PhoneNumber() {
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

}
