package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.List;

public class KksQueryCriteria {

  private String pic;
  private List<String> tagNames;

  public KksQueryCriteria() {
    tagNames = new ArrayList<String>();
  }

  public KksQueryCriteria(String pic, List<String> tagNames) {
    this.pic = pic;
    this.tagNames = tagNames;
  }

  public String getPic() {
    return pic;
  }

  public void setPic(String pic) {
    this.pic = pic;
  }

  public List<String> getTagNames() {
    return tagNames;
  }

  public void setTagNames(List<String> tagNames) {
    this.tagNames = tagNames;
  }

}
