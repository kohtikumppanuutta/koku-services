package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.common.soa.UsersAndGroupsService;
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
    
    @EJB
    private UsersAndGroupsService userService;
    
    /**
     * @param receiverUid
     * @param query
     * @return
     */
    @Override
    public List<InformationRequestSummary> getRepliedRequests(String receiverUid, InformationRequestQuery query) {
        updateQuery(query);
        return serviceFacade.getRepliedRequests(receiverUid, query);
    }

    private void updateQuery(InformationRequestQuery query) {
        if (query != null) {
            query.setCriteria(updateUserUid(query.getCriteria()));
        }
    }

    /**
     * @param receiverUid
     * @param criteria
     * @return
     */
    @Override
    public int getTotalRepliedRequests(String receiverUid, InformationRequestCriteria criteria) {
        return serviceFacade.getTotalRepliedRequests(receiverUid, updateUserUid(criteria));
    }

    private InformationRequestCriteria updateUserUid(final InformationRequestCriteria criteria) {
        if (criteria == null) {
            return null;
        }
        criteria.setReceiverUid(userService.getUserUidByEmployeeSsn(criteria.getReceiverUid()));
        criteria.setSenderUid(userService.getUserUidByEmployeeSsn(criteria.getSenderUid()));
        criteria.setTargetPersonUid(userService.getUserUidByKunpoSsn(criteria.getTargetPersonUid()));
        return criteria;
    }

    /**
     * @param senderUid
     * @param query
     * @return
     */
    @Override
    public List<InformationRequestSummary> getSentRequests(String senderUid, InformationRequestQuery query) {
        updateQuery(query);
        return serviceFacade.getSentRequests(senderUid, query);
    }

    /**
     * @param senderUid
     * @param criteria
     * @return
     */
    @Override
    public int getTotalSentRequests(String senderUid, InformationRequestCriteria criteria) {
        return serviceFacade.getTotalSentRequests(senderUid, updateUserUid(criteria));
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
        return serviceFacade.getTotalRequests(updateUserUid(criteria));
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<InformationRequestSummary> getRequests(InformationRequestQuery query) {
        updateQuery(query);
        return serviceFacade.getRequests(query);
    }

}
