/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
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
