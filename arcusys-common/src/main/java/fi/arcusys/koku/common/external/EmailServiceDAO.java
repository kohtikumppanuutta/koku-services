package fi.arcusys.koku.common.external;

import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jan 2, 2012
 */
public interface EmailServiceDAO {

    /**
     * @param toUser
     * @param valueFromBundle
     * @param format
     */
    boolean sendMessage(User toUser, String subject, String content);
}
