/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.authorization.impl;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fi.koku.services.utility.authorization.v1.AuthorizationInfoServicePortType;
import fi.koku.services.utility.authorization.v1.GroupQueryCriteriaType;
import fi.koku.services.utility.authorization.v1.GroupsType;
import fi.koku.services.utility.authorization.v1.ServiceFault;

/**
 * KoKu authorizationinfo service endpoint implementation class.
 * 
 * @author Ixonos / aspluma
 */
@Stateless
@WebService(wsdlLocation = "META-INF/wsdl/authorizationInfoService.wsdl",
  endpointInterface = "fi.koku.services.utility.authorization.v1.AuthorizationInfoServicePortType",
  targetNamespace = "http://services.koku.fi/utility/authorization/v1",
  portName = "authorizationInfoService-soap11-port",
  serviceName = "authorizationInfoService")
@RolesAllowed("koku-role")
@Interceptors(AuthorizationInfoAutowiringInterceptor.class)
public class AuthorizationInfoServiceEndpointBean implements AuthorizationInfoServicePortType {
  private Logger logger = LoggerFactory.getLogger(AuthorizationInfoServiceEndpointBean.class);

  private GroupService groupService;

  @Autowired
  private GroupServiceHolder groupServiceHolder;

  public AuthorizationInfoServiceEndpointBean() {
  }
  
  @PostConstruct
  public void init() {    
    logger.debug("init(): "+groupServiceHolder);
    
    // null check is needed, because this method is called twice, by EJB Container and by Spring Framework.
    // when the EJB Container makes the call groupServiceHolder has not yet been populated.
    if (groupServiceHolder != null) {
      groupService = groupServiceHolder.getGroupService();
    }
  }
  
  @Override
  public GroupsType opQueryGroups(GroupQueryCriteriaType groupQueryCriteria) throws ServiceFault {
    logger.debug("opQueryGroups (impl: "+groupService+")");
    return groupService.getGroups(groupQueryCriteria);
  }

}
