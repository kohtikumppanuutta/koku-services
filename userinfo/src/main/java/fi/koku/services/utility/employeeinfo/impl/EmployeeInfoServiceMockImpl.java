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

    for (String id : idsType.getId()) {
      EmployeeType emp = getEmployeeById(id);

      if (emp != null) {
        employeesType.getEmployee().add(emp);
      }
    }
    
    // TODO throw exception if the list is empty and real employee service does that also.
    return employeesType;
  }

  @Override
  public EmployeesType getEmployeesByPics(EmployeePicsType picsType) {
    EmployeesType employeesType = new EmployeesType();
    
    for(String pic : picsType.getPic()){
      EmployeeType emp = getEmployeeByPic(pic);

      if (emp != null) {
        employeesType.getEmployee().add(emp);
      }
    }
    
    // TODO throw exception if the list is empty and real employee service does that also.
    return employeesType;
  }

  private EmployeeType getEmployeeById(String id) {
    // Currently supported (and required) user information:
    // userId,ssn,firstName,lastName,email.
    // Example row:
    // toivo.toivola=toivo.toivola,111111-1111,Toivo,Toivola,toivo.toivola@localhost.fi
    Properties props = load("/getUsersByIdMock.properties");

    return getEmp(id, props);
  }

  private EmployeeType getEmployeeByPic(String pic) {
    // Currently supported (and required) user information:
    // userId,ssn,firstName,lastName,email.
    // Example row:
    // 111111-1111=toivo.toivola,111111-1111,Toivo,Toivola,toivo.toivola@localhost.fi
    Properties props = load("/getUsersByPicMock.properties");

    return getEmp(pic, props);
  }

  private EmployeeType getEmp(String key, Properties props) {
    EmployeeType emp = null;
    if (props != null) {
      LOG.info("props=" + props.toString());
      String property = props.getProperty(key);
      if (property == null) {
        LOG.info("could not find person with key " + key);
      } else {
        property = property.trim();
      }

      LOG.info("used property=" + property);
      if (property != null && !"".equals(property)) {
        emp = new EmployeeType();
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