/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
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
}
