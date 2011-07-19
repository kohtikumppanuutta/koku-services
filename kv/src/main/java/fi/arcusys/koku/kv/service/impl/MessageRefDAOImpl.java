package fi.arcusys.koku.kv.service.impl;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.Query;

import fi.arcusys.koku.kv.service.MessageRefDAO;
import fi.arcusys.koku.kv.service.datamodel.MessageRef;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
@Stateless
public class MessageRefDAOImpl extends AbstractEntityDAOImpl<MessageRef> implements MessageRefDAO{
	public MessageRefDAOImpl() {
		super(MessageRef.class);
	}
	
	/**
	 * @param msgRef
	 * @return
	 */
	@Override
	public MessageRef create(final MessageRef msgRef) {
		// workaround: each DAO maintains its separate PesistenceContext - Folder from FolderDAO is not managed in MessageRefDAO's EntityManager
		msgRef.setFolder(em.merge(msgRef.getFolder()));
		msgRef.setMessage(em.merge(msgRef.getMessage()));
		
		return super.create(msgRef);
	}

	@Override
	protected String getListByIdsQueryName() {
		return MessageRef.GET_MESSAGE_REFS_BY_IDS;
	}

	/**
	 * @param messageRefs
	 */
	@Override
	public void updateAll(List<MessageRef> messageRefs) {
		for (final MessageRef messageRef : messageRefs) {
			super.update(messageRef);
		}
	}

	protected String getDeleteByIdsQueryName() {
		return MessageRef.DELETE_MESSAGE_REFS_BY_IDS;
	}
}
