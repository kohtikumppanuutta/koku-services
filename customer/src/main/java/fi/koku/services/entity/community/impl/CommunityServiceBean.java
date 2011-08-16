package fi.koku.services.entity.community.impl;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.koku.services.entity.community.v1.CommunitiesType;
import fi.koku.services.entity.community.v1.CommunityQueryCriteriaType;
import fi.koku.services.entity.community.v1.CommunityServicePortType;
import fi.koku.services.entity.community.v1.CommunityType;


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

public class CommunityServiceBean implements CommunityServicePortType {

  @Override
  public String opAddCommunity(CommunityType community) {
    return "12345";
  }

  @Override
  public CommunityType opGetCommunity(String communityId) {
    CommunityType c = new CommunityType();
    return c;
  }

  @Override
  public void opUpdateCommunity(CommunityType community) {
  }

  @Override
  public void opDeleteCommunity(String communityId) {
  }

  @Override
  public CommunitiesType opQueryCommunities(CommunityQueryCriteriaType communityQueryCriteria) {
    CommunitiesType c = new CommunitiesType();
    c.getCommunity().add(new CommunityType());
    c.getCommunity().add(new CommunityType());
    return c;
  }

}
