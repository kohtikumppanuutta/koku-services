package fi.arcusys.koku.common.soa;

import javax.ejb.Stateless;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 16, 2011
 */
@Stateless
@WebService(serviceName = "UsersAndGroupsService", portName = "UsersAndGroupsServicePort", 
        endpointInterface = "fi.arcusys.koku.common.soa.UsersAndGroupsService",
        targetNamespace = "http://soa.common.koku.arcusys.fi/")
public class UsersAndGroupsServiceImpl implements UsersAndGroupsService {

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

}
