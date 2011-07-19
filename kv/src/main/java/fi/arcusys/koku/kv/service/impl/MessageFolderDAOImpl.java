package fi.arcusys.koku.kv.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fi.arcusys.koku.kv.service.datamodel.Message;
import fi.arcusys.koku.kv.service.datamodel.User;
import fi.arcusys.koku.kv.service.MessageFolderDAO;
import fi.arcusys.koku.kv.service.MessageRefDAO;
import fi.arcusys.koku.kv.service.datamodel.Folder;
import fi.arcusys.koku.kv.service.datamodel.FolderType;
import fi.arcusys.koku.kv.service.datamodel.MessageRef;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Stateless
public class MessageFolderDAOImpl extends AbstractEntityDAOImpl<Folder> implements MessageFolderDAO {
	@EJB
	private MessageRefDAO messageRefDao;
	
	public MessageFolderDAOImpl() {
		super(Folder.class);
	}

	/**
	 * @param user
	 * @param folderType
	 * @return
	 */
	public Folder getFolderByUserAndType(final User user, final FolderType folderType) {
		return getSingleResultOrNull("findFolderByUserAndType", getCommonQueryParams(user, folderType));
	}

	/**
	 * @param user
	 * @param folderType
	 * @param message
	 * @return
	 */
	public MessageRef storeMessage(final User user, final FolderType folderType, final Message message) {
		Folder folder = getFolderByUserAndType(user, folderType);
		
		if (folder == null) {
			folder = createNewFolderByUserAndType(user, folderType);
		}
		
		final MessageRef msgRef = new MessageRef();
		msgRef.setFolder(folder);
		msgRef.setMessage(message);
		return messageRefDao.create(msgRef);
	}

	/**
	 * @param user
	 * @param folderType
	 * @return
	 */
	@Override
	public List<MessageRef> getMessagesByUserAndFolderType(final User user, final FolderType folderType) {
		return getMessagesByUserAndFolderType(user, folderType, null, FIRST_RESULT_NUMBER, MAX_RESULTS_COUNT);
	}

	/**
	 * @param user
	 * @param folderType
	 * @return
	 */
	public List<MessageRef> getMessagesByUserAndFolderType(final User user, final FolderType folderType, final Object query, final int startNum, final int maxNum) {
		return getResultList("findMessagesByUserAndFolderType", getCommonQueryParams(user, folderType), startNum, maxNum);
	}

	/**
	 * @param user
	 * @param folderType
	 * @return
	 */
	public Folder createNewFolderByUserAndType(final User user, final FolderType folderType) {
		final Folder newFolder = new Folder();
		newFolder.setFolderType(folderType);
		newFolder.setUser(user);
		return create(newFolder);
	}

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	@Override
	public Long getTotalMessagesCount(final User user, final FolderType folderType) {
		return getSingleResult("getTotalMessagesCount", getCommonQueryParams(user, folderType));
	}

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	@Override
	public Long getUnreadMessagesCount(final User user, FolderType folderType) {
		final Map<String, Object> params = getCommonQueryParams(user, folderType);
		params.put("isRead", Boolean.FALSE);
		return getSingleResult("getMessagesCountByReadStatus", params);
	}

	private Map<String, Object> getCommonQueryParams(final User user, final FolderType folderType) {
		final Map<String, Object> params = new HashMap<String,Object>();
		params.put("user", user);
		params.put("folderType", folderType);
		return params;
	}
}
