package fi.arcusys.koku.common.external;

import java.util.List;

import fi.arcusys.koku.common.soa.Group;
import fi.arcusys.koku.common.soa.UserInfo;

/**
 * DAO interface for accessing user-visible groups information. 
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 17, 2011
 */
public interface GroupsDAO {

    public List<Group> searchGroups(String searchString, int limit);

    public List<UserInfo> getUsersByGroupUid(String groupUid);
}
