package fi.koku.services.entity.community.impl;

/**
 * Community query criteria.
 * 
 * @author aspluma
 */
public class CommunityQueryCriteria {
  private String communityType;
  private String memberPic;

  public CommunityQueryCriteria(String communityType, String memberPic) {
    this.communityType = communityType;
    this.memberPic = memberPic;
  }
  
  public String getCommunityType() {
    return communityType;
  }

  public String getMemberPic() {
    return memberPic;
  }
}
