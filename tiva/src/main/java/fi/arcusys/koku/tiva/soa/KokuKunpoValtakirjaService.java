package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 13, 2011
 */
@WebService(targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public interface KokuKunpoValtakirjaService {
    
    List<AuthorizationShortSummary> getSentAuthorizations(final String userUid, final int startNum, final int maxNum);

    int getTotalSentAuthorizations(final String userUid);
    
    List<AuthorizationShortSummary> getReceivedAuthorizations(final String userUid, final int startNum, final int maxNum);

    int getTotalReceivedAuthorizations(final String userUid);
    
    AuthorizationSummary getAuthorizationSummaryById(final long authorizationId, final String userUid);
    
    void revokeOwnAuthorization(final long authorizationId, final String userUid, final String comment);
}
