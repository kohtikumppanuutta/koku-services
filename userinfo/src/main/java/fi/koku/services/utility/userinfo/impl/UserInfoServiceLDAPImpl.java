package fi.koku.services.utility.userinfo.impl;

import java.util.Arrays;
import java.util.List;

import javax.naming.NamingException;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.ldap.core.DirContextAdapter;
import org.springframework.ldap.core.DirContextProcessor;
import org.springframework.ldap.core.simple.ParameterizedContextMapper;
import org.springframework.ldap.core.simple.SimpleLdapTemplate;
import org.springframework.ldap.filter.AndFilter;
import org.springframework.ldap.filter.EqualsFilter;
import org.springframework.ldap.filter.OrFilter;

import fi.koku.services.utility.user.v1.UserIdsQueryParamType;
import fi.koku.services.utility.user.v1.UserPicsQueryParamType;
import fi.koku.services.utility.user.v1.UserType;
import fi.koku.services.utility.user.v1.UsersType;
import fi.koku.services.utility.userinfo.impl.model.LDAPUser;

/**
 * KoKu userInfo service LDAP implementation class.
 * 
 * @author hanhian
 */
public class UserInfoServiceLDAPImpl implements UserInfoService {

  private Logger logger = LoggerFactory.getLogger(UserInfoServiceLDAPImpl.class);
  private SimpleLdapTemplate ldapTemplate;

  @Override
  public UsersType getUsersByIds(UserIdsQueryParamType idsType) {
    List<LDAPUser> users = getUsers(getUserQuery(idsType.getId(), "cn"));
    // TODO the group check is not efficient.
    // use member overlay in the LDAP and take it to use in here to speed things
    // up
    return checkGroup(users, idsType.getDomain());
  }

  @Override
  public UsersType getUsersByPics(UserPicsQueryParamType picsType) {
    List<LDAPUser> users = getUsers(getUserQuery(picsType.getPic(), "uid"));
    // TODO the group check is not efficient.
    // use member overlay in the LDAP and take it to use in here to speed things
    // up
    return checkGroup(users, picsType.getDomain());
  }

  public void setLdapTemplate(SimpleLdapTemplate ldapTemplate) {
    this.ldapTemplate = ldapTemplate;
  }

  private List<LDAPUser> getUsers(String query) {
    SearchControls ctrl = new SearchControls();
    ctrl.setReturningAttributes(new String[] { "cn", "givenName", "sn", "uid", "mail" });
    ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
    logger.debug("getUser: query: " + query.toString());

    return ldapTemplate.search("", query, ctrl, new LdapPersonMapper(), new DirContextProcessorNoop());
  }

  private UsersType checkGroup(List<LDAPUser> users, String group) {
    SearchControls ctrl = new SearchControls();
    ctrl.setReturningAttributes(new String[] { "member" });
    ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);

    String query = getGroupQuery(group);
    logger.debug("getGroup: query: " + query.toString());

    UsersType usersType = new UsersType();
    List<UserType> userTypes = usersType.getUser();

    List<List<String>> groups = ldapTemplate.search("", query, ctrl, new LdapGroupMapper(),
        new DirContextProcessorNoop());

    if (groups != null) {
      List<String> members = groups.get(0);
      if (members != null) {
        if (users != null) {
          for (LDAPUser ldapUser : users) {            
            if (members.contains(ldapUser.getDn())) {
              userTypes.add(ldapUser);
            }
          }
        }
      }
    }
    return usersType;
  }

  private String getUserQuery(List<String> criteria, String queryType) {
    // Add criteria to OR filter
    OrFilter orFilter = new OrFilter();
    for (String crit : criteria) {
      orFilter.or(new EqualsFilter(queryType, crit));
    }

    // Combine filters to query
    AndFilter mainFilter = new AndFilter();
    mainFilter.and(new EqualsFilter("objectclass", "inetorgperson"));
    mainFilter.and(orFilter);

    return mainFilter.encode();
  }

  private String getGroupQuery(String group) {
    // create group filter to fetch all members of the group
    // this might cause trouble if there is lots of members.
    AndFilter groupFilter = new AndFilter();
    groupFilter.and(new EqualsFilter("objectclass", "groupOfNames"));

    // add group check
    if (UserInfoServiceConstants.USER_INFO_SERVICE_DOMAIN_CUSTOMER.equals(group)) {
      groupFilter.and(new EqualsFilter("cn", "kuntalainen"));
    } else if (UserInfoServiceConstants.USER_INFO_SERVICE_DOMAIN_OFFICER.equals(group)) {
      groupFilter.and(new EqualsFilter("cn", "virkailija"));
    }

    return groupFilter.encode();
  }

  private class LdapPersonMapper implements ParameterizedContextMapper<LDAPUser> {
    @Override
    public LDAPUser mapFromContext(Object ctx) {
      DirContextAdapter a = (DirContextAdapter) ctx;
      LDAPUser emp = new LDAPUser();
      emp.setUserId(a.getStringAttribute("cn"));
      emp.setFirstname(a.getStringAttribute("givenName"));
      emp.setLastname(a.getStringAttribute("sn"));
      emp.setPic(a.getStringAttribute("uid"));
      emp.setEmail(a.getStringAttribute("mail"));
      emp.setDn(a.getNameInNamespace());
      return emp;
    }
  }

  private class LdapGroupMapper implements ParameterizedContextMapper<List<String>> {
    @Override
    public List<String> mapFromContext(Object ctx) {
      DirContextAdapter a = (DirContextAdapter) ctx;
      return Arrays.asList(a.getStringAttributes("member"));
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
}