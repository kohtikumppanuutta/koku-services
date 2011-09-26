package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 23, 2011
 */
@WebService(targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public interface KokuLooraTietopyyntoService {
    List<InformationRequestSummary> getRepliedRequests(
            @WebParam(name = "receiverUid") final String receiverUid, 
            @WebParam(name = "query") final InformationRequestQuery query);
    
    int getTotalRepliedRequests(
            @WebParam(name = "receiverUid") final String receiverUid, 
            @WebParam(name = "criteria") final InformationRequestCriteria criteria);

    List<InformationRequestSummary> getSentRequests(
            @WebParam(name = "senderUid") final String senderUid, 
            @WebParam(name = "query") final InformationRequestQuery query);

    int getTotalSentRequests(
            final String senderUid, 
            @WebParam(name = "criteria") final InformationRequestCriteria criteria);
    
    InformationRequestDetail getRequestDetails(
            @WebParam(name = "requestId") final long requestId);

    int getTotalRequests(
            @WebParam(name = "criteria") final InformationRequestCriteria criteria);

    List<InformationRequestSummary> getRequests(
            @WebParam(name = "query") final InformationRequestQuery query);
}
