/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.employeeinfo.impl;

import javax.annotation.PostConstruct;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import fi.koku.services.utility.employee.v1.EmployeeInfoServicePortType;
import fi.koku.services.utility.employee.v1.EmployeePicsType;
import fi.koku.services.utility.employee.v1.EmployeesType;
import fi.koku.services.utility.employee.v1.ServiceFault;
import fi.koku.services.utility.employee.v1.UserIdsType;

/**
 * KoKu employeeInfo service endpoint implementation class.
 * 
 * @author hanhian
 */
@Stateless
@WebService(wsdlLocation = "META-INF/wsdl/employeeInfoService.wsdl",
  endpointInterface = "fi.koku.services.utility.employee.v1.EmployeeInfoServicePortType",
  targetNamespace = "http://services.koku.fi/utility/employee/v1",
  portName = "employeeInfoService-soap11-port",
  serviceName = "employeeInfoService")
@RolesAllowed("koku-role")
@Interceptors(EmployeeAutoInfowiringInterceptor.class)
public class EmployeeInfoServiceEndpointBean implements EmployeeInfoServicePortType {
  private Logger logger = LoggerFactory.getLogger(EmployeeInfoServiceEndpointBean.class);

  private EmployeeInfoService employeeInfoService;

  @Autowired
  private EmployeeInfoServiceHolder employeeInfoServiceHolder;
  
  @PostConstruct
  public void init() {
    logger.debug("init(): "+employeeInfoServiceHolder);
    
    // null check is needed, because this method is called twice, by EJB Container and by Spring Framework.
    // when the EJB Container makes the call employeeInfoServiceHolder has not yet been populated.
    if (employeeInfoServiceHolder != null) {
      employeeInfoService = employeeInfoServiceHolder.getEmployeeInfoService();
    } 
  }

  public EmployeesType opGetEmployeesByIds(UserIdsType userIds) throws ServiceFault {
    logger.debug("opGetEmployeeById (impl: " + employeeInfoService + ")");
    return employeeInfoService.getEmployeesByIds(userIds);
  }

  public EmployeesType opGetEmployeesByPics(EmployeePicsType employeePics) throws ServiceFault {
    logger.debug("opGetEmployeeByPic (impl: " + employeeInfoService + ")");
    return employeeInfoService.getEmployeesByPics(employeePics);
  }
}
