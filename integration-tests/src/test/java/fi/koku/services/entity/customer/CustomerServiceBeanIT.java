package fi.koku.services.entity.customer;

import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.junit.Test;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import fi.koku.services.entity.customer.v1.CustomerService;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;

import javax.xml.namespace.QName;

/**
 * Example integration test.
 * 
 * @author Ixonos / laukksa
 *
 */
public class CustomerServiceBeanIT {

  public final URL CUSTOMER_WSDL_LOCATION = getClass().getClassLoader().getResource("/wsdl/customerService.wsdl");
  
  //@Test
  public void testGetCustomer() {
    CustomerService service = new CustomerService(CUSTOMER_WSDL_LOCATION, 
        new QName("http://services.koku.fi/entity/customer/v1", "customerService"));
    CustomerServicePortType customerServicePort = service.getCustomerServiceSoap11Port();
    String endpointAddress = "http://localhost:8080/customer-service-0.0.1-SNAPSHOT/CustomerServiceBean";    

    ((BindingProvider) customerServicePort).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY, endpointAddress);
    ((BindingProvider) customerServicePort).getRequestContext().put(BindingProvider.USERNAME_PROPERTY, "marko");
    ((BindingProvider) customerServicePort).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, "marko"); 
    
    // Setup fixture here
    
    // Call the web service
    CustomerType customer = customerServicePort.opGetCustomer("foobar");
    
    // Verify the returned result or DB state
    assertThat(customer.getId(), is("1234567890"));
  }
}
