package fi.arcusys.koku.kv.soa;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@WebService(targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public interface KokuRequestProcessingService {
	@WebResult(name = "requestId")
	Long sendRequest(@WebParam(name = "fromUser") final String fromUserUid, 
					 @WebParam(name = "subject") final String subject, 
					 @WebParam(name = "receipients") final Receipients receipients,
					 @WebParam(name = "questions") final Questions questions, 
					 @WebParam(name = "requestContent") final String content);
	
	void receiveRequest(@WebParam(name = "toUser") final String toUserUid, 
						@WebParam(name = "requestId") final Long requestId);

	void replyToRequest(@WebParam(name = "user") final String userUid, 
						@WebParam(name = "requestId") final Long requestId,
						@WebParam(name = "answers") final Answers answers);
}
