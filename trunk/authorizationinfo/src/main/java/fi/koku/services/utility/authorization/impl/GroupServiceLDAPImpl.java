package fi.koku.services.utility.authorization.impl;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextProcessor;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;

import fi.koku.KoKuFaultException;
import fi.koku.services.utility.authorization.v1.GroupQueryCriteriaType;
import fi.koku.services.utility.authorization.v1.GroupType;
import fi.koku.services.utility.authorization.v1.GroupsType;
import fi.koku.services.utility.authorization.v1.MemberPicsType;

/**
 * LDAP implementation of the group service interface.
 * 
 * potential enhancements
 * - use OpenLDAP memberof overlay (long term)
 * - use Spring LDAP dynamic filters API
 * 
 * @author aspluma
 */
public class GroupServiceLDAPImpl implements GroupService {
  private Logger logger = LoggerFactory.getLogger(GroupServiceLDAPImpl.class);
  private SimpleLdapTemplate ldapTemplate;
  private Map<String, String> groupTypeToDIT;
  private String groupSearchBase;
  
  public GroupServiceLDAPImpl() {
    groupTypeToDIT = getGroupTypeToDITMap();
  }
  
  private Map<String, String> getGroupTypeToDITMap() {
    Map<String, String> map = new HashMap<String, String>();
    map.put("registry", "ou=registries");
    map.put("role", "ou=roles");
    map.put("unit", "ou=orgunits");
    map.put("group", "groups-int");
    return map;
  }
  
  @Override
  public GroupsType getGroups(GroupQueryCriteriaType gqc) {
    GroupsType g = new GroupsType();
    String searchBase = groupTypeToDIT.get(gqc.getGroupClass());
    if(searchBase == null)
      throw new KoKuFaultException(12345, "Invalid group query criteria: group class: "+gqc.getGroupClass());
    searchBase += ","+groupSearchBase;

    List<LdapPerson> persons = getPersonDnsByPics(gqc.getMemberPics().getMemberPic());
    if(persons.isEmpty())
      return g;
    Map<String, String> dnToPic = new HashMap<String, String>();
    for(LdapPerson p : persons) {
      dnToPic.put(p.getDn().toLowerCase(), p.getUid());
    }
    
    String q = getGroupsQuery(persons);
    logger.debug("getGroups: base: "+searchBase+", query: "+q.toString());
    List<GroupType> groups = ldapTemplate.search(searchBase, q, new GroupMapper(dnToPic));
    logger.debug("groups: "+groups.size());
    g.getGroup().addAll(groups);
    return g;
  }
  
  // query groups by a list of members
  String getGroupsQuery(List<LdapPerson> persons) {
    StringBuilder q = new StringBuilder("(&(objectclass=groupofnames)");
    boolean hasMultipleCriteria = persons.size() > 1;
    if(hasMultipleCriteria)
      q.append("(|");
    for(Iterator<LdapPerson> i = persons.iterator(); i.hasNext(); ) {
      q.append("(member="+i.next().getDn()+")");
    }
    if(hasMultipleCriteria)
      q.append(")");
    q.append(")");
    return q.toString();
  }
  
  // query a list of persons by uid (i.e. PIC)
  private List<LdapPerson> getPersonDnsByPics(List<String> pics) {
    SearchControls ctrl = new SearchControls();
    ctrl.setReturningAttributes(new String[] {"uid"});
    ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
    String q = getPersonsQuery(pics);
    logger.debug("getPersonDnsByPics: query: "+q.toString());
    List<LdapPerson> persons = ldapTemplate.search("", q, ctrl, new LdapPersonMapper(), new DirContextProcessorNoop());
    logger.debug("persons: "+persons.size());
    return persons;
  }

  String getPersonsQuery(List<String> pics) {
    StringBuilder q = new StringBuilder("(&(objectclass=inetorgperson)");
    boolean hasMultipleCriteria = pics.size() > 1;
    if(hasMultipleCriteria)
      q.append("(|");
    for(Iterator<String> i = pics.iterator(); i.hasNext(); ) {
      q.append("(uid="+i.next()+")");
    }
    if(hasMultipleCriteria)
      q.append(")");
    q.append(")");
    return q.toString();
  }

  private class LdapPersonMapper implements ParameterizedContextMapper<LdapPerson> {
    @Override
    public LdapPerson mapFromContext(Object ctx) {
      DirContextAdapter a = (DirContextAdapter) ctx;
      return new LdapPerson(a.getNameInNamespace(), a.getStringAttribute("uid"));
    }
  }
  
  static class LdapPerson {
    private String dn;
    private String uid;
    
    public LdapPerson(String dn, String uid) {
      this.dn = dn;
      this.uid = uid;
    }

    public String getDn() {
      return dn;
    }

    public String getUid() {
      return uid;
    }
  }
  
  private class GroupMapper implements ParameterizedContextMapper<GroupType> {
    private Map<String, String> dnToPicMap;
    
    public GroupMapper(Map<String, String> dnToPicMap) {
      this.dnToPicMap = dnToPicMap;
    }
    
    @Override
    public GroupType mapFromContext(Object ctx) {
      DirContextAdapter a = (DirContextAdapter) ctx;
      String gn = a.getStringAttribute("cn");
      GroupType gt = new GroupType();
      gt.setId(gn);
      gt.setName(gn);
      String[] members = a.getStringAttributes("member");
      if(members != null) {
        gt.setMemberPics(new MemberPicsType());
        for(String m : members) {
          String pic = dnToPicMap.get(m);
          if(pic != null) {
            gt.getMemberPics().getMemberPic().add(pic);
          }
        }
      }
      return gt;
    }
  }

  private static class DirContextProcessorNoop implements DirContextProcessor {
    @Override
    public void postProcess(DirContext ctx) throws NamingException {
    }
    @Override
    public void preProcess(DirContext ctx) throws NamingException {
    }
  }
  
  public void setLdapTemplate(SimpleLdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  public void setGroupSearchBase(String groupSearchBase) {
    this.groupSearchBase = groupSearchBase;
  }
  
}
