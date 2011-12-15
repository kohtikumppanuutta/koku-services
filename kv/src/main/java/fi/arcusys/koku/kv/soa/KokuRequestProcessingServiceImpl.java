package fi.arcusys.koku.kv.soa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.external.RolesDAO;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.soa.Role;
import fi.arcusys.koku.kv.service.MessageServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@Stateless
@WebService(serviceName = "KokuRequestProcessingService", portName = "KokuRequestProcessingServicePort", 
		endpointInterface = "fi.arcusys.koku.kv.soa.KokuRequestProcessingService",
		targetNamespace = "http://soa.kv.koku.arcusys.fi/")
@Interceptors(KokuRequestInterceptor.class)
public class KokuRequestProcessingServiceImpl implements KokuRequestProcessingService {
	private final static Logger logger = LoggerFactory.getLogger(KokuRequestProcessingServiceImpl.class);

	@EJB
	private MessageServiceFacade kvFacade;
	
	@EJB
	private UserDAO userDao;
	
	@EJB
	private RolesDAO roleDao;
	
	/**
	 * @param toUserUid
	 * @param requestId
	 */
	@Override
	public void receiveRequest(String toUserUid, Long requestId, final String content) {
		logger.debug("receiveRequest: [toUserUid,requestId] = [" + toUserUid + "," + requestId + "]");
		final Long receivedRequest = kvFacade.receiveRequest(toUserUid, requestId, content);
		logger.debug("request received: " + receivedRequest);
	}

	/**
	 * @param userUid
	 * @param requestId
	 * @param answers
	 */
	@Override
	public void replyToRequest(String userUid, Long requestId, Answers answers, final String comment) {
		logger.debug("replyToRequest: [userUid, requestId, answers] = " +
				"[" + userUid
				+ "," + requestId
				+ "," + answers.getAnswers()
				+ "]");
		final Long replyId = kvFacade.replyToRequest(userUid, requestId, answers.getAnswers(), comment);
		logger.debug("Reply sent: " + replyId);
	}

	/**
	 * @param fromUserUid
	 * @param subject
	 * @param receipients
	 * @param questions
	 * @param content
	 * @return
	 */
	@Override
	public Long sendRequest(final String fromUserUid, final String subject, final Receipients receipients, 
	        final Questions questions, final MultipleChoices choices, final RequestTemplateVisibility visibility, final String content, 
	        final XMLGregorianCalendar replyTill, final Integer notifyBeforeDays) {
        final RequestTemplateTO templateTO = new RequestTemplateTO();
        templateTO.setChoices(choices != null ? choices.getChoices() : new ArrayList<MultipleChoiceTO>());
        templateTO.setCreatorUid(fromUserUid);
        templateTO.setQuestions(questions != null ? questions.getQuestions() : new ArrayList<QuestionTO>());
        templateTO.setSubject(subject);
        templateTO.setVisibility(visibility);

        final RequestProcessingTO requestTO = createRequestProcessingTO(
                fromUserUid, subject, receipients, content, replyTill,
                notifyBeforeDays);

        final Long requestId = kvFacade.sendRequest(templateTO, requestTO);
		return requestId;
	}

    private RequestProcessingTO createRequestProcessingTO(
            final String fromUserUid, final String subject,
            final Receipients receipients, final String content,
            final XMLGregorianCalendar replyTill, final Integer notifyBeforeDays) {
        final RequestProcessingTO requestTO = new RequestProcessingTO();
        requestTO.setContent(content);
        // check if "default" role should be used
        requestTO.setFromRole(getDefaultUserRole(fromUserUid));
        requestTO.setFromUserUid(fromUserUid);
        requestTO.setNotifyBeforeDays(notifyBeforeDays);
        requestTO.setReceipients(receipients.getReceipients());
        requestTO.setReplyTill(replyTill);
        requestTO.setSubject(subject);
        return requestTO;
    }

    /**
     * @param fromUserUid
     * @return
     */
    private String getDefaultUserRole(String fromUserUid) {
        final User user = userDao.getOrCreateUser(fromUserUid);
        final List<Role> employeeRoles = roleDao.getEmployeeRoles(user.getEmployeePortalName());
        if (employeeRoles != null && !employeeRoles.isEmpty()) {
            Collections.sort(employeeRoles, new Comparator<Role>() {
                @Override
                public int compare(Role o1, Role o2) {
                    return o1.getRoleUid().compareTo(o2.getRoleUid());
                }
            });
            return employeeRoles.get(0).getRoleUid();
        }
        return null;
    }

    /**
     * @param userUid
     * @param subject
     * @param questions
     */
    @Override
    public void createRequestTemplate(String userUid, String subject, Questions questions, final MultipleChoices choices, final RequestTemplateVisibility visibility) {
        kvFacade.createRequestTemplate(userUid, subject, 
                questions != null ? questions.getQuestions() : new ArrayList<QuestionTO>(), 
                choices != null ? choices.getChoices() : new ArrayList<MultipleChoiceTO>(),
                visibility);
    }

    /**
     * @param subjectPrefix
     * @param limit
     * @return
     */
    @Override
    public List<RequestTemplateSummary> getRequestTemplateSummary(final String userUid, String subjectPrefix, int limit) {
        return kvFacade.getRequestTemplateSummary(userUid, subjectPrefix, limit);
    }

    /**
     * @param requestTemplateId
     * @return
     */
    @Override
    public RequestTemplateTO getRequestTemplateById(long requestTemplateId) {
        return kvFacade.getRequestTemplateById(requestTemplateId);
    }

    /**
     * @param fromUserUid
     * @param requestTemplateId
     * @param receipients
     * @param content
     * @return
     */
    @Override
    public Long sendRequestWithTemplate(String fromUserUid, long requestTemplateId, String subject, Receipients receipients, String content, 
            final XMLGregorianCalendar replyTill, final Integer notifyBeforeDays) {
        final RequestProcessingTO requestTO = createRequestProcessingTO(
                fromUserUid, subject, receipients, content, replyTill,
                notifyBeforeDays);
        return kvFacade.sendRequestWithTemplate(requestTemplateId, requestTO);
    }

    /**
     * @param userUid
     * @param subject
     * @return
     */
    @Override
    public RequestTemplateExistenceStatus isRequestTemplateExist(String userUid, String subject) {
        return kvFacade.isRequestTemplateExist(userUid, subject);
    }

    /**
     * @param userUid
     * @param subject
     * @param questions
     * @param choices
     */
    @Override
    public void updateRequestTemplate(String userUid, String subject, Questions questions, MultipleChoices choices, final RequestTemplateVisibility visibility) {
        kvFacade.updateRequestTemplate(userUid, subject, 
                questions != null ? questions.getQuestions() : new ArrayList<QuestionTO>(), 
                choices != null ? choices.getChoices() : new ArrayList<MultipleChoiceTO>(),
                visibility);
    }

    /**
     * @param template
     * @param request
     * @return
     */
    @Override
    public Long sendRequestNew(RequestTemplateTO template, RequestProcessingTO request) {
        return kvFacade.sendRequest(template, request);
    }

    /**
     * @param requestTemplateId
     * @param request
     * @return
     */
    @Override
    public Long sendRequestWithTemplateNew(long requestTemplateId, RequestProcessingTO request) {
        return kvFacade.sendRequestWithTemplate(requestTemplateId, request);
    }
}
