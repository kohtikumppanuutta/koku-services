package fi.arcusys.koku.common.external;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
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
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.soa.User;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.koku.services.entity.customer.v1.AuditInfoType;
import fi.koku.services.entity.customer.v1.CustomerServiceFactory;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
import fi.koku.services.entity.customer.v1.CustomerType;
import fi.koku.services.entity.customer.v1.ServiceFault;
import fi.koku.services.entity.family.FamilyConstants;
import fi.koku.services.entity.family.v1.FamilyService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 17, 2011
 */
@Stateless
public class CustomerServiceDAOImpl implements CustomerServiceDAO {

    private final static Logger logger = LoggerFactory.getLogger(CustomerServiceDAOImpl.class);
    
    @EJB
    private UserDAO userDao;
    
    //  @Resource(mappedName = "external/ldap/myldap")
//    private DirContext dirContext;
    private String ssnAttributeName;
    private String usernameAttributeName;
    private String userSearchFilter;
    
    private String kunpoUserBaseDn;
    private String kuntalainenGroupUid;

    private CustomerServicePortType customerService;
    
    private String customerServiceUserUid;
    private String customerServiceUserPwd;
    private String customerServiceEndpoint;

    @PostConstruct
    public void init() {
        try {
            final InitialContext ctx = new InitialContext();
            customerServiceEndpoint = (String) ctx.lookup("koku/urls/customerservice-baseurl");
            logger.debug("Overwrite customerServiceEndpoint with " + customerServiceEndpoint);
        } catch (NamingException e) {
            logger.error(null, e);
        }
        try {
            CustomerServiceFactory customerServiceFactory = new CustomerServiceFactory(
                    customerServiceUserUid, customerServiceUserPwd, customerServiceEndpoint);
            customerService = customerServiceFactory.getCustomerService();
        } catch(Exception re) {
            logger.error(null, re);
        } 
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public User getUserInfo(final String userUid) {
        final fi.arcusys.koku.common.service.datamodel.User userByUid = userDao.getUserByUid(userUid);
        if (userByUid.getCitizenPortalName() != null && !userByUid.getCitizenPortalName().trim().isEmpty()) {
            return getUserInfoByUidAndSsn(userUid, getSsnByKunpoName(userByUid.getCitizenPortalName()));
        } else {
            return getEmployeeUserInfo(userByUid);
        }
    }

    @Override
    public String getSsnByUserUid(final String userUid) {
        final fi.arcusys.koku.common.service.datamodel.User userByUid = userDao.getOrCreateUser(userUid);
        final String ssn;
        if (userByUid.getCitizenPortalName() != null && !userByUid.getCitizenPortalName().trim().isEmpty()) {
            ssn = getSsnByKunpoName(userByUid.getCitizenPortalName());
        } else {
            ssn = getSsnByLooraName(userByUid.getEmployeePortalName());
        }
        return ssn;
    }

    private User getUserInfoByUidAndSsn(final String userUid, final String ssn) {
        final CustomerType customer;
        try {
            customer = getCustomer(ssn);
        } catch (ServiceFault e) {
            logger.error("Failed to get user info by userUid " + userUid + " and ssn " + ssn, e);
            throw new RuntimeException(e);
        }
        return ExternalDAOsUtil.convertCustomerToUser(customer, userUid);
    }

    private CustomerType getCustomer(final String ssn) throws ServiceFault {
        final AuditInfoType auditHeader = new AuditInfoType();
        auditHeader.setComponent("tiva");
        auditHeader.setUserId(ssn);
        return customerService.opGetCustomer(ssn, auditHeader);
    }

    /**
     * @param employeePortalName
     * @return
     */
    @Override
    public String getSsnByLooraName(String employeePortalName) {
        userDao.getOrCreateUserByEmployeePortalName(employeePortalName);
        return getSsnByLdapName(employeePortalName);
    }

    /**
     * @param citizenPortalName
     * @return
     */
    @Override
    public String getSsnByKunpoName(String citizenPortalName) {
        userDao.getOrCreateUserByCitizenPortalName(citizenPortalName);
        return getSsnByLdapName(citizenPortalName);
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public User getKunpoUserInfoBySsn(final String ssn) {
        if (ssn == null || ssn.isEmpty()) {
            return null;
        }
        final String ldapNameBySsn = getLdapNameBySsn(ssn);
        if (ldapNameBySsn == null || ldapNameBySsn.isEmpty()) {
            try {
                if (getCustomer(ssn) != null) {
                    createKunpoUserInLdap(ssn, ssn);
                    return getUserInfoByUidAndSsn(userDao.getOrCreateUserByCitizenPortalName(ssn).getUid(), ssn);
                } 
            } catch (ServiceFault e) {
                logger.warn("Failed to get customer by ssn " + ssn + ". Probably, incorrect ssn is used. " + e.getMessage());
            }
            return null;
        } else {
            return getUserInfoByUidAndSsn(userDao.getOrCreateUserByCitizenPortalName(ldapNameBySsn).getUid(), ssn);
        }
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public User getEmployeeUserInfoBySsn(String ssn) {
        if (ssn == null || ssn.isEmpty()) {
            return null;
        }
        final String ldapNameBySsn = getLdapNameBySsn(ssn);
        if (ldapNameBySsn == null || ldapNameBySsn.isEmpty()) {
            return null;
        }
        return getEmployeeUserInfo(userDao.getOrCreateUserByEmployeePortalName(ldapNameBySsn));
    }

    private User getEmployeeUserInfo(
            final fi.arcusys.koku.common.service.datamodel.User employee) {
        final User user = new User();
        user.setDisplayName(employee.getEmployeePortalName());
        user.setUid(employee.getUid());
        final Pattern username = Pattern.compile("(\\w+)\\.(\\w+)");
        final Matcher matcher = username.matcher(employee.getEmployeePortalName());
        if (matcher.matches()) {
            user.setFirstname(matcher.group(1));
            user.setLastname(matcher.group(2));
        } else {
            user.setFirstname(employee.getEmployeePortalName());
        }
        return user;
    }
    
    private String getLdapNameBySsn(final String ssn) {
        return getUsersAttrNameByFilter(ssnAttributeName, ssn, usernameAttributeName);
    }

    private String getSsnByLdapName(final String ldapName) {
        return getUsersAttrNameByFilter(usernameAttributeName, ldapName, ssnAttributeName);
    }

    private String getUsersAttrNameByFilter(final String filterAttrName, final String filterAttrValue, final String searchAttrName) {
        try {
            DirContext dirContext = createUsersDirContext();
            try {
                SearchControls controls = new SearchControls();
                controls.setSearchScope(SearchControls.SUBTREE_SCOPE);
                final NamingEnumeration<SearchResult> results = dirContext.search("", 
                        userSearchFilter.replaceAll("#attrName#", filterAttrName)
                                        .replaceAll("#attrValue#", filterAttrValue), controls);
                try {
                    if (results.hasMore()) {
                        final SearchResult searchResult = results.next();
                        if (results.hasMore()) {
                            return "multipleFound";
                        }
                        final Attributes attributes = searchResult.getAttributes();
                        final Attribute attr = attributes.get(searchAttrName);
                        if (attr != null) {
                            return (String) attr.get();
                        }
                    }
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
        return null;
    }
    
    private void createKunpoUserInLdap(final String kunpoUsername, final String ssn) {
        try {
            String etuNimi;
            String sukuNimi;
            
            try {
                final CustomerType customer = getCustomer(ssn);

                etuNimi = customer.getEtuNimi();
                sukuNimi = customer.getSukuNimi();
            } catch (ServiceFault e) {
                logger.error("Failed to get Customer by ssn '" + ssn + "': " + e.getClass() + ", " + e.getMessage());
                etuNimi = kunpoUsername;
                sukuNimi = ssn;
            }

            Attributes personAttributes = new BasicAttributes();
            BasicAttribute personBasicAttribute = new BasicAttribute(
                    "objectclass");
            personBasicAttribute.add("inetOrgPerson");
            personBasicAttribute.add("top");
            personAttributes.put(personBasicAttribute);

            personAttributes.put("givenName", etuNimi);
            personAttributes.put("cn", kunpoUsername);
            personAttributes.put("sn", sukuNimi);
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
        } catch (NamingException e) {
            logger.error("Failed to create new user in ldap by portal name '" + kunpoUsername + "' and ssn '" + ssn + "'", e);
            throw new RuntimeException(e);
        } 
    }

    private String getUserDn(final String kunpoUsername) {
        return usernameAttributeName + "=" + kunpoUsername;
    }
    
    private void updateLdapName(final GroupsDAO groupsDao, final String oldLdapName, final String newLdapName) {
        try {
            DirContext dirContext = createUsersDirContext();
            try {
                final String oldName = getUserDn(oldLdapName);
                final String newName = getUserDn(newLdapName);
                final String userOldDn = getUserDnWithBase(oldName);
                final String userNewDn = getUserDnWithBase(newName);
                dirContext.rename(oldName, newName);
                groupsDao.updateMembership(userOldDn, userNewDn);
            } finally {
                dirContext.close();
            }
        } catch (NamingException e) {
            logger.error("Failed to update user in ldap: old name '" + oldLdapName + "', new name '" + newLdapName + "'", e);
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

    /**
     * @param kunpoUsername
     * @param ssn
     * @return
     */
    @Override
    public User getKunpoUserInfoByPortalNameAndSsn(final GroupsDAO groupsDao, final String kunpoUsername, final String ssn) {
        if (kunpoUsername == null || kunpoUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Get of Kunpo user info failed: Kunpo username is empty: '" + kunpoUsername + "'");
        }
        if (ssn == null || ssn.trim().isEmpty()) {
            throw new IllegalArgumentException("Get of Kunpo user info failed: Kunpo user ssn is empty: '" + ssn + "'");
        }
        
        final String ldapNameBySsn = getLdapNameBySsn(ssn);
        if (ldapNameBySsn == null || ldapNameBySsn.isEmpty()) {
            // create new user in ldap/DB
            createKunpoUserInLdap(kunpoUsername, ssn);
        } else if (!kunpoUsername.equals(ldapNameBySsn)) {
            // update username in ldap/db
            final fi.arcusys.koku.common.service.datamodel.User userForUpdate = userDao.getOrCreateUserByCitizenPortalName(ldapNameBySsn);
            userForUpdate.setCitizenPortalName(kunpoUsername);
            userDao.update(userForUpdate);
            updateLdapName(groupsDao, ldapNameBySsn, kunpoUsername);
        }

        try {
            // add user to 'kuntalainen' group
            final DirContext dirContext = createUsersDirContext();
            try {
                groupsDao.addUserToSystemGroup(getUserDnWithBase(getUserDn(kunpoUsername)), kuntalainenGroupUid);
            } finally {
                dirContext.close();
            }
        } catch (NamingException e) {
            logger.warn("Failed to add user '" + getUserDn(kunpoUsername) + "' to group " + kuntalainenGroupUid + ": " + e.getMessage());
        }
        return getUserInfoByUidAndSsn(userDao.getOrCreateUserByCitizenPortalName(kunpoUsername).getUid(), ssn);
    }

    /**
     * @param looraUsername
     * @param ssn
     * @return
     */
    @Override
    public User getEmployeeUserInfoByPortalNameAndSsn(final String looraUsername, final String ssn) {
        // TODO Auto-generated method stub
        return null;
    }
}
