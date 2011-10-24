package fi.koku.services.entity.community.impl;

import java.util.Collection;

import javax.ejb.Local;

/**
 * CommunityDAO.
 * 
 * @author laukksa
 *
 */
@Local
public interface CommunityDAO {

  Community getCommunity(Long id);
  
  Long insertCommunity(Community c);
  
  void updateCommunity(Community c);
  
  void deleteCommunity(Long id);
  
  public Collection<Community> queryCommunities(CommunityQueryCriteria qc);
  
  Long insertMembershipRequest(MembershipRequest rq);
  
  Collection<MembershipRequest> queryMembershipRequests(MembershipRequestQueryCriteria q);
  
  void updateMembershipApproval(MembershipApproval approval);
  
  MembershipRequest getMembershipRequest(Long requestId);
  
  void deleteMembershipRequest(Long requestId);
  
}
