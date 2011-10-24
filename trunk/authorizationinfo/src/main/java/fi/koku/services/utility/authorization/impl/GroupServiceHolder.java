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
