package fi.koku.services.entity.community.impl;

import java.util.Set;

/**
 * Community membership request.
 * 
 * @author aspluma
 */
public class MembershipRequest {
  private Long id;
  private Long communityId;
  private String memberRole;
  private String memberPic;
  private String requesterPic;
  private Set<MembershipApproval> approvals;
  
  public MembershipRequest() {
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public Long getCommunityId() {
    return communityId;
  }

  public void setCommunityId(Long communityId) {
    this.communityId = communityId;
  }

  public String getMemberRole() {
    return memberRole;
  }

  public void setMemberRole(String memberRole) {
    this.memberRole = memberRole;
  }

  public String getMemberPic() {
    return memberPic;
  }

  public void setMemberPic(String memberPic) {
    this.memberPic = memberPic;
  }

  public String getRequesterPic() {
    return requesterPic;
  }

  public void setRequesterPic(String requesterPic) {
    this.requesterPic = requesterPic;
  }

  public Set<MembershipApproval> getApprovals() {
    return approvals;
  }

  public void setApprovals(Set<MembershipApproval> approvals) {
    this.approvals = approvals;
  }

}
