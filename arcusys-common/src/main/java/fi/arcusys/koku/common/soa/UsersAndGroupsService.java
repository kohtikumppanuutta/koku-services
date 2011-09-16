package fi.arcusys.koku.common.soa;

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
    String getUserUidByKunpoName(@WebParam(name = "username") final String username);

    @WebResult(name = "userUid")
    String getUserUidByLooraName(@WebParam(name = "username") final String username);
    
    // Intalio part
    
    // get user's relations: children, parents/guardians
    
    // get groups
    // get users
}
