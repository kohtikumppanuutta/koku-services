package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 13, 2011
 */
@WebService(targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public interface KokuKunpoValtakirjaService {
    
    List<AuthorizationShortSummary> getSentAuthorizations(
            @WebParam(name = "user") final String userUid, 
            @WebParam(name = "startNum") int startNum, 
            @WebParam(name = "maxNum") int maxNum);

    int getTotalSentAuthorizations(@WebParam(name = "user") final String userUid);
    
    List<AuthorizationShortSummary> getReceivedAuthorizations(
            @WebParam(name = "user") final String userUid, 
            @WebParam(name = "startNum") int startNum, 
            @WebParam(name = "maxNum") int maxNum);

    int getTotalReceivedAuthorizations(@WebParam(name = "user") final String userUid);
    
    AuthorizationSummary getAuthorizationSummaryById(
            @WebParam(name = "authorizationId") final long authorizationId, 
            @WebParam(name = "user") final String userUid);
    
    void revokeOwnAuthorization(
            @WebParam(name = "authorizationId") final long authorizationId, 
            @WebParam(name = "user") final String userUid, 
            @WebParam(name = "comment") final String comment);
}
