/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.customer.impl;

/**
 * Error codes for customer service.
 * 
 * @author laukksa
 *
 */
public enum CustomerServiceErrorCode {

  CUSTOMER_NOT_FOUND(1001, "Customer not found."),
  NO_QUERY_CRITERIA(1002, "Query criteria missing.");
  
  private final int value;

  private final String description;
  
  CustomerServiceErrorCode(int value, String description) {
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
