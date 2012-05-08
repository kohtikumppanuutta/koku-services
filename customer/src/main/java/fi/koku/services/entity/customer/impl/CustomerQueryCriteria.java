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
package fi.koku.services.entity.customer.impl;

import java.util.Set;


/**
 * Customer query criteria.
 * 
 * @author aspluma
 */
public class CustomerQueryCriteria {

  private Set<String> pics;
  
  private String selection;

  public CustomerQueryCriteria(Set<String> pics, String selection) {
    this.pics = pics;
    this.selection = selection;
  }

  public Set<String> getPics() {
    return pics;
  }

  public String getSelection() {
    return selection;
  }
}