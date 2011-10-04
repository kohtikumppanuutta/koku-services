package fi.arcusys.koku.common.soa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.naming.NamingEnumeration;
import javax.naming.NamingException;
import javax.naming.directory.Attribute;
import javax.naming.directory.Attributes;
import javax.naming.directory.DirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.external.LogMessage;
import fi.arcusys.koku.common.external.LogServiceDAO;
import fi.arcusys.koku.common.external.LoggedOperation;
import fi.arcusys.koku.common.external.SystemArea;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 16, 2011
 */
@Stateless
@WebService(serviceName = "UsersAndGroupsService", portName = "UsersAndGroupsServicePort", 
        endpointInterface = "fi.arcusys.koku.common.soa.UsersAndGroupsService",
        targetNamespace = "http://soa.common.koku.arcusys.fi/")
public class UsersAndGroupsServiceImpl implements UsersAndGroupsService {

    private static final Logger logger = LoggerFactory.getLogger(UsersAndGroupsServiceImpl.class);
    
    private final List<User> users = new ArrayList<User>();
    private final List<Group> groups = new ArrayList<Group>();
    private final Map<String, List<User>> groupWithUsers = new HashMap<String, List<User>>();
    private final List<Child> children = new ArrayList<Child>();
    
    private static List<User> getStaticUsersData() {
        final List<User> users = new ArrayList<User>();
        // fill users
        final String[] usersData = {
                "Kalle,Kuntalainen,050-1234567,kalle.kuntalainen@testi.fi",
                "Kirsi,Kuntalainen,050-1234567,kirsi.kuntalainen@testi.fi",
                "Keijo,Kuntalainen,050-1234567,keijo.kuntalainen@testi.fi",
                "Kerttu,Kuntalainen,050-1234567,kerttu.kuntalainen@testi.fi",
                "Anna,Karkkainen,050-1234567,esimerkkiposti@testi.fi",
                "Asko,Lippo,050-1234567,esimerkkiposti@testi.fi",
                "Eeva-Riitta,Pirhonen,050-1234567,esimerkkiposti@testi.fi",
                "Jaakko,Rekola,050-1234567,esimerkkiposti@testi.fi",
                "Jarmo,Hallikainen,050-1234567,esimerkkiposti@testi.fi",
                "Juhani,Heikka,050-1234567,esimerkkiposti@testi.fi",
                "Krista,Piippo,050-1234567,esimerkkiposti@testi.fi",
                "Marjukka,Saarijarvi,050-1234567,esimerkkiposti@testi.fi",
                "Marja,Kanerva,050-1234567,esimerkkiposti@testi.fi",
                "Marko,Monni,050-1234567,esimerkkiposti@testi.fi",
                "Marko,Tanska,050-1234567,esimerkkiposti@testi.fi",
                "Minna,Saario,050-1234567,esimerkkiposti@testi.fi",
                "Niina,Kuisma,050-1234567,esimerkkiposti@testi.fi",
                "Pekka,Kortelainen,050-1234567,esimerkkiposti@testi.fi",
                "Riitta,Viitala,050-1234567,esimerkkiposti@testi.fi",
                "Tarja,Miikkulainen,050-1234567,esimerkkiposti@testi.fi",
                "Ulla,Soukainen,050-1234567,esimerkkiposti@testi.fi",
                "Vesa,Komonen,050-1234567,esimerkkiposti@testi.fi",
                "Ville-Veikko,Ahonen,050-1234567,esimerkkiposti@testi.fi",
                "Ville,Virkamies,050-1234567,esimerkkiposti@testi.fi",
                "kirsi,kuntalainen,050-1234567,esimerkkiposti@testi.fi", 
                "kalle,kuntalainen,050-1234567,esimerkkiposti@testi.fi",
                "kaisa,kuntalainen,050-1234567,esimerkkiposti@testi.fi",
                "kauko,kuntalainen,050-1234567,esimerkkiposti@testi.fi",
                "keijo,keinonen,050-1234567,esimerkkiposti@testi.fi",
                "kerttu,kuntalainen,050-1234567,esimerkkiposti@testi.fi",
                "päivi,päiväkoti,050-1234567,esimerkkiposti@testi.fi",
                "nelli,neuvola,050-1234567,esimerkkiposti@testi.fi",
                "veeti,virkamies,050-1234567,esimerkkiposti@testi.fi",
                "pertti,pääkäyttäjä,050-1234567,esimerkkiposti@testi.fi",
                "piia,pääkäyttäjä,050-1234567,esimerkkiposti@testi.fi",
                
                "liisa,lahtinen,050-1234567,esimerkkiposti@testi.fi",
                "sami,salminen,050-1234567,esimerkkiposti@testi.fi",
                "sanni,suhonen,050-1234567,esimerkkiposti@testi.fi",
                "sulo,simonen,050-1234567,esimerkkiposti@testi.fi"
        };
        for (final String userData : usersData) {
            final String[] data = userData.split(",");
            final String firstName = data[0];
            final String lastName = data[1];
            final String displayName = firstName + "." + lastName;
            final String uid = displayName;
            final String phone = data[2];
            final String email = data[3];
            final User user = new User(uid, displayName);
            user.setEmail(email);
            user.setFirstname(firstName);
            user.setLastname(lastName);
            user.setPhoneNumber(phone);
            users.add(user);
        }
        return users;
        
/*        // fill groups
        final String[][] groupsData = {
                {
                    "Oravat",
                    "kirsi.kuntalainen",
                    "kalle.kuntalainen",
                    "liisa.lahtinen"
                },
                {
                    "Siilit",
                    "sami.salminen",
                    "sanni.suhonen",
                    "sulo.simonen"
                },
                {
                    "Esimerkkikoulun luokan 1C huoltajat",
                    "kalle.kuntalainen",
                    "kirsi.kuntalainen",
                    "keijo.keinonen"
                }
//                ,
//                {
//                    "Virkailijat",
//                    "ville.virkamies",
//                    "Anna Karkkainen",
//                    "Asko Lippo",
//                    "Eeva-Riitta Pirhonen",
//                    "Jaakko Rekola",
//                    "Jarmo Hallikainen",
//                    "Juhani Heikka",
//                    "Krista Piippo",
//                    "Marjukka Saarijarvi",
//                    "Marja Kanerva",
//                    "Marko Monni",
//                    "Marko Tanska",
//                    "Minna Saario",
//                    "Niina Kuisma",
//                    "Pekka Kortelainen",
//                    "Riitta Viitala",
//                    "Tarja Miikkulainen",
//                    "Ulla Soukainen",
//                    "Vesa Komonen",
//                    "Ville-Veikko Ahonen"
//                }
        };
        for (int i = 0; i < groupsData.length ; i++ ) {
            final String groupName = groupsData[i][0]; 
            final Group group = new Group();
            group.setGroupName(groupName);
            group.setGroupUid("" + i);
            groups.add(group);
            final List<User> groupUsers = new ArrayList<User>();
            for (int j = 1; j < groupsData[i].length; j++) {
                groupUsers.add(getUserByUid(groupsData[i][j]));
            }
            groupWithUsers.put(group.getGroupUid(), groupUsers);
        }
        // fill children
        final Child kaisa = new Child("kaisa.kuntalainen", "kaisa.kuntalainen");
        kaisa.setFirstname("kaisa");
        kaisa.setLastname("kuntalainen");
        kaisa.setParents(Arrays.asList(getUserByUid("kirsi.kuntalainen"), getUserByUid("kalle.kuntalainen")));
        final Child kauko = new Child("kauko.kuntalainen", "kauko.kuntalainen");
        kauko.setFirstname("kauko");
        kauko.setLastname("kuntalainen");
        kauko.setParents(Arrays.asList(getUserByUid("kirsi.kuntalainen"), getUserByUid("keijo.keinonen")));
        children.add(kaisa);
        children.add(kauko);
        */
    }
    
    // NOTE: injected through ejb-jar.xml
//    @Resource(mappedName = "external/ldap/myldap")
    private DirContext dirContext;
    
    // NOTE: injected through ejb-jar.xml
//    @Resource(name = "ssnAttributeName")
    private String ssnAttributeName;

    // NOTE: injected through ejb-jar.xml
//    @Resource(name = "userSearchFilter")
    private String userSearchFilter;

    @PostConstruct
    public void loadUsers() {
        if (dirContext != null) {
            try {
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                final NamingEnumeration<SearchResult> results = dirContext.search("", "(objectclass=inetOrgPerson)", controls);
                try {
                    while (results.hasMore()) {
                        final SearchResult searchResult = results.next();
                        final Attributes attributes = searchResult.getAttributes();

                        final String firstName = getAttributeValue(attributes, "givenName");
                        final String lastName = getAttributeValue(attributes, "sn");
                        final String displayName = getAttributeValue(attributes, "cn");
                        final String uid = displayName;
                        final String phone = getAttributeValue(attributes, "telephoneNumber");
                        final String email = getAttributeValue(attributes, "mail");
                        final User user = new User(uid, displayName);
                        user.setEmail(email);
                        user.setFirstname(firstName);
                        user.setLastname(lastName);
                        user.setPhoneNumber(phone);
                        users.add(user);
                    }
                } finally {
                    results.close();
                }
            } catch (NamingException e) {
                logger.error(null, e);
                throw new RuntimeException(e);
            }
        } else {
            users.addAll(getStaticUsersData());
        }
        final String[][] groupsData = getGroupsData();
        for (int i = 0; i < groupsData.length ; i++ ) {
            final String groupName = groupsData[i][0]; 
            final Group group = new Group();
            group.setGroupName(groupName);
            group.setGroupUid("" + i);
            groups.add(group);
            final List<User> groupUsers = new ArrayList<User>();
            for (int j = 1; j < groupsData[i].length; j++) {
                groupUsers.add(getUserByUid(groupsData[i][j]));
            }
            groupWithUsers.put(group.getGroupUid(), groupUsers);
        }
        // fill children
        final Child kaisa = new Child("kaisa.kuntalainen", "kaisa.kuntalainen");
        kaisa.setFirstname("kaisa");
        kaisa.setLastname("kuntalainen");
        kaisa.setParents(Arrays.asList(getUserByUid("kirsi.kuntalainen"), getUserByUid("kalle.kuntalainen")));
        final Child kauko = new Child("kauko.kuntalainen", "kauko.kuntalainen");
        kauko.setFirstname("kauko");
        kauko.setLastname("kuntalainen");
        kauko.setParents(Arrays.asList(getUserByUid("kirsi.kuntalainen"), getUserByUid("keijo.keinonen")));
        children.add(kaisa);
        children.add(kauko);
    }

    private String[][] getGroupsData() {
        if (dirContext == null) {
            return getStaticGroupsData();
        }
        try {
            final List<List<String>> result = new ArrayList<List<String>>();
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            final NamingEnumeration<SearchResult> results = dirContext.search("", "(objectclass=groupOfNames)", controls);
            try {
                while (results.hasMore()) {
                    final SearchResult searchResult = results.next();
                    final Attributes attributes = searchResult.getAttributes();

                    final Pattern pattern = Pattern.compile("cn=([^,]+)\\,");
                    
                    final List<String> groupWithUserNames = new ArrayList<String>();
                    groupWithUserNames.add(getAttributeValue(attributes, "cn"));
                    
                    final Attribute attribute = attributes.get("member");
                    for (int i = 0; i < attribute.size(); i++) {
                        final String member = (String)attribute.get(i);
                        final Matcher matcher = pattern.matcher(member);
                        if (matcher.find()) {
                            final String ldapName = matcher.group(1);
                            if (isUserExistsByUid(ldapName)) {
                                groupWithUserNames.add(ldapName);
                            }
                        } else {
                            logger.info("Can't get user uid: " + member);
                        }
                    }
                    if (groupWithUserNames.size() > 1) {
                        result.add(groupWithUserNames);
                    } else {
                        logger.info("Empty group, skipped: " + groupWithUserNames);
                    }
                }
                final String[][] groupData = new String[result.size()][];
                if (result.size() > 0) {
                    for (int i = 0; i < result.size(); i++) {
                        groupData[i] = result.get(i).toArray(new String[result.get(i).size()]);
                    }
                }
                return groupData;
            } finally {
                results.close();
            }
        } catch (NamingException e) {
            logger.error(null, e);
            throw new RuntimeException(e);
        }
    }

    private String[][] getStaticGroupsData() {
        final String[][] groupsData = {
                {
                    "Oravat",
                    "kirsi.kuntalainen",
                    "kalle.kuntalainen",
                    "liisa.lahtinen"
                },
                {
                    "Siilit",
                    "sami.salminen",
                    "sanni.suhonen",
                    "sulo.simonen"
                },
                {
                    "Esimerkkikoulun luokan 1C huoltajat",
                    "kalle.kuntalainen",
                    "kirsi.kuntalainen",
                    "keijo.keinonen"
                }
//                ,
//                {
//                    "Virkailijat",
//                    "ville.virkamies",
//                    "Anna Karkkainen",
//                    "Asko Lippo",
//                    "Eeva-Riitta Pirhonen",
//                    "Jaakko Rekola",
//                    "Jarmo Hallikainen",
//                    "Juhani Heikka",
//                    "Krista Piippo",
//                    "Marjukka Saarijarvi",
//                    "Marja Kanerva",
//                    "Marko Monni",
//                    "Marko Tanska",
//                    "Minna Saario",
//                    "Niina Kuisma",
//                    "Pekka Kortelainen",
//                    "Riitta Viitala",
//                    "Tarja Miikkulainen",
//                    "Ulla Soukainen",
//                    "Vesa Komonen",
//                    "Ville-Veikko Ahonen"
//                }
        };
        return groupsData;
    }

    private String getAttributeValue(final Attributes attributes, final String attrName) throws NamingException {
        final Attribute attr = attributes.get(attrName);
        if (attr != null) {
            return (String) attr.get();
        }
        return null;
    }
    
    public String getSsnByLdapName(final String username) {
        try {
            SearchControls controls = new SearchControls();
            controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
            final NamingEnumeration<SearchResult> results = dirContext.search("", userSearchFilter.replaceAll("#username#", username), controls);
            try {
                if (results.hasMore()) {
                    final SearchResult searchResult = results.next();
                    if (results.hasMore()) {
                        return "multipleFound";
                    }
                    final Attributes attributes = searchResult.getAttributes();
                    final Attribute attr = attributes.get(ssnAttributeName);
                    if (attr != null) {
                        return (String) attr.get();
                    }
                }
            } finally {
                results.close();
            }
        } catch (NamingException e) {
            logger.error(null, e);
            throw new RuntimeException(e);
        }
        return "unknown";
    }
    
    private  User getUserByUid(final String userUid) {
        for (final User user : users ) {
            if (user.getUid().equalsIgnoreCase(userUid) ) {
                return user;
            }
        }
        throw new IllegalArgumentException("User with uid " + userUid + " is not found.");
    }
    
    private boolean isUserExistsByUid(final String userUid) {
        for (final User user : users ) {
            if (user.getUid().equalsIgnoreCase(userUid) ) {
                return true;
            }
        }
        return false;
    }

    private  Child getChildByUid(final String childUid) {
        for (final Child child : children ) {
            if (child.getUid().equalsIgnoreCase(childUid) ) {
                return child;
            }
        }
        throw new IllegalArgumentException("User with uid " + childUid + " is not found.");
    }

    private  List<Child> getChildrenByParentUid(final String parentUid) {
        final List<Child> result = new ArrayList<Child>();
        for (final Child child : children ) {
            for (final User parent : child.getParents()) {
                if (parent.getUid().equalsIgnoreCase(parentUid) ) {
                    result.add(child);
                }
            }
        }
        return result;
    }

    /**
     * @param username
     * @return
     */
    @Override
    public String getUserUidByKunpoName(String username) {
        // TODO Auto-generated method stub
        return username;
    }

    /**
     * @param username
     * @return
     */
    @Override
    public String getUserUidByLooraName(String username) {
        // TODO Auto-generated method stub
        return username;
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public List<ChildWithHetu> getUsersChildren(String userUid) {
        // TODO Auto-generated method stub
        final List<ChildWithHetu> result = new ArrayList<ChildWithHetu>();
        for (final Child child : getChildrenByParentUid(userUid)) {
            final ChildWithHetu resultChild = new ChildWithHetu(child.getUid(), child.getDisplayName());
            resultChild.setEmail(child.getEmail());
            resultChild.setFirstname(child.getFirstname());
            resultChild.setLastname(child.getLastname());
            resultChild.setPhoneNumber(child.getPhoneNumber());
            resultChild.setHetu(getSsnByLdapName(child.getDisplayName()));
            final List<User> parents = new ArrayList<User>();
            for (final User parent : child.getParents()) {
                if (!parent.getUid().equals(userUid)) {
                    parents.add(parent);
                }
            }
            resultChild.setParents(parents);
            result.add(resultChild);
        }
        
        return result;
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public String getKunpoNameByUserUid(String userUid) {
        // TODO Auto-generated method stub
        return userUid;
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public String getLooraNameByUserUid(String userUid) {
        // TODO Auto-generated method stub
        return userUid;
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<User> searchUsers(String searchString, int limit) {
        // TODO Auto-generated method stub
        final List<User> result = new ArrayList<User>();
        for (final User user : users) {
            if (checkUserBySearchString(searchString, user)    ) {
                result.add(user);
                if (result.size() >= limit) {
                    return result;
                }
            }
        }
        return result;
    }
    
    @Override
    public String getUserUid(final String ssn) {
        if (ssn == null || ssn.trim().isEmpty()) {
            return null;
        }
        
        final List<User> users = searchUsers(ssn, 1);
        if (users == null || users.isEmpty()) {
            logger.info("Users not found by by uniq id: " + ssn);
            return ssn;
        } else if (users.size() > 1) {
            logger.warn("Found many users by the same uniq id: " + ssn);
            return ssn;
        } else {
            return users.get(0).getUid();
        }
    }

    private boolean checkUserBySearchString(String searchString, final User user) {
        return user.getDisplayName().toLowerCase().equals(searchString.toLowerCase()) ||
            getUserSsn(user.getDisplayName()).equals(searchString.toLowerCase());
    }
    
    private String getUserSsn(final String username) {
        return getSsnByLdapName(username);
    } 

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<Group> searchGroups(String searchString, int limit) {
        // TODO Auto-generated method stub
        final List<Group> result = new ArrayList<Group>();
        final String[] searchTerms = searchString.toLowerCase().split(" ");
        for (final Group group : groups) {
            if (matchToSearchTerms(searchTerms, group.getGroupName().toLowerCase())) {
                result.add(group);
                if (result.size() >= limit) {
                    return result;
                }
            }
        }
        return result;
    }

    private boolean matchToSearchTerms(final String[] searchTerms, final String groupName) {
        final String[] groupNameTokens = groupName.split(" ");
        for (final String searchTerm : searchTerms) {
            if (!searchTermFound(searchTerm, groupNameTokens)) {
                return false;
            }
        }
        return true;
    }

    /**
     * @param searchTerm
     * @param groupNameTokens
     * @return
     */
    private boolean searchTermFound(String searchTerm, String[] groupNameTokens) {
        for (final String token : groupNameTokens) {
            if (token.startsWith(searchTerm)) {
                return true;
            }
        }
        return false;
    }

    /**
     * @param groupUid
     * @return
     */
    @Override
    public List<User> getUsersByGroupUid(String groupUid) {
        // TODO Auto-generated method stub
        return groupWithUsers.get(groupUid);
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<Child> searchChildren(String searchString, int limit) {
        // TODO Auto-generated method stub
        final List<Child> result = new ArrayList<Child>();
        for (final Child child : children) {
            if (
                    checkUserBySearchString(searchString, child)
//                    child.getLastname().toLowerCase().startsWith(searchString.toLowerCase())
                    ) {
                result.add(child);
                if (result.size() >= limit) {
                    return result;
                }
            }
        }
        return result;
    }

    /**
     * @param childUid
     * @return
     */
    @Override
    public Child getChildInfo(String childUid) {
        // TODO Auto-generated method stub
        return getChildByUid(childUid);
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public User getUserInfo(String userUid) {
        // TODO Auto-generated method stub
        return getUserByUid(userUid);
    }

}
