package fi.arcusys.koku.kv.soa;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.dto.Criteria;
import fi.arcusys.koku.common.service.dto.MessageQuery;


import javax.jws.WebParam;
import javax.jws.WebService;

import fi.arcusys.koku.kv.service.dto.MessageTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 9, 2011
 */
@WebService(targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public interface KokuMessageService {
	/**
	 * Gets the total message number
	 * @param user Username
	 * @param messageType Message Type such as inbox, outbox and archive
	 * @param subQuery Basic query for the message, such as 'message_subject like %keyword%' , 'ORDER BY message_creationDate'
	 * @return The number of messages
	 */
	public int getTotalMessagesOld(@WebParam(name = "user") String user, 
			@WebParam(name = "folderType") FolderType messageType, 
			@WebParam(name = "subQuery") String subQuery);
	
	public int getTotalMessages(@WebParam(name = "user") String user, 
			@WebParam(name = "folderType") FolderType messageType, 
			@WebParam(name = "criteria") Criteria criteria);

	/**
	 * Gets unread messages
	 * @param user Username
	 * @param messageType Message Type such as inbox, outbox and archive
	 * @return Unread messages
	 */
	public int getUnreadMessages(@WebParam(name = "user") String user, 
			@WebParam(name = "folderType") FolderType messageType);
	
	/**
	 * Gets the list of message summary.
	 * @param user Username
	 * @param messageType Message Type such as inbox, outbox and archive
	 * @param subQuery Basic query for the message, such as 'message_subject like %keyword%' , 'ORDER BY message_creationDate'
	 * @param startNum The start message number that fulfill the condition
	 * @param maxNum The maximum amount of messages that fulfill the condition
	 * @return List of messages
	 */
	public List<MessageSummary> getMessagesOld(@WebParam(name = "user") String user, 
			@WebParam(name = "folderType") FolderType messageType, 
			@WebParam(name = "subQuery") String subQuery, 
			@WebParam(name = "startNum") int startNum, 
			@WebParam(name = "maxNum") int maxNum);
	
	public List<MessageSummary> getMessages(@WebParam(name = "user") String user, 
			@WebParam(name = "folderType") FolderType messageType, 
			@WebParam(name = "subQuery") MessageQuery subQuery); 

//	public List<MessageSummary> getMessagesByQueryParams(@WebParam(name = "user") String user, 
//			@WebParam(name = "folderType") FolderType messageType,
//			@WebParam(name = "keywords") String keywords, 
//			@WebParam(name = "fields") List<MessageQuery.Fields> fields, 
//			@WebParam(name = "orderByField") MessageQuery.Fields orderByField,
//			@WebParam(name = "orderByType") OrderBy.Type orderByType,
//			@WebParam(name = "startNum") int startNum, 
//			@WebParam(name = "maxNum") int maxNum);

	/**
	 * Gets the detailed message with content by messageId
	 * @param messageId
	 * @return The detailed message
	 */
	public MessageTO getMessageById(@WebParam(name = "messageId") long messageId);
	
	/**
	 * Sets the message status: read or unread
	 * @param messageIds List of message ids
	 * @param messageStatus
	 */
	public void setMessagesStatus(@WebParam(name = "messageId") List<Long> messageIds, @WebParam(name = "messageStatus")  MessageStatus messageStatus);
	
	/**
	 * Sets the message type: inbox, outbox or archive
	 * @param messageIds List of message ids
	 * @param messageType
	 */
	public void archiveMessages(@WebParam(name = "messageId") List<Long> messageIds);
	
	/**
	 * Deletes the messages
	 * @param messageIds List of message ids to be deleted
	 */
	public void deleteMessages(@WebParam(name = "messageId") List<Long> messageIds);

}
