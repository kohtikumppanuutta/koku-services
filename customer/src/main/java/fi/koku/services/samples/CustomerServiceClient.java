package fi.koku.services.samples;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import fi.koku.services.common.AuditInfoType;
import fi.koku.services.entity.customer.CustomerService;
import fi.koku.services.entity.customer.CustomerServicePortType;
import fi.koku.services.entity.customer.CustomerType;

/**
 * Demonstrate invoking Customer service.
 * 
 * @author aspluma
 */
public class CustomerServiceClient {
	public static void main(String ... args) throws MalformedURLException {
		URL wsdlLocation = new URL("http://localhost:8080/customer-service-0.0.1-SNAPSHOT/CustomerServiceBean?wsdl");
        QName serviceName = new QName("http://services.koku.fi/entity/customer", "customerService");
		CustomerService customerService = new CustomerService(wsdlLocation, serviceName);
		CustomerServicePortType port = customerService.getCustomerServiceSoap12Port();
	
		((BindingProvider)port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "marko");
		((BindingProvider)port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "marko"); 

		AuditInfoType audit = new AuditInfoType();
		audit.setComponent("kks");
		audit.setUserId("aspluma");
		String id = port.opAddCustomer(getCustomer(), audit);
		System.out.println("id: "+id);
	}
	
	 private static CustomerType getCustomer() {
	    CustomerType c = new CustomerType();
	    c.setId("1234567890");
	    c.setHetu("123-456");
	    c.setFirstName("simo");
	    c.setLastName("salminen");
	    return c;
	  }


}
