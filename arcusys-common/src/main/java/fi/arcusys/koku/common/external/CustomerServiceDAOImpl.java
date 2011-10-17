package fi.arcusys.koku.common.external;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.annotation.PostConstruct;
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

import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.soa.User;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.koku.services.entity.customer.v1.AuditInfoType;
import fi.koku.services.entity.customer.v1.CustomerServiceFactory;
import fi.koku.services.entity.customer.v1.CustomerServicePortType;
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
    private DirContext dirContext;
    private String ssnAttributeName;
    private String usernameAttributeName;
    private String userSearchFilter;

    private CustomerServicePortType customerService;
    
    private String customerServiceUserUid;
    private String customerServiceUserPwd;
    private String customerServiceEndpoint;

    @PostConstruct
    public void init() {
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
        final fi.arcusys.koku.common.service.datamodel.User userByUid = userDao.getUserByUid(userUid);
        final String ssn;
        if (userByUid.getCitizenPortalName() != null && !userByUid.getCitizenPortalName().trim().isEmpty()) {
            ssn = getSsnByKunpoName(userByUid.getCitizenPortalName());
        } else {
            ssn = getSsnByLooraName(userByUid.getEmployeePortalName());
        }
        return ssn;
    }

    private User getUserInfoByUidAndSsn(final String userUid, final String ssn) {
        final AuditInfoType auditHeader = new AuditInfoType();
        auditHeader.setComponent("tiva");
        auditHeader.setUserId(ssn);
        try {
            return ExternalDAOsUtil.convertCustomerToUser(customerService.opGetCustomer(ssn, auditHeader), userUid);
        } catch (ServiceFault e) {
            logger.error("Failed to get user info by userUid " + userUid + " and ssn " + ssn, e);
            throw new RuntimeException(e);
        }
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
    public User getKunpoUserInfoBySsn(String ssn) {
        return getUserInfoByUidAndSsn(userDao.getOrCreateUserByCitizenPortalName(getLdapNameBySsn(ssn)).getUid(), ssn);
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public User getEmployeeUserInfoBySsn(String ssn) {
        final fi.arcusys.koku.common.service.datamodel.User employee = userDao.getOrCreateUserByEmployeePortalName(getLdapNameBySsn(ssn));
        return getEmployeeUserInfo(employee);
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
        } catch (NamingException e) {
            logger.error(null, e);
            throw new RuntimeException(e);
        }
        return "unknown";
    }
}
