package fi.arcusys.koku.kv.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Local;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.AbstractEntityDAO;
import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.KokuSystemNotificationsService;
import fi.arcusys.koku.common.service.MessageDAO;
import fi.arcusys.koku.common.service.MessageFolderDAO;
import fi.arcusys.koku.common.service.MessageRefDAO;
import fi.arcusys.koku.common.service.RequestDAO;
import fi.arcusys.koku.common.service.RequestTemplateDAO;
import fi.arcusys.koku.common.service.ResponseDAO;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.Folder;
import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.datamodel.FreeTextAnswer;
import fi.arcusys.koku.common.service.datamodel.Message;
import fi.arcusys.koku.common.service.datamodel.MessageRef;
import fi.arcusys.koku.common.service.datamodel.MultipleChoice;
import fi.arcusys.koku.common.service.datamodel.Question;
import fi.arcusys.koku.common.service.datamodel.QuestionType;
import fi.arcusys.koku.common.service.datamodel.Request;
import fi.arcusys.koku.common.service.datamodel.RequestTemplate;
import fi.arcusys.koku.common.service.datamodel.Response;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.datamodel.Visibility;
import fi.arcusys.koku.common.service.datamodel.YesNoAnswer;
import fi.arcusys.koku.common.service.dto.Criteria;
import fi.arcusys.koku.common.service.dto.MessageQuery;
import fi.arcusys.koku.common.service.exception.UserNotFoundException;
import fi.arcusys.koku.kv.service.MessageServiceFacade;
import fi.arcusys.koku.kv.soa.Answer;
import fi.arcusys.koku.kv.soa.AnswerTO;
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
import fi.arcusys.koku.kv.soa.RequestTemplateVisibility;
import fi.arcusys.koku.kv.soa.ResponseTO;
import fi.arcusys.koku.kv.service.dto.MessageTO;

import static fi.arcusys.koku.common.service.AbstractEntityDAO.FIRST_RESULT_NUMBER;
import static fi.arcusys.koku.common.service.AbstractEntityDAO.MAX_RESULTS_COUNT;

/**
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Stateless
@Local({MessageServiceFacade.class, KokuSystemNotificationsService.class})
public class MessageServiceFacadeImpl implements MessageServiceFacade, KokuSystemNotificationsService {
	private final static Logger logger = LoggerFactory.getLogger(MessageServiceFacadeImpl.class);
	
	public static final String SYSTEM_USER_NAME_FOR_NOTIFICATIONS = "KohtiKumppanuutta";
	
	@EJB
	private MessageDAO messageDao;

	@EJB
	private MessageRefDAO messageRefDao;

	@EJB
	private UserDAO userDao;
	
	@EJB
	private MessageFolderDAO folderDAO;
	
	@EJB
	private RequestDAO requestDAO;

    @EJB
    private RequestTemplateDAO requestTemplateDAO;

    @EJB
	private ResponseDAO responseDAO;

	public Long sendNewMessage(final String fromUserUid, final String subject, final List<String> receipientUids, final String content) {
		final User fromUser = getUserByUid(fromUserUid);
		
        Message msg = new Message();
		fillMessage(msg, fromUser, subject, receipientUids, content);
		
		msg = messageDao.create(msg);
		
		final MessageRef storedMessage = folderDAO.storeMessage(fromUser, FolderType.Outbox, msg);
		storedMessage.setRead(true);
		messageRefDao.update(storedMessage);
		return storedMessage.getId();
	}

	private void fillMessage(Message msg,
			final User fromUser, final String subject,
			final List<String> receipientUids, final String content) {
		final Set<User> receipients = new HashSet<User>();
		for (final String receipientUid : receipientUids) {
			receipients.add(getUserByUid(receipientUid));
		}
		msg.setFrom(fromUser);
		msg.setSubject(subject);
		msg.setReceipients(receipients);
		msg.setText(content);
	}
	
	private User getUserByUid(final String userUid) {
		return userDao.getOrCreateUser(userUid);
	}

	/**
	 * @param userUid
	 * @return
	 */
	public List<MessageTO> getSentMessages(final String userUid) {
		final List<MessageRef> messages = folderDAO.getMessagesByUserAndFolderType(getUserByUid(userUid), FolderType.Outbox, 
				null, FIRST_RESULT_NUMBER, FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);
		
		final List<MessageTO> result = new ArrayList<MessageTO>();
		for (final MessageRef messageRef : messages) {
			final MessageTO msg = new MessageTO();
			msg.setContent(messageRef.getMessage().getText());
			result.add(convertMessageToDTO(messageRef, msg));
		}
		return result;
	}

	private <M extends MessageSummary> M convertMessageToDTO(final MessageRef messageRef, final M msg) {
		final Message message = messageRef.getMessage();
		msg.setMessageId(messageRef.getId());
		msg.setSender(message.getUser().getUid());
		msg.setSubject(message.getSubject());
		msg.setCreationDate(CalendarUtil.getXmlGregorianCalendar(message.getCreatedDate()));
		msg.setMessageStatus(MessageStatus.getStatus(messageRef.isRead()));
		msg.setMessageType(messageRef.getFolder().getFolderType());
		
		msg.setRecipients(getUidsListByUsers(message.getReceipients()));
		return msg;
	}

	private List<String> getUidsListByUsers(final Set<User> receipients2) {
		final List<String> receipients = new ArrayList<String>();
		for (final User receipient : receipients2) {
			receipients.add(receipient.getUid());
		}
		return receipients;
	}

	/**
	 * @param messageId
	 * @return
	 */
	@Override
	public MessageTO getMessageById(final Long messageRefId) {
		final MessageRef msgRef = messageRefDao.getById(messageRefId);
		if (msgRef == null) {
			logger.info("Message with ID " + messageRefId + " is not found.");
			return null;
		}
		final MessageTO msg = new MessageTO();
		msg.setContent(msgRef.getMessage().getText());
		return convertMessageToDTO(msgRef, msg);
	}

	/**
	 * @param userUid
	 * @param folderType
	 * @return
	 */
	@Override
	public List<MessageSummary> getMessages(final String userUid, final FolderType folderType) {
		final MessageQuery query = new MessageQuery();
		query.setStartNum(FIRST_RESULT_NUMBER);
		query.setMaxNum(FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);
		return getMessages(userUid, folderType, query);
	}

	/**
	 * @param toUserUid
	 * @param messageId
	 */
	@Override
	public Long receiveMessage(final String toUserUid, final  Long messageId) {
		final MessageRef sentMessage = messageRefDao.getById(messageId);
		if (sentMessage == null) {
			throw new IllegalArgumentException("Message with ID " + messageId + " not found.");
		}

        final User toUser = getUserByUid(toUserUid);
        final Message message = sentMessage.getMessage();
        
        return doReceiveNewMessage(toUser, message).getId();
	}

    private MessageRef doReceiveNewMessage(final User toUser,
            final Message message) {
        final MessageRef msgRef = new MessageRef();
        Folder folder = getOrCreateFolder(toUser, FolderType.Inbox);

		msgRef.setFolder(folder);
        msgRef.setMessage(message);
		messageRefDao.create(msgRef);
        return msgRef;
    }

	private Folder getOrCreateFolder(final User toUser, FolderType folderType) {
		Folder folder = folderDAO.getFolderByUserAndType(toUser, folderType);
		if (folder == null) {
			folder = folderDAO.createNewFolderByUserAndType(toUser, folderType);
		}
		return folder;
	}

	/**
	 * @param messageIds
	 */
	@Override
	public void archiveMessages(final List<Long> messageRefIds) {
		final List<MessageRef> messageRefs = messageRefDao.getListByIds(messageRefIds);

		final Map<User, Folders> folders = new HashMap<User, Folders>();
		
		for (final MessageRef messageRef : messageRefs) {
			final User user = messageRef.getFolder().getUser();
			if (!folders.containsKey(user)) {
				folders.put(user, new Folders(
						getOrCreateFolder(user, FolderType.Archive_Inbox),
						getOrCreateFolder(user, FolderType.Archive_Outbox))
						);
			}
			if (FolderType.Inbox == messageRef.getFolder().getFolderType()) {
				messageRef.setFolder(folders.get(user).archiveInbox);
			} else if (FolderType.Outbox == messageRef.getFolder().getFolderType()) {
				messageRef.setFolder(folders.get(user).archiveOutbox);
			}
		}
		messageRefDao.updateAll(messageRefs);
	}
	
	private static class Folders {
		private Folder archiveInbox;
		private Folder archiveOutbox;
		public Folders(final Folder archiveInbox, final Folder archiveOutbox) {
			this.archiveInbox = archiveInbox; 
			this.archiveOutbox = archiveOutbox;
		} 
	}

	/**
	 * @param messageIds
	 */
	@Override
	public void deleteMessages(List<Long> messageRefIds) {
		messageRefDao.deleteAll(messageRefIds);
	}

	/**
	 * @param messageId
	 * @param read
	 */
	@Override
	public void setMessageStatus(final List<Long> messageIds, MessageStatus status) {
		final List<MessageRef> messages = messageRefDao.getListByIds(messageIds);
		if (messages.isEmpty()) {
			return;
		}
		
		for (final MessageRef message : messages) {
			message.setRead(status.isRead());
		}
		messageRefDao.updateAll(messages);
	}

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	@Override
	public int getTotalMessagesCount(final String userId, final FolderType folderType, final Criteria criteria) {
		return getIntValue(folderDAO.getTotalMessagesCount(getUserByUid(userId), folderType, criteria));
	}

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	@Override
	public int getUnreadMessagesCount(final String userId, final FolderType folderType) {
		return getIntValue(folderDAO.getUnreadMessagesCount(getUserByUid(userId), folderType));
	}
	
	private int getIntValue(final Long count) {
		if (count == null) {
			return 0;
		}
		return count.intValue();
	}

	/**
	 * @param userUid
	 * @param folderType
	 * @param query
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<MessageSummary> getMessages(final String userUid, final FolderType folderType, final MessageQuery query) {
		final int startNum = query.getStartNum();
		final int maxNum = query.getMaxNum();
		validateStartAndMaxNum(startNum, maxNum);
		
		final List<MessageRef> messages = folderDAO.getMessagesByUserAndFolderType(getUserByUid(userUid), folderType, query, startNum, maxNum - startNum + 1);
		
		final List<MessageSummary> result = new ArrayList<MessageSummary>();
		for (final MessageRef messageRef : messages) {
			result.add(convertMessageToDTO(messageRef, new MessageSummary()));
		}
		return result;
	}

	private void validateStartAndMaxNum(final int startNum, final int maxNum) {
		if (startNum < 1) {
			throw new IllegalArgumentException("Incorrect number for start number: " + startNum + ", it should be greater or equal to 1.");
		}
		if (maxNum < startNum) {
			throw new IllegalArgumentException("Incorrect number for max number: " + maxNum + ", it should be greater or equal to start number.");
		}
	}

	/**
	 * @param requestId
	 * @return
	 */
	@Override
	public RequestTO getRequestById(final Long requestId) {
		final RequestTO result = new RequestTO();
		
		final Request request = requestDAO.getById(requestId);
		final List<String> receipientsNotResponded = getUidsListByUsers(request.getReceipients());
		final List<ResponseTO> responseTOs = new ArrayList<ResponseTO>();

		fillRequestSummary(result, request, receipientsNotResponded, responseTOs);
		result.setContent(request.getText());
		result.setNotResponded(receipientsNotResponded);
		result.setResponses(responseTOs);

		result.setQuestions(getQuestionsTObyDTO(request.getTemplate().getQuestions()));

		return result;
	}

    private List<QuestionTO> getQuestionsTObyDTO(Set<Question> questions) {
        final List<QuestionTO> questionTOs = new ArrayList<QuestionTO>();
		for (final Question question : questions) {
			final QuestionTO questionTO = new QuestionTO();
			questionTO.setNumber(question.getIndex());
			questionTO.setDescription(question.getDescription());
			questionTO.setType(fi.arcusys.koku.kv.soa.QuestionType.valueOf(question.getType()));
			questionTOs.add(questionTO);
		}
		Collections.sort(questionTOs, new Comparator<QuestionTO>() {
            @Override
            public int compare(QuestionTO o1, QuestionTO o2) {
                return o1.getNumber() - o2.getNumber();
            }
        });
        return questionTOs;
    }

	private void fillRequestSummary(final RequestSummary result, final Request request,
			final List<String> receipientsNotResponded,
			final List<ResponseTO> responseTOs) {
		result.setRequestId(request.getId());
		result.setSender(request.getUser().getUid());
		result.setSubject(request.getSubject());
		result.setCreationDate(CalendarUtil.getXmlGregorianCalendar(request.getCreatedDate()));
		result.setEndDate(CalendarUtil.getXmlGregorianCalendar(request.getReplyTill()));
		
		result.setRespondedAmount(request.getResponses().size());
		for (final Response response : request.getResponses()) {
			final ResponseTO responseTO = new ResponseTO();
			final List<AnswerTO> answers = new ArrayList<AnswerTO>();
			for (final fi.arcusys.koku.common.service.datamodel.Answer answer : response.getAnswers()) {
				final AnswerTO answerTO = new AnswerTO();
				answerTO.setAnswer(answer.getValueAsString());
				answerTO.setComment(answer.getComment());
				answerTO.setQuestionNumber(answer.getIndex());
				answers.add(answerTO);
			}
			responseTO.setAnswers(answers);
			responseTO.setName(response.getReplier().getUid());
			receipientsNotResponded.remove(responseTO.getName());
			responseTOs.add(responseTO);
		}
		
		result.setMissedAmout(receipientsNotResponded.size());
	}

	/**
	 * @param fromUserId
	 * @param subject
	 * @param receipients
	 * @param content
	 * @param questionTOs
	 * @return
	 */
	@Override
	public Long sendRequest(final String fromUserId, final String subject, final List<String> receipients, final String content, 
	        final List<QuestionTO> questionTOs, final List<MultipleChoiceTO> choices, final RequestTemplateVisibility visibility,
            final XMLGregorianCalendar replyTill, final Integer notifyBeforeDays) {
		final RequestTemplate template = doCreateRequestTemplate(fromUserId, subject, questionTOs, choices, visibility);

		return doCreateRequest(fromUserId, subject, receipients, content, template, replyTill, notifyBeforeDays).getId();
	}

    private Request doCreateRequest(final String fromUserId,
            final String subject, final List<String> receipients,
            final String content, final RequestTemplate template, XMLGregorianCalendar replyTill, Integer notifyBeforeDays) {
        Request request = new Request();
        request.setTemplate(template);
        request.setReplyTill(CalendarUtil.getSafeDate(replyTill));
        request.setNotifyBeforeDays(notifyBeforeDays);
		final User fromUser = getUserByUid(fromUserId);
		
		fillMessage(request, fromUser, subject, receipients, content);
		
		request = requestDAO.create(request);
		
		final MessageRef storedMessage = folderDAO.storeMessage(fromUser, FolderType.Outbox, request);
		storedMessage.setRead(true);
		messageRefDao.update(storedMessage);
        return request;
    }

    /**
     * @param fromUserId
     * @param subject
     * @param questionTOs
     * @param visibility 
     * @return
     */
    private RequestTemplate doCreateRequestTemplate(String fromUserId, String subject, List<QuestionTO> questionTOs, List<MultipleChoiceTO> choiceTOs, RequestTemplateVisibility visibility) {
        final RequestTemplate requestTemplate = new RequestTemplate();
        fillRequestTemplate(fromUserId, subject, questionTOs, choiceTOs, visibility, requestTemplate);
        return requestTemplateDAO.create(requestTemplate);
    }

    private void fillRequestTemplate(String fromUserId, String subject,
            List<QuestionTO> questionTOs, List<MultipleChoiceTO> choiceTOs,
            RequestTemplateVisibility visibility, final RequestTemplate requestTemplate) {
        requestTemplate.setCreator(getUserByUid(fromUserId));
        requestTemplate.setSubject(subject);
        final Map<Integer, Question> numberToQuestion = new HashMap<Integer, Question>();
        for (final QuestionTO questionTO : questionTOs) {
            final Question question = new Question();
            question.setIndex(questionTO.getNumber());
            question.setDescription(questionTO.getDescription());
            question.setType(getQuestionType(questionTO));
            numberToQuestion.put(questionTO.getNumber(), question);
        }
        if (choiceTOs != null) {
            for (final MultipleChoiceTO choiceTO : choiceTOs) {
                final Question question = numberToQuestion.get(choiceTO.getQuestionNumber());
                if (question.getChoices() == null) {
                    question.setChoices(new HashSet<MultipleChoice>());
                }
                final MultipleChoice multipleChoice = new MultipleChoice();
                multipleChoice.setNumber(choiceTO.getNumber());
                multipleChoice.setDescription(choiceTO.getDescription());
                question.getChoices().add(multipleChoice);
            }
        }
        requestTemplate.setQuestions(new HashSet<Question>(numberToQuestion.values()));
        requestTemplate.setVisibility(RequestTemplateVisibility.toDmType(visibility == null ? RequestTemplateVisibility.All : visibility));
    }

	private QuestionType getQuestionType(final QuestionTO questionTO) {
		return questionTO.getType().getDMQuestionType();
	}

	/**
	 * @param toUserId
	 * @param requestId
	 * @return
	 */
	@Override
	public Long receiveRequest(final String toUserId, final Long requestId) {
		final Request sentRequest = requestDAO.getById(requestId);
		if (sentRequest == null) {
			throw new IllegalArgumentException("Request with ID " + requestId + " not found.");
		}

		final User toUser = getUserByUid(toUserId);
		final MessageRef msgRef = new MessageRef();
		Folder folder = getOrCreateFolder(toUser, FolderType.Inbox);

		msgRef.setFolder(folder);
		msgRef.setMessage(sentRequest);
		return messageRefDao.create(msgRef).getId();
	}

	/**
	 * @param toUserId
	 * @param requestId
	 * @param answers
	 * @return
	 */
	@Override
	public Long replyToRequest(final String toUserId, final Long requestId, final List<Answer> answers) {
		final User replier = getUserByUid(toUserId);
		
		final Request sentRequest = requestDAO.getById(requestId);
		if (sentRequest == null) {
			throw new IllegalArgumentException("Request with ID " + requestId + " not found.");
		}
		
		final Response response = new Response();
		response.setReplier(replier);
		response.setRequest(sentRequest);
		final Set<fi.arcusys.koku.common.service.datamodel.Answer> answersList = new HashSet<fi.arcusys.koku.common.service.datamodel.Answer>();
		for (final Answer answerSoa : answers) {
			fi.arcusys.koku.common.service.datamodel.Answer answer = new fi.arcusys.koku.common.service.datamodel.Answer();
			if (answerSoa.getValue() != null) {
				answer.setValue(answerSoa.getValue() ? "Kyllä" : "Ei");
			} else if (answerSoa.getTextValue() != null) {
				answer.setValue(answerSoa.getTextValue());
			} else {
				throw new IllegalArgumentException("Unknown answer type: value or textValue should be filled.");
			}
			answer.setComment(answerSoa.getComment());
			answer.setIndex(answerSoa.getQuestionNumber());
			answersList.add(answer); 
		}
		response.setAnswers(answersList);
		
		return responseDAO.create(response).getId();
	}

	/**
	 * @param userId
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<RequestSummary> getRequests(String userId, int startNum, int maxNum) {
		validateStartAndMaxNum(startNum, maxNum);

		final List<Request> requests = requestDAO.getRequestsByUser(getUserByUid(userId), startNum, maxNum);
		
		final List<RequestSummary> result = new ArrayList<RequestSummary>();
		for (final Request request : requests) {
			final RequestSummary requestSummary = new RequestSummary();
			
			final List<String> receipientsNotResponded = getUidsListByUsers(request.getReceipients());
			final List<ResponseTO> responseTOs = new ArrayList<ResponseTO>();

			fillRequestSummary(requestSummary, request, receipientsNotResponded, responseTOs);
			result.add(requestSummary);
		}
		return result;
	}

    /**
     * @param fromUserUid
     * @param subject
     * @param receipients
     * @param content
     */
    @Override
    public void sendNotification(String subject, List<String> receipients, String content) {
        doDeliverMessage(SYSTEM_USER_NAME_FOR_NOTIFICATIONS, receipients, subject, content);
    }

    private void doDeliverMessage(final String fromUser,
            List<String> receipients, String subject, String content) {
        final Long messageId = sendNewMessage(fromUser, subject, receipients, content);
        for (final String receipient : receipients) {
            receiveMessage(receipient, messageId);
        }
    }
    
    public void deliverMessage(final String fromUser, final List<String> toUsers, final String subject, final String content) {
        doDeliverMessage(fromUser, toUsers, subject, content);
    }

    /**
     * @param userUid
     * @param subject
     * @param questionTOs
     */
    @Override
    public void createRequestTemplate(String userUid, String subject, List<QuestionTO> questionTOs, List<MultipleChoiceTO> choices, RequestTemplateVisibility visibility) {
        doCreateRequestTemplate(userUid, subject, questionTOs, choices, visibility);
    }

    /**
     * @param subjectPrefix
     * @param limit
     * @return
     */
    @Override
    public List<RequestTemplateSummary> getRequestTemplateSummary(final String userUid, String subjectPrefix, int limit) {
        final List<RequestTemplateSummary> result = new ArrayList<RequestTemplateSummary>();
        final User user = (userUid != null && !userUid.isEmpty()) ? userDao.getOrCreateUser(userUid) : null;
        for (final RequestTemplate template : requestTemplateDAO.searchTemplates(user, subjectPrefix, limit)) {
            final RequestTemplateSummary summaryTO = new RequestTemplateSummary();
            convertTemplateToDTO(template, summaryTO);
            result.add(summaryTO);
        }
        return result;
    }

    private <RTS extends RequestTemplateSummary> void convertTemplateToDTO(final RequestTemplate template,
            final RTS summaryTO) {
        summaryTO.setRequestTemplateId(template.getId());
        summaryTO.setSubject(template.getSubject());
    }

    /**
     * @param requestTemplateId
     * @return
     */
    @Override
    public RequestTemplateTO getRequestTemplateById(long requestTemplateId) {
        final RequestTemplate template = loadRequestTemplate(requestTemplateId);

        final RequestTemplateTO templateTO = new RequestTemplateTO();
        convertTemplateToDTO(template, templateTO);
        templateTO.setQuestions(getQuestionsTObyDTO(template.getQuestions()));
        templateTO.setChoices(getChoicesTO(template.getQuestions()));
        return templateTO;
    }

    /**
     * @param questions
     * @return
     */
    private List<MultipleChoiceTO> getChoicesTO(Set<Question> questions) {
        final List<MultipleChoiceTO> result = new ArrayList<MultipleChoiceTO>();
        for (final Question question : questions) {
            for (final MultipleChoice choice : question.getChoices()) {
                final MultipleChoiceTO choiceTO = new MultipleChoiceTO();
                choiceTO.setQuestionNumber(question.getIndex());
                choiceTO.setNumber(choice.getNumber());
                choiceTO.setDescription(choice.getDescription());
                result.add(choiceTO);
            }
        }
        Collections.sort(result, new Comparator<MultipleChoiceTO>() {
            @Override
            public int compare(MultipleChoiceTO o1, MultipleChoiceTO o2) {
                final int questionComparation = o1.getQuestionNumber() - o2.getQuestionNumber();
                if (questionComparation != 0) {
                    return questionComparation;
                } else {
                    return o1.getNumber() - o2.getNumber();
                }
            }
        });
        return result;
    }

    /**
     * @param fromUserUid
     * @param requestTemplateId
     * @param receipients
     * @param content
     * @return
     */
    @Override
    public Long sendRequestWithTemplate(String fromUserUid, long requestTemplateId, final String subject, 
            List<String> receipients, String content,
            final XMLGregorianCalendar replyTill, final Integer notifyBeforeDays) {
        final RequestTemplate template = loadRequestTemplate(requestTemplateId);
        
        return doCreateRequest(fromUserUid, subject, receipients, content, template, replyTill, notifyBeforeDays).getId();
    }

    private RequestTemplate loadRequestTemplate(long requestTemplateId) {
        final RequestTemplate template = requestTemplateDAO.getById(requestTemplateId);
        if (template == null) {
            throw new IllegalArgumentException("Request template not found: ID = " + requestTemplateId );
        }
        return template;
    }

    /**
     * @param fromUserUid
     * @param subject
     * @param toUserUid
     * @param content
     */
    @Override
    public Long receiveNewMessage(final String fromUserUid, final String subject, final String toUserUid, final String content) {
        Message msg = new Message();
        fillMessage(msg, getUserByUid(fromUserUid), subject, Collections.singletonList(toUserUid), content);
        msg = messageDao.create(msg);
        
        return doReceiveNewMessage(getUserByUid(toUserUid), msg).getId();
    }

    /**
     * @param userUid
     * @param subject
     * @return
     */
    @Override
    public RequestTemplateExistenceStatus isRequestTemplateExist(String userUid, String subject) {
        final RequestTemplate template = getTemplateBySubject(subject);
        if (template == null) {
            return RequestTemplateExistenceStatus.NotExists;
        } else if (!template.getCreator().getUid().equals(userUid) ||
                getIntValue(requestDAO.getTotalByTemplate(template)) > 0) {
            return RequestTemplateExistenceStatus.ExistsActive;
        } else {
            return RequestTemplateExistenceStatus.ExistsNotActive;
        }
    }

    private RequestTemplate getTemplateBySubject(String subject) {
        final List<RequestTemplate> templates = requestTemplateDAO.searchBySubject(subject);
        if (templates.size() == 0) {
            return null;
        } else {
            if (templates.size() > 1) {
                logger.warn("More then one template with subject '" + subject + "'");
            }
            return templates.get(0);
        }
    }

    /**
     * @param requestTemplateId
     * @param userUid
     * @param subject
     * @param questions
     * @param choices
     */
    @Override
    public void updateRequestTemplate(String userUid, String subject, List<QuestionTO> questions, List<MultipleChoiceTO> choices, RequestTemplateVisibility visibility) {
        final RequestTemplate template = getTemplateBySubject(subject);
        if (template == null) {
            throw new IllegalStateException("Error in update: request template with subject '" + subject + "' is not found.");
        } 
        if (!template.getCreator().getUid().equals(userUid)) {
            throw new IllegalStateException("Error in update: request template with subject '" + subject + "' is created by different user.");
        } 
        if (getIntValue(requestDAO.getTotalByTemplate(template)) > 0) {
            throw new IllegalStateException("Error in update: request template with subject '" + subject + "' is active.");
        } 

        fillRequestTemplate(userUid, subject, questions, choices, visibility, template);
        requestTemplateDAO.update(template);
    }
}
