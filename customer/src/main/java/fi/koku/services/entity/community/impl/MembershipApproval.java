package fi.koku.services.entity.community.impl;

/**
 * Membership request approval.
 * 
 * @author aspluma
 */
public class MembershipApproval {
  private Long id;
  private String membershipRequestId;
  private String approverPic;
  private String status;

  public MembershipApproval() {
  }

  public MembershipApproval(String membershipRequestId, String approverPic, String status) {
    this.membershipRequestId = membershipRequestId;
    this.approverPic = approverPic;
    this.status = status;
  }

  public Long getId() {
    return id;
  }

  public void setId(Long id) {
    this.id = id;
  }

  public String getMembershipRequestId() {
    return membershipRequestId;
  }

  public void setMembershipRequestId(String membershipRequestId) {
    this.membershipRequestId = membershipRequestId;
  }

  public String getApproverPic() {
    return approverPic;
  }

  public void setApproverPic(String approverPic) {
    this.approverPic = approverPic;
  }

  public String getStatus() {
    return status;
  }

  public void setStatus(String status) {
    this.status = status;
  }
  
}
