package fi.koku.services.entity.customer.impl;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Customer service API implementation.
 * 
 * TODO
 * - exception handling
 * 
 * @author aspluma
 * @author laukksa
 */
@Stateless
public class CustomerServiceImpl implements CustomerService {
  
  @EJB
  CustomerDAO customerDAO;
  
  @SuppressWarnings("unused")
  private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);

  public CustomerServiceImpl() {
  }

  @Override
  public Customer get(String pic) {
    return customerDAO.findCustomer(pic);
  }
  
  @Override
  public Long add(Customer c) {
    return customerDAO.insertCustomer(c);
  }
  
  @Override
  public void update(Customer customer) {
    customerDAO.updateCustomer(customer);
  }

  @Override
  public void delete(String pic) {
    customerDAO.deleteCustomer(pic);
  }

  @Override
  public Collection<Customer> query(CustomerQueryCriteria c) {
    return customerDAO.queryCustomers(c);
  }
  
}
