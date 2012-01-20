package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.kv.service.MessageServiceFacade;

/**
 * UI service implementation for citizen-related operations in KV-Requests functional area.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 6, 2011
 */
@Stateless
@WebService(serviceName = "KokuKunpoRequestService", portName = "KokuKunpoRequestServicePort", 
        endpointInterface = "fi.arcusys.koku.kv.soa.KokuKunpoRequestService",
        targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public class KokuKunpoRequestServiceImpl implements KokuKunpoRequestService {

    @EJB
    private MessageServiceFacade serviceFacade;
    
    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalRepliedRequests(String userUid) {
        return serviceFacade.getTotalRepliedRequests(userUid);
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalOldRequests(String userUid) {
        return serviceFacade.getTotalOldRepliedRequests(userUid);
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ResponseSummary> getRepliedRequests(final String userUid, final int startNum, final int maxNum) {
        return serviceFacade.getRepliedRequests(userUid, startNum, maxNum);
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ResponseSummary> getOldRequests(String userUid, int startNum, int maxNum) {
        return serviceFacade.getOldRepliedRequests(userUid, startNum, maxNum);
    }

    /**
     * @param responseId
     * @return
     */
    @Override
    public ResponseDetail getResponseDetail(long responseId) {
        return serviceFacade.getResponseDetail(responseId);
    }

}
