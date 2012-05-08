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
package fi.koku.services.utility.authorization.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
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

public class AuthorizationInfoServiceMockImpl implements GroupService {
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

  /* (non-Javadoc)
   * @see fi.koku.services.utility.authorization.impl.GroupService#getGroups(fi.koku.services.utility.authorization.v1.GroupQueryCriteriaType)
   */
  @Override
  public GroupsType getGroups(GroupQueryCriteriaType gqc) {
    GroupsType res = new GroupsType();
    List<GroupType> groupsList = new ArrayList<GroupType>();

    String grpClass = gqc.getGroupClass();
    String uid = gqc.getMemberPics().getMemberPic().get(0);
    if ("registry".equals(grpClass)) {
      groupsList.addAll(getGroups(registries, uid));
    } else if ("role".equals(grpClass)) {
      groupsList.addAll(getGroups(roles, uid));
    } else if ("unit".equals(grpClass)) {
      groupsList.addAll(getGroups(units, uid));
      groupsList.add(createGroup("unit", "kk.servicearea.childHealth", "Health care unit of Porolahti", uid));
    } else if ("group".equals(grpClass)) {
      groupsList.addAll(getGroups(groups, uid));
      groupsList.add(createGroup("group", "gid1",
          "City region wide day care workgroup - consists of people from serveral organization units", uid));
    } else {
      throw new KoKuFaultException(3001, "unsupported group type");
    }

    res.getGroup().addAll(groupsList);
    return res;
  }

  private List<GroupType> getGroups(Map<String, List<GroupType>> groupMap, String key) {
    List<GroupType> r = groupMap.get(key);
    if (r == null)
      r = Collections.emptyList();
    return r;
  }

  private Map<String, List<GroupType>> initRegistries() {
    Map<String, List<GroupType>> r = new HashMap<String, List<GroupType>>();
    String groupClass = "registry";

    //healthcare
    r.put("101010-1001", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1001")));
    r.put("101010-1002", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1002")));
    r.put("101010-1003", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1003")));
    r.put("101010-1004", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1004")));
    r.put("101010-1005", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1005")));
    r.put("101010-1006", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1006")));
    r.put("101010-1007", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1007")));
    r.put("101010-1008", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1008")));
    r.put("101010-1009", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1009")));
    r.put("101010-1010", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1010")));
    r.put("101010-1011", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1011")));
    r.put("101010-1012", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1012")));
    r.put("101010-1013", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1013")));
    r.put("101010-1014", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1014")));
    r.put("101010-1015", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1015")));
    r.put("101010-1016", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1016")));
    r.put("101010-1017", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1017")));
    r.put("101010-1018", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1018")));
    r.put("101010-1019", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1019")));
    r.put("101010-1020", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "101010-1020")));
    
    //daycare
    r.put("202020-2001", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2001")));
    r.put("202020-2002", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2002")));
    r.put("202020-2003", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2003")));
    r.put("202020-2004", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2004")));
    r.put("202020-2005", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2005")));
    r.put("202020-2006", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2006")));
    r.put("202020-2007", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2007")));
    r.put("202020-2008", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2008")));
    r.put("202020-2009", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2009")));
    r.put("202020-2010", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2010")));
    r.put("202020-2011", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2011")));
    r.put("202020-2012", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2012")));
    r.put("202020-2013", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2013")));
    r.put("202020-2014", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2014")));
    r.put("202020-2015", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "202020-2015")));
    
    r.put("777777-7777", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "777777-7777")));
    r.put("282828-2828", Arrays.asList(createGroup(groupClass, "daycareregistry", "Paivahoidon rekisteri", "282828-2828")));
   

    r.put("888888-8888", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "888888-8888")));
    r.put("292929-2929", Arrays.asList(createGroup(groupClass, "healthcareregistry", "Potilastietorekisteri", "292929-2929")));
    return r;
  }

  private Map<String, List<GroupType>> initRoles() {
    Map<String, List<GroupType>> r = new HashMap<String, List<GroupType>>();
    String groupClass = "role";
    r.put("101010-1010", Arrays.asList(createGroup(groupClass, "ROLE_LOK_ADMIN", "ROLE_LOK_ADMIN", "101010-1010")));
    r.put("121212-1212",
        Arrays.asList(createGroup(groupClass, "ROLE_LOK_LOG_ADMIN", "ROLE_LOK_LOG_ADMIN", "121212-1212")));
    return r;
  }

  private Map<String, List<GroupType>> initUnits() {
    Map<String, List<GroupType>> r = new HashMap<String, List<GroupType>>();
    String groupClass = "unit";
    
    
    //healthcare
    r.put("101010-1001", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1001")));
    r.put("101010-1002", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1002")));
    r.put("101010-1003", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1003")));
    r.put("101010-1004", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1004")));
    r.put("101010-1005", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1005")));
    r.put("101010-1006", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1006")));
    r.put("101010-1007", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1007")));
    r.put("101010-1008", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1008")));
    r.put("101010-1009", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1009")));
    r.put("101010-1010", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1010")));
    r.put("101010-1011", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1011")));
    r.put("101010-1012", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1012")));
    r.put("101010-1013", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1013")));
    r.put("101010-1014", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1014")));
    r.put("101010-1015", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1015")));
    r.put("101010-1016", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1016")));
    r.put("101010-1017", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1017")));
    r.put("101010-1018", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1018")));
    r.put("101010-1019", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1019")));
    r.put("101010-1020", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth", "Health care unit of Porolahti", "101010-1020")));
   
    
    //daycare
    r.put("202020-2001", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2001")));
    r.put("202020-2002", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2002")));
    r.put("202020-2003", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2003")));
    r.put("202020-2004", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2004")));
    r.put("202020-2005", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2005")));
    r.put("202020-2006", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2006")));
    r.put("202020-2007", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2007")));
    r.put("202020-2008", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2008")));
    r.put("202020-2009", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2009")));
    r.put("202020-2010", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2010")));
    r.put("202020-2011", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2011")));
    r.put("202020-2012", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2012")));
    r.put("202020-2013", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2013")));
    r.put("202020-2014", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2014")));
    r.put("202020-2015", Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "202020-2015")));
      
    
    r.put("777777-7777",
        Arrays.asList(createGroup(groupClass, "kk.servicearea.daycare", "Day care unit of Porolahti", "777777-7777")));
    r.put("888888-8888", Arrays.asList(createGroup(groupClass, "kk.servicearea.childHealth",
        "Health care unit of Porolahti", "888888-8888")));
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
