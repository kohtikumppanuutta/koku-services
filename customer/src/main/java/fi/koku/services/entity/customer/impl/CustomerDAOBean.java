package fi.koku.services.entity.customer.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 * CustomerDAOBean.
 * 
 * @author laukksa
 *
 */
@Stateless
public class CustomerDAOBean implements CustomerDAO {
  
  @PersistenceContext
  private EntityManager em;
  
  public CustomerDAOBean() {
  }

  @Override
  public Customer findCustomer(String pic) {
    Query q = em.createNamedQuery("getCustomerByPic");
    q.setParameter("pic", pic);
    Customer c = (Customer) q.getSingleResult();
    return c;
  }

  @Override
  public Long insertCustomer(Customer c) {
    em.persist(c);
    return c.getId();
  }

  @Override
  public void updateCustomer(Customer c) {
    Customer customer = em.find(Customer.class, c.getId());
    customer.setCustomer(c);
    em.merge(c);
  }

  @Override
  public void deleteCustomer(String pic) {
    Customer c = findCustomer(pic);
    em.remove(c.getId());
  }

  @SuppressWarnings("unchecked")
  @Override
  public Collection<Customer> queryCustomers(CustomerQueryCriteria c) {
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
