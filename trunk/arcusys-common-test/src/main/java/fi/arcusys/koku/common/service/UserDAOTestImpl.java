package fi.arcusys.koku.common.service;

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
}
