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
import fi.koku.services.entity.customer.v1.ElectronicContactInfoType;
import fi.koku.services.entity.customer.v1.ElectronicContactInfosType;
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
    phoneNumber.setPuhelinnumeroTeksti("+35891234566"); 
    phoneNumber.setNumberType("HOME"); // TODO: number type?
    phoneNumber.setNumberClass("?"); // TODO: number class?
    phoneNumbers.getPhone().add(phoneNumber);
    customer.setPhoneNumbers(phoneNumbers);
    
    ElectronicContactInfosType contactInfos = new ElectronicContactInfosType();
    ElectronicContactInfoType contactInfo = new ElectronicContactInfoType();
    contactInfo.setContactInfo("matti.virtanen");
    contactInfo.setContactInfoType("SKYPE");
    contactInfos.getEContactInfo().add(contactInfo);
    contactInfo = new ElectronicContactInfoType();
    contactInfo.setContactInfo("testinfo");
    contactInfo.setContactInfoType("INFO1");
    contactInfos.getEContactInfo().add(contactInfo);
    customer.setElectronicContactInfos(contactInfos);
    
    String customerId = customerServicePort.opAddCustomer(customer, audit);
    assertThat(customerId, notNullValue());
    
    // Get the inserted customer
    CustomerType insertedCustomer = customerServicePort.opGetCustomer(customer.getHenkiloTunnus(), audit);
    
    assertThat(insertedCustomer.getHenkiloTunnus(), is(customer.getHenkiloTunnus()));
    assertThat(insertedCustomer.getAddresses().getAddress().size(), is(2));
    assertThat(insertedCustomer.getPhoneNumbers().getPhone().size(), is(2));
    assertThat(insertedCustomer.getElectronicContactInfos().getEContactInfo().size(), is(2));
    
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
    assertThat(insertedCustomer.getElectronicContactInfos().getEContactInfo().size(), is(2));
    
    // Update address, phone, and electronic contact info
    customer = customerServicePort.opGetCustomer("150790-123A", audit);
    // Update address
    AddressType address = getAddressType("TYPE1", customer.getAddresses());
    assertThat(address.getKatuNimi(), is("Hitsaajankatu 24"));
    address.setKatuNimi(address.getKatuNimi() + " upd");
    // Add one new address
    AddressType newAddress = new AddressType();
    newAddress.setAddressType("TYPE3"); // TODO: address type values?
    newAddress.setAlkuPvm(Calendar.getInstance());
    newAddress.setLoppuPvm(Calendar.getInstance());
    newAddress.setKatuNimi("Kuusitie 4");
    newAddress.setPostilokeroTeksti("PL 284");
    newAddress.setPostinumeroKoodi("00811");
    newAddress.setPostitoimipaikkaNimi("Helsinki");
    newAddress.setMaatunnusKoodi("FI");
    customer.getAddresses().getAddress().add(newAddress);

    // Update phone number
    PhoneNumberType phone = getPhoneNumbersType("HOME", customer.getPhoneNumbers());
    assertThat(phone.getPuhelinnumeroTeksti(), is("+35891234566"));
    phone.setPuhelinnumeroTeksti(phone.getPuhelinnumeroTeksti() + " upd");
    // Add one new phone number
    PhoneNumberType newPhone = new PhoneNumberType();
    newPhone.setNumberType("TYPE3");
    newPhone.setNumberClass("CLASS1");
    newPhone.setPuhelinnumeroTeksti("+358123477");
    customer.getPhoneNumbers().getPhone().add(newPhone);

    // Update e contact info
    contactInfo = getElectronicContactInfoType("SKYPE", customer.getElectronicContactInfos());
    assertThat(contactInfo.getContactInfo(), is("matti.virtanen"));
    contactInfo.setContactInfo(contactInfo.getContactInfo() + " upd");
    // Add one new contact info
    ElectronicContactInfoType newContactInfo = new ElectronicContactInfoType();
    newContactInfo.setContactInfo("test123");
    newContactInfo.setContactInfoType("TYPE3");
    customer.getElectronicContactInfos().getEContactInfo().add(newContactInfo);
    
    customerServicePort.opUpdateCustomer(customer, audit);
    // Verify update
    customer = customerServicePort.opGetCustomer("150790-123A", audit);
    address = getAddressType("TYPE1", customer.getAddresses());
    assertThat(address.getKatuNimi(), is("Hitsaajankatu 24 upd"));
    assertThat(customer.getAddresses().getAddress().size(), is(3));
    
    phone = getPhoneNumbersType("HOME", customer.getPhoneNumbers());
    assertThat(phone.getPuhelinnumeroTeksti(), is("+35891234566 upd"));
    assertThat(customer.getPhoneNumbers().getPhone().size(), is(3));
    
    contactInfo = getElectronicContactInfoType("SKYPE", customer.getElectronicContactInfos());
    assertThat(contactInfo.getContactInfo(), is("matti.virtanen upd"));
    assertThat(customer.getElectronicContactInfos().getEContactInfo().size(), is(3));

    // Test remove
    customer = customerServicePort.opGetCustomer("150790-123A", audit);
    address = getAddressType("TYPE1", customer.getAddresses());
    customer.getAddresses().getAddress().remove(address);
    phone = getPhoneNumbersType("HOME", customer.getPhoneNumbers());
    customer.getPhoneNumbers().getPhone().remove(phone);
    contactInfo = getElectronicContactInfoType("SKYPE", customer.getElectronicContactInfos());
    customer.getElectronicContactInfos().getEContactInfo().remove(contactInfo);
    
    customerServicePort.opUpdateCustomer(customer, audit);
    
    customer = customerServicePort.opGetCustomer("150790-123A", audit);
    assertThat(customer.getAddresses().getAddress().size(), is(2));
    assertThat(customer.getPhoneNumbers().getPhone().size(), is(2));
    assertThat(customer.getElectronicContactInfos().getEContactInfo().size(), is(2));
    assertThat(getAddressType("TYPE1", customer.getAddresses()), nullValue());
    assertThat(getPhoneNumbersType("HOME", customer.getPhoneNumbers()), nullValue());
    assertThat(getElectronicContactInfoType("SKYPE", customer.getElectronicContactInfos()), nullValue());
  }
  
  private AddressType getAddressType(String type, AddressesType addresses) {
    for (AddressType a: addresses.getAddress()) {
      if (a.getAddressType().equals(type)) {
        return a;
      }
    }
    return null;
  }
  
  private PhoneNumberType getPhoneNumbersType(String type, PhoneNumbersType phoneNumbersType) {
    for (PhoneNumberType p : phoneNumbersType.getPhone()) {
      if (p.getNumberType().equals(type)) {
        return p;
      }
    }
    return null;
  }
  
  private ElectronicContactInfoType getElectronicContactInfoType(String type, ElectronicContactInfosType electronicContactInfosType) {
    for (ElectronicContactInfoType e : electronicContactInfosType.getEContactInfo()) {
      if (e.getContactInfoType().equals(type)) {
        return e;
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
