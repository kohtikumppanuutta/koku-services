package fi.koku.services.entity.community.impl;

/**
 * Membership request query criteria.
 * 
 * @author aspluma
 */
public class MembershipRequestQueryCriteria {
  private String requesterPic;
  private String approverPic;

  public MembershipRequestQueryCriteria() {
  }

  public MembershipRequestQueryCriteria(String requesterPic, String approverPic) {
    this.requesterPic = requesterPic;
    this.approverPic = approverPic;
  }

  public String getRequesterPic() {
    return requesterPic;
  }

  public void setRequesterPic(String requesterPic) {
    this.requesterPic = requesterPic;
  }

  public String getApproverPic() {
    return approverPic;
  }

  public void setApproverPic(String approverPic) {
    this.approverPic = approverPic;
  }

}
