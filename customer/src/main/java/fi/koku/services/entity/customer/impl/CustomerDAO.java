/*
 * Copyright 2011 Ixonos Plc, Finland. All rights reserved.
 * 
 * You should have received a copy of the license text along with this program.
 * If not, please contact the copyright holder (http://www.ixonos.com/).
 * 
 */
package fi.koku.services.entity.customer.impl;

import java.util.Collection;

import javax.ejb.Local;

/**
 * Customer related data access facilities.
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
