package fi.arcusys.koku.common.external;

import java.util.List;

import fi.arcusys.koku.common.soa.Group;
import fi.arcusys.koku.common.soa.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 17, 2011
 */
public interface GroupsDAO {

    public List<Group> searchGroups(String searchString, int limit);

    public List<User> getUsersByGroupUid(String groupUid);
    
    public void addUserToSystemGroup(final String userDn, final String groupUid);
    
    public void updateMembership(final String oldUserDn, final String newUserDn);
}
