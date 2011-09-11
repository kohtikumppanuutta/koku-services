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
}
