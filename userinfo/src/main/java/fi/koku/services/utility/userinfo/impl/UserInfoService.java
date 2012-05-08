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
package fi.koku.services.utility.userinfo.impl;

import fi.koku.services.utility.user.v1.UserIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserPicsQueryParamType;
import fi.koku.services.utility.user.v1.UsersType;

/**
 * UserInfo service interface.
 * 
 * @author hanhian
 */
public interface UserInfoService {

  /**
   * Returns the User by UserID.
   * 
   * @param id
   *          UserIDs of the user that is searched for.
   * @return the user whose UserID matches the given ID.
   */
  UsersType getUsersByIds(UserIdsQueryParamType ids);

  /**
   * Returns Users by Pics.
   * 
   * @param pics
   *          pics of the users that is searched for.
   * @return the user whose pic matches the given pic.
   */
  UsersType getUsersByPics(UserPicsQueryParamType pics);
}