package fi.arcusys.koku.common.service.impl;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.MessageDAO;
import fi.arcusys.koku.common.service.datamodel.Message;

/**
 * CRUD operations for messages. 
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Apr 6, 2011
 */
@Stateless
public class MessageDAOImpl extends AbstractEntityDAOImpl<Message> implements MessageDAO {
	public MessageDAOImpl() {
		super(Message.class);
	}
}
