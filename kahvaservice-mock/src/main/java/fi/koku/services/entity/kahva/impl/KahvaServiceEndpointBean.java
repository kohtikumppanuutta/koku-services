package fi.koku.services.entity.kahva.impl;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.tampere.hrsoa.entity.Organization;
import fi.arcusys.tampere.hrsoa.entity.User;
import fi.arcusys.tampere.hrsoa.ws.ldap.LdapService;



/**
 * Kahva service MOCK implementation class.
 * 
 * @author Ixonos / mikkope
 */
@Stateless
@WebService(wsdlLocation="META-INF/wsdl/ldapService.wsdl",
    endpointInterface="fi.arcusys.tampere.hrsoa.ws.ldap.LdapService",
    targetNamespace="http://www.arcusys.fi/tampere/hrsoa/ws/ldap/",    
    portName="ldapServiceSOAP",
    serviceName="ldapService"
)
//@RolesAllowed("koku-role")
public class KahvaServiceEndpointBean implements LdapService {
  private final Logger log = LoggerFactory.getLogger(KahvaServiceEndpointBean.class);

  //@Resource
  //private WebServiceContext wsCtx;
  
  public KahvaServiceEndpointBean(){

  }
  
  @Override
  public List<User> getEmployees() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> getEmployeesById(String userId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> getEmployeesByOrganization(String organizationId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public Organization getOrganizationById(String organizationId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> getOrganizationEmployees(String employeeId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<Organization> getOrganizations() {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> getSuperiorsByEmployeeId(String employeeId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> getSuperiorsById(String userId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> getSuperiorsByOrganization(String organizationId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> getSuperiorsBySSN(String ssn) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User getUserByEmployeeId(String employeeId) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public User getUserById(String userId) {
    log.info("GetUserByID called with userid="+userId);
    User u = null;
    KahvaServiceMockImpl m = new KahvaServiceMockImpl();
    u = m.getUserById(userId);
    return u;
  }

  @Override
  public User getUserBySSN(String ssn) {
    // TODO Auto-generated method stub
    return null;
  }

  @Override
  public List<User> getUsersByIds(List<String> userId) {
    // TODO Auto-generated method stub
    return null;
  }

 
}
