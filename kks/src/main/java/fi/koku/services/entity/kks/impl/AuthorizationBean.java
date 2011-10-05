package fi.koku.services.entity.kks.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.services.entity.community.v1.CommunitiesType;
import fi.koku.services.entity.community.v1.CommunityQueryCriteriaType;
import fi.koku.services.entity.community.v1.CommunityServiceFactory;
import fi.koku.services.entity.community.v1.CommunityServicePortType;
import fi.koku.services.entity.community.v1.CommunityType;
import fi.koku.services.entity.community.v1.MemberPicsType;
import fi.koku.services.entity.community.v1.MemberType;
import fi.koku.services.entity.community.v1.MembersType;
import fi.koku.services.entity.community.v1.ServiceFault;
import fi.koku.services.entity.userinfo.v1.UserInfoService;
import fi.koku.services.entity.userinfo.v1.impl.UserInfoServiceDummyImpl;
import fi.koku.services.entity.userinfo.v1.model.Registry;

/**
 * Authorization services for KKS
 * 
 * @author Ixonos / tuomape
 */
@Stateless
public class AuthorizationBean implements Authorization {

  final public static String ENDPOINT = "http://localhost:8180";
  final public static String COMMUNITY_SERVICE_USER_ID = "marko";
  final public static String COMMUNITY_SERVICE_PASSWORD = "marko";
  final public static String ROLE_GUARDIAN = "guardian";
  final public static String ROLE_DEPENDANT = "dependant";
  final public static String COMMUNITY_TYPE_GUARDIAN_COMMUNITY = "guardian_community";
  private static final Logger LOG = LoggerFactory.getLogger(AuthorizationBean.class);

  @Override
  public Map<String, Registry> getAuthorizedRegistries(String user) {
    UserInfoService uis = new UserInfoServiceDummyImpl();
    Map<String, Registry> tmp = new HashMap<String, Registry>();
    List<Registry> register = uis.getUsersAuthorizedRegistries(user);
    for (Registry r : register) {
      tmp.put(r.getId(), r);
    }
    return tmp;
  }

  @Override
  public List<String> getAuthorizedRegistryNames(String user) {
    UserInfoService uis = new UserInfoServiceDummyImpl();
    List<String> tmp = new ArrayList<String>();
    List<Registry> register = uis.getUsersAuthorizedRegistries(user);
    for (Registry r : register) {
      tmp.add(r.getId());
    }
    return tmp;
  }

  @Override
  public boolean isMasterUser(String user, KksCollection collection) {
    return collection.getCreator().equals(user) || isParent(user, collection.getCustomer());
  }

  @Override
  public boolean isParent(String user, String customer) {
    return getChilds(user).contains(customer);
  }

  @Override
  public boolean hasAuthorizedRegisters(List<String> registers, String user) {
    Map<String, Registry> tmp = getAuthorizedRegistries(user);
    for (String s : registers) {
      if (tmp.containsKey(s)) {
        return true;
      }
    }
    return false;
  }

  /**
   * Gets user childs
   * 
   * @param user
   *          pic
   * @return user childs
   */
  private List<String> getChilds(String user) {
    List<String> childs = new ArrayList<String>();
    try {
      CommunityQueryCriteriaType communityQueryCriteria = new CommunityQueryCriteriaType();
      communityQueryCriteria.setCommunityType(COMMUNITY_TYPE_GUARDIAN_COMMUNITY);
      MemberPicsType mpt = new MemberPicsType();
      mpt.getMemberPic().add(user);
      communityQueryCriteria.setMemberPics(mpt);
      CommunitiesType communitiesType = null;
      communitiesType = getCommunityService().opQueryCommunities(communityQueryCriteria, getCommynityAuditInfo(user));

      if (communitiesType != null) {
        List<CommunityType> communities = communitiesType.getCommunity();
        for (CommunityType community : communities) {
          MembersType membersType = community.getMembers();
          List<MemberType> members = membersType.getMember();

          for (MemberType member : members) {
            if (member.getRole().equals(ROLE_DEPENDANT)) {
              childs.add(member.getPic());
            }
          }
        }
      }
    } catch (ServiceFault e) {
      LOG.error("Failed to get user childs", e);
    }
    return childs;
  }

  private CommunityServicePortType getCommunityService() {
    CommunityServiceFactory csf = new CommunityServiceFactory(COMMUNITY_SERVICE_USER_ID, COMMUNITY_SERVICE_PASSWORD,
        ENDPOINT);
    return csf.getCommunityService();
  }

  public fi.koku.services.entity.community.v1.AuditInfoType getCommynityAuditInfo(String user) {
    fi.koku.services.entity.community.v1.AuditInfoType a = new fi.koku.services.entity.community.v1.AuditInfoType();
    a.setComponent("KKS");
    a.setUserId(user);
    return a;
  }
}
