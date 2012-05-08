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

import java.util.ArrayList;
import java.util.List;

/**
 * Criteria for KKS queries
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
