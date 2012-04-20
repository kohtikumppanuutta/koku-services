package fi.arcusys.koku.common.service;

import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.impl.UserDAOImpl;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 13, 2011
 */
public class UserDAOTestImpl extends UserDAOImpl {
    /**
     * @return
     */
    @Override
    protected boolean allowCreationByUid() {
        // TODO Auto-generated method stub
        return true;
    }

    /**
     * @param kunpoName
     * @return
     */
    @Override
    public User getUserByCitizenPortalNameOrNull(String kunpoName) {
        return getOrCreateUserByCitizenPortalName(kunpoName);
    }

    /**
     * @param looraName
     * @return
     */
    @Override
    public User getUserByEmployeePortalNameOrNull(String looraName) {
        return getOrCreateUserByEmployeePortalName(looraName);
    }
}
