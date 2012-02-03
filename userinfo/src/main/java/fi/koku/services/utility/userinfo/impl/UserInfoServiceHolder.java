package fi.koku.services.utility.userinfo.impl;

/**
 * User info service implementation holder class.
 * 
 * @author hanhian
 */
public class UserInfoServiceHolder {

  private UserInfoService userInfoService;

  public UserInfoService getUserInfoService() {
    return userInfoService;
  }

  public void setUserInfoService(UserInfoService userInfoService) {
    this.userInfoService = userInfoService;
  }
}