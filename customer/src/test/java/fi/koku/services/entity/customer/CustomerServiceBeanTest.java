package fi.koku.services.entity.customer;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.ArrayList;
import java.util.Collection;
import java.util.GregorianCalendar;

import org.junit.BeforeClass;
import org.junit.Test;

import fi.koku.services.common.v1.AuditInfoType;
import fi.koku.services.entity.customer.impl.Customer;
import fi.koku.services.entity.customer.impl.CustomerQueryCriteria;
import fi.koku.services.entity.customer.impl.CustomerService;
import fi.koku.services.entity.customer.impl.CustomerServiceBean;
import fi.koku.services.entity.customer.v1.CustomerType;

/**
 * Example JUnit test with Hamcrest matchers.
 * 
 * @author Ixonos / laukksa
 *
 */
public class CustomerServiceBeanTest {
  private static CustomerServiceBean customerServiceBean;

  @BeforeClass
  public static void initialize() {
    customerServiceBean = new CustomerServiceBean();
    customerServiceBean.setCustomerService(new CustomerServiceImplTest());
  }
  
  @Test
  public void testExample() {
    boolean flag = true;
    assertThat("Flag must be true", flag, is(true));
    assertThat("Flag must not be false", flag, not(false));
    
    final String name = null;
    assertThat("Name must be null", name, nullValue());
  }
  
  @Test
  public void testGet() {
    AuditInfoType audit = new AuditInfoType();
    audit.setComponent("kks");
    audit.setUserId("erkki");
    CustomerType cust = customerServiceBean.opGetCustomer("abc", audit);
    assertEquals("asiakas", cust.getSukuNimi());
  }
  
  
  public static class CustomerServiceImplTest implements CustomerService {
    private static final Long CUST_ID = 112233L;

    @Override
    public Long add(Customer c) {
      return CUST_ID;
    }

    @Override
    public Customer get(String pic) {
      return getCustomer();
    }
    
    @Override
    public void update(Customer c) {
    }

    @Override
    public void delete(String pic) {
    }

    @Override
    public Collection<Customer> query(CustomerQueryCriteria q) {
      Collection<Customer> r = new ArrayList<Customer>();
      r.add(getCustomer());
      return r;
    }
    
    private Customer getCustomer() {
      Customer c = new Customer();
      c.setBirthDate(new GregorianCalendar(2004, 11, 16).getTime());
      c.setFirstName("assi");
      c.setId(CUST_ID);
      c.setLastName("asiakas");
      c.setMunicipality("tpe");
      c.setNationality("fi");
      c.setPic("161204A4444");
      c.setStatus("elossa");
      c.setStatusDate(new GregorianCalendar(2011, 7, 22).getTime());
      c.setTurvakielto(false);
      return c;
    }
    
  }
  
}
