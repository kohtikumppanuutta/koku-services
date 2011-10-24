package fi.koku.services.entity.kks.impl;



public enum KksServiceErrorCode {

  COLLECTION_CLASS_NOT_FOUND(1001, "Collection class not found");
  
  private final int value;

  private final String description;
  
  KksServiceErrorCode(int value, String description) {
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
