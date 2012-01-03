package fi.arcusys.koku.common.service;

import java.util.Date;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jan 3, 2012
 */
public interface MessageRefDAOTest extends MessageRefDAO {
    void updateCreatedDate(final long messageId, final Date date);
}
