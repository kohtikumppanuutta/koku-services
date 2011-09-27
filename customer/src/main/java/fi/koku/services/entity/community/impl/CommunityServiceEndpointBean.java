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
import fi.koku.services.entity.community.v1.MembershipApprovalType;
import fi.koku.services.entity.community.v1.MembershipApprovalsType;
import fi.koku.services.entity.community.v1.MembershipRequestQueryCriteriaType;
import fi.koku.services.entity.community.v1.MembershipRequestType;
import fi.koku.services.entity.community.v1.MembershipRequestsType;
import fi.koku.services.entity.community.v1.ServiceFault;
import fi.koku.services.entity.community.v1.VoidType;


/**
 * KoKu Community service implementation class.
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

  
  private WSTypeConverter<CommunityType, Community> communityConverter;
  private WSTypeConverter<MembershipRequestType, MembershipRequest> membershipRequestConverter;
  
  
  public CommunityServiceEndpointBean() {
    communityConverter = new CommunityConverter();
    membershipRequestConverter = new MembershipRequestConverter();
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

  @Override
  public String opAddMembershipRequest(MembershipRequestType membershipRequest, AuditInfoType auditHeader)
      throws ServiceFault {
    Long id = communityService.addMembershipRequest(membershipRequestConverter.fromWsType(membershipRequest));
    return id.toString();
  }

  @Override
  public MembershipRequestsType opQueryMembershipRequests(MembershipRequestQueryCriteriaType mrqc, AuditInfoType auditHeader)
      throws ServiceFault {
    MembershipRequestQueryCriteria qc = new MembershipRequestQueryCriteria(mrqc.getRequesterPic(), mrqc.getApproverPic());
    Collection<MembershipRequest> rqs = communityService.queryMembershipRequests(qc);
    MembershipRequestsType ret = new MembershipRequestsType();
    for(MembershipRequest rq : rqs) {
      ret.getMembershipRequest().add(membershipRequestConverter.toWsType(rq));
    }
    return ret;
  }

  @Override
  public VoidType opUpdateMembershipApproval(MembershipApprovalType ma, AuditInfoType auditHeader)
      throws ServiceFault {
    MembershipApproval app = new MembershipApproval(ma.getMembershipRequestId(), ma.getApproverPic(), ma.getStatus());
    communityService.updateMembershipApproval(app);
    return new VoidType();
  }
  
  
  private static class CommunityConverter implements WSTypeConverter<CommunityType, Community> {
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(CommunityConverter.class);
    
    public CommunityConverter() {
    }

    /* (non-Javadoc)
     * @see fi.koku.services.entity.community.impl.WSTypeConverter#fromWsType(fi.koku.services.entity.community.v1.CommunityType)
     */
    @Override
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

    /* (non-Javadoc)
     * @see fi.koku.services.entity.community.impl.WSTypeConverter#toWsType(fi.koku.services.entity.community.impl.Community)
     */
    @Override
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

  private static class MembershipRequestConverter implements WSTypeConverter<MembershipRequestType, MembershipRequest> {
    @SuppressWarnings("unused")
    private Logger logger = LoggerFactory.getLogger(MembershipRequestConverter.class);

    public MembershipRequestConverter() {
    }

    @Override
    public MembershipRequest fromWsType(MembershipRequestType from) {
      MembershipRequest to = new MembershipRequest();
      to.setCommunityId(Long.valueOf(from.getCommunityId()));
      to.setMemberRole(from.getMemberRole());
      to.setMemberPic(from.getMemberPic());
      to.setRequesterPic(from.getRequesterPic());
      for(MembershipApprovalType a : from.getApprovals().getApproval()) {
        to.getApprovals().add(new MembershipApproval(a.getApproverPic(), a.getStatus()));
      }
      return to;
    }

    @Override
    public MembershipRequestType toWsType(MembershipRequest from) {
      MembershipRequestType to = new MembershipRequestType();
      to.setCommunityId(from.getCommunityId().toString());
      to.setMemberRole(from.getMemberRole());
      to.setMemberPic(from.getMemberPic());
      to.setRequesterPic(from.getRequesterPic());
      to.setApprovals(new MembershipApprovalsType());
      for(MembershipApproval a : from.getApprovals()) {
        MembershipApprovalType at = new MembershipApprovalType();
        at.setApproverPic(a.getApproverPic());
        at.setStatus(a.getStatus());
        to.getApprovals().getApproval().add(at);
      }
      return to;
    }
  }

}
