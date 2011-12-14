package fi.arcusys.koku.common.external;

import java.util.List;
import java.util.Map;

import fi.arcusys.koku.common.soa.Role;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 2, 2011
 */
public interface LdapDAO {

    /**
     * @param employeePortalName
     * @return
     */
    String getSsnByLooraName(String employeePortalName);

    /**
     * @param citizenPortalName
     * @return
     */
    String getSsnByKunpoName(String citizenPortalName);

    /**
     * @param ssn
     * @return
     */
    String getLooraNameBySsn(String ssn);

    /**
     * @param ssn
     * @param ssn2
     */
    void createKunpoUserInLdap(final String kunpoUsername, final String ssn, final String firstName, final String lastName);

    /**
     * @param ssn
     * @return
     */
    String getKunpoNameBySsn(String ssn);

    /**
     * @param groupsDao
     * @param ldapNameBySsn
     * @param kunpoUsername
     */
    void updateKunpoLdapName(String ldapNameBySsn, String kunpoUsername);

    /**
     * @param searchString
     * @return
     */
    Map<String, String> searchGroups(String searchString);

    /**
     * @param groupUid
     * @return
     */
    List<String> getGroupMembers(String groupUid);

    /**
     * @param employeeName
     * @return
     */
    List<Role> getEmployeeRoles(String employeeName);

    /**
     * @param searchString
     * @return
     */
    List<Role> searchRoles(String searchString);

    List<String> getRoleMembers(String groupUid);
}
