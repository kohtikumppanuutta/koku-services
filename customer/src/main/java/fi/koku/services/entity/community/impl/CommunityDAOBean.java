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

import fi.koku.KoKuFaultException;

/**
 * Community related data access facilities.
 * 
 * @author aspluma
 * @author laukksa
 */
@Stateless
public class CommunityDAOBean implements CommunityDAO {

  @PersistenceContext
  private EntityManager em;
  
  public CommunityDAOBean() {
  }

  @Override
  public Community getCommunity(Long id) {
    Query q = em.createNamedQuery(Community.QUERY_GET_COMMUNITY_BY_ID);
    q.setParameter("id", id);
    return (Community) q.getSingleResult();
  }
  
  @Override
  public Long insertCommunity(Community c) {
    c.setId(null);
    em.persist(c);
    return c.getId();
  }  
  
  /**
   * Merge from c2 to c1.
   */
  @Override
  public void updateCommunity(Community c2) {
    Community c1 = getCommunity(c2.getId());
    c1.setName(c2.getName());
    c1.setType(c2.getType());

    // some temporary data structures
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
  public Collection<Community> queryCommunities(CommunityQueryCriteria qc) {
    // JPQL generates an unnecessary join in the SQL subquery.
    StringBuilder jpql = new StringBuilder("SELECT c FROM Community c " +
        "JOIN FETCH c.members " +
        "WHERE c IN (" +
        "SELECT cm.community FROM CommunityMember cm WHERE cm.memberPic IN (:memberPics))");    
    if(qc.getCommunityType() != null) {
      jpql.append(" AND c.type = :type");
    }
    Query q = em.createQuery(jpql.toString());
    
    if (qc.getMemberPics() != null && qc.getMemberPics().size() > 0) {
      q.setParameter("memberPics", qc.getMemberPics());
    } else {
      // MemberPics are mandatory
      CommunityServiceErrorCode errorCode = CommunityServiceErrorCode.NO_MEMBER_PICS_QUERY_CRITERIA;
      throw new KoKuFaultException(errorCode.getValue(), errorCode.getDescription());
    }
    
    if (qc.getCommunityType() != null) {
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

  @Override
  public Collection<MembershipRequest> queryMembershipRequests(MembershipRequestQueryCriteria qc) {
    Query q = null;

    if(qc.getRequesterPic() != null) {
      q = em.createNamedQuery(MembershipRequest.QUERY_GET_MEM_REQUESTS_BY_REQUESTER_PIC);
      q.setParameter("requesterPic", qc.getRequesterPic());
    } else if(qc.getApproverPic() != null) {
      q = em.createNamedQuery(MembershipRequest.QUERY_GET_MEM_REQUESTS_BY_APPROVER_PIC);
      q.setParameter("approverPic", qc.getApproverPic());
    } else {
      throw new IllegalArgumentException("missing query parameters");
    }

    @SuppressWarnings("unchecked")
    Collection<MembershipRequest> requests = q.getResultList();
    Set<Long> ids = new HashSet<Long>();
    for(Iterator<MembershipRequest> i = requests.iterator(); i.hasNext(); ) {
      MembershipRequest mr = i.next();
      if(ids.contains(mr.getId())) {
        i.remove();
      }
      ids.add(mr.getId());
    }
    
    return requests;
  }

  @Override
  public void updateMembershipApproval(MembershipApproval a) {
    em.merge(a);
  }

  @Override
  public void deleteCommunity(Long id) {
    Community c = em.find(Community.class, id);
    em.remove(c);
  }

  @Override
  public Long insertMembershipRequest(MembershipRequest rq) {
    rq.setId(null);
    em.persist(rq);
    return rq.getId();
  }

  @Override
  public MembershipRequest getMembershipRequest(Long requestId) {
    Query q = em.createNamedQuery(MembershipRequest.QUERY_GET_MEM_REQUEST_BY_ID);
    q.setParameter("id", requestId);
    MembershipRequest rq = (MembershipRequest) q.getSingleResult();
    
    return rq;
  }

  @Override
  public void deleteMembershipRequest(Long requestId) {
    MembershipRequest rq = getMembershipRequest(requestId);
    em.remove(rq);
  }
}