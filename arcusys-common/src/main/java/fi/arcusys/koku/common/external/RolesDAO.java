package fi.arcusys.koku.common.external;

import java.util.List;

import javax.jws.WebParam;

import fi.arcusys.koku.common.soa.Role;
import fi.arcusys.koku.common.soa.User;

/**
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
