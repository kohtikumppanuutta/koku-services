package fi.arcusys.koku.hak.soa;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.hak.service.DaycareRequestServiceFacade;

/**
 * Implementation of HAK-processing operations, called from the HAK Intalio process.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 20, 2011
 */
@Stateless
@WebService(serviceName = "KokuHakProcessingService", portName = "KokuHakProcessingServicePort", 
        endpointInterface = "fi.arcusys.koku.hak.soa.KokuHakProcessingService",
        targetNamespace = "http://soa.hak.koku.arcusys.fi/")
public class KokuHakProcessingServiceImpl implements KokuHakProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(KokuHakProcessingServiceImpl.class);
    
    @EJB
    private DaycareRequestServiceFacade serviceFacade;
    
    /**
     * @param request
     * @return
     */
    @Override
    public long requestForDaycare(DaycareRequestTO request) {
        return serviceFacade.requestForDaycare(request);
    }

    /**
     * @param requestId
     * @return
     */
    @Override
    public DaycareRequestTO getDaycareRequestById(long requestId) {
        return serviceFacade.getDaycareRequestById(requestId);
//        // TODO Auto-generated method stub
//        logger.info("getDaycareRequestById: " + requestId);
//        if (requestId != 1) {
//            throw new IllegalArgumentException("DaycareRequest with ID " + requestId + " is not found.");
//        }
//        final DaycareRequestTO daycareRequestTO = new DaycareRequestTO();
//        daycareRequestTO.setCreatorUid("Kalle Kuntalainen");
//        daycareRequestTO.setDaycareNeededFromDate(CalendarUtil.getXmlDate(new Date()));
//        daycareRequestTO.setFormContent("Some html content");
//        daycareRequestTO.setRequestId(1L);
//        daycareRequestTO.setTargetPersonUid("Lassi Lapsi");
//        return daycareRequestTO;
    }

    /**
     * @param requestId
     * @param userUid
     */
    @Override
    public void processDaycareRequest(long requestId, String userUid) {
//        // TODO Auto-generated method stub
//        logger.info("processDaycareRequest: " + requestId + ", " + userUid);
        serviceFacade.processDaycareRequest(requestId, userUid);
    }

    /**
     * @param requestId
     * @param userUid
     * @param comment
     */
    @Override
    public void rejectDaycarePlace(long requestId, String userUid,
            String comment) {
//        // TODO Auto-generated method stub
//        logger.info("rejectDaycarePlace: " + requestId + ", " + userUid + ", " + comment);
        serviceFacade.rejectDaycarePlace(requestId, userUid, comment);
    }

    /**
     * @param requestId
     * @param userUid
     * @param location
     * @param highestPrice
     * @param comment
     */
    @Override
    public void approveDaycarePlace(long requestId, String userUid,
            String location, boolean highestPrice, String comment) {
//        // TODO Auto-generated method stub
//        logger.info("approveDaycarePlace: " + requestId + ", " + userUid + ", " + location + ", " + comment);
        serviceFacade.approveDaycarePlace(requestId, userUid, location, highestPrice, comment);
    }

}
