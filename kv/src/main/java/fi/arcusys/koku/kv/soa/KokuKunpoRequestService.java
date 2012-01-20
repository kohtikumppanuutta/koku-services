package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * UI service interface for citizen-related operations in KV-Requests functional area.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 6, 2011
 */
@WebService(targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public interface KokuKunpoRequestService {
    public int getTotalRepliedRequests(
            @WebParam(name = "userUid") final String userUid);

    public int getTotalOldRequests(
            @WebParam(name = "userUid") final String userUid);

    public List<ResponseSummary> getRepliedRequests(
            @WebParam(name = "userUid") final String userUid, 
            @WebParam(name = "startNum") final int startNum, 
            @WebParam(name = "maxNum") final int maxNum);

    public List<ResponseSummary> getOldRequests(
            @WebParam(name = "userUid") final String userUid, 
            @WebParam(name = "startNum") final int startNum, 
            @WebParam(name = "maxNum") final int maxNum);
    
    public ResponseDetail getResponseDetail(final long responseId);
}
