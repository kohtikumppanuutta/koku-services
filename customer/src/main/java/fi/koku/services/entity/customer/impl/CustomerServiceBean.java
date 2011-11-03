/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.customer.impl;

import java.util.Collection;
import javax.ejb.EJB;
import javax.ejb.Stateless;

/**
 * Customer service implementation.
 * 
 * @author aspluma
 * @author laukksa
 */
@Stateless
public class CustomerServiceBean implements CustomerService {
  
  @EJB
  private CustomerDAO customerDAO;

  public CustomerServiceBean() {
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
