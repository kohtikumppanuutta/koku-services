package fi.arcusys.koku.common.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.soa.Group;
import fi.arcusys.koku.common.soa.Role;
import fi.arcusys.koku.common.soa.UserInfo;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.ServiceFault;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 2, 2011
 */
@Stateless
public class LdapDAOImpl implements LdapDAO {

    private final static Logger logger = LoggerFactory.getLogger(LdapDAOImpl.class);

    //  @Resource(mappedName = "external/ldap/myldap")
//  private DirContext dirContext;
    
    private String ssnAttributeName;
    private String usernameAttributeName;
    private String userSearchFilter;
  
    private String kunpoUserBaseDn;
    private String citizensGroupUid;
    private String employeesGroupUid;

    private String groupsSearchFilter;
    private String groupNameAttribute;
    private String groupUidAttribute;

    /**
     * @param employeePortalName
     * @return
     */
    @Override
    public String getSsnByLooraName(String employeePortalName) {
        return getSsnByLdapName(employeePortalName, employeesGroupUid);
    }

    /**
     * @param citizenPortalName
     * @return
     */
    @Override
    public String getSsnByKunpoName(String citizenPortalName) {
        return getSsnByLdapName(citizenPortalName, citizensGroupUid);
    }

    private String getLdapNameBySsn(final String ssn, final String systemRole) {
        return getUsersAttrNameByFilter(ssnAttributeName, ssn, usernameAttributeName, systemRole);
    }

    private String getSsnByLdapName(final String ldapName, final String systemRole) {
        return getUsersAttrNameByFilter(usernameAttributeName, ldapName, ssnAttributeName, systemRole);
    }

    private String getUsersAttrNameByFilter(final String filterAttrName, final String filterAttrValue, final String searchAttrName, final String systemRole) {
        try {
            final Map<String, String> dnToAttrValue = new HashMap<String, String>();
            DirContext dirContext = createUsersDirContext();
            try {
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                controls.setReturningAttributes(new String[] {"dn", searchAttrName});
                final NamingEnumeration<SearchResult> results = dirContext.search("", 
                        userSearchFilter.replaceAll("#attrName#", filterAttrName)
                                        .replaceAll("#attrValue#", filterAttrValue), controls);
                try {
                    while (results.hasMore()) {
                        final SearchResult searchResult = results.next();
                        final Attributes attributes = searchResult.getAttributes();
                        final Attribute attr = attributes.get(searchAttrName);
                        if (attr != null && attr.get() != null) {
                            dnToAttrValue.put(searchResult.getNameInNamespace(), (String) attr.get());
                        }
                    }
                } finally {
                    results.close();
                }
            } finally {
                dirContext.close();
            }
            for (final Iterator<Map.Entry<String, String>> iter = dnToAttrValue.entrySet().iterator(); iter.hasNext();) {
                if (!isMemberOf(iter.next().getKey(), systemRole)) {
                    iter.remove();
                }
            } 
            if (dnToAttrValue.size() > 1) {
                throw new IllegalStateException("Multiple values found in users context for filterAttrName = " + 
                        filterAttrName + " and filterAttrValue = " + filterAttrValue + ": " + dnToAttrValue);
            } else if (!dnToAttrValue.isEmpty()) {
                return dnToAttrValue.values().iterator().next();
            }
        } catch (NamingException e) {
            logger.error(null, e);
            throw new RuntimeException(e);
        }
        return null;
    }
    
    /**
     * @param key
     * @param systemRole
     * @return
     */
    private boolean isMemberOf(String dn, String systemRole) {
        return doSearchGroups("member", dn, "member", GroupType.SystemGroup).containsKey(systemRole);
    }

    @Override
    public void createKunpoUserInLdap(final String kunpoUsername, final String ssn, final String firstName, final String lastName) {
        try {
            Attributes personAttributes = new BasicAttributes();
            BasicAttribute personBasicAttribute = new BasicAttribute(
                    "objectclass");
            personBasicAttribute.add("inetOrgPerson");
            personBasicAttribute.add("top");
            personAttributes.put(personBasicAttribute);

            personAttributes.put("givenName", firstName);
            personAttributes.put("cn", kunpoUsername);
            personAttributes.put("sn", lastName);
            personAttributes.put("description", ssn);
            personAttributes.put("uid", ssn);
            personAttributes.put("userPassword", "test");

            final String newContactDN = getUserDn(kunpoUsername);

            DirContext dirContext = createUsersDirContext();
            try {
                logger.info("Creating new user in ldap: " + newContactDN);
                dirContext.bind(newContactDN, null, personAttributes);
            } finally {
                dirContext.close();
            }
            addUserToSystemGroup(getUserDnWithBase(newContactDN), citizensGroupUid);
        } catch (NamingException e) {
            logger.error("Failed to create new user in ldap by portal name '" + kunpoUsername + "' and ssn '" + ssn + "'", e);
            throw new RuntimeException(e);
        } 
    }

    private String getUserDn(final String kunpoUsername) {
        return usernameAttributeName + "=" + kunpoUsername;
    }
    
    @Override
    public void updateKunpoLdapName(final String oldKunpoName, final String newKunpoName) {
        try {
            final String oldName = getUserDn(oldKunpoName);
            final String newName = getUserDn(newKunpoName);
            final String userOldDn = getUserDnWithBase(oldName);
            final String userNewDn = getUserDnWithBase(newName);

            DirContext dirContext = createUsersDirContext();
            try {
                dirContext.rename(oldName, newName);
            } finally {
                dirContext.close();
            }
            updateMembership(userOldDn, userNewDn);
        } catch (NamingException e) {
            logger.error("Failed to update user in ldap: old name '" + oldKunpoName + "', new name '" + newKunpoName + "'", e);
            throw new RuntimeException(e);
        }
    }

    private String getUserDnWithBase(final String oldName) {
        return oldName + "," + kunpoUserBaseDn;
    }

    private DirContext createUsersDirContext() throws NamingException {
        InitialContext iniCtx = new InitialContext();
        DirContext dirContext = (DirContext) iniCtx.lookup("external/ldap/myldap");
        return (DirContext)dirContext.lookup("ou=People");
    } 
    
    // Groups
    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public Map<String, String> searchGroups(String searchString) {
        final String filterAttrName = groupNameAttribute;
        final String filterAttrValue = "*" + searchString + "*";
        return doSearchGroups(filterAttrName, filterAttrValue, filterAttrName, GroupType.CommunityGroup);
    }
    
    private Map<String, String> doSearchGroups(final String filterAttrName,
            final String filterAttrValue, final String returnAttrValue, final GroupType groupType) {
        try {
            DirContext dirContext = getGroupsContext(groupType);
            try {
                final Map<String, String> result = new HashMap<String, String>();
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                final NamingEnumeration<SearchResult> results = dirContext.search("", 
                        groupsSearchFilter.replaceAll("#attrName#", filterAttrName)
                        .replaceAll("#attrValue#", filterAttrValue), controls);
                try {
                    while (results.hasMore()) {
                        final SearchResult searchResult = results.next();
                        final Attributes attributes = searchResult.getAttributes();

                        result.put(getAttributeValue(attributes, groupUidAttribute), getAttributeValue(attributes, filterAttrName));
                    }
                    return result;
                } finally {
                    results.close();
                }
            } finally {
                dirContext.close();
            }
        } catch (NamingException e) {
            logger.error(null, e);
            throw new RuntimeException(e);
        }
    }

    private DirContext getCommunityGroupsContext() throws NamingException {
        InitialContext iniCtx = new InitialContext();
        DirContext dirContext = (DirContext)iniCtx.lookup("external/ldap/myldap");
        DirContext communities = (DirContext)dirContext.lookup("ou=KokuCommunities");
        return (DirContext)communities.lookup("ou=Groups");
    }

    private DirContext getRolesContext() throws NamingException {
        InitialContext iniCtx = new InitialContext();
        DirContext dirContext = (DirContext)iniCtx.lookup("external/ldap/myldap");
        DirContext communities = (DirContext)dirContext.lookup("ou=KokuCommunities");
        return (DirContext)communities.lookup("ou=Roles");
    }

    private DirContext getSystemGroupsContext() throws NamingException {
        InitialContext iniCtx = new InitialContext();
        DirContext dirContext = (DirContext)iniCtx.lookup("external/ldap/myldap");
        return (DirContext)dirContext.lookup("ou=Groups");
    }

    private List<String> getGroupMembers(final String filterAttrName, final String filterAttrValue, final GroupType groupType) {
        try {
            DirContext dirContext = getGroupsContext(groupType);
            try {
                final List<String> result = new ArrayList<String>();
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                final NamingEnumeration<SearchResult> results = dirContext.search("", 
                        groupsSearchFilter.replaceAll("#attrName#", filterAttrName)
                        .replaceAll("#attrValue#", filterAttrValue), controls);
                try {
                    if (results.hasMore()) {
                        final SearchResult searchResult = results.next();
                        final Attributes attributes = searchResult.getAttributes();

                        final Attribute attribute = attributes.get("member");
                        for (int i = 0; i < attribute.size(); i++) {
                            result.add((String)attribute.get(i));
                        }
                    }
                    return result;
                } finally {
                    results.close();
                }
            } finally {
                dirContext.close();
            }
        } catch (NamingException e) {
            logger.error(null, e);
            throw new RuntimeException(e);
        }
    }

    private String getAttributeValue(final Attributes attributes, final String attrName) throws NamingException {
        final Attribute attr = attributes.get(attrName);
        if (attr != null) {
            return (String) attr.get();
        }
        return null;
    }

    private void addUserToSystemGroup(String userDn, String groupUid) {
        if (getGroupMembers(groupUidAttribute, groupUid, GroupType.SystemGroup).contains(userDn)) {
            logger.info("User " + userDn + " is already in group " + groupUid);
            return;
        }
        try {
            DirContext dirContext = getGroupsContext(GroupType.SystemGroup);
    
            try {
                dirContext.modifyAttributes(groupUidAttribute + "=" + groupUid, 
                        DirContext.ADD_ATTRIBUTE, new BasicAttributes("member", userDn));
            } finally {
                dirContext.close();
            }
        } catch (NamingException e) {
            logger.error("Failed to add user " + userDn + " to group " + groupUid, e);
            throw new RuntimeException(e);
        } 
    }

    private void updateMembership(String oldUserDn, String newUserDn) {
        for (final GroupType groupType : GroupType.values()) {
            doUpdateMembership(oldUserDn, newUserDn, groupType); 
        }
    }

    private void doUpdateMembership(String oldUserDn, String newUserDn, final GroupType groupType) {
        final Map<String, String> groups = doSearchGroups("member", oldUserDn, "member", groupType);
        if (groups == null || groups.isEmpty()) {
            return;
        }
        
        final Map<String, BasicAttributes> groupsForUpdate = new HashMap<String, BasicAttributes>();
        for (final String groupUid : groups.keySet()) {
            final Set<String> members = new HashSet<String>(getGroupMembers(groupUidAttribute, groupUid, groupType));
            members.remove(oldUserDn);
            members.add(newUserDn);
            final BasicAttributes attributes = new BasicAttributes();
            final BasicAttribute member = new BasicAttribute("member");
            for (final String memberDn : members) {
                member.add(memberDn);
            }
            attributes.put(member);
            groupsForUpdate.put(groupUid, attributes);
        }
        
        try {
            DirContext dirContext = getGroupsContext(groupType);
            
            try {
                for (final Map.Entry<String, BasicAttributes> entry : groupsForUpdate.entrySet()) {
                    dirContext.modifyAttributes(groupUidAttribute + "=" + entry.getKey(), DirContext.REPLACE_ATTRIBUTE, entry.getValue());
                }
            } finally {
                dirContext.close();
            }
        } catch (NamingException e) {
            logger.error("Failed to update user membership: oldUserDn '" + oldUserDn + "', newUserDn '" + newUserDn +  ", groups " + groupsForUpdate.keySet(), e);
            throw new RuntimeException(e);
        }
    }

    private DirContext getGroupsContext(final GroupType groupType)
            throws NamingException {
        DirContext dirContext;
        if (groupType == GroupType.SystemGroup) {
            dirContext = getSystemGroupsContext();
        } else if (groupType == GroupType.CommunityGroup) {
            dirContext = getCommunityGroupsContext();
        } else { // Roles
            dirContext = getRolesContext();
        }
        return dirContext;
    }
    
    /**
     * @param groupUid
     * @return
     */
    @Override
    public List<String> getGroupMembers(String groupUid) {
        return doGetGroupMembers(groupUid, GroupType.CommunityGroup);
    }

    private List<String> doGetGroupMembers(String groupUid, final GroupType groupType) {
        final List<String> result = new ArrayList<String>();
        final Pattern pattern = Pattern.compile(usernameAttributeName + "=([^,]+)\\,");
        
        for (final String member : getGroupMembers(groupUidAttribute, groupUid, groupType)) {
            final Matcher matcher = pattern.matcher(member);
            if (matcher.find()) {
                result.add(matcher.group(1));
            } else {
                logger.info("Can't get user uid: " + member);
            }
        }
        return result;
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public String getLooraNameBySsn(String ssn) {
        return getLdapNameBySsn(ssn, employeesGroupUid);
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public String getKunpoNameBySsn(String ssn) {
        return getLdapNameBySsn(ssn, citizensGroupUid);
    }

    /**
     * @param employeeName
     * @return
     */
    @Override
    public List<Role> getEmployeeRoles(String employeeName) {
        return createRolesFromSearchResult(doSearchGroups("member", getUserDnWithBase(getUserDn(employeeName)), "description", GroupType.Roles));
    }

    private List<Role> createRolesFromSearchResult(
            final Map<String, String> roles) {
        final List<Role> result = new ArrayList<Role>();
        for (final Map.Entry<String, String> entry : roles.entrySet() ) {
            final Role role = new Role();
            role.setRoleUid(entry.getKey());
            if (entry.getValue() != null && !entry.getValue().isEmpty()) {
                role.setRoleName(entry.getKey());
            } else {
                role.setRoleName(entry.getValue());
            }
            result.add(role);
        }
        return result;
    }

    /**
     * @param searchString
     * @return
     */
    @Override
    public List<Role> searchRoles(String searchString) {
        return createRolesFromSearchResult(doSearchGroups(groupNameAttribute, "*" + searchString + "*", groupNameAttribute, GroupType.Roles));
    }
    
    private enum GroupType {
        SystemGroup, CommunityGroup, Roles;
    }

    /**
     * @param groupUid
     * @return
     */
    @Override
    public List<String> getRoleMembers(String groupUid) {
        return doGetGroupMembers(groupUid, GroupType.Roles);
    }
}
