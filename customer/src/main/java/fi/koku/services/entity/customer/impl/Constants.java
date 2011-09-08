package fi.koku.services.entity.customer.impl;

import javax.xml.namespace.QName;

/*
 * Customer service implementation constants.
 * 
 *  @author Ixonos / aspluma
 */
public class Constants {
  
  private Constants() {
  }
  
  // namespaces
  public static final String NS_KOKU_COMMON = "http://services.koku.fi/common/v1";
  public static final String NS_CUSTOMER = "http://services.koku.fi/entity/customer/v1";

  // elements
  public static final String ELEM_NAME_AUDIT_INFO = "auditInfo";
  public static final QName NAME_AUDIT_INFO = new QName(NS_KOKU_COMMON, ELEM_NAME_AUDIT_INFO);

}
