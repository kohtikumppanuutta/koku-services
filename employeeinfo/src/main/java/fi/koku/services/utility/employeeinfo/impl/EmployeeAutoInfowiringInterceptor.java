package fi.koku.services.utility.employeeinfo.impl;

import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

/**
 * KoKu employeeInfo service custom autowiring interceptor. This class only
 * returns beanfactorylocator key that matches the bean named in the
 * beanRefContext.xml
 * 
 * @author hanhian
 */
public class EmployeeAutoInfowiringInterceptor extends SpringBeanAutowiringInterceptor{
  
  @Override
  protected String getBeanFactoryLocatorKey(Object target) {  
    return "employeeinfo-ejb-context";
  }  
}