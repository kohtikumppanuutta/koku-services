package fi.koku.services.entity.customer;

import java.net.URL;

import javax.xml.ws.BindingProvider;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import fi.koku.services.entity.customer.v1.AuditInfoType;
import fi.koku.services.entity.customer.v1.CustomerService;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.ServiceFault;
import fi.koku.services.test.util.TestDbUtils;
import fi.koku.services.test.util.TestPropertiesUtil;

import javax.xml.namespace.QName;

/**
 * Integration tests for CustomerService.
 * 
 * @author Ixonos / laukksa
 *
 */
public class CustomerServiceBeanIT {

  private final URL CUSTOMER_WSDL_LOCATION = getClass().getClassLoader().getResource("/wsdl/customerService.wsdl");
  
  JdbcTemplate jdbcTemplate = TestDbUtils.getJdbcTemplateInstance();
  
  @Before
  public void beforeTest() {
    TestDbUtils.deleteFromAllTables(jdbcTemplate);
  }
  
  @Test
  public void testGetCustomer() {
    CustomerServicePortType customerServicePort = getCustomerServicePort();
    AuditInfoType audit = getAudit();
    
    // Setup fixture here
    TestDbUtils.executeSqlScriptFromClasspath("/test-data/customer-test.sql", jdbcTemplate);
    assertThat(jdbcTemplate.queryForInt("SELECT COUNT(*) FROM CUSTOMER"), is(2));
    
    // Call the web service
    CustomerType customer;
    try {
      customer = customerServicePort.opGetCustomer("12346", audit);
    } catch (ServiceFault e) {
      throw new RuntimeException(e);
    }
    
    // Verify the returned result or DB state
    assertThat(customer.getId(), is("12346"));
    assertThat(customer.getEtuNimi(), is("Liisa"));
    assertThat(customer.getSukuNimi(), is("Virtanen"));
  }
  
  private CustomerServicePortType getCustomerServicePort() {
    CustomerService service = new CustomerService(CUSTOMER_WSDL_LOCATION, new QName(
        "http://services.koku.fi/entity/customer/v1", "customerService"));
    CustomerServicePortType customerServicePort = service.getCustomerServiceSoap11Port();
    String endpointAddress = TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_ENDPOINT_ADDRESS)
        + "/customer-service-0.0.1-SNAPSHOT/CustomerServiceEndpointBean";

    ((BindingProvider) customerServicePort).getRequestContext().put(BindingProvider.ENDPOINT_ADDRESS_PROPERTY,
        endpointAddress);
    ((BindingProvider) customerServicePort).getRequestContext().put(BindingProvider.USERNAME_PROPERTY,
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_WS_USERNAME));
    ((BindingProvider) customerServicePort).getRequestContext().put(BindingProvider.PASSWORD_PROPERTY, 
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_WS_PWD));
    return customerServicePort;
  }
  
  private AuditInfoType getAudit() {
    AuditInfoType audit = new AuditInfoType();
    audit.setComponent("kks");
    audit.setUserId("integration-test");
    return audit;
  }
}
