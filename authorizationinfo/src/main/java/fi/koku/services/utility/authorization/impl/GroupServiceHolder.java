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
