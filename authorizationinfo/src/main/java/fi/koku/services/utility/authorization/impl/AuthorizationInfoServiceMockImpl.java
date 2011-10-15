package i.koku.services.utility.authorization.impl;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.koku.KoKuFaultException;
import fi.koku.services.utility.authorization.v1.GroupQueryCriteriaType;
import fi.koku.services.utility.authorization.v1.GroupType;
import fi.koku.services.utility.authorization.v1.GroupsType;
import fi.koku.services.utility.authorization.v1.MemberPicsType;


public class AuthorizationInfoServiceMockImpl {
  private Logger logger = LoggerFactory.getLogger(AuthorizationInfoServiceMockImpl.class);

  private Map<String, List<GroupType>> registries = null;
  private Map<String, List<GroupType>> roles = null;
  private Map<String, List<GroupType>> units = null;
  private Map<String, List<GroupType>> groups = null;
  
  public AuthorizationInfoServiceMockImpl() {
    registries = initRegistries();
    roles = initRoles();
    units = initUnits();
    groups = initGroups();
  }

  public GroupsType getGroups(GroupQueryCriteriaType gqc) {
    GroupsType res = new GroupsType();
    List<GroupType> groupsList = null;
    
    String grpClass = gqc.getGroupClass();
    String uid = gqc.getMemberPics().getMemberPic().get(0);
    if("registry".equals(grpClass)) {
      groupsList = registries.get(uid);
    } else if("role".equals(grpClass)) {
      groupsList = roles.get(uid);
    } else if("unit".equals(grpClass)) {
      groupsList = units.get(uid);
    } else if("group".equals(grpClass)) {
      groupsList = groups.get(uid);
    } else {
      throw new KoKuFaultException(3001, "unsupported group type");
    }
    
    res.getGroup().addAll(groupsList);
    return res;
  }

  private Map<String, List<GroupType>> initRegistries() {
    Map<String, List<GroupType>> r = new HashMap<String, List<GroupType>>();
    String groupClass = "registry";

    r.put("777777-7777", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "777777-7777")));
    r.put("888888-8888", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "888888-8888")));
    return r;
  }

  private Map<String, List<GroupType>> initRoles() {
    Map<String, List<GroupType>> r = new HashMap<String, List<GroupType>>();
    String groupClass = "role";
    return r;
  }

  private Map<String, List<GroupType>> initUnits() {
    Map<String, List<GroupType>> r = new HashMap<String, List<GroupType>>();
    String groupClass = "unit";
    return r;
  }

  private Map<String, List<GroupType>> initGroups() {
    Map<String, List<GroupType>> r = new HashMap<String, List<GroupType>>();
    String groupClass = "group";
    return r;
  }

  private GroupType createGroup(String groupClass, String id, String name, String memberUid) {
    GroupType g = new GroupType();
    g.setClazz(groupClass);
    g.setId(id);
    g.setName(name);
    g.setMemberPics(new MemberPicsType());
    g.getMemberPics().getMemberPic().add(memberUid);
    return g;
  }
  
}
