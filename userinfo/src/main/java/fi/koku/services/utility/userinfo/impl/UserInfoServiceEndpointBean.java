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

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fi.koku.services.utility.user.v1.ServiceFault;
import fi.koku.services.utility.user.v1.UserIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserInfoServicePortType;
import fi.koku.services.utility.user.v1.UserPicsQueryParamType;
import fi.koku.services.utility.user.v1.UsersType;

/**
 * KoKu userInfo service endpoint implementation class.
 * 
 * @author hanhian
 */
@Stateless
@WebService(wsdlLocation = "META-INF/wsdl/userInfoService.wsdl",
  endpointInterface = "fi.koku.services.utility.user.v1.UserInfoServicePortType",
  targetNamespace = "http://services.koku.fi/utility/user/v1",
  portName = "userInfoService-soap11-port",
  serviceName = "userInfoService")
@RolesAllowed("koku-role")
@Interceptors(UserInfoAutowiringInterceptor.class)
public class UserInfoServiceEndpointBean implements UserInfoServicePortType {
  private Logger logger = LoggerFactory.getLogger(UserInfoServiceEndpointBean.class);

  private UserInfoService userInfoService;

  @Autowired
  private UserInfoServiceHolder userInfoServiceHolder;

  public UserInfoServiceEndpointBean() {
  }
  
  @PostConstruct
  public void init() {
    logger.info("init(): "+userInfoServiceHolder);
    
    // null check is needed, because this method is called twice, by EJB Container and by Spring Framework.
    // when the EJB Container makes the call userInfoServiceHolder has not yet been populated.
    if (userInfoServiceHolder != null) {
      userInfoService = userInfoServiceHolder.getUserInfoService();
    } 
  }

  @Override
  public UsersType opGetUsersByIds(UserIdsQueryParamType userIdsQueryParam) throws ServiceFault {
    logger.info("opGetUsersByIds (impl: " + userInfoService + ")");
    return userInfoService.getUsersByIds(userIdsQueryParam);
  }

  @Override
  public UsersType opGetUsersByPics(UserPicsQueryParamType userPicsQueryParam) throws ServiceFault {
    logger.info("opGetUsersByPics (impl: " + userInfoService + ")");
    return userInfoService.getUsersByPics(userPicsQueryParam);
  }
}