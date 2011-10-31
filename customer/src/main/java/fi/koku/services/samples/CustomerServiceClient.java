/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.samples;

import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import fi.koku.services.entity.customer.v1.AuditInfoType;
import fi.koku.services.entity.customer.v1.CustomerService;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;

/**
 * Demonstrate invoking Customer service.
 * 
 * @author aspluma
 */
public class CustomerServiceClient {
	public static void main(String ... args) throws Exception {
	  String ep = "http://localhost:8080/customer-service-0.0.1-SNAPSHOT/CustomerServiceBean?wsdl";
//	  ep = "http://localhost:8088/mockcustomerService-soap11-binding?wsdl";
		URL wsdlLocation = new URL(ep);
		System.out.println("ep: "+wsdlLocation);
		QName serviceName = new QName("http://services.koku.fi/entity/customer/v1", "customerService");
		CustomerService customerService = new CustomerService(wsdlLocation, serviceName);
		
		CustomerServicePortType port = customerService.getCustomerServiceSoap11Port();
	
		((BindingProvider)port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "marko");
		((BindingProvider)port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "marko"); 

		AuditInfoType audit = new AuditInfoType();
		audit.setComponent("kks");
		audit.setUserId("aspluma");

		String id = port.opAddCustomer(getCustomer(), audit);
		System.out.println("id2: "+id);
	}
	
	 private static CustomerType getCustomer() {
	    CustomerType c = new CustomerType();
	    c.setId("1234567890");
	    return c;
	  }


}
