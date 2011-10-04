package fi.arcusys.koku.kv.service;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.common.service.KokuSystemNotificationsService;
import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.dto.Criteria;
import fi.arcusys.koku.common.service.dto.MessageQuery;
import fi.arcusys.koku.common.service.exception.UserNotFoundException;
import fi.arcusys.koku.kv.soa.Answer;
import fi.arcusys.koku.kv.soa.MessageStatus;
import fi.arcusys.koku.kv.soa.MessageSummary;
import fi.arcusys.koku.kv.soa.MultipleChoiceTO;
import fi.arcusys.koku.kv.soa.QuestionTO;
import fi.arcusys.koku.kv.soa.Questions;
import fi.arcusys.koku.kv.soa.Receipients;
import fi.arcusys.koku.kv.soa.RequestSummary;
import fi.arcusys.koku.kv.soa.RequestTO;
import fi.arcusys.koku.kv.soa.RequestTemplateExistenceStatus;
import fi.arcusys.koku.kv.soa.RequestTemplateSummary;
import fi.arcusys.koku.kv.soa.RequestTemplateTO;
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

	Long sendRequest(final String fromUserId, final String subject, final List<String> receipients, final String content, 
	        final List<QuestionTO> questions, final List<MultipleChoiceTO> choices, 
            XMLGregorianCalendar replyTill, 
            Integer notifyBeforeDays);
	
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
	
    void createRequestTemplate(final String userUid, 
            final String subject, 
            final List<QuestionTO> questions,
            final List<MultipleChoiceTO> choices);
    
    List<RequestTemplateSummary> getRequestTemplateSummary(
            final String subjectPrefix, 
            final int limit);
    
    RequestTemplateTO getRequestTemplateById(
            final long requestTemplateId);
    
    Long sendRequestWithTemplate(
            final String fromUserUid, 
            final long requestTemplateId, 
            final String subject,
            final List<String> receipients,
            final String content, 
            XMLGregorianCalendar replyTill, 
            Integer notifyBeforeDays);

    /**
     * @param fromUserUid
     * @param subject
     * @param toUserUid
     * @param content
     * @return
     */
    Long receiveNewMessage(final String fromUserUid, final String subject, final String toUserUid, final String content);

    void deliverMessage(final String fromUser, final List<String> toUsers, final String subject, final String content);

    /**
     * @param userUid
     * @param subject
     * @return
     */
    RequestTemplateExistenceStatus isRequestTemplateExist(final String userUid, final String subject);

    /**
     * @param requestTemplateId
     * @param userUid
     * @param subject
     * @param list
     * @param list2
     * @return
     */
    void updateRequestTemplate(String userUid, String subject, List<QuestionTO> questions, List<MultipleChoiceTO> choices);
}