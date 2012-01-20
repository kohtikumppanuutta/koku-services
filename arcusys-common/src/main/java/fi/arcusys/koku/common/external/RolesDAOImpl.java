package fi.arcusys.koku.common.external;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fi.arcusys.koku.common.soa.Role;

/**
 * DAO implementation for accessing roles information: search roles, get role users etc.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 12, 2011
 */
@Stateless
public class RolesDAOImpl implements RolesDAO {

    @EJB
    private LdapDAO ldapDao;
    
    /**
     * @param userUid
     * @return
     */
    @Override
    public List<Role> getEmployeeRoles(final String employeeName) {
        return ldapDao.getEmployeeRoles(employeeName);
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<Role> searchRoles(String searchString, int limit) {
        return ldapDao.searchRoles(searchString);
    }

    /**
     * @param roleUid
     * @return
     */
    @Override
    public List<String> getUsernamesInRole(String roleUid) {
        return ldapDao.getRoleMembers(roleUid);
    }

}
