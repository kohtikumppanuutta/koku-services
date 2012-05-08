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
package fi.koku.services.entity.community.impl;

import java.util.Collection;

import javax.ejb.Local;

/**
 * Community related data access facilities.
 * 
 * @author laukksa
 */
@Local
public interface CommunityDAO {

  Community getCommunity(Long id);
  
  Long insertCommunity(Community c);
  
  void updateCommunity(Community c);
  
  void deleteCommunity(Long id);
  
  Collection<Community> queryCommunities(CommunityQueryCriteria qc);
  
  Long insertMembershipRequest(MembershipRequest rq);
  
  Collection<MembershipRequest> queryMembershipRequests(MembershipRequestQueryCriteria q);
  
  void updateMembershipApproval(MembershipApproval approval);
  
  MembershipRequest getMembershipRequest(Long requestId);
  
  void deleteMembershipRequest(Long requestId);
}