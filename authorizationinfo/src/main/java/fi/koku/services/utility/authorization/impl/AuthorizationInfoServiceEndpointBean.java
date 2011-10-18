package fi.koku.services.utility.authorization.impl;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
public class AuthorizationInfoServiceEndpointBean implements AuthorizationInfoServicePortType {
  private Logger logger = LoggerFactory.getLogger(AuthorizationInfoServiceEndpointBean.class);
  private GroupService srv = new AuthorizationInfoServiceMockImpl();

  public AuthorizationInfoServiceEndpointBean() {
  }
  
  @Override
  public GroupsType opQueryGroups(GroupQueryCriteriaType groupQueryCriteria) throws ServiceFault {
    logger.debug("opQueryGroups");
    return srv.getGroups(groupQueryCriteria);
  }
  
}
