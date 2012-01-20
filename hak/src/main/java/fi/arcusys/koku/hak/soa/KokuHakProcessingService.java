package fi.arcusys.koku.hak.soa;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * Interface with HAK-processing operations, called from the HAK Intalio process.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 20, 2011
 */
@WebService(targetNamespace = "http://soa.hak.koku.arcusys.fi/")
public interface KokuHakProcessingService {
    
    @WebResult(name = "requestId")
    long requestForDaycare(@WebParam(name = "request") final DaycareRequestTO request);
    
    DaycareRequestTO getDaycareRequestById(@WebParam(name = "request") final long requestId);
    
    void processDaycareRequest(
            @WebParam(name = "requestId") final long requestId, 
            @WebParam(name = "userUid") final String userUid);
    
    void rejectDaycarePlace(
            @WebParam(name = "requestId") final long requestId, 
            @WebParam(name = "userUid") final String userUid, 
            @WebParam(name = "comment") final String comment);
    
    void approveDaycarePlace(
            @WebParam(name = "requestId") final long requestId, 
            @WebParam(name = "userUid") final String userUid, 
            @WebParam(name = "location") final String location, 
            @WebParam(name = "highestPrice") final boolean highestPrice, 
            @WebParam(name = "comment") final String comment);
}
