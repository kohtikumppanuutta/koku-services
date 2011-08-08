package fi.arcusys.koku.kv.service;

import java.util.List;

import fi.arcusys.koku.kv.service.datamodel.Message;
import fi.arcusys.koku.kv.service.datamodel.User;
import fi.arcusys.koku.kv.service.datamodel.Folder;
import fi.arcusys.koku.kv.service.datamodel.FolderType;
import fi.arcusys.koku.kv.service.datamodel.MessageRef;
import fi.arcusys.koku.kv.soa.Criteria;
import fi.arcusys.koku.kv.soa.MessageQuery;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
public interface MessageFolderDAO extends AbstractEntityDAO<Folder>{

	/**
	 * @param testUser
	 * @param string
	 * @return
	 */
	Folder getFolderByUserAndType(final User user, final FolderType folderType);

	/**
	 * @param fromUser
	 * @param sent
	 * @param msg
	 */
	MessageRef storeMessage(final User user, final FolderType folderType, final Message message);

	/**
	 * @param testUser
	 * @param outbox
	 * @return
	 */
	List<MessageRef> getMessagesByUserAndFolderType(final User user, final FolderType folderType);

	/**
	 * @param maxNum 
	 * @param startNum 
	 * @param query 
	 * @param testUser
	 * @param sent
	 * @return
	 */
	List<MessageRef> getMessagesByUserAndFolderType(final User user, final FolderType folderType, final MessageQuery query, final int startNum, final int maxNum);

	/**
	 * @param testUser
	 * @param sent
	 */
	Folder createNewFolderByUserAndType(final User user, final FolderType folderType);

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	Long getTotalMessagesCount(final User user, final FolderType folderType);

	Long getTotalMessagesCount(final User user, final FolderType folderType, final Criteria criteria);

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	Long getUnreadMessagesCount(final User user, final FolderType folderType);
}