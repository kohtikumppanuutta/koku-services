package fi.koku.services.entity.customer;

import java.net.URL;
import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import fi.koku.services.entity.customer.v1.AuditInfoType;
import fi.koku.services.entity.customer.v1.CustomerServiceFactory;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.ServiceFault;
import fi.koku.services.test.util.TestDbUtils;
import fi.koku.services.test.util.TestPropertiesUtil;

/**
 * Integration tests for CustomerService.
 * 
 * @author Ixonos / laukksa
 *
 */
public class CustomerServiceBeanIT {
 
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
  
  @Test
  public void testInsertAndUpdateCustomer() {
    // in progress
    CustomerServicePortType customerServicePort = getCustomerServicePort();
    AuditInfoType audit = getAudit();
    
    CustomerType customer = new CustomerType();
    customer.setEtuNimi("Matti");
    customer.setSukuNimi("Virtanen");
    customer.setHenkiloTunnus("150790-123A");
    customer.setKieliKoodi("FI");
    customer.setSyntymaPvm(Calendar.getInstance());
    customer.setStatusDate(Calendar.getInstance());
    customer.setTurvakieltoKytkin(false);
    customer.setAddresses(null);

    try {
      customerServicePort.opAddCustomer(customer, audit);
    } catch (ServiceFault e) {
      throw new RuntimeException(e);
    }
    
    assertThat(jdbcTemplate.queryForInt("SELECT COUNT(*) FROM CUSTOMER"), is(1));
  }  
  
  private CustomerServicePortType getCustomerServicePort() {
    return new CustomerServiceFactory(TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_WS_USERNAME),
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_WS_PWD),
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_ENDPOINT_ADDRESS)).getCustomerService();
  }
  
  private AuditInfoType getAudit() {
    AuditInfoType audit = new AuditInfoType();
    audit.setComponent("kks");
    audit.setUserId("integration-test");
    return audit;
  }
}
