package fi.koku.services.entity.community.impl;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

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
    
    Query q = em.createQuery("SELECT c FROM Community c JOIN FETCH c.members WHERE c.id = :id");
    q.setParameter("id", id);
    Community c = (Community)q.getSingleResult();

    return c;
  }

  @Override
  public void update(Community c2) {
    Community c1 = em.find(Community.class, c2.getId().toString());
    c1.setName(c2.getName());
    c1.setType(c2.getType());

    // remove deleted members
    Set<CommunityMember> m2 = new HashSet<CommunityMember>();
    m2.addAll(c2.getMembers());
    for(Iterator<CommunityMember> i = c1.getMembers().iterator(); i.hasNext() ; ) {
      CommunityMember cm = i.next();
      if(!m2.contains(cm)) {
        i.remove();
        em.remove(cm);
      }
    }
    
    // add new members
    m2.removeAll(c1.getMembers());
    for(Iterator<CommunityMember> i = m2.iterator(); i.hasNext() ; ) {
      CommunityMember cm = i.next();
      c1.getMembers().add(cm);
      cm.setCommunity(c1);
    }
  }

  @Override
  public void delete(String id) {
    Community c = get(id);
    em.remove(c);
  }

  @Override
  public Collection<Community> query(CommunityQueryCriteria qc) {
    // JPQL generates an unnecessary join in the SQL subquery.
    StringBuilder jpql = new StringBuilder("SELECT c FROM Community c " +
    		"JOIN FETCH c.members " +
    		"WHERE c IN (" +
    		"SELECT cm.community FROM CommunityMember cm WHERE cm.memberId = :memberId" +
    		")"
        );
    if(qc.getCommunityType() != null) {
      jpql.append(" AND c.type = :type");
    }
    Query q = em.createQuery(jpql.toString());
    q.setParameter("memberId", qc.getMemberPic());
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
