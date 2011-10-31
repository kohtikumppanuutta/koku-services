/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.community.impl;

import java.util.Set;

/**
 * Community query criteria.
 * 
 * @author aspluma
 */
public class CommunityQueryCriteria {
  private Set<String> memberPics;
  private String communityType;

  public CommunityQueryCriteria(Set<String> memberPics, String communityType) {
    this.memberPics = memberPics;
    this.communityType = communityType;
  }
  
  public String getCommunityType() {
    return communityType;
  }

  public Set<String> getMemberPics() {
    return memberPics;
  }
}
