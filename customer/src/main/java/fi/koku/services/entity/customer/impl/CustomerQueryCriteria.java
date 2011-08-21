package fi.koku.services.entity.customer.impl;

import java.math.BigInteger;

/**
 * Customer query criteria.
 * 
 * @author aspluma
 */
public class CustomerQueryCriteria {
  private BigInteger id;
  private String pic;
  private String selection;

  public CustomerQueryCriteria(BigInteger id, String pic, String selection) {
    this.id = id;
    this.pic = pic;
    this.selection = selection;
  }

  public BigInteger getId() {
    return id;
  }

  public String getPic() {
    return pic;
  }

  public String getSelection() {
    return selection;
  }

}
