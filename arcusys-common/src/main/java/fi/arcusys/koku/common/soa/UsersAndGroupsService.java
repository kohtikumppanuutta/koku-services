package fi.arcusys.koku.common.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 16, 2011
 */
@WebService(targetNamespace = "http://soa.common.koku.arcusys.fi/")
public interface UsersAndGroupsService {
    // Portal part
    
    @WebResult(name = "userUid")
    String getUserUidByKunpoName(@WebParam(name = "kunpoUsername") final String username);

    @WebResult(name = "kunpoUsername")
    String getKunpoNameByUserUid(@WebParam(name = "userUid") final String userUid);

    @WebResult(name = "userUid")
    String getUserUidByLooraName(@WebParam(name = "looraUsername") final String username);
    
    @WebResult(name = "looraUsername")
    String getLooraNameByUserUid(@WebParam(name = "userUid") final String userUid);

    // Intalio part

    // get groups
    // get users
    @WebResult(name = "user")
    List<User> searchUsers(
            @WebParam(name = "searchString") final String searchString, 
            @WebParam(name = "limit") final int limit);
    
    @WebResult(name = "group")
    List<Group> searchGroups(
            @WebParam(name = "searchString") final String searchString, 
            @WebParam(name = "limit") final int limit);
    
    @WebResult(name = "user")
    List<User> getUsersByGroupUid(@WebParam(name = "groupUid") final String groupUid);

    @WebResult(name = "child")
    List<Child> searchChildren(
            @WebParam(name = "searchString") final String searchString, 
            @WebParam(name = "limit") final int limit);

    // get user's children with their parents/guardians (excluding user itself)
    @WebResult(name = "child")
    List<ChildWithHetu> getUsersChildren(@WebParam(name = "userUid") final String userUid);
    
    @WebResult(name = "child")
    Child getChildInfo(@WebParam(name = "childUid") final String childUid);

    @WebResult(name = "user")
    User getUserInfo(@WebParam(name = "userUid") final String userUid);

    String getSsnByLdapName(final String username);

    /**
     * @param searchString
     * @return
     */
    String getUserUid(@WebParam(name = "ssn") final String ssn);
}
