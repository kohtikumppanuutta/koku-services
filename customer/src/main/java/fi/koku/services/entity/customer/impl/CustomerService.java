package fi.koku.services.entity.customer.impl;

import java.util.Collection;

import javax.ejb.Local;

/**
 * Service API for customer object access.
 * 
 * @author aspluma
 */
@Local
public interface CustomerService {
  
  Long add(Customer c);

  Customer get(String pic);

  void update(Customer c);

  void delete(String pic);

  Collection<Customer> query(CustomerQueryCriteria q);
  
}
