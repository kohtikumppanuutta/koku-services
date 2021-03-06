package fi.koku.services.utility.authorization.impl;

import org.springframework.beans.factory.access.BeanFactoryLocator;
import org.springframework.context.access.ContextSingletonBeanFactoryLocator;
import org.springframework.ejb.interceptor.SpringBeanAutowiringInterceptor;

/**
 * KoKu authorizationInfo service custom autowiring interceptor. This class only
 * returns beanfactorylocator key that matches the bean named in the
 * beanRefContext.xml
 * 
 * @author hanhian
 */
public class AuthorizationInfoAutowiringInterceptor extends SpringBeanAutowiringInterceptor{
  private static final String BEAN_FACTORY_RESOURCE_LOCATION = "classpath*:authorizationinfo-beanRefContext.xml";

  @Override
  protected BeanFactoryLocator getBeanFactoryLocator(Object target) {
    return ContextSingletonBeanFactoryLocator.getInstance(BEAN_FACTORY_RESOURCE_LOCATION);
  }  
}