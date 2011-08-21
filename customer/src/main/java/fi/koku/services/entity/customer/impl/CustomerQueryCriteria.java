package fi.koku.services.entity.customer.impl;


/**
 * Customer query criteria.
 * 
 * @author aspluma
 */
public class CustomerQueryCriteria {
  private Long id;
  private String pic;
  private String selection;

  public CustomerQueryCriteria(Long id, String pic, String selection) {
    this.id = id;
    this.pic = pic;
    this.selection = selection;
  }

  public Long getId() {
    return id;
  }

  public String getPic() {
    return pic;
  }

  public String getSelection() {
    return selection;
  }

}
