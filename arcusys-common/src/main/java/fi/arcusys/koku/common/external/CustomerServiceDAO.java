package fi.arcusys.koku.common.external;

import java.util.List;

import fi.arcusys.koku.common.soa.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 17, 2011
 */
public interface CustomerServiceDAO {
    public User getUserInfo(final String userUid);

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
     * @param searchString
     * @return
     */
    User getKunpoUserInfoBySsn(final String ssn);

    /**
     * @param searchString
     * @return
     */
    User getEmployeeUserInfoBySsn(final String ssn);

    /**
     * @param userUid
     * @return
     */
    String getSsnByUserUid(String userUid);

    /**
     * @param kunpoUsername
     * @param ssn
     * @return
     */
    User getKunpoUserInfoByPortalNameAndSsn(final GroupsDAO groupsDao, String kunpoUsername, String ssn);

    /**
     * @param looraUsername
     * @param ssn
     * @return
     */
    User getEmployeeUserInfoByPortalNameAndSsn(String looraUsername, String ssn);
}
