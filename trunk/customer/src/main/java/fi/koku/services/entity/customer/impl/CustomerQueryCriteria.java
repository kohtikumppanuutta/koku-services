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
