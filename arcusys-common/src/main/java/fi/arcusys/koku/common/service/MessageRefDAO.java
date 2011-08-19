package fi.arcusys.koku.common.service;

import java.util.Collections;
import java.util.List;

import javax.persistence.Query;

import fi.arcusys.koku.common.service.datamodel.MessageRef;
import fi.arcusys.koku.common.service.impl.MessageRefDAOImpl;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
public interface MessageRefDAO extends AbstractEntityDAO<MessageRef>{

	/**
	 * @param messageRefs
	 */
	void updateAll(final List<MessageRef> messageRefs);

}