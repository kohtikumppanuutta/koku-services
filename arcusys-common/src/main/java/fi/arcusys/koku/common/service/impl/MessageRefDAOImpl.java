package fi.arcusys.koku.common.service.impl;

import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.MessageRefDAO;
import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.datamodel.MessageRef;
import fi.arcusys.koku.common.service.datamodel.User;

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

    /**
     * @param olderThen
     */
    @Override
    public int deleteOldMessages(Date olderThen) {
        return super.executeBulkOperation("deleteOldMessages", Collections.singletonMap("olderThen", olderThen));
    }

    /**
     * @param asList
     * @param time
     * @return
     */
    @Override
    public List<MessageRef> getMessagesByFolderTypeAndCreateDate(Collection<FolderType> folderTypes, Date time) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("folderTypes", folderTypes);
        params.put("olderThen", time);
        return super.getResultList("findOldMessagesByFolderType", params);
    }

    /**
     * @param userUid
     * @param singleton
     * @param time
     * @return
     */
    @Override
    public List<MessageRef> getMessagesByUserAndFolderTypeAndCreateDate(User user, Collection<FolderType> folderTypes, Date time) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("user", user);
        params.put("folderTypes", folderTypes);
        params.put("olderThen", time);
        return super.getResultList("findOldMessagesByUserAndFolderType", params);
    }
}
