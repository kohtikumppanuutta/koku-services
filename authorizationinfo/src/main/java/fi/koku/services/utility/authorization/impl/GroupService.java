package fi.koku.services.utility.authorization.impl;

import fi.koku.services.utility.authorization.v1.GroupQueryCriteriaType;
import fi.koku.services.utility.authorization.v1.GroupsType;

/**
 * Group service interface.
 * 
 * @author aspluma
 */
public interface GroupService {
  GroupsType getGroups(GroupQueryCriteriaType gqc);
}