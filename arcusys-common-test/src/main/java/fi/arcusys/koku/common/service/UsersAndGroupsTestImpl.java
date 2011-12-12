package fi.arcusys.koku.common.service;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.arcusys.koku.common.soa.Role;
import fi.arcusys.koku.common.soa.UsersAndGroupsServiceImpl;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 12, 2011
 */
public class UsersAndGroupsTestImpl extends UsersAndGroupsServiceImpl {
    
    private Map<String, List<Role>> userRoles = new HashMap<String, List<Role>>();
    
    public void setUserRoles(final String userUid, final List<Role> roles) {
        userRoles.put(userUid, roles);
    }
    
    /**
     * @param userUid
     * @return
     */
    @Override
    public List<Role> getUserRoles(String userUid) {
        if (userRoles.containsKey(userUid)) {
            return userRoles.get(userUid);
        }
        return Collections.emptyList();
    }
}
