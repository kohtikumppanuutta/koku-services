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
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.soa.UserInfo;
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
    
    @EJB
    private LdapDAO ldapDao;
    
    @EJB
    private CacheDAO cacheDao;
    
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
    public UserInfo getUserInfo(final User user) {
        if (user.getCitizenPortalName() != null && !user.getCitizenPortalName().trim().isEmpty()) {
            return getUserInfoByUidAndSsn(user, getSsnByKunpoName(user.getCitizenPortalName()));
        } else {
            return getEmployeeUserInfo(user);
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

    private UserInfo getUserInfoByUidAndSsn(final User user, final String ssn) {
        final CustomerType customer;
        try {
            customer = getCustomer(ssn);
        } catch (ServiceFault e) {
            logger.error("Failed to get user info by userUid " + user.getUid() + " and ssn " + ssn, e);
            throw new RuntimeException(e);
        }
        return ExternalDAOsUtil.convertCustomerToUser(customer, user);
    }

    private CustomerType getCustomer(final String ssn) throws ServiceFault {
        final CustomerType cachedCustomer = (CustomerType)cacheDao.get(CustomerServiceDAOImpl.class, ssn);
        if (cachedCustomer != null) {
            return cachedCustomer;
        }
        
        final AuditInfoType auditHeader = new AuditInfoType();
        auditHeader.setComponent("tiva");
        auditHeader.setUserId(ssn);
        final CustomerType customerFromWs = customerService.opGetCustomer(ssn, auditHeader);
        
        cacheDao.put(CustomerServiceDAOImpl.class, ssn, customerFromWs);
        
        return customerFromWs;
    }

    /**
     * @param employeePortalName
     * @return
     */
    @Override
    public String getSsnByLooraName(String employeePortalName) {
        userDao.getOrCreateUserByEmployeePortalName(employeePortalName);
        return ldapDao.getSsnByLooraName(employeePortalName);
    }

    /**
     * @param citizenPortalName
     * @return
     */
    @Override
    public String getSsnByKunpoName(String citizenPortalName) {
        final String ssn = ldapDao.getSsnByKunpoName(citizenPortalName);
        if (ssn != null && !ssn.isEmpty()) {
            userDao.getOrCreateUserByCitizenPortalName(citizenPortalName);
        }
        return ssn;
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public UserInfo getKunpoUserInfoBySsn(final String ssn) {
        if (ssn == null || ssn.isEmpty()) {
            return null;
        }
        final String ldapNameBySsn = ldapDao.getKunpoNameBySsn(ssn);
        if (ldapNameBySsn == null || ldapNameBySsn.isEmpty()) {
            try {
                if (getCustomer(ssn) != null) {
                    String firstName;
                    String lastName;
                    try {
                        final CustomerType customer = getCustomer(ssn);

                        firstName = customer.getEtuNimi();
                        lastName = customer.getSukuNimi();
                    } catch (ServiceFault e) {
                        logger.error("Failed to get Customer by ssn '" + ssn + "': " + e.getClass() + ", " + e.getMessage());
                        firstName = ssn;
                        lastName = ssn;
                    }
                    
                    ldapDao.createKunpoUserInLdap(ssn, ssn, firstName, lastName);
                    return getUserInfoByUidAndSsn(userDao.getOrCreateUserByCitizenPortalName(ssn), ssn);
                } 
            } catch (ServiceFault e) {
                logger.warn("Failed to get customer by ssn " + ssn + ". Probably, incorrect ssn is used. " + e.getMessage());
            }
            return null;
        } else {
            return getUserInfoByUidAndSsn(userDao.getOrCreateUserByCitizenPortalName(ldapNameBySsn), ssn);
        }
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public UserInfo getEmployeeUserInfoBySsn(String ssn) {
        if (ssn == null || ssn.isEmpty()) {
            return null;
        }
        final String ldapNameBySsn = ldapDao.getLooraNameBySsn(ssn);
        if (ldapNameBySsn == null || ldapNameBySsn.isEmpty()) {
            return null;
        }
        return getEmployeeUserInfo(userDao.getOrCreateUserByEmployeePortalName(ldapNameBySsn));
    }

    private UserInfo getEmployeeUserInfo(
            final fi.arcusys.koku.common.service.datamodel.User employee) {
        final UserInfo user = new UserInfo();
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
    
    /**
     * @param kunpoUsername
     * @param ssn
     * @return
     */
    @Override
    public UserInfo getKunpoUserInfoByPortalNameAndSsn(final String kunpoUsername, final String ssn) {
        if (kunpoUsername == null || kunpoUsername.trim().isEmpty()) {
            throw new IllegalArgumentException("Get of Kunpo user info failed: Kunpo username is empty: '" + kunpoUsername + "'");
        }
        if (ssn == null || ssn.trim().isEmpty()) {
            throw new IllegalArgumentException("Get of Kunpo user info failed: Kunpo user ssn is empty: '" + ssn + "'");
        }
        
        final String ldapNameBySsn = ldapDao.getKunpoNameBySsn(ssn);
        if (ldapNameBySsn == null || ldapNameBySsn.isEmpty()) {
            // create new user in ldap/DB
            String firstName;
            String lastName;
            try {
                final CustomerType customer = getCustomer(ssn);

                firstName = customer.getEtuNimi();
                lastName = customer.getSukuNimi();
            } catch (ServiceFault e) {
                logger.error("Failed to get Customer by ssn '" + ssn + "': " + e.getClass() + ", " + e.getMessage());
                firstName = kunpoUsername;
                lastName = ssn;
            }
            ldapDao.createKunpoUserInLdap(kunpoUsername, ssn, firstName, lastName);
        } else if (!kunpoUsername.equals(ldapNameBySsn)) {
            // update username in ldap/db
            final fi.arcusys.koku.common.service.datamodel.User userForUpdate = userDao.getOrCreateUserByCitizenPortalName(ldapNameBySsn);
            userForUpdate.setCitizenPortalName(kunpoUsername);
            userDao.update(userForUpdate);
            ldapDao.updateKunpoLdapName(ldapNameBySsn, kunpoUsername);
        }

        return getUserInfoByUidAndSsn(userDao.getOrCreateUserByCitizenPortalName(kunpoUsername), ssn);
    }

    /**
     * @param looraUsername
     * @param ssn
     * @return
     */
    @Override
    public UserInfo getEmployeeUserInfoByPortalNameAndSsn(final String looraUsername, final String ssn) {
        // TODO Auto-generated method stub
        return null;
    }
}
