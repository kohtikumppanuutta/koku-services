/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
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
