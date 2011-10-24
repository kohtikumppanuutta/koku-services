package fi.koku.services.entity.community.impl;

/**
 * Error code for community service.
 * 
 * @author laukksa
 *
 */
public enum CommunityServiceErrorCode {

  CUSTOMER_NOT_FOUND(1001, "Customer not found."),
  NO_MEMBER_PICS_QUERY_CRITERIA(1002, "Query criteria 'member pics' missing.");
  
  private final int value;

  private final String description;
  
  CommunityServiceErrorCode(int value, String description) {
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
