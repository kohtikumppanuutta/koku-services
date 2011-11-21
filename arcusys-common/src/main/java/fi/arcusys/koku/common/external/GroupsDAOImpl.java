package fi.arcusys.koku.common.external;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.BasicAttribute;
import javax.naming.directory.BasicAttributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.ModificationItem;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.soa.Group;
import fi.arcusys.koku.common.soa.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 17, 2011
 */
@Stateless
public class GroupsDAOImpl implements GroupsDAO {

    private final static Logger logger = LoggerFactory.getLogger(GroupsDAOImpl.class); 
    
    //  @Resource(mappedName = "external/ldap/groups")
//    private DirContext dirContext;
    private String groupsSearchFilter = "(objectclass=groupOfNames)";
    private String groupNameAttribute = "cn";
    private String groupUidAttribute = "cn";
    private String usernameAttribute = "cn";

    @EJB
    private CustomerServiceDAO customerDao;
    
    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<Group> searchGroups(String searchString, int limit) {
        final String filterAttrName = groupNameAttribute;
        final String filterAttrValue = "*" + searchString + "*";
        return doSearchGroups(filterAttrName, filterAttrValue);
    }

    private List<Group> doSearchGroups(final String filterAttrName,
            final String filterAttrValue) {
        try {
            InitialContext iniCtx = new InitialContext();
            DirContext dirContext = (DirContext)iniCtx.lookup("external/ldap/groups");
            try {
                final List<Group> result = new ArrayList<Group>();
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                final NamingEnumeration<SearchResult> results = dirContext.search("", 
                        groupsSearchFilter.replaceAll("#attrName#", filterAttrName)
                        .replaceAll("#attrValue#", filterAttrValue), controls);
                try {
                    while (results.hasMore()) {
                        final SearchResult searchResult = results.next();
                        final Attributes attributes = searchResult.getAttributes();

                        final Group group = new Group();
                        group.setGroupName(getAttributeValue(attributes, filterAttrName));
                        group.setGroupUid(getAttributeValue(attributes, groupUidAttribute));
                        result.add(group);
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

    /**
     * @param groupUid
     * @return
     */
    @Override
    public List<User> getUsersByGroupUid(String groupUid) {
        final List<User> result = new ArrayList<User>();
        final Pattern pattern = Pattern.compile(usernameAttribute + "=([^,]+)\\,");
        
        for (final String member : getGroupMembers(groupUidAttribute, groupUid)) {
            final Matcher matcher = pattern.matcher(member);
            if (matcher.find()) {
                final String ldapName = matcher.group(1);
                result.add(customerDao.getKunpoUserInfoBySsn(customerDao.getSsnByKunpoName(ldapName)));
            } else {
                logger.info("Can't get user uid: " + member);
            }
        }
        return result;
    }

    private List<String> getGroupMembers(final String filterAttrName, final String filterAttrValue) {
        try {
            InitialContext iniCtx = new InitialContext();
            DirContext dirContext = (DirContext)iniCtx.lookup("external/ldap/groups");
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

    /**
     * @param userDn
     * @param groupUid
     */
    @Override
    public void addUserToGroup(String userDn, String groupUid) {
        if (getGroupMembers(groupUidAttribute, groupUid).contains(userDn)) {
            logger.info("User " + userDn + " is already in group " + groupUid);
            return;
        }
        try {
            InitialContext iniCtx = new InitialContext();
            DirContext dirContext = (DirContext)iniCtx.lookup("external/ldap/groups");
    
            try {
                dirContext.modifyAttributes(dirContext.composeName(groupUidAttribute + "=" + groupUid, dirContext.getNameInNamespace()), 
                        DirContext.ADD_ATTRIBUTE, new BasicAttributes("member", userDn));
            } finally {
                dirContext.close();
            }
        } catch (NamingException e) {
            logger.error("Failed to add user " + userDn + " to group " + groupUid, e);
            throw new RuntimeException(e);
        } 
    }

    /**
     * @param oldUserDn
     * @param newUserDn
     */
    @Override
    public void updateMembership(String oldUserDn, String newUserDn) {
        final List<Group> groups = doSearchGroups("member", oldUserDn);
        if (groups == null || groups.isEmpty()) {
            return;
        }
        
        final Map<String, BasicAttributes> groupsForUpdate = new HashMap<String, BasicAttributes>();
        for (final Group group : groups) {
            final Set<String> members = new HashSet<String>(getGroupMembers(groupUidAttribute, group.getGroupUid()));
            members.remove(oldUserDn);
            members.add(newUserDn);
            final BasicAttributes attributes = new BasicAttributes();
            final BasicAttribute member = new BasicAttribute("member");
            for (final String memberDn : members) {
                member.add(memberDn);
            }
            attributes.put(member);
            groupsForUpdate.put(group.getGroupUid(), attributes);
        }
        
        try {
            InitialContext iniCtx = new InitialContext();
            DirContext dirContext = (DirContext)iniCtx.lookup("external/ldap/groups");
            
            try {
                for (final Map.Entry<String, BasicAttributes> entry : groupsForUpdate.entrySet()) {
                    dirContext.modifyAttributes(dirContext.composeName(groupUidAttribute + "=" + entry.getKey(), dirContext.getNameInNamespace()), 
                            DirContext.REPLACE_ATTRIBUTE, entry.getValue());
                }
            } finally {
                dirContext.close();
            }
        } catch (NamingException e) {
            logger.error("Failed to update user membership: oldUserDn '" + oldUserDn + "', newUserDn '" + newUserDn +  ", groups " + groupsForUpdate.keySet(), e);
            throw new RuntimeException(e);
        } 
    }
}
