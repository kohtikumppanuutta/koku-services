package fi.koku.services.utility.authorization.impl;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

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
@Interceptors(SpringBeanAutowiringInterceptor.class)
public class AuthorizationInfoServiceEndpointBean implements AuthorizationInfoServicePortType {
  private Logger logger = LoggerFactory.getLogger(AuthorizationInfoServiceEndpointBean.class);

  private GroupService groupService;

  public AuthorizationInfoServiceEndpointBean() {
  }
  
  @PostConstruct
  public void init() {
  }
  
  @Override
  public GroupsType opQueryGroups(GroupQueryCriteriaType groupQueryCriteria) throws ServiceFault {
    logger.debug("opQueryGroups (impl: "+groupService+")");
    return groupService.getGroups(groupQueryCriteria);
  }

  @Autowired
  @Qualifier("groupServiceHolder")
  public void setGroupServiceHolder(GroupServiceHolder groupServiceHolder) {
    this.groupService = groupServiceHolder.getGroupService();
  }
  
}
