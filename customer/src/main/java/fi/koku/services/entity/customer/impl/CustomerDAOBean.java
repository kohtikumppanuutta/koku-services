package fi.koku.services.entity.customer.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
  public void updateCustomer(Customer c2) {
    Customer c1 = findCustomer(c2.getPic());
    c1.setCustomer(c2);
    
    // Update child entities
    updateAddresses(c1, c2);
    updatePhoneNumbers(c1, c2);
    updateElectronicContactInfos(c1, c2);
  }
  
  /**
   * Merge from c2 to c1.
   * 
   * @param c1
   * @param c2
   */
  private void updateAddresses(Customer c1, Customer c2) {
    Set<String> c1Addresses = new HashSet<String>();
    Map<String, Address> c2Addresses = new HashMap<String, Address>();
    
    for (Address a : c2.getAddresses()) {
      c2Addresses.put(a.getType(), a);
    }
    
    for(Iterator<Address> i = c1.getAddresses().iterator(); i.hasNext(); ) {
      Address a = i.next();
      Address a2 = c2Addresses.get(a.getType());
      if (a2 != null) {
        // Update Address
        a.setAddress(a2);
      } else {
        // Delete Address
        i.remove();
        em.remove(a);        
      }
      c1Addresses.add(a.getType());
    }
    
    // Add Address
    Set<String> addressTypes = c2Addresses.keySet(); 
    addressTypes.removeAll(c1Addresses);
    for(String type : addressTypes) {
      Address a = c2Addresses.get(type);
      a.setCustomer(c1);
      c1.getAddresses().add(a);
    }    
  }
  
  /**
   * Merge from c2 to c1.
   * 
   * @param c1
   * @param c2
   */  
  private void updatePhoneNumbers(Customer c1, Customer c2) {
    Set<String> c1PhoneNumbers = new HashSet<String>();
    Map<String, PhoneNumber> c2PhoneNumbers = new HashMap<String, PhoneNumber>();
    
    for (PhoneNumber p : c2.getPhones()) {
      c2PhoneNumbers.put(p.getType(), p);
    }
    
    for(Iterator<PhoneNumber> i = c1.getPhones().iterator(); i.hasNext(); ) {
      PhoneNumber p = i.next();
      PhoneNumber p2 = c2PhoneNumbers.get(p.getType());
      if (p2 != null) {
        // Update PhoneNumber
        p.setPhoneNumber(p2);
      } else {
        // Delete PhoneNumber
        i.remove();
        em.remove(p);        
      }
      c1PhoneNumbers.add(p.getType());
    }
    
    // Add new PhoneNumber
    Set<String> PhoneNumberTypes = c2PhoneNumbers.keySet(); 
    PhoneNumberTypes.removeAll(c1PhoneNumbers);
    for(String type : PhoneNumberTypes) {
      PhoneNumber p = c2PhoneNumbers.get(type);
      p.setCustomer(c1);
      c1.getPhones().add(p);
    }    
  }
  
  /**
   * Merge from c2 to c1.
   * 
   * @param c1
   * @param c2
   */  
  private void updateElectronicContactInfos(Customer c1, Customer c2) {
    Set<String> c1ElectronicContactInfos = new HashSet<String>();
    Map<String, ElectronicContactInfo> c2ElectronicContactInfos = new HashMap<String, ElectronicContactInfo>();
    
    for (ElectronicContactInfo e : c2.getElectronicContacts()) {
      c2ElectronicContactInfos.put(e.getType(), e);
    }
    
    for(Iterator<ElectronicContactInfo> i = c1.getElectronicContacts().iterator(); i.hasNext(); ) {
      ElectronicContactInfo e = i.next();
      ElectronicContactInfo e2 = c2ElectronicContactInfos.get(e.getType());
      if (e2 != null) {
        // Update ElectronicContactInfo
        e.setElectronicContactInfo(e2);
      } else {
        // Delete ElectronicContactInfo
        i.remove();
        em.remove(e);        
      }
      c1ElectronicContactInfos.add(e.getType());
    }
    
    // Add new ElectronicContactInfo
    Set<String> ElectronicContactInfoTypes = c2ElectronicContactInfos.keySet(); 
    ElectronicContactInfoTypes.removeAll(c1ElectronicContactInfos);
    for(String type : ElectronicContactInfoTypes) {
      ElectronicContactInfo e = c2ElectronicContactInfos.get(type);
      e.setCustomer(c1);
      c1.getElectronicContacts().add(e);
    }    
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
