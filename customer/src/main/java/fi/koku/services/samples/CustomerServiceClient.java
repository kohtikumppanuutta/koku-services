package fi.koku.services.samples;

import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.namespace.QName;
import javax.xml.ws.BindingProvider;

import fi.koku.services.entity.customer.CustomerType;
import fi.koku.services.entity.customer.PtCustomer;
import fi.koku.services.entity.customer.SvCustomer;

/**
 * Demonstrate invoking Customer service.
 * 
 * @author aspluma
 */
public class CustomerServiceClient {
	public static void main(String ... args) throws MalformedURLException {
		URL wsdlLocation = new URL("http://localhost:8080/customer-service-0.0.1-SNAPSHOT/CustomerServiceBean?wsdl");
        QName serviceName = new QName("http://services.koku.fi/entity/customer", "svCustomer");
		SvCustomer customerService = new SvCustomer(wsdlLocation, serviceName);
		PtCustomer port = customerService.getCustomerServiceHttpSoap12();
	
		((BindingProvider)port).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "marko");
		((BindingProvider)port).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "marko"); 

		String id = port.opAddCustomer(new CustomerType());
		System.out.println("id: "+id);
	}

}
