package fi.koku.services.entity.community.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Community service API implementation.
 * 
 * TODO:
 * - use DAO
 * 
 * @author aspluma
*/
@Stateless
public class CommunityServiceBean implements CommunityService {

  @PersistenceContext
  private EntityManager em;

  
  @Override
  public Long add(Community c) {
    c.setId(null);
    em.persist(c);
    return c.getId();
  }

  @Override
  public Community get(String communityId) {
    Long id = Long.valueOf(communityId);
    Community c = em.find(Community.class, id);
    return c;
  }

  @Override
  public void update(Community c2) {
    Community c1 = get(c2.getId().toString());

    // remove deleted members
    Set<CommunityMember> m2 = new HashSet<CommunityMember>();
    m2.addAll(c2.getCommunityMembers());
    for(Iterator<CommunityMember> i = c1.getCommunityMembers().iterator(); i.hasNext() ; ) {
      if(!m2.contains(i.next()))
        i.remove();
    }
    
    // add new members
    Set<CommunityMember> m1 = new HashSet<CommunityMember>();
    m1.addAll(c1.getCommunityMembers());
    if(m2.removeAll(m1)) {
      for(Iterator<CommunityMember> i = m2.iterator(); i.hasNext() ; ) {
        c1.getCommunityMembers().add(i.next());
      }
    }    
   
  }

  @Override
  public void delete(String id) {
    Community c = get(id);
    em.remove(c);
  }

  @Override
  public Collection<Community> query(CommunityQueryCriteria q) {
    return null;
  }

}
