package fi.arcusys.koku.common.service;

import java.util.Date;

import fi.arcusys.koku.common.service.datamodel.MessageRef;
import fi.arcusys.koku.common.service.impl.MessageRefDAOImpl;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jan 3, 2012
 */
public class MessageRefDAOTestImpl extends MessageRefDAOImpl implements MessageRefDAOTest {
    public void updateCreatedDate(final long messageId, final Date date) {
        final MessageRef msgRef = getById(messageId);
        msgRef.setCreatedDate(date);
        update(msgRef);
    }
}
