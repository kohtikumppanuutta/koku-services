package fi.arcusys.koku.kv.soa;

import java.util.List;
import fi.arcusys.koku.kv.service.datamodel.FolderType;


import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.kv.service.MessageServiceFacade;
import fi.arcusys.koku.kv.service.dto.MessageTO;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 9, 2011
 */
@Stateless
@WebService(serviceName = "KokuMessageService", portName = "KokuMessageServicePort", 
		endpointInterface = "fi.arcusys.koku.kv.soa.KokuMessageService",
		targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public class KokuMessageServiceImpl implements KokuMessageService {

	@EJB
	private MessageServiceFacade kvFacade;

	/**
	 * @param messageIds
	 */
	@Override
	public void archiveMessages(List<Long> messageIds) {
		kvFacade.archiveMessages(messageIds);
	}

	/**
	 * @param messageIds
	 */
	@Override
	public void deleteMessages(List<Long> messageIds) {
		kvFacade.deleteMessages(messageIds);
	}

	/**
	 * @param messageId
	 * @return
	 */
	@Override
	public MessageTO getMessageById(final long messageId) {
		return kvFacade.getMessageById(messageId);
	}

	/**
	 * @param user
	 * @param folderType
	 * @param subQuery
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<MessageSummary> getMessages(final String user, FolderType folderType, String subQuery, int startNum, int maxNum) {
		return kvFacade.getMessages(user, folderType, null, startNum, maxNum);
	}

	/**
	 * @param user
	 * @param messageType
	 * @param subQuery
	 * @return
	 */
	@Override
	public int getTotalMessages(String user, FolderType messageType, String subQuery) {
		return kvFacade.getTotalMessagesCount(user, messageType);
	}

	/**
	 * @param user
	 * @param messageType
	 * @return
	 */
	@Override
	public int getUnreadMessages(String user, FolderType messageType) {
		return kvFacade.getUnreadMessagesCount(user, messageType);
	}

	/**
	 * @param messageIds
	 * @param messageStatus
	 */
	@Override
	public void setMessagesStatus(final List<Long> messageIds, final MessageStatus messageStatus) {
		kvFacade.setMessageStatus(messageIds, messageStatus);
	}
}
