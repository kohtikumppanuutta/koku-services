package fi.koku.services.utility.authorization.impl;

import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

/**
 * KoKu authorizationInfo service custom autowiring interceptor. This class only
 * returns beanfactorylocator key that matches the bean named in the
 * beanRefContext.xml
 * 
 * @author hanhian
 */
public class AuthorizationInfoAutowiringInterceptor extends SpringBeanAutowiringInterceptor{

  @Override
  protected String getBeanFactoryLocatorKey(Object target) {   
    return "authorizationinfo-ejb-context";
  }  
}