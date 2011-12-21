package fi.arcusys.koku.common.service;

import java.util.Date;
import java.util.List;

import fi.arcusys.koku.common.service.datamodel.MessageRef;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
public interface MessageRefDAO extends AbstractEntityDAO<MessageRef>{

	/**
	 * @param messageRefs
	 */
	void updateAll(final List<MessageRef> messageRefs);

    /**
     * @param time
     */
    int deleteOldMessages(Date olderThen);

}