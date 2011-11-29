/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.community.impl;

import java.util.Collection;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.SessionContext;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuNotAuthorizedException;

/**
 * Community service API implementation.
 * 
 * @author aspluma
 * @author laukksa
*/
@Stateless
@RolesAllowed("koku-role")
public class CommunityServiceBean implements CommunityService {
  
  private Logger logger = LoggerFactory.getLogger(CommunityService.class);
  
  private static final String GUARDIAN_COMMUNITY = "guardian_community";
  
  @Resource
  private SessionContext ctx;

  @EJB
  private CommunityDAO communityDAO;
  
  @Override
  public Long add(Community c) {    
    verifyUserRole(c); 
    return communityDAO.insertCommunity(c);
  }

  @Override
  public Community get(String communityId) {
    return communityDAO.getCommunity(Long.valueOf(communityId));
  }
  
  @Override
  public void update(Community c) {
    verifyUserRole(c); 
    communityDAO.updateCommunity(c);
  }

  @Override
  public void delete(String communityId) {
    long id = Long.valueOf(communityId);
    Community c = communityDAO.getCommunity(id);
    verifyUserRole(c); 
    communityDAO.deleteCommunity(id);
  }

  @Override
  public Collection<Community> query(CommunityQueryCriteria qc) {
    return communityDAO.queryCommunities(qc);
  }

  @Override
  public Long addMembershipRequest(MembershipRequest rq) {
    return communityDAO.insertMembershipRequest(rq);
  }

  @Override
  public Collection<MembershipRequest> queryMembershipRequests(MembershipRequestQueryCriteria qc) {    
    return communityDAO.queryMembershipRequests(qc);
  }

  /**
   * Update MembershipApproval based on approval's status
   */
  @Override
  public void updateMembershipApproval(MembershipApproval approval) {
    MembershipRequest rq = communityDAO.getMembershipRequest(approval.getMembershipRequestId());

    String approvalLogInfo = "request=" + approval.getMembershipRequestId() + "; community=" + rq.getCommunityId()
        + "; person=" + approval.getApproverPic();
    
    if (!CommunityConstants.MEM_APPROVAL_REQUEST_APPROVED.equals(approval.getStatus())) {
      // a) a non-approval (==> rejection). an approver does not approve, dismiss the request.
      logger.info("membership request rejected: " + approvalLogInfo);
      communityDAO.deleteMembershipRequest(rq.getId());
    } else if (isFinalMembershipRequestApproval(rq, approval)) {
      // b) final approval. add to community and remove request.
      // add member to community
      Community c = communityDAO.getCommunity(rq.getCommunityId());
      CommunityMember member = new CommunityMember(c, null, rq.getMemberPic(), rq.getMemberRole());
      c.getMembers().add(member);
      logger.info("membership request approved: " + approvalLogInfo);
      communityDAO.deleteMembershipRequest(rq.getId());
    } else {
      // c) an approval. update approval.
      logger.debug("membership request update: " + approvalLogInfo);
      for(MembershipApproval a : rq.getApprovals()) {
        if(a.getApproverPic().equals(approval.getApproverPic())) {
          a.setStatus(approval.getStatus());
          communityDAO.updateMembershipApproval(a);
          break;
        }
      }
    }
    
  }
  
  @Override
  public void deleteMembershipRequest(String membershipRequestId) {
    communityDAO.deleteMembershipRequest(Long.valueOf(membershipRequestId));
  }
  
  private boolean isFinalMembershipRequestApproval(MembershipRequest rq, MembershipApproval approval) {
    for (MembershipApproval a : rq.getApprovals()) {
      if (!a.getApproverPic().equals(approval.getApproverPic())
          && !CommunityConstants.MEM_APPROVAL_REQUEST_APPROVED.equals(a.getStatus())) {
        return false;
      }
    }
    return true;
  }
  
  /**
   * Verifies user role
   * 
   * @param c
   * @throws KoKuNotAuthorizedException if user is not in correct role
   */
  private void verifyUserRole(Community c) {
    if (c.getType().equals(GUARDIAN_COMMUNITY) && !ctx.isCallerInRole("koku-role-community-admin") ) {
        throw new KoKuNotAuthorizedException( CommunityServiceErrorCode.UNAUTHORIZED.getValue(), 
            CommunityServiceErrorCode.UNAUTHORIZED.getDescription() );   
    }
  }
  
}
