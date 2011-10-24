package fi.arcusys.koku.common.external;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
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
    private DirContext dirContext;
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
        final String filterAttrName = groupUidAttribute;
        final String filterAttrValue = groupUid;
        try {
            final List<User> result = new ArrayList<User>();
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            final NamingEnumeration<SearchResult> results = dirContext.search("", 
                    groupsSearchFilter.replaceAll("#attrName#", filterAttrName)
                    .replaceAll("#attrValue#", filterAttrValue), controls);
            try {
                if (results.hasMore()) {
                    final SearchResult searchResult = results.next();
                    final Attributes attributes = searchResult.getAttributes();

                    final Pattern pattern = Pattern.compile(usernameAttribute + "=([^,]+)\\,");
                    
                    final Attribute attribute = attributes.get("member");
                    for (int i = 0; i < attribute.size(); i++) {
                        final String member = (String)attribute.get(i);
                        final Matcher matcher = pattern.matcher(member);
                        if (matcher.find()) {
                            final String ldapName = matcher.group(1);
                            result.add(customerDao.getKunpoUserInfoBySsn(customerDao.getSsnByKunpoName(ldapName)));
                        } else {
                            logger.info("Can't get user uid: " + member);
                        }
                    }
                }
                return result;
            } finally {
                results.close();
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
}
