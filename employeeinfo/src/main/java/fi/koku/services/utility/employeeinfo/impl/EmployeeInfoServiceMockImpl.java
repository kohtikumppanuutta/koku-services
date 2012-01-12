package fi.koku.services.utility.employeeinfo.impl;

import java.io.IOException;
import java.net.URL;
import java.util.Properties;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.utility.employee.v1.EmployeePicsType;
import fi.koku.services.utility.employee.v1.EmployeeType;
import fi.koku.services.utility.employee.v1.EmployeesType;
import fi.koku.services.utility.employee.v1.UserIdsType;

/**
 * KoKu employeeInfo service Mock implementation class. Uses the same data format as the Kahva mock service.
 * 
 * @author hanhian
 */
public class EmployeeInfoServiceMockImpl implements EmployeeInfoService {

  private final Logger LOG = LoggerFactory.getLogger(EmployeeInfoServiceMockImpl.class);

  @Override
  public EmployeesType getEmployeesByIds(UserIdsType idsType) {
    EmployeesType employeesType = new EmployeesType();
   
    for(String id : idsType.getId()){
      employeesType.getEmployee().add(getEmployeeById(id));
    }
    return employeesType;
  }

  @Override
  public EmployeesType getEmployeesByPics(EmployeePicsType picsType) {
    EmployeesType employeesType = new EmployeesType();
    
    for(String pic : picsType.getPic()){
      employeesType.getEmployee().add(getEmployeeByPic(pic));
    }
    return employeesType;
  }

  /**
   * Mock user data
   * 
   * @param id userId (from portal in this case) that is a key in properties file
   * @return Returns mock user data read from properties file. If match not
   *         found return empty User object
   */
  private EmployeeType getEmployeeById(String id) {
    Properties props = load("/getUsersByIdMock.properties");

    // Currently supported (and required) user information: userId,ssn,firstName,lastName,email.
    // Example row:
    // toivo.toivola=toivo.toivola,111111-1111,Toivo,Toivola,toivo.toivola@localhost.fi

    EmployeeType emp = new EmployeeType();
    if (props != null) {
      LOG.info("props=" + props.toString());
      String property = props.getProperty(id).trim(); // id is key in properties
      LOG.info("used property=" + property);
      if (property != null && !"".equals(property)) {

        // Put values from props-file to User object
        String[] p = property.split(",");
        emp.setUserId(p[0]);
        emp.setPic(p[1]);
        emp.setFirstname(p[2]);
        emp.setLastname(p[3]);
        emp.setEmail(p[4]);
      }
    }
    return emp;
  }

  private EmployeeType getEmployeeByPic(String pic) {
    Properties props = load("/getUsersByPicMock.properties");

    // Currently supported (and required) user information: userId,ssn,firstName,lastName,email.
    // Example row:
    // 111111-1111=toivo.toivola,111111-1111,Toivo,Toivola,toivo.toivola@localhost.fi

    EmployeeType emp = new EmployeeType();
    if (props != null) {
      LOG.info("props=" + props.toString());
      String property = props.getProperty(pic); // id is key in properties
      if (property == null) {
        LOG.info("could not find person with pic " + pic);
      } else {
        property = property.trim();
      }
      LOG.info("used property=" + property);
      if (property != null && !"".equals(property)) {

        // Put values from props-file to User object
        String[] p = property.split(",");
        emp.setUserId(p[0]);
        emp.setPic(p[1]);

        emp.setFirstname(p[2]);
        emp.setLastname(p[3]);
        emp.setEmail(p[4]);
      }
    }
    return emp;
  }

  private Properties load(String propsName) {
    LOG.info("Trying to load properties propsName=" + propsName);
    Properties props = new Properties();
    try {
      URL in = this.getClass().getClassLoader().getResource(propsName);
      props.load(in.openStream());

    } catch (IOException e) {
      LOG.error("Failed to load properties file with propsName=" + propsName);
    }
    return props;
  }
}