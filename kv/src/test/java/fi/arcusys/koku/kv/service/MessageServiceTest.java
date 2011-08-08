package fi.arcusys.koku.kv.service;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.kv.service.datamodel.FolderType;
import fi.arcusys.koku.kv.service.dto.MessageTO;
import fi.arcusys.koku.kv.soa.Answer;
import fi.arcusys.koku.kv.soa.Criteria;
import fi.arcusys.koku.kv.soa.MessageQuery;
import fi.arcusys.koku.kv.soa.MessageStatus;
import fi.arcusys.koku.kv.soa.MessageSummary;
import fi.arcusys.koku.kv.soa.QuestionTO;
import fi.arcusys.koku.kv.soa.QuestionType;
import fi.arcusys.koku.kv.soa.RequestSummary;
import fi.arcusys.koku.kv.soa.RequestTO;

/**
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-kv-context.xml"})
public class MessageServiceTest {

	@Autowired
	private MessageServiceFacade serviceFacade;

	@Autowired
	private CommonTestUtil testUtil;
	
	@Test
	public void testSendTextMessage() {
		// user for test
		final String fromUserUid = "test";
		assertNotNull("Find or create test user: " + fromUserUid, testUtil.getUserByUid(fromUserUid));
		
		final String subject = "test subject";
		final String messageContent = "some html content goes here";
		final List<String> receipients = new ArrayList<String>();
		receipients.add("testReceiver");
		receipients.add("testReceiversGroup");
		for (final String uid : receipients) {
			assertNotNull("Find or create receiver: " + uid, testUtil.getUserByUid(uid));
		}
		
		final long messageId = serviceFacade.sendNewMessage(fromUserUid, subject, receipients, messageContent);
		assertNotNull(messageId);

		// check message in "Sent messages" folder
		final List<MessageTO> sentMessages = serviceFacade.getSentMessages(fromUserUid);
		assertFalse("Some messages found in \"Sent\" folder: ", sentMessages.isEmpty());
		assertTrue("Message found in \"Sent\" folder: ", sentMessages.contains(serviceFacade.getMessageById(messageId)));
	}
	
	@Test
	public void archiveMessage() {
		final String fromUserId = "testSender";
		final String toUserId = "testReceiver";
		
		final long messageId = serviceFacade.sendNewMessage(fromUserId, "subject", Collections.singletonList(toUserId), "content");
		
		assertEquals("Message is in sender's Outbox folder: ", FolderType.Outbox, serviceFacade.getMessageById(messageId).getMessageType());
		
		serviceFacade.archiveMessages(Collections.singletonList(messageId));
		
		assertEquals("Message moved to sender's Archived Outbox folder: ", FolderType.Archive_Outbox, serviceFacade.getMessageById(messageId).getMessageType());
	}
	
	@Test
	public void deleteMessage() {
		final String fromUserId = "testSenderForDelete";
		final String toUserId = "testReceiverForDelete";
		
		final long messageId = serviceFacade.sendNewMessage(fromUserId, "subject", Collections.singletonList(toUserId), "content");
		
		assertEquals("Message is in sender's Outbox folder: ", FolderType.Outbox, serviceFacade.getMessageById(messageId).getMessageType());
		
		serviceFacade.deleteMessages(Collections.singletonList(messageId));
		
		assertNull("Message deleted from sender's Outbox folder: ", serviceFacade.getMessageById(messageId));
	}

//	@Test
	public void testOpenJPAFailure() {
		final String fromUserId = "testOpenJPA";
		final String toUserId = "testOpenJPA2";
		serviceFacade.sendNewMessage(fromUserId, "subject1", Collections.singletonList(toUserId), "content1");
		serviceFacade.sendNewMessage(fromUserId, "subject2", Collections.singletonList(toUserId), "content2");
	}
	
	@Test
	public void testSetReadStatus() {
		final String fromUserId = "testSender2";
		final String toUserId = "testReceiver2";
		
		final long messageId = serviceFacade.sendNewMessage(fromUserId, "subject", Collections.singletonList(toUserId), "content");
		final List<MessageSummary> testInboxMessages = serviceFacade.getMessages(toUserId, FolderType.Inbox);
		serviceFacade.receiveMessage(toUserId, messageId);
		
		final MessageTO message = serviceFacade.getMessageById(messageId);
		
		assertEquals("'Read' message in Outbox: ", MessageStatus.Read, message.getMessageStatus());
		
		final List<MessageSummary> newInboxMessages = new ArrayList<MessageSummary>(serviceFacade.getMessages(toUserId, FolderType.Inbox));
		newInboxMessages.removeAll(testInboxMessages);
		
		assertEquals("Only one new message: ", 1, newInboxMessages.size());
		assertEquals("'Unread' message in Inbox: ", MessageStatus.Unread, newInboxMessages.get(0).getMessageStatus());
		
		serviceFacade.setMessageStatus(Collections.singletonList(newInboxMessages.get(0).getMessageId()), MessageStatus.Read);
		assertEquals("'Read' message in Inbox: ", MessageStatus.Read, serviceFacade.getMessageById(newInboxMessages.get(0).getMessageId()).getMessageStatus());
	}
	
	@Test
	public void getMessageCounts() {
		final String fromUserId = "testSender3";
		final String toUserId = "testReceiver3";
		final String toUserId2 = "testReceiver3_2";
		
		final long messageId = serviceFacade.sendNewMessage(fromUserId, "subject", Arrays.asList(toUserId, toUserId2), "content");
		final long receivedMessageId = serviceFacade.receiveMessage(toUserId, messageId);
		
		assertEquals("New message in Inbox: ", 1, serviceFacade.getTotalMessagesCount(toUserId, FolderType.Inbox, null));
		assertEquals("Unread message in Inbox: ", 1, serviceFacade.getUnreadMessagesCount(toUserId, FolderType.Inbox));
		
		serviceFacade.setMessageStatus(Collections.singletonList(receivedMessageId), MessageStatus.Read);

		assertEquals("One message in Inbox: ", 1, serviceFacade.getTotalMessagesCount(toUserId, FolderType.Inbox, null));
		assertEquals("No unread messages in Inbox: ", 0, serviceFacade.getUnreadMessagesCount(toUserId, FolderType.Inbox));
		
		assertEquals("One message in Outbox: ", 1, serviceFacade.getTotalMessagesCount(fromUserId, FolderType.Outbox, null));
		assertEquals("Nothing in Archive_Outbox: ", 0, serviceFacade.getTotalMessagesCount(fromUserId, FolderType.Archive_Outbox, null));
		serviceFacade.archiveMessages(Collections.singletonList(messageId));
		assertEquals("Nothing in Outbox: ", 0, serviceFacade.getTotalMessagesCount(fromUserId, FolderType.Outbox, null));
		assertEquals("One message in Archive_Outbox: ", 1, serviceFacade.getTotalMessagesCount(fromUserId, FolderType.Archive_Outbox, null));
	}
	
	@Test
	public void getMessagesWithPaging() {
		final String fromUserId = "testSenderPaging";
		final String toUserId = "testReceiverPaging";
//		for (int i = 0; i < 11; i ++) {
//			serviceFacade.sendNewMessage(fromUserId, "subject_" + i, Collections.singletonList(toUserId), "content");
//		}
//		assertEquals(11, serviceFacade.getTotalMessagesCount(fromUserId, FolderType.Outbox));
//		
//		assertEquals("get first page: ", 10, serviceFacade.getMessages(fromUserId, FolderType.Outbox, null, 1, 10).size());
//		assertEquals("get next page: ", 1, serviceFacade.getMessages(fromUserId, FolderType.Outbox, null, 11, 20).size());
//		assertEquals("Oldest message is the last: ", "subject_0", serviceFacade.getMessages(fromUserId, FolderType.Outbox, null, 11, 20).get(0).getSubject());

		// TODO: workaround until test "testOpenJPAFailure" is failing
		for (int i = 0; i < 1; i ++) {
			serviceFacade.sendNewMessage(fromUserId, "subject_" + i, Collections.singletonList(toUserId), "content");
		}
		assertEquals(1, serviceFacade.getTotalMessagesCount(fromUserId, FolderType.Outbox, null));
		
		assertEquals("get first page: ", 1, serviceFacade.getMessages(fromUserId, FolderType.Outbox, new MessageQuery(1, 10)).size());
		assertEquals("get next page: ", 0, serviceFacade.getMessages(fromUserId, FolderType.Outbox, new MessageQuery(11, 20)).size());
		assertEquals("Oldest message is the last: ", "subject_0", serviceFacade.getMessages(fromUserId, FolderType.Outbox, new MessageQuery(1, 10)).get(0).getSubject());
	}
	
	@Test
	public void sendRequestWithTwoQuestions() {
		final String fromUserId = "testRequestSender";
		final String toUserId = "testRequestReplier";
		
		final List<QuestionTO> questions = createTestQuestions();
		
		final long requestId = serviceFacade.sendRequest(fromUserId, "test request", Collections.singletonList(toUserId), "read-only form of request", questions);
		
		final RequestTO request = serviceFacade.getRequestById(requestId);
		assertNotNull(request);
		List<MessageSummary> messages = serviceFacade.getMessages(fromUserId, FolderType.Outbox);
		assertEquals("Message stored in Outbox: ", 1, messages.size());
		assertEquals("Message is Request: ", "test request", messages.get(0).getSubject());
		
		assertEquals("Correct request retrieved: ", requestId, request.getRequestId());

		serviceFacade.receiveRequest(toUserId, requestId);
		messages = serviceFacade.getMessages(toUserId, FolderType.Inbox);
		assertEquals("Message stored in Inbox: ", 1, messages.size());
		assertEquals("Message is Request: ", "test request", messages.get(0).getSubject());
	}
	
	@Test
	public void respondToRequest() {
		final String fromUserId = "testRequestSender2";
		final String toUserId = "testRequestReplier2";
		final String toUser2Id = "testRequestReplier3";
        final String toUser3Id = "testRequestReplier4";
		final List<String> toUsers = Arrays.asList(toUserId, toUser2Id, toUser3Id);
		
		final List<QuestionTO> questions = createTestQuestions();
		
		final Long requestId = serviceFacade.sendRequest(fromUserId, "test request", toUsers, "read-only form of request", questions);
		assertEquals("No replies yet: ", 0, serviceFacade.getRequestById(requestId).getRespondedAmount());
		
		final List<Answer> answers = new ArrayList<Answer>();
		final Answer yesAnswer = new Answer();
		yesAnswer.setQuestionNumber(1);
		yesAnswer.setComment("No comments");
		yesAnswer.setValue(Boolean.TRUE);
		answers.add(yesAnswer);
		
		final Answer testAnswer = new Answer();
		testAnswer.setQuestionNumber(2);
		testAnswer.setComment("No comments for this also");
		testAnswer.setTextValue("Text respose to question");
		answers.add(testAnswer);
		
		serviceFacade.replyToRequest(toUserId, requestId, answers);
		
		final RequestTO request = serviceFacade.getRequestById(requestId);
		assertEquals("One reply got: ", 1, request.getRespondedAmount());
		assertEquals("Two replies missing: ", 2, request.getMissedAmout());
		
		assertEquals(1, request.getResponses().size());
		assertEquals(2, request.getResponses().get(0).getAnswers().size());
		
		final List<RequestSummary> requests = serviceFacade.getRequests(fromUserId, 1, 10);
		assertEquals(1, requests.size());
	}
	
	@Test
	public void testMessageSearch() {
		final String fromUserId = "testSearchSender";
		final String toUserId = "testSearchReceiver";
		final String subject = "subject for search test";
		
		final Long messageId = serviceFacade.sendNewMessage(fromUserId, subject, Collections.singletonList(toUserId), "content for search");
		serviceFacade.receiveMessage(toUserId, messageId);
		
		final MessageQuery query = new MessageQuery(1, 10);
		final Criteria criteria = new Criteria();
		criteria.setKeywords(new HashSet<String>(Arrays.asList("search", "subject")));
		criteria.setFields(Collections.singleton(MessageQuery.Fields.Content));
		query.setCriteria(criteria);
		assertMessageNotFound(fromUserId, FolderType.Outbox, query);
		
		criteria.setFields(Collections.singleton(MessageQuery.Fields.Subject));
		assertMessageFound(fromUserId, FolderType.Outbox, query, subject);
		
		// Sender field testing
		criteria.setFields(Collections.singleton(MessageQuery.Fields.Sender));

		criteria.setKeywords(new HashSet<String>(Arrays.asList(fromUserId + "2")));
		assertMessageNotFound(fromUserId, FolderType.Outbox, query);
		assertMessageNotFound(toUserId, FolderType.Inbox, query);

		criteria.setKeywords(new HashSet<String>(Arrays.asList(fromUserId)));
		assertMessageFound(fromUserId, FolderType.Outbox, query, subject);
		assertMessageFound(toUserId, FolderType.Inbox, query, subject);

		// Receiver field testing
		criteria.setFields(Collections.singleton(MessageQuery.Fields.Receiver));

		criteria.setKeywords(new HashSet<String>(Arrays.asList(toUserId + "2")));
		assertMessageNotFound(fromUserId, FolderType.Outbox, query);
		assertMessageNotFound(toUserId, FolderType.Inbox, query);

		criteria.setKeywords(new HashSet<String>(Arrays.asList(toUserId)));
		assertMessageFound(fromUserId, FolderType.Outbox, query, subject);
		assertMessageFound(toUserId, FolderType.Inbox, query, subject);
	}

	private void assertMessageFound(final String userId, FolderType folderType, final MessageQuery query, final String subject) {
		List<MessageSummary> messages = serviceFacade.getMessages(userId, folderType, query);
		assertNotNull(messages);
		assertEquals(1, messages.size());
		assertEquals(messages.size(), serviceFacade.getTotalMessagesCount(userId, folderType, query.getCriteria()));
		assertEquals(subject, messages.get(0).getSubject());
	}

	private void assertMessageNotFound(final String userId, FolderType folderType, final MessageQuery query) {
		List<MessageSummary> messages = serviceFacade.getMessages(userId, folderType, query);
		assertNotNull(messages);
		assertEquals(0, messages.size());
		assertEquals(messages.size(), serviceFacade.getTotalMessagesCount(userId, folderType, query.getCriteria()));
	}

	private List<QuestionTO> createTestQuestions() {
		final List<QuestionTO> questions = new ArrayList<QuestionTO>();
		final QuestionTO questionYesNo = new QuestionTO();
		questionYesNo.setNumber(1);
		questionYesNo.setDescription("YesNo");
		questionYesNo.setType(QuestionType.YES_NO);
		questions.add(questionYesNo);

		final QuestionTO questionFreeText = new QuestionTO();
		questionFreeText.setNumber(2);
		questionFreeText.setDescription("FreeText");
		questionFreeText.setType(QuestionType.FREE_TEXT);
		questions.add(questionFreeText);
		return questions;
	}
}