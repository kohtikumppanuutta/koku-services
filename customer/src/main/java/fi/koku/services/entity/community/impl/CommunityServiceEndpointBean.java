package fi.koku.services.entity.community.impl;

import java.util.Collection;

import javax.annotation.Resource;
import javax.annotation.security.RolesAllowed;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.ws.WebServiceContext;

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
 * - ID handling: which ID to use for customers; ID data types
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

  @SuppressWarnings("unused")
  @Resource
  private WebServiceContext wsCtx;
  
  @EJB
  private CommunityService communityService;

  
  private CommunityConverter communityConverter;
  
  
  public CommunityServiceEndpointBean() {
    communityConverter = new CommunityConverter();
  }

  @Override
  public String opAddCommunity(CommunityType community, AuditInfoType auditHeader) {
    logger.debug("opAddCommunity");
    Long id = communityService.add(communityConverter.fromWsType(community));
    return id != null ? id.toString() : "null";
  }

  @Override
  public CommunityType opGetCommunity(String communityId, AuditInfoType auditHeader) {
    return communityConverter.toWsType(communityService.get(communityId));
  }

  @Override
  public VoidType opUpdateCommunity(CommunityType community, AuditInfoType auditHeader) {
    communityService.update(communityConverter.fromWsType(community));
    return new VoidType();
  }

  @Override
  public VoidType opDeleteCommunity(String communityId, AuditInfoType auditHeader) {
    communityService.delete(communityId);
    return new VoidType();
  }

  @Override
  public CommunitiesType opQueryCommunities(CommunityQueryCriteriaType query, AuditInfoType auditHeader) {
    CommunityQueryCriteria qc = new CommunityQueryCriteria(query.getMemberPic(), query.getCommunityType());
    Collection<Community> comms = communityService.query(qc);
    CommunitiesType ret = new CommunitiesType();
    for(Community c : comms) {
      ret.getCommunity().add(communityConverter.toWsType(c));
    }
    return ret;
  }

  
  private static class CommunityConverter {
    
    public CommunityConverter() {
    }

    public Community fromWsType(CommunityType from) {
      Community to = new Community();
      to.setId(Long.valueOf(from.getId()));
      to.setType(from.getType());
      to.setName(from.getName());
      MembersType mt = from.getMembers();
      for(MemberType m : mt.getMember()) {
        to.getMembers().add(new CommunityMember(to, m.getId(), m.getPic(), m.getRole()));
      }
      
      return  to;
    }

    public CommunityType toWsType(Community from) {
      CommunityType to = new CommunityType();
      to.setId(from.getId().toString());
      to.setType(from.getType());
      to.setName(from.getName());
      to.setMembers(new MembersType());
      for(CommunityMember m : from.getMembers()) {
        MemberType mt = new MemberType();
        mt.setId(m.getMemberId());
        mt.setPic(m.getMemberPic());
        mt.setRole(m.getRole());
        to.getMembers().getMember().add(mt);
      }
      return to;
    }
    
  }
  
  
}
