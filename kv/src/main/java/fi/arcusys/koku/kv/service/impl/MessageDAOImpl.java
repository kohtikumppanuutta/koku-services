package fi.arcusys.koku.kv.service.impl;

import javax.ejb.Stateless;

import fi.arcusys.koku.kv.service.datamodel.Message;
import fi.arcusys.koku.kv.service.MessageDAO;

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
