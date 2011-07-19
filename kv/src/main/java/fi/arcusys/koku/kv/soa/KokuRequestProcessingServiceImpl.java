package fi.arcusys.koku.kv.soa;

import java.util.Collections;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.kv.service.MessageServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@Stateless
@WebService(serviceName = "KokuRequestProcessingService", portName = "KokuRequestProcessingServicePort", 
		endpointInterface = "fi.arcusys.koku.kv.soa.KokuRequestProcessingService",
		targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public class KokuRequestProcessingServiceImpl implements KokuRequestProcessingService {
	private final static Logger logger = LoggerFactory.getLogger(KokuRequestProcessingServiceImpl.class);

	@EJB
	private MessageServiceFacade kvFacade;
	
	/**
	 * @param toUserUid
	 * @param requestId
	 */
	@Override
	public void receiveRequest(String toUserUid, Long requestId) {
		logger.debug("receiveRequest: [toUserUid,requestId] = [" + toUserUid + "," + requestId + "]");
		final Long receivedRequest = kvFacade.receiveRequest(toUserUid, requestId);
		logger.debug("request received: " + receivedRequest);
	}

	/**
	 * @param userUid
	 * @param requestId
	 * @param answers
	 */
	@Override
	public void replyToRequest(String userUid, Long requestId, Answers answers) {
		logger.debug("replyToRequest: [userUid, requestId, answers] = " +
				"[" + userUid
				+ "," + requestId
				+ "," + answers.getAnswers()
				+ "]");
		final Long replyId = kvFacade.replyToRequest(userUid, requestId, answers.getAnswers());
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
	public Long sendRequest(final String fromUserUid, final String subject, final Receipients receipients, final Questions questions, final String content) {
		logger.debug("sendRequest: [fromUserUid, subject, receipients, questions, content] = " +
				"[" + fromUserUid
				+ "," + subject 
				+ "," + receipients.getReceipients() 
				+ "," + questions.getQuestions()
				+ ", contentLength=" + content.length()
				+ "]");
		final Long requestId = kvFacade.sendRequest(fromUserUid, subject, receipients.getReceipients(), content, questions.getQuestions());
		logger.debug("Request sent: " + requestId);
		return requestId;
	}
}
