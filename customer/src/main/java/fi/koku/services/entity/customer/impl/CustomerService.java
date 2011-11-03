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
