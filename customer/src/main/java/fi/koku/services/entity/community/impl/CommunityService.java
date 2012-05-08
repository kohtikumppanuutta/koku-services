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
 * Service API for community object access.
 * 
 * @author aspluma
 */
@Local
public interface CommunityService {

  Long add(Community c);

  Community get(String id);

  void update(Community c);

  void delete(String id);

  Collection<Community> query(CommunityQueryCriteria q);

  Long addMembershipRequest(MembershipRequest rq);

  Collection<MembershipRequest> queryMembershipRequests(MembershipRequestQueryCriteria q);

  void updateMembershipApproval(MembershipApproval approval);
  
  void deleteMembershipRequest(String membershipRequestId);
}