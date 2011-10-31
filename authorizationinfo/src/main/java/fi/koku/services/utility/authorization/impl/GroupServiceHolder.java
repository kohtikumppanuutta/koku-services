/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.authorization.impl;

/**
 * Group service implementation holder class.
 * 
 * @author aspluma
 */
public class GroupServiceHolder {
  private GroupService groupService;
  
  public GroupServiceHolder() {
  }

  public GroupService getGroupService() {
    return groupService;
  }

  public void setGroupService(GroupService groupService) {
    this.groupService = groupService;
  }
  
}
