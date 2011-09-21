package fi.koku.services.entity.community.impl;

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

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
  private Logger logger = LoggerFactory.getLogger(CommunityService.class);

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
    return get(Long.valueOf(communityId));
  }
  
  private Community get(Long id) {
    Query q = em.createQuery("SELECT c FROM Community c LEFT JOIN FETCH c.members WHERE c.id = :id");
    q.setParameter("id", id);
    Community c = (Community)q.getSingleResult();
    return c;
  }
  
  

  @Override
  public void update(Community c2) {
    Community c1 = get(c2.getId());
    c1.setName(c2.getName());
    c1.setType(c2.getType());

    Set<String> m1Ids = new HashSet<String>();
    Map<String, CommunityMember> m2 = new HashMap<String, CommunityMember>();
    for(CommunityMember cm : c2.getMembers()) {
      m2.put(cm.getMemberPic(), cm);
    }

    // update modified and delete removed members
    for(Iterator<CommunityMember> i = c1.getMembers().iterator(); i.hasNext() ; ) {
      CommunityMember cm = i.next();
      CommunityMember cm2 = m2.get(cm.getMemberPic());
      if(cm2 != null) {
        if(!cm.getRole().equals(cm2.getRole())) {
          cm.setRole(cm2.getRole());
        }
      } else {
        i.remove();
        em.remove(cm);
      }
      m1Ids.add(cm.getMemberPic());
    }
    
    // add new members
    Set<String> m2Ids = m2.keySet(); 
    m2Ids.removeAll(m1Ids);
    for(Iterator<String> i = m2Ids.iterator(); i.hasNext() ; ) {
      CommunityMember cm = m2.get(i.next()); 
      c1.getMembers().add(cm);
      cm.setCommunity(c1);
    }
  }

  @Override
  public void delete(String communityId) {
    Community c = em.find(Community.class, Long.valueOf(communityId));
    em.remove(c);
  }

  @Override
  public Collection<Community> query(CommunityQueryCriteria qc) {
    // JPQL generates an unnecessary join in the SQL subquery.
    StringBuilder jpql = new StringBuilder("SELECT c FROM Community c " +
    		"JOIN FETCH c.members " +
    		"WHERE c IN (" +
    		"SELECT cm.community FROM CommunityMember cm WHERE cm.memberPic = :memberPic" +
    		")"
        );
    if(qc.getCommunityType() != null) {
      jpql.append(" AND c.type = :type");
    }
    Query q = em.createQuery(jpql.toString());
    q.setParameter("memberPic", qc.getMemberPic());
    if(qc.getCommunityType() != null) {
      q.setParameter("type", qc.getCommunityType());
    }
    
    @SuppressWarnings("unchecked")
    List<Community> communities = q.getResultList();
    // remove duplicate communities.
    Set<Community> r = new HashSet<Community>();
    for(Community c : communities) {
      r.add(c);
    }
    return r;
  }

}
