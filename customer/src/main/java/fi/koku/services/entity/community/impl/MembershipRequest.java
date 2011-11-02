/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.community.impl;

import static javax.persistence.CascadeType.PERSIST;
import static javax.persistence.CascadeType.REMOVE;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

/**
 * Community membership request.
 * 
 * @author aspluma
 */
@Entity
@NamedQueries({
  @NamedQuery(name=MembershipRequest.QUERY_GET_MEM_REQUEST_BY_ID, query="SELECT r FROM MembershipRequest r JOIN FETCH r.approvals WHERE r.id = :id"),
  @NamedQuery(name=MembershipRequest.QUERY_GET_MEM_REQUESTS_BY_REQUESTER_PIC, query="SELECT r FROM MembershipRequest r JOIN FETCH r.approvals WHERE r.requesterPic = :requesterPic"),
  @NamedQuery(name=MembershipRequest.QUERY_GET_MEM_REQUESTS_BY_APPROVER_PIC, query="SELECT r FROM MembershipRequest r " +
  "JOIN FETCH r.approvals WHERE r IN (" +
  "SELECT a.membershipRequest FROM MembershipApproval a WHERE a.approverPic = :approverPic)")
})
@Table(name = "community_membership_request")
public class MembershipRequest implements Serializable {
  
  private static final long serialVersionUID = -409898477181862486L;

  public static final String QUERY_GET_MEM_REQUEST_BY_ID = "getMembershipRequestById";
  
  public static final String QUERY_GET_MEM_REQUESTS_BY_REQUESTER_PIC = "getMembershipRequestByRequesterPic";
  
  public static final String QUERY_GET_MEM_REQUESTS_BY_APPROVER_PIC = "getMembershipRequestByApproverPic";
  
  @Id
  @GeneratedValue
  private Long id;

  // map as id, not relationship
  @Column(name="community_id", nullable=false)
  private Long communityId;

  @Column(name="member_role", nullable=false)
  private String memberRole;

  @Column(name="member_pic", nullable=false)
  private String memberPic;

  @Column(name="requester_pic", nullable=false)
  private String requesterPic;

  @OneToMany(mappedBy="membershipRequest", cascade={PERSIST, REMOVE})
  private Collection<MembershipApproval> approvals = new ArrayList<MembershipApproval>();
  
  @Column(nullable=false)
  @Temporal(TemporalType.DATE)
  private Date created;
  
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

  public Collection<MembershipApproval> getApprovals() {
    return approvals;
  }

  public void setApprovals(Collection<MembershipApproval> approvals) {
    this.approvals = approvals;
  }

  public Date getCreated() {
    return created;
  }

  public void setCreated(Date created) {
    this.created = created;
  }

}
