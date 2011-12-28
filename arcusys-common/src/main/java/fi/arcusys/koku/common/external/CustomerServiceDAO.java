package fi.arcusys.koku.common.external;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.soa.UserInfo;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 17, 2011
 */
public interface CustomerServiceDAO {
    public UserInfo getUserInfo(final User user);

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
    UserInfo getKunpoUserInfoBySsn(final String ssn);

    /**
     * @param searchString
     * @return
     */
    UserInfo getEmployeeUserInfoBySsn(final String ssn);

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
    UserInfo getKunpoUserInfoByPortalNameAndSsn(String kunpoUsername, String ssn);

    /**
     * @param looraUsername
     * @param ssn
     * @return
     */
    UserInfo getEmployeeUserInfoByPortalNameAndSsn(String looraUsername, String ssn);
}
