package fi.koku.services.entity.community.impl;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.entity.community.v1.AuditInfoType;
import fi.koku.services.entity.community.v1.CommunitiesType;
import fi.koku.services.entity.community.v1.CommunityQueryCriteriaType;
import fi.koku.services.entity.community.v1.CommunityServicePortType;
import fi.koku.services.entity.community.v1.CommunityType;
import fi.koku.services.entity.community.v1.MemberType;
import fi.koku.services.entity.community.v1.MembersType;
import fi.koku.services.entity.community.v1.VoidType;


/**
 * KoKu Community service implementation class.
 * 
 * TODO
 * - move data access code to server + dao layers
 * 
 * @author Ixonos / aspluma
 */
@Stateless
@WebService(wsdlLocation="META-INF/wsdl/communityService.wsdl",
    endpointInterface="fi.koku.services.entity.community.v1.CommunityServicePortType",
    targetNamespace="http://services.koku.fi/entity/community/v1",
    portName="communityService-soap11-port",
    serviceName="communityService"
)
@RolesAllowed("koku-role")

public class CommunityServiceEndpointBean implements CommunityServicePortType {
  private Logger logger = LoggerFactory.getLogger(CommunityServiceEndpointBean.class);

  private CommunityConverter communityConverter;
  
  @PersistenceContext
  private EntityManager em;
  
  public CommunityServiceEndpointBean() {
    communityConverter = new CommunityConverter();
  }

  @Override
  public String opAddCommunity(CommunityType community, AuditInfoType auditHeader) {
    logger.debug("opAddCommunity");
    return "12345";
  }

  @Override
  public CommunityType opGetCommunity(String communityId, AuditInfoType auditHeader) {
    Long id = Long.valueOf(communityId);
    Community c = em.find(Community.class, id);
    
    return communityConverter.toWsType(c);
  }

  @Override
  public VoidType opUpdateCommunity(CommunityType community, AuditInfoType auditHeader) {
    return new VoidType();
  }

  @Override
  public VoidType opDeleteCommunity(String communityId, AuditInfoType auditHeader) {
    return new VoidType();
  }

  @Override
  public CommunitiesType opQueryCommunities(CommunityQueryCriteriaType communityQueryCriteria, AuditInfoType auditHeader) {
    CommunitiesType c = new CommunitiesType();
    c.getCommunity().add(new CommunityType());
    c.getCommunity().add(new CommunityType());
    return c;
  }

  
  private static class CommunityConverter {
    
    public CommunityConverter() {
    }

    public CommunityType toWsType(Community from) {
      CommunityType to = new CommunityType();
      to.setId(from.getId().toString());
      to.setType(from.getType());
      to.setName(from.getName());
      to.setMembers(new MembersType());
      for(CommunityMember m : from.getCommunityMembers()) {
        MemberType mt = new MemberType();
        mt.setPic(m.getMemberId()); //FIXME
        mt.setRole(m.getRole());
        to.getMembers().getMember().add(mt);
      }
      return to;
    }
    
  }
  
  
}
