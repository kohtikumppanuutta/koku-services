package fi.arcusys.koku.common.soa;

import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.common.external.CustomerServiceDAO;
import fi.arcusys.koku.common.external.GroupsDAO;
import fi.arcusys.koku.common.external.PyhServiceDAO;
import fi.arcusys.koku.common.service.UserDAO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 10, 2011
 */
//@Stateless
//@WebService(serviceName = "UsersAndGroupsServiceIntegrated", portName = "UsersAndGroupsServiceIntegratedPort", 
//        endpointInterface = "fi.arcusys.koku.common.soa.UsersAndGroupsService",
//        targetNamespace = "http://soa.common.koku.arcusys.fi/")
@Stateless
@WebService(serviceName = "UsersAndGroupsService", portName = "UsersAndGroupsServicePort", 
      endpointInterface = "fi.arcusys.koku.common.soa.UsersAndGroupsService",
      targetNamespace = "http://soa.common.koku.arcusys.fi/")
public class UsersAndGroupsServiceImpl implements UsersAndGroupsService {
    
    @EJB
    private PyhServiceDAO pyhServiceDao;
    
    @EJB
    private UserDAO userDao;
    
    @EJB
    private CustomerServiceDAO customerDao;
    
    @EJB
    private GroupsDAO groupsDao;
    
    /**
     * @param username
     * @return
     */
    @Override
    public String getUserUidByKunpoName(String username) {
        final fi.arcusys.koku.common.service.datamodel.User user = userDao.getUserByCitizenPortalNameOrNull(username);
        if (user == null) {
            final String ssnByKunpoName = customerDao.getSsnByKunpoName(username);
            if (ssnByKunpoName != null && !ssnByKunpoName.isEmpty()) {
                return userDao.getOrCreateUserByCitizenPortalName(username).getUid();
            } else {
                throw new IllegalArgumentException("KunPo user '" + username + "' not found in ldap and DB.");
            }
        } else {
            return user.getUid();
        }
        
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public String getKunpoNameByUserUid(String userUid) {
        return userDao.getOrCreateUser(userUid).getCitizenPortalName();
    }

    /**
     * @param username
     * @return
     */
    @Override
    public String getUserUidByLooraName(String username) {
        return userDao.getOrCreateUserByEmployeePortalName(username).getUid();
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public String getLooraNameByUserUid(String userUid) {
        return userDao.getOrCreateUser(userUid).getEmployeePortalName();
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<User> searchUsers(String searchString, int limit) {
        return getListBySingleValue(customerDao.getKunpoUserInfoBySsn(searchString));
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<User> searchEmployees(String searchString, int limit) {
        // searchString is username in portal
        final String ssnByLooraName = customerDao.getSsnByLooraName(searchString);
        if (ssnByLooraName != null && !ssnByLooraName.isEmpty()) {
            return getListBySingleValue(customerDao.getEmployeeUserInfoBySsn(ssnByLooraName));
        } else {
            return Collections.emptyList();
        }
    }

    private <E> List<E> getListBySingleValue(final E singleValue) {
        if (singleValue != null) {
            return Collections.singletonList(singleValue);
        }
        return Collections.emptyList();
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<Group> searchGroups(String searchString, int limit) {
        return groupsDao.searchGroups(searchString, limit);
    }

    /**
     * @param groupUid
     * @return
     */
    @Override
    public List<User> getUsersByGroupUid(String groupUid) {
        return groupsDao.getUsersByGroupUid(groupUid);
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<Child> searchChildren(String searchString, int limit) {
        return getListBySingleValue(pyhServiceDao.getChildInfo(getUserUidByKunpoSsn(searchString)));
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public List<ChildWithHetu> getUsersChildren(String userUid) {
        return pyhServiceDao.getUsersChildren(userUid);
    }

    /**
     * @param childUid
     * @return
     */
    @Override
    public Child getChildInfo(String childUid) {
        return pyhServiceDao.getChildInfo(childUid);
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public User getUserInfo(String userUid) {
        return customerDao.getUserInfo(userUid);
    }

    /**
     * @param username
     * @return
     */
    @Override
    public String getSsnByLdapName(String username) {
        String result = getSsnByKunpoName(username);
        if (result == null || result.isEmpty()) {
            result = getSsnByLooraName(username);
        }
        return result;
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public String getUserUidByKunpoSsn(String ssn) {
        final User user = customerDao.getKunpoUserInfoBySsn(ssn);
        if (user != null) {
            return user.getUid();
        } else {
            return "";
        }
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public String getUserUidByEmployeeSsn(String ssn) {
        final User user = customerDao.getEmployeeUserInfoBySsn(ssn);
        if (user != null) {
            return user.getUid();
        } else {
            return "";
        }
    }

    /**
     * @param looraName
     * @return
     */
    @Override
    public String getSsnByLooraName(String looraName) {
        return customerDao.getSsnByLooraName(looraName);
    }

    /**
     * @param kunpoName
     * @return
     */
    @Override
    public String getSsnByKunpoName(String kunpoName) {
        return customerDao.getSsnByKunpoName(kunpoName);
    }

    /**
     * @param username
     * @param ssn
     * @return
     */
    @Override
    public User loginByKunpoNameAndSsn(String kunpoUsername, String ssn) {
        if (ssn == null || ssn.isEmpty()) {
            return customerDao.getKunpoUserInfoBySsn(customerDao.getSsnByKunpoName(kunpoUsername));
        }
        
        return customerDao.getKunpoUserInfoByPortalNameAndSsn(kunpoUsername, ssn);
    }

    /**
     * @param username
     * @param ssn
     * @return
     */
    @Override
    public User loginByLooraNameAndSsn(String looraUsername, String ssn) {
        if (ssn == null || ssn.isEmpty()) {
            return customerDao.getEmployeeUserInfoBySsn(customerDao.getSsnByLooraName(looraUsername));
        }
        
        return customerDao.getEmployeeUserInfoByPortalNameAndSsn(looraUsername, ssn);
    }
}
