package fi.arcusys.koku.common.soa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.common.external.CustomerServiceDAO;
import fi.arcusys.koku.common.external.GroupsDAO;
import fi.arcusys.koku.common.external.PyhServiceDAO;
import fi.arcusys.koku.common.external.RolesDAO;
import fi.arcusys.koku.common.service.UserDAO;

/**
 * Implementation of external SOA/Web Service interface for providing user/group/role related operations to other parts of the system (UI, Intalio Forms etc.)
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 10, 2011
 */
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
    
    @EJB
    private RolesDAO rolesDao;
    
    /**
     * @param username
     * @return
     */
    @Override
    public String getUserUidByKunpoName(String username) {
        if (username == null || username.isEmpty()) {
            return null;
        }
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
        if (username == null || username.isEmpty()) {
            return null;
        }
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
    public List<UserInfo> searchUsers(String searchString, int limit) {
        return getListBySingleValue(customerDao.getKunpoUserInfoBySsn(searchString));
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<UserInfo> searchEmployees(String searchString, int limit) {
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
    public List<UserInfo> getUsersByGroupUid(String groupUid) {
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
    public List<Child> getChildInfo(List<String> childUids) {
        if (childUids == null || childUids.isEmpty()) {
            return Collections.emptyList();
        }
        final List<Child> result = new ArrayList<Child>();
        for (final String childUid : childUids) {
            result.add(pyhServiceDao.getChildInfo(childUid));
        }
        return result;
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public UserInfo getUserInfo(String userUid) {
        return customerDao.getUserInfo(userDao.getUserByUid(userUid));
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
        final UserInfo user = customerDao.getKunpoUserInfoBySsn(ssn);
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
        final UserInfo user = customerDao.getEmployeeUserInfoBySsn(ssn);
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
    public UserInfo loginByKunpoNameAndSsn(String kunpoUsername, String ssn) {
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
    public UserInfo loginByLooraNameAndSsn(String looraUsername, String ssn) {
        if (ssn == null || ssn.isEmpty()) {
            return customerDao.getEmployeeUserInfoBySsn(customerDao.getSsnByLooraName(looraUsername));
        }
        
        return customerDao.getEmployeeUserInfoByPortalNameAndSsn(looraUsername, ssn);
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public List<Role> getUserRoles(String userUid) {
        return rolesDao.getEmployeeRoles(getLooraNameByUserUid(userUid));
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<Role> searchRoles(String searchString, int limit) {
        return rolesDao.searchRoles(searchString, limit);
    }

    /**
     * @param roleUid
     * @return
     */
    @Override
    public List<String> getUsernamesInRole(String roleUid) {
        return rolesDao.getUsernamesInRole(roleUid);
    }
}
