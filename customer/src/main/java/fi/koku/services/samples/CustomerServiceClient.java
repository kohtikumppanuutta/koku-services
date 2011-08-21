package fi.koku.services.samples;

import java.net.MalformedURLException;
import java.net.URL;
import java.util.List;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import fi.koku.services.common.v1.AuditInfoType;
import fi.koku.services.entity.community.v1.CommunitiesType;
import fi.koku.services.entity.community.v1.CommunityQueryCriteriaType;
import fi.koku.services.entity.community.v1.CommunityService;
import fi.koku.services.entity.community.v1.CommunityServicePortType;
import fi.koku.services.entity.customer.v1.CustomerQueryCriteriaType;
import fi.koku.services.entity.customer.v1.CustomerService;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.CustomersType;

/**
 * Demonstrate invoking Customer service.
 * 
 * @author aspluma
 */
public class CustomerServiceClient {
	public static void main(String ... args) throws MalformedURLException {
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

		String id = port.opAddCustomer(getCustomer());
		System.out.println("id2: "+id);
	}
	
	 private static CustomerType getCustomer() {
	    CustomerType c = new CustomerType();
	    c.setId("1234567890");
	    return c;
	  }


}
