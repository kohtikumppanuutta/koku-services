package fi.koku.services.entity.community.impl;

/**
 * Community query criteria.
 * 
 * @author aspluma
 */
public class CommunityQueryCriteria {
  private String memberPic;
  private String communityType;

  public CommunityQueryCriteria(String memberPic, String communityType) {
    this.memberPic = memberPic;
    this.communityType = communityType;
  }
  
  public String getCommunityType() {
    return communityType;
  }

  public String getMemberPic() {
    return memberPic;
  }
}
