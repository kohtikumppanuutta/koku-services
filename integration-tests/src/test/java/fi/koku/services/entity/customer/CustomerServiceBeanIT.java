package fi.koku.services.entity.customer;

import java.util.Calendar;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;
import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import fi.koku.services.entity.customer.v1.AddressType;
import fi.koku.services.entity.customer.v1.AddressesType;
import fi.koku.services.entity.customer.v1.AuditInfoType;
import fi.koku.services.entity.customer.v1.CustomerServiceFactory;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.PhoneNumberType;
import fi.koku.services.entity.customer.v1.PhoneNumbersType;
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
  public void testGetCustomer() throws ServiceFault {
    CustomerServicePortType customerServicePort = getCustomerServicePort();
    AuditInfoType audit = getAudit();
    
    // Setup fixture here
    TestDbUtils.executeSqlScriptFromClasspath("/test-data/customer-test.sql", jdbcTemplate);
    assertThat(jdbcTemplate.queryForInt("SELECT COUNT(*) FROM customer"), is(2));
    
    // Call the web service
    CustomerType customer = customerServicePort.opGetCustomer("12346", audit);
    
    // Verify the returned result or DB state
    assertThat(customer.getId(), is("12346"));
    assertThat(customer.getEtuNimi(), is("Liisa"));
    assertThat(customer.getSukuNimi(), is("Virtanen"));
  }
  
  @Test
  public void testInsertAndUpdateCustomer() throws ServiceFault {
    // in progress
    CustomerServicePortType customerServicePort = getCustomerServicePort();
    AuditInfoType audit = getAudit();
    
    CustomerType customer = new CustomerType();
    customer.setEtuNimi("Matti");
    customer.setEtunimetNimi("");
    customer.setSukuNimi("Virtanen");
    customer.setHenkiloTunnus("150790-123A");
    customer.setKieliKoodi("FI");
    customer.setSyntymaPvm(Calendar.getInstance());
    customer.setStatusDate(Calendar.getInstance());
    customer.setTurvakieltoKytkin(false);
    customer.setStatus(""); // TODO: status values?
    customer.setKansalaisuusKoodi("FI");
    customer.setKuntaKoodi("1000"); // TODO: municipality codes?
    
    AddressesType addresses = new AddressesType();
    AddressType address1 = new AddressType();
    address1.setAddressType("TYPE1"); // TODO: address type values?
    address1.setAlkuPvm(Calendar.getInstance());
    address1.setLoppuPvm(Calendar.getInstance());
    address1.setKatuNimi("Hitsaajankatu 24");
    address1.setPostilokeroTeksti("PL 284");
    address1.setPostinumeroKoodi("00811");
    address1.setPostitoimipaikkaNimi("Helsinki");
    address1.setMaatunnusKoodi("FI");
    addresses.getAddress().add(address1);
    AddressType address2 = new AddressType();
    address2.setAddressType("TYPE2");
    address2.setAlkuPvm(Calendar.getInstance());
    address2.setLoppuPvm(Calendar.getInstance());
    address2.setKatuNimi("Iltatie 24");
    address2.setPostilokeroTeksti(null);
    address2.setPostinumeroKoodi("02220");
    address2.setPostitoimipaikkaNimi("Espoo");
    address2.setMaatunnusKoodi("FI");    
    addresses.getAddress().add(address2);
    customer.setAddresses(addresses);
    
    PhoneNumbersType phoneNumbers = new PhoneNumbersType();
    PhoneNumberType phoneNumber = new PhoneNumberType();
    phoneNumber.setPuhelinnumeroTeksti("+358501234567"); 
    phoneNumber.setNumberType("WORK"); // TODO: number type?
    phoneNumber.setNumberClass("?"); // TODO: number class?
    phoneNumbers.getPhone().add(phoneNumber);
    phoneNumber = new PhoneNumberType();
    phoneNumber.setPuhelinnumeroTeksti("+35891234567"); 
    phoneNumber.setNumberType("HOME"); // TODO: number type?
    phoneNumber.setNumberClass("?"); // TODO: number class?
    phoneNumbers.getPhone().add(phoneNumber);
    customer.setPhoneNumbers(phoneNumbers);
    
    String customerId = customerServicePort.opAddCustomer(customer, audit);
    
    // Get the inserted customer
    CustomerType insertedCustomer = customerServicePort.opGetCustomer(customer.getHenkiloTunnus(), audit);
    
    assertThat(insertedCustomer.getHenkiloTunnus(), is(customer.getHenkiloTunnus()));
    assertThat(insertedCustomer.getAddresses().getAddress().size(), is(2));
    assertThat(insertedCustomer.getPhoneNumbers().getPhone().size(), is(2));
    
    // Change etunimi property
    insertedCustomer.setEtuNimi(insertedCustomer.getEtuNimi() + " upd");
    // Update the customer
    customerServicePort.opUpdateCustomer(insertedCustomer, audit);
    // Get the customer again
    CustomerType updatedCustomer = customerServicePort.opGetCustomer(insertedCustomer.getHenkiloTunnus(), audit);
    assertThat(updatedCustomer.getHenkiloTunnus(), is(insertedCustomer.getHenkiloTunnus()));
    // Field should be updated
    assertThat(updatedCustomer.getEtuNimi(), is("Matti upd"));
    // These should be unchanged
    assertThat(updatedCustomer.getAddresses().getAddress().size(), is(2));
    assertThat(updatedCustomer.getPhoneNumbers().getPhone().size(), is(2));
  }
  
  @SuppressWarnings("unused")
  private AddressType getAddressType(String type, AddressesType addresses) {
    for (AddressType a: addresses.getAddress()) {
      if (a.getAddressType().equals(type)) {
        return a;
      }
    }
    return null;
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
