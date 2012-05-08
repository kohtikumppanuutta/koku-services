/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (http://www.ixonos.com/).
 *
 */
package fi.koku.services.entity.kks.impl;


/**
 * KKS related error codes
 * 
 * @author Ixonos / tuomape
 *
 */
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
