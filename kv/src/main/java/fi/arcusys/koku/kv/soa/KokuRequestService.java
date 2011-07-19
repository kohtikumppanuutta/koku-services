package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@WebService(targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public interface KokuRequestService {
	/**
	 * Gets amount of requests
	 * @param user
	 * @param requestType Request type such as valid, outdated
	 * @return
	 */
	public int getTotalRequests(@WebParam(name = "user") final String user, 
								@WebParam(name = "requestType") final RequestType requestType);
	
	/**
	 * Gets list of request summary
	 * @param user
	 * @param requestType Request type such as valid, outdated
	 * @param subQuery Basic query for the request, such as 'request_subject like %keyword%' , 'ORDER BY request_creationDate'
	 * @param startNum The start request number that fulfill the condition
	 * @param maxNum The maximum amount of requests that fulfill the condition
	 * @return List of requests
	 */
	public List<RequestSummary> getRequests(@WebParam(name = "user") final String user, 
											@WebParam(name = "requestType") final RequestType requestType, 
											@WebParam(name = "subQuery") final String subQuery, 
											@WebParam(name = "startNum") final int startNum, 
											@WebParam(name = "maxNum") final int maxNum);
	
	/**
	 * Gets the detailed request with content by requestId
	 * @param requestId
	 * @return The detailed request
	 */
	public RequestTO getRequestById(@WebParam(name = "requestId") final long requestId);
}
