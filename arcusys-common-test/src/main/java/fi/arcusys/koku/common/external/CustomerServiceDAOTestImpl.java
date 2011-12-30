package fi.arcusys.koku.common.external;

import fi.arcusys.koku.common.external.CustomerServiceDAOImpl;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.soa.UserInfo;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 28, 2011
 */
public class CustomerServiceDAOTestImpl extends CustomerServiceDAOImpl {
    /**
     * @param user
     * @return
     */
    @Override
    public UserInfo getUserInfo(User user) {
        if (user == null) {
            return null;
        }
        
        return new UserInfo(user.getUid(), "User: " + user.getUid());
    }
}
