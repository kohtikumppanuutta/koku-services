package fi.koku.services.entity.kahva.impl;

/**
 * Error codes for customer service.
 * 
 * @author laukksa
 * @author aspluma
 */
public enum KahvaServiceErrorCode {
  ENTITY_NOT_FOUND(1001, "Customer not found.");
  
  private final int value;

  private final String description;
  
  KahvaServiceErrorCode(int value, String description) {
    this.value = value;
    this.description = description;
  }

  public int getValue() {
    return value;
  }

  public String getDescription() {
    return description;
  }
}
