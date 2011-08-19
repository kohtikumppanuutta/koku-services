package fi.arcusys.koku.kv.service;

import java.util.List;

import javax.jws.WebService;

import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.dto.Criteria;
import fi.arcusys.koku.common.service.dto.MessageQuery;
import fi.arcusys.koku.common.service.exception.UserNotFoundException;
import fi.arcusys.koku.kv.soa.Answer;
import fi.arcusys.koku.kv.soa.MessageStatus;
import fi.arcusys.koku.kv.soa.MessageSummary;
import fi.arcusys.koku.kv.soa.QuestionTO;
import fi.arcusys.koku.kv.soa.RequestSummary;
import fi.arcusys.koku.kv.soa.RequestTO;
import fi.arcusys.koku.kv.service.dto.MessageTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
//@WebService
public interface MessageServiceFacade {

	List<MessageTO> getSentMessages(final String userUid);
	
	Long sendNewMessage(final String fromUserUid, final String subject, final List<String> receipients, final String content);
	
	Long receiveMessage(final String toUserUid, final Long messageId);
	
	List<MessageSummary> getMessages(final String userUid, final FolderType folderType);
	
	MessageTO getMessageById(final Long messageId);
	
	void archiveMessages(final List<Long> messageIds);

	void deleteMessages(final List<Long> messageIds);

	void setMessageStatus(final List<Long> messageIds, MessageStatus read);

	int getTotalMessagesCount(final String userId, final FolderType folderType, final Criteria criteria);

	int getUnreadMessagesCount(final String userId, final FolderType folderType);

	Long sendRequest(final String fromUserId, final String subject, final List<String> receipients, final String content, final List<QuestionTO> questions);
	
	RequestTO getRequestById(final Long requestId);

	Long receiveRequest(final String toUserId, final Long requestId);

	Long replyToRequest(final String toUserId, final Long requestId, final List<Answer> answers);

	/**
	 * @param fromUserId
	 * @param i
	 * @param j
	 * @return
	 */
	List<RequestSummary> getRequests(final String userId, int startNum, int maxNum);

	/**
	 * @param fromUserId
	 * @param outbox
	 * @param messageQuery
	 * @return
	 */
	List<MessageSummary> getMessages(final String userId, final FolderType folderType, final MessageQuery query);
}