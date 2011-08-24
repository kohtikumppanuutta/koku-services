package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
public interface ConsentDAO extends AbstractEntityDAO<Consent> {

    /**
     * @param orCreateUser
     * @param startNum
     * @param maxNum
     * @return
     */
    List<Consent> getAssignedConsents(final User user, final int startNum, final int maxNum);

}
