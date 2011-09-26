package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.tiva.service.InformationRequestServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 23, 2011
 */
@Stateless
@WebService(serviceName = "KokuLooraTietopyyntoService", portName = "KokuLooraTietopyyntoServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuLooraTietopyyntoService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public class KokuLooraTietopyyntoServiceImpl implements KokuLooraTietopyyntoService {

    @EJB
    private InformationRequestServiceFacade serviceFacade;
    
    /**
     * @param receiverUid
     * @param query
     * @return
     */
    @Override
    public List<InformationRequestSummary> getRepliedRequests(String receiverUid, InformationRequestQuery query) {
        return serviceFacade.getRepliedRequests(receiverUid, query);
    }

    /**
     * @param receiverUid
     * @param criteria
     * @return
     */
    @Override
    public int getTotalRepliedRequests(String receiverUid, InformationRequestCriteria criteria) {
        return serviceFacade.getTotalRepliedRequests(receiverUid, criteria);
    }

    /**
     * @param senderUid
     * @param query
     * @return
     */
    @Override
    public List<InformationRequestSummary> getSentRequests(String senderUid, InformationRequestQuery query) {
        return serviceFacade.getSentRequests(senderUid, query);
    }

    /**
     * @param senderUid
     * @param criteria
     * @return
     */
    @Override
    public int getTotalSentRequests(String senderUid, InformationRequestCriteria criteria) {
        return serviceFacade.getTotalSentRequests(senderUid, criteria);
    }

    /**
     * @param requestId
     * @return
     */
    @Override
    public InformationRequestDetail getRequestDetails(long requestId) {
        return serviceFacade.getRequestDetails(requestId);
    }

    /**
     * @param criteria
     * @return
     */
    @Override
    public int getTotalRequests(InformationRequestCriteria criteria) {
        return serviceFacade.getTotalRequests(criteria);
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<InformationRequestSummary> getRequests(InformationRequestQuery query) {
        return serviceFacade.getRequests(query);
    }

}
