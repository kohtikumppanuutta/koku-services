/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.List;

/**
 * Query criteria
 * 
 * @author Ixonos / tuomape
 * 
 */
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
