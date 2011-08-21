package fi.koku.services.entity.customer.impl;

/**
 * 
 * @author aspluma
 */
public interface CustomerService {
  public Long addCustomer(Customer c);
  public void updateCustomer(Customer c);
  public Customer getCustomer(String pic);
}
