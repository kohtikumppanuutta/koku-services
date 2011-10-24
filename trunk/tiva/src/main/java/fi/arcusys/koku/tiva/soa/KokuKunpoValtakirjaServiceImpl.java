package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import fi.arcusys.koku.tiva.service.AuthorizationServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 16, 2011
 */
@Stateless
@WebService(serviceName = "KokuKunpoValtakirjaService", portName = "KokuKunpoValtakirjaServicePort", 
endpointInterface = "fi.arcusys.koku.tiva.soa.KokuKunpoValtakirjaService",
targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
@Interceptors(KokuValtakirjaInterceptor.class)
public class KokuKunpoValtakirjaServiceImpl implements KokuKunpoValtakirjaService {

    @EJB
    private AuthorizationServiceFacade serviceFacade;
    
    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AuthorizationShortSummary> getSentAuthorizations(String userUid, int startNum, int maxNum) {
        return serviceFacade.getSentAuthorizations(userUid, startNum, maxNum);
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalSentAuthorizations(String userUid) {
        return serviceFacade.getTotalSentAuthorizations(userUid);
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AuthorizationShortSummary> getReceivedAuthorizations(String userUid, int startNum, int maxNum) {
        return serviceFacade.getReceivedAuthorizations(userUid, startNum, maxNum);
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalReceivedAuthorizations(String userUid) {
        return serviceFacade.getTotalReceivedAuthorizations(userUid);
    }

    /**
     * @param authorizationId
     * @return
     */
    @Override
    public AuthorizationSummary getAuthorizationSummaryById(long authorizationId, final String userUid) {
        return serviceFacade.getAuthorizationSummary(authorizationId, userUid);
    }

    /**
     * @param authorizationId
     * @param userUid
     */
    @Override
    public void revokeOwnAuthorization(long authorizationId, String userUid, final String comment) {
        serviceFacade.revokeAuthorization(authorizationId, userUid, comment);
    }

}
