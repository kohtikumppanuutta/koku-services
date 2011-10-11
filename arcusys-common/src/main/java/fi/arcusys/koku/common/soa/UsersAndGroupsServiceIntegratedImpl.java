package fi.arcusys.koku.common.soa;

import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 10, 2011
 */
//@Stateless
//@WebService(serviceName = "UsersAndGroupsServiceIntegrated", portName = "UsersAndGroupsServiceIntegratedPort", 
//        endpointInterface = "fi.arcusys.koku.common.soa.UsersAndGroupsService",
//        targetNamespace = "http://soa.common.koku.arcusys.fi/")
public class UsersAndGroupsServiceIntegratedImpl implements UsersAndGroupsService {

    /**
     * @param username
     * @return
     */
    @Override
    public String getUserUidByKunpoName(String username) {
        // TODO Auto-generated method stub
        // userDao.getOrCreateUserByKunpoName(); getUid();
        return null;
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public String getKunpoNameByUserUid(String userUid) {
        // TODO Auto-generated method stub
        // userDao.getUserByUid(); getKunpoName();
        return null;
    }

    /**
     * @param username
     * @return
     */
    @Override
    public String getUserUidByLooraName(String username) {
        // TODO Auto-generated method stub
        // userDao.getOrCreateUserByLooraName(); getUid();
        return null;
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public String getLooraNameByUserUid(String userUid) {
        // TODO Auto-generated method stub
        // userDao.getUserByUid(); getLooraName();
        return null;
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<User> searchUsers(String searchString, int limit) {
        // TODO Auto-generated method stub
        // search user by hetu from customer service; userDao.getOrCreateUserByKunpoName(); return new User;
        return null;
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<User> searchEmployees(String searchString, int limit) {
        // TODO Auto-generated method stub
        // search user in Kahva??? ; userDao.getOrCreateUserByLooraName(); return new User;
        return null;
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<Group> searchGroups(String searchString, int limit) {
        // TODO Auto-generated method stub
        // groups configuration???
        return null;
    }

    /**
     * @param groupUid
     * @return
     */
    @Override
    public List<User> getUsersByGroupUid(String groupUid) {
        // TODO Auto-generated method stub
        // groups configuration???
        return null;
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<Child> searchChildren(String searchString, int limit) {
        // TODO Auto-generated method stub
        // KOKU-500 - get info from PYH by hetu; userDao.getOrCreateUserByKunpoName(); return new Child();
        return null;
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public List<ChildWithHetu> getUsersChildren(String userUid) {
        // TODO Auto-generated method stub
        // userDao.getUserByUid().getKunpoName(); ??? getHetuByKunpoName ? KOKU-500 getUsersChildren(); userDao.getOrCreateByKunpoName
        return null;
    }

    /**
     * @param childUid
     * @return
     */
    @Override
    public Child getChildInfo(String childUid) {
        // TODO Auto-generated method stub
        // userDao.getUserByUid().getKunpoName(); ??? getHetuByKunpoName ? KOKU-500 - get info from PYH by hetu; userDao.getOrCreateUserByKunpoName(); return new Child();
        return null;
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public User getUserInfo(String userUid) {
        // TODO Auto-generated method stub
        // userDao.getUserByUid()
        return null;
    }

    /**
     * @param username
     * @return
     */
    @Override
    public String getSsnByLdapName(String username) {
        // TODO Auto-generated method stub
        // getHetuByKunpoName ???
        return null;
    }

    /**
     * @param ssn
     * @return
     */
    @Override
    public String getUserUid(String ssn) {
        // TODO Auto-generated method stub
        // searchUser(ssn, 1).get(0).getUid()
        return null;
    }

}
