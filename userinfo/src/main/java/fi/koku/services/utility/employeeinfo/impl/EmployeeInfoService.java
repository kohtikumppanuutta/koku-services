/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.utility.employeeinfo.impl;

import fi.koku.services.utility.employee.v1.EmployeePicsType;
import fi.koku.services.utility.employee.v1.EmployeesType;
import fi.koku.services.utility.employee.v1.UserIdsType;

/**
 * EmployeeInfo service interface.
 * 
 * @author hanhian
 */
public interface EmployeeInfoService {

  /**
   * Returns the Employee by UserID.
   * @param id UserID of the employee that is searched for.
   * @return the employee whose UserID matches the given ID.
   */
  EmployeesType getEmployeesByIds(UserIdsType ids);

  /**
   * Returns the Employee by Pic.
   * @param pic pic of the employee that is searched for.
   * @return the employee whose pic matches the given pic.
   */
  EmployeesType getEmployeesByPics(EmployeePicsType pics);
}