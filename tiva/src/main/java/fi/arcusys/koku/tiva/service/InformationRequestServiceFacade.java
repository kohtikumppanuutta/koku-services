package fi.arcusys.koku.tiva.service;

import java.util.List;

import fi.arcusys.koku.tiva.soa.InformationCategoryTO;
import fi.arcusys.koku.tiva.soa.InformationRequestCriteria;
import fi.arcusys.koku.tiva.soa.InformationRequestDetail;
import fi.arcusys.koku.tiva.soa.InformationRequestQuery;
import fi.arcusys.koku.tiva.soa.InformationRequestReplyTO;
import fi.arcusys.koku.tiva.soa.InformationRequestSummary;
import fi.arcusys.koku.tiva.soa.InformationRequestTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
public interface InformationRequestServiceFacade {
    Long createInformationRequest(final InformationRequestTO request);
    
    void approveRequest(final InformationRequestReplyTO reply);
    
    void declineRequest(final Long requestId, final String employeeUid, String explanation);
    
    InformationCategoryTO getCategories(final String employeeUid);

    /**
     * @param receiverUid
     * @param informationRequestQuery
     * @return
     */
    List<InformationRequestSummary> getRepliedRequests(final String receiverUid, final InformationRequestQuery query);
    
    int getTotalRepliedRequests(final String receiverUid, final InformationRequestCriteria criteria);

    /**
     * @param senderUid
     * @param query
     * @return
     */
    List<InformationRequestSummary> getSentRequests(String senderUid, InformationRequestQuery query);

    int getTotalSentRequests(final String senderUid, final InformationRequestCriteria criteria);
    
    InformationRequestDetail getRequestDetails(final long requestId);

    /**
     * @param criteria
     * @return
     */
    int getTotalRequests(final InformationRequestCriteria criteria);

    /**
     * @param criteria
     * @return
     */
    List<InformationRequestSummary> getRequests(final InformationRequestQuery query);
}
