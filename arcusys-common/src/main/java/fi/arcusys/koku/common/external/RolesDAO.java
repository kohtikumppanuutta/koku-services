package fi.arcusys.koku.common.external;

import java.util.List;

import fi.arcusys.koku.common.soa.Role;

/**
 * DAO interface for processing roles: search, getting role-users etc.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 12, 2011
 */
public interface RolesDAO {
    List<Role> getEmployeeRoles(final String employeeName);
    
    List<Role> searchRoles(final String searchString, final int limit);

    /**
     * @param roleUid
     * @return
     */
    List<String> getUsernamesInRole(String roleUid);
}
