package fi.koku.services.entity.customer.impl;

import java.util.Collection;

/**
 * Service API for customer object access.
 *
 * @author aspluma
 */
public interface CustomerService {
  public Long add(Customer c);
  public Customer get(String pic);
  public void update(Customer c);
  public void delete(String pic);
  public Collection<Customer> query(CustomerQueryCriteria q);
}
