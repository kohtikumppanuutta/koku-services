package fi.koku.services.entity.customer.impl;

import java.util.Collection;

import javax.ejb.Local;

/**
 * CustomerDAO.
 * 
 * @author laukksa
 *
 */
@Local
public interface CustomerDAO {
  
  Customer findCustomer(String pic);
  
  Long insertCustomer(Customer c);
  
  void updateCustomer(Customer c);
  
  void deleteCustomer(String pic);
  
  Collection<Customer> queryCustomers(CustomerQueryCriteria c);

}
