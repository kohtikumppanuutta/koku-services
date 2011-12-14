package fi.arcusys.koku.kv.soa;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 15, 2011
 */
@WebService(targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public interface KokuMessageProcessingService {
	@WebResult(name = "messageId")
	Long sendMessage(@WebParam(name = "fromUser") final String fromUserUid, 
					 @WebParam(name = "subject") final String subject, 
					 @WebParam(name = "receipients") final Receipients receipients, 
					 @WebParam(name = "messageContent") final String content,
					 @WebParam(name = "sendToFamilyMembers") final Boolean sendToFamilyMembers,
					 @WebParam(name = "sendToGroupSite") final Boolean sendToGroupSite);
	
	void receiveMessage(@WebParam(name = "toUser") final String toUserUid, 
						@WebParam(name = "messageId") final Long messageId);

    @WebResult(name = "messageId")
    Long receiveNewMessage(
            @WebParam(name = "fromUser") final String fromUserUid, 
            @WebParam(name = "subject") final String subject,
            @WebParam(name = "toUser") final String toUserUid, 
            @WebParam(name = "messageContent") final String content);
}
