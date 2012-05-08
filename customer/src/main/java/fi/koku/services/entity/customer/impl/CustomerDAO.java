/*
 * Copyright 2012 Ixonos Plc, Finland. All rights reserved.
 * 
 * This file is part of Kohti kumppanuutta.
 *
 * This file is licensed under GNU LGPL version 3.
 * Please see the 'license.txt' file in the root directory of the package you received.
 * If you did not receive a license, please contact the copyright holder
 * (http://www.ixonos.com/).
 *
 */
package fi.koku.services.entity.customer.impl;

import java.util.Collection;

import javax.ejb.Local;

/**
 * Customer related data access facilities.
 * 
 * @author laukksa
 */
@Local
public interface CustomerDAO {
  
  Customer findCustomer(String pic);
  
  Long insertCustomer(Customer c);
  
  void updateCustomer(Customer c);
  
  void updateCustomerElectronicContacts(Customer c2);
  
  void deleteCustomer(String pic);
  
  Collection<Customer> queryCustomers(CustomerQueryCriteria c);

}
