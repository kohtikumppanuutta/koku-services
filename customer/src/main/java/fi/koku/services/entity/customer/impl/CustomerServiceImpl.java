package fi.koku.services.entity.customer.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Customer service API implementation.
 * 
 * TODO
 * - exception handling
 * 
 * @author aspluma
 */
public class CustomerServiceImpl implements CustomerService {
  @SuppressWarnings("unused")
  private Logger logger = LoggerFactory.getLogger(CustomerServiceImpl.class);
  private EntityManager em;

  public CustomerServiceImpl(EntityManager em) {
    this.em = em;
  }

  @Override
  public Customer get(String pic) {
    Query q = em.createNamedQuery("getCustomerByPic");
    q.setParameter("pic", pic);
    Customer c = (Customer)q.getSingleResult();
    return c;
  }
  
  @Override
  public Long add(Customer c) {
    em.persist(c);
    return c.getId().longValue();
  }
  
  @Override
  public void update(Customer customer) {
    Customer c = em.find(Customer.class, customer.getId());
    c.setCustomer(customer);
    em.merge(c);
  }

  @Override
  public void delete(String pic) {
    Customer c = get(pic);
    em.remove(c.getId());
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<Customer> query(CustomerQueryCriteria c) {
    StringBuilder qs = new StringBuilder();
    List<Object[]> params = new ArrayList<Object[]>();
    if(c.getPic() != null) {
      qs.append("c.pic = :pic");
      params.add(new Object[] {"pic", c.getPic()});
    }
    if(c.getId() != null) {
      if(params.size() > 0)
        qs.append(" OR ");
      qs.append("c.id = :id");
      params.add(new Object[] {"id", c.getId()});
    }
    // TODO: selection determines which related objects to return.

    if(params.size() == 0)
      throw new RuntimeException("missing criteria");
    qs.insert(0, "FROM Customer c WHERE ");
    Query q = em.createQuery(qs.toString());

    for(int i = 0; i<params.size(); i++)
      q.setParameter((String)params.get(i)[0], params.get(i)[1]);
      
    return q.getResultList();
  }
  
}
