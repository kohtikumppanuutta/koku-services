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
    public User getKunpoUserInfoBySsn(final String ssn);

    /**
     * @param searchString
     * @return
     */
    public User getEmployeeUserInfoBySsn(final String ssn);

    /**
     * @param userUid
     * @return
     */
    String getSsnByUserUid(String userUid);
}
