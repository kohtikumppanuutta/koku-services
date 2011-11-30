package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

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
					 @WebParam(name = "choices") final MultipleChoices choices, 
					 @WebParam(name = "visibility") final RequestTemplateVisibility visibility,
					 @WebParam(name = "requestContent") final String content,
					 @WebParam(name = "replyTill") final XMLGregorianCalendar replyTill,
					 @WebParam(name = "notifyBeforeDays") final Integer notifyBeforeDays);
	
	void receiveRequest(@WebParam(name = "toUser") final String toUserUid, 
						@WebParam(name = "requestId") final Long requestId,
						@WebParam(name = "content") final String content);

	void replyToRequest(@WebParam(name = "user") final String userUid, 
						@WebParam(name = "requestId") final Long requestId,
						@WebParam(name = "answers") final Answers answers,
						@WebParam(name = "comment") final String comment);
	
	void createRequestTemplate(
	        @WebParam(name = "creator") final String userUid, 
            @WebParam(name = "subject") final String subject, 
            @WebParam(name = "questions") final Questions questions,
            @WebParam(name = "choices") final MultipleChoices choices,
            @WebParam(name = "visibility") final RequestTemplateVisibility visibility);
	
	RequestTemplateExistenceStatus isRequestTemplateExist(
	        @WebParam(name = "creator") final String userUid, 
	        @WebParam(name = "subject") final String subject);
	
    void updateRequestTemplate(
            @WebParam(name = "creator") final String userUid, 
            @WebParam(name = "subject") final String subject, 
            @WebParam(name = "questions") final Questions questions,
            @WebParam(name = "choices") final MultipleChoices choices, 
            @WebParam(name = "visibility") final RequestTemplateVisibility visibility);

    List<RequestTemplateSummary> getRequestTemplateSummary(
            @WebParam(name = "user") final String userUid, 
	        @WebParam(name = "subjectPrefix") final String subjectPrefix, 
	        @WebParam(name = "limit") final int limit);
	
	RequestTemplateTO getRequestTemplateById(
	        @WebParam(name = "requestTemplateId") final long requestTemplateId);
	
	Long sendRequestWithTemplate(
	        @WebParam(name = "fromUser") final String fromUserUid, 
            @WebParam(name = "requestTemplateId") final long requestTemplateId, 
            @WebParam(name = "subject") final String subject, 
            @WebParam(name = "receipients") final Receipients receipients,
            @WebParam(name = "requestContent") final String content,
            @WebParam(name = "replyTill") final XMLGregorianCalendar replyTill,
            @WebParam(name = "notifyBeforeDays") final Integer notifyBeforeDays);
}
