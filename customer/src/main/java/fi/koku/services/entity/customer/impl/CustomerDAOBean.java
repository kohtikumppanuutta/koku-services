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
    Customer customer = findCustomer(c.getPic());
    customer.setCustomer(c);
    em.merge(customer);
  }

  @Override
  public void deleteCustomer(String pic) {
    Customer c = findCustomer(pic);
    em.remove(c.getId());
  }

  @Override
  public Collection<Customer> queryCustomers(CustomerQueryCriteria qc) {
    StringBuilder qs = new StringBuilder("FROM Customer c ");
    if("full".equals(qc.getSelection())) {
    		qs.append("LEFT JOIN FETCH c.addresses ");
    }
    qs.append("WHERE ");
    List<Object[]> params = new ArrayList<Object[]>();
    if(qc.getPic() != null) {
      qs.append("c.pic = :pic");
      params.add(new Object[] {"pic", qc.getPic()});
    }
    if(qc.getId() != null) {
      if(params.size() > 0)
        qs.append(" OR ");
      qs.append("c.id = :id");
      params.add(new Object[] {"id", qc.getId()});
    }

    if(params.size() == 0)
      throw new RuntimeException("missing criteria");
    Query q = em.createQuery(qs.toString());

    for(int i = 0; i<params.size(); i++)
      q.setParameter((String)params.get(i)[0], params.get(i)[1]);
    
    @SuppressWarnings("unchecked")
    List<Customer> customers = q.getResultList();
    if("full".equals(qc.getSelection())) {
      for(Customer c : customers) {
        c.getPhones();
        c.getElectronicContacts();
      }
    }
    
    return customers;
  }

}
