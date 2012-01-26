package fi.koku.services.utility.employeeinfo.impl;

/**
 * Employee info service implementation holder class.
 * 
 * @author hanhian
 */
public class EmployeeInfoServiceHolder {

  private EmployeeInfoService employeeInfoService;

  public EmployeeInfoService getEmployeeInfoService() {
    return employeeInfoService;
  }

  public void setEmployeeInfoService(EmployeeInfoService employeeInfoService) {
    this.employeeInfoService = employeeInfoService;
  }
}