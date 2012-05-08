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
 * Customer service.
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