package fi.arcusys.koku.kv.soa;

import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.kv.service.MessageServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@Stateless
@WebService(serviceName = "KokuRequestService", portName = "KokuRequestServicePort", 
		endpointInterface = "fi.arcusys.koku.kv.soa.KokuRequestService",
		targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public class KokuRequestServiceImpl implements KokuRequestService {
	private final static Logger logger = LoggerFactory.getLogger(KokuRequestServiceImpl.class);

	@EJB
	private MessageServiceFacade kvFacade;
	
	/**
	 * @param requestId
	 * @return
	 */
	@Override
	public RequestTO getRequestById(long requestId) {
		return kvFacade.getRequestById(requestId);
		// TODO make real implementation
//		if (requestId != -1) {
//			return null;
//		}
//		
//		final RequestTO request = new RequestTO();
//		fillTestRequest(request);
//		request.setContent("<html><body>Test content</body></html>");
//		request.setNotResponded(Collections.<String>emptyList());
//		final ResponseTO response = createTestResponse();
//		request.setResponses(Collections.singletonList(response));
//		return request;
	}

	private ResponseTO createTestResponse() {
		final ResponseTO response = new ResponseTO();
		response.setName("Kalle Kuntalainen");
		final AnswerTO answer = new AnswerTO();
		answer.setAnswer("Kyllä");
		answer.setComment("Test comment");
		response.setAnswers(Collections.singletonList(answer));
		return response;
	}

	/**
	 * @param user
	 * @param requestType
	 * @param subQuery
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<RequestSummary> getRequests(final String user, final RequestType requestType, final String subQuery, final int startNum, final int maxNum) {
		if (RequestType.Valid == requestType) {
	        return kvFacade.getRequests(user, startNum, maxNum);
		} else {
	        return kvFacade.getOldRequests(user, startNum, maxNum);
		}
	}

	/**
	 * @param user
	 * @param requestType
	 * @return
	 */
	@Override
	public int getTotalRequests(String user, RequestType requestType) {
		if (RequestType.Valid == requestType) {
	        return kvFacade.getTotalRequests(user);
		} else {
            return kvFacade.getTotalOldRequests(user);
		}
	}

}
