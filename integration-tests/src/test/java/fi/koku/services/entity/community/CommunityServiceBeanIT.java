package fi.koku.services.entity.community;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.springframework.jdbc.core.JdbcTemplate;

import static org.junit.Assert.assertThat;
import static org.hamcrest.CoreMatchers.*;
import fi.koku.services.entity.community.v1.CommunitiesType;
import fi.koku.services.entity.community.v1.CommunityQueryCriteriaType;
import fi.koku.services.entity.community.v1.CommunityServiceFactory;
import fi.koku.services.entity.community.v1.CommunityServicePortType;
import fi.koku.services.entity.community.v1.CommunityType;
import fi.koku.services.entity.community.v1.MemberPicsType;
import fi.koku.services.entity.community.v1.MemberType;
import fi.koku.services.entity.community.v1.MembersType;
import fi.koku.services.entity.community.v1.AuditInfoType;
import fi.koku.services.entity.community.v1.ServiceFault;
import fi.koku.services.test.util.TestDbUtils;
import fi.koku.services.test.util.TestPropertiesUtil;

/**
 * Integration tests for community service.
 * 
 * @author laukksa
 *
 */
public class CommunityServiceBeanIT {

  JdbcTemplate jdbcTemplate = TestDbUtils.getJdbcTemplateInstance();
  
  @Before
  public void beforeTest() {
    TestDbUtils.deleteFromAllTables(jdbcTemplate);
  }
  
  @Test
  public void testQueryCommunities() throws ServiceFault {
    CommunityServicePortType communityService = getCommunityServicePort();
    AuditInfoType audit = getAudit();
    
    CommunityType community = new CommunityType();
    community.setName("Community 1");
    community.setType("FAMILY");
    MembersType members = new MembersType();
    MemberType member = new MemberType();
    member.setId("12347");
    member.setPic("123456-110K");
    member.setRole("CHILD");
    members.getMember().add(member);
    member = new MemberType();
    member.setId("12346");
    member.setPic("123456-123A");
    member.setRole("MOTHER");
    members.getMember().add(member);
    community.setMembers(members);
    String community1Id = communityService.opAddCommunity(community, audit);
    
    community = new CommunityType();
    community.setName("Community 2");
    community.setType("FAMILY");
    members = new MembersType();
    member = new MemberType();
    member.setId("12345");
    member.setPic("111456-163A");
    member.setRole("OTHER");
    members.getMember().add(member);
    community.setMembers(members);
    String community2Id = communityService.opAddCommunity(community, audit);
    
    community = new CommunityType();
    community.setName("Community 3");
    community.setType("OTHER");
    members = new MembersType();
    member = new MemberType();
    member.setId("12345");
    member.setPic("111456-100A");
    member.setRole("OTHER");
    members.getMember().add(member);
    member = new MemberType();
    member.setId("12345");
    member.setPic("111456-163A");
    member.setRole("FATHER");
    members.getMember().add(member);    
    community.setMembers(members);
    String community3Id = communityService.opAddCommunity(community, audit);    
    
    CommunityQueryCriteriaType criteria = new CommunityQueryCriteriaType();
    MemberPicsType pics = new MemberPicsType();
    pics.getMemberPic().add("123456-110K");
    pics.getMemberPic().add("111456-100A");
    criteria.setMemberPics(pics);
    CommunitiesType communities = communityService.opQueryCommunities(criteria, audit);
    assertThat(communities.getCommunity().size(), is(2));
    assertThat(communitiesContains(communities, community1Id, community3Id), is(true));
    
    pics = new MemberPicsType();
    // Belongs to only one community
    pics.getMemberPic().add("111456-100A");
    criteria.setMemberPics(pics);
    communities = communityService.opQueryCommunities(criteria, audit);
    assertThat(communities.getCommunity().size(), is(1));
    assertThat(communitiesContains(communities, community3Id), is(true));
    
    pics = new MemberPicsType();
    // Belongs to two communities
    pics.getMemberPic().add("111456-163A");
    criteria.setMemberPics(pics);
    communities = communityService.opQueryCommunities(criteria, audit);
    assertThat(communities.getCommunity().size(), is(2));
    assertThat(communitiesContains(communities, community2Id, community3Id), is(true));
    // "OTHER" type should be filtered out
    criteria.setCommunityType("FAMILY");
    communities = communityService.opQueryCommunities(criteria, audit);
    assertThat(communities.getCommunity().size(), is(1));
    assertThat(communitiesContains(communities, community2Id), is(true));
  }
  
  private boolean communitiesContains(CommunitiesType communities, String...expectedCommunityIds) {
    List<String> communityIds = new ArrayList<String>();
    for (CommunityType community : communities.getCommunity()) {
      communityIds.add(community.getId());
    }
    return communityIds.containsAll(Arrays.asList(expectedCommunityIds));
  }
  
  private CommunityServicePortType getCommunityServicePort() {
    return new CommunityServiceFactory(TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_WS_USERNAME),
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_WS_PWD),
        TestPropertiesUtil.getProperty(TestPropertiesUtil.KOKU_SRV_LAYER_ENDPOINT_ADDRESS)).getCommunityService();
  }
  
  private AuditInfoType getAudit() {
    AuditInfoType audit = new AuditInfoType();
    audit.setComponent("kks");
    audit.setUserId("integration-test");
    return audit;
  }  
  
}
