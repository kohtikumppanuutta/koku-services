package fi.koku.services.entity.customer.impl;

import javax.persistence.EntityManager;
import javax.persistence.Query;


/**
 * 
 * 
 * @author aspluma
 */
public class CustomerServiceImpl implements CustomerService {
  private EntityManager em;

  public CustomerServiceImpl(EntityManager em) {
    this.em = em;
  }

  @Override
  public Customer getCustomer(String pic) {
    Query q = em.createNamedQuery("getCustomerByPic");
    q.setParameter("pic", pic);
    Customer c = (Customer)q.getSingleResult();
    return c;
  }
  
  @Override
  public Long addCustomer(Customer c) {
    em.persist(c);
    return c.getId();
  }
  
  @Override
  public void updateCustomer(Customer c) {
    em.merge(c);
  }
  
}
