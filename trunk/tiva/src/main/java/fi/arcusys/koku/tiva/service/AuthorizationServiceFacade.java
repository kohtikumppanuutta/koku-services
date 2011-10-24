package fi.arcusys.koku.tiva.service;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.tiva.soa.AuthorizationCriteria;
import fi.arcusys.koku.tiva.soa.AuthorizationDetailTO;
import fi.arcusys.koku.tiva.soa.AuthorizationQuery;
import fi.arcusys.koku.tiva.soa.AuthorizationShortSummary;
import fi.arcusys.koku.tiva.soa.AuthorizationSummary;
import fi.arcusys.koku.tiva.soa.AuthorizationTemplateTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 12, 2011
 */
public interface AuthorizationServiceFacade {
    List<AuthorizationTemplateTO> getAuthorizationTemplates(final String searchString, final int limit);
    
    Long createAuthorization(final long authorizationTemplateId, final XMLGregorianCalendar endDate, final String senderUid, final String receiverUid, final String targetPersonUid);
    
    AuthorizationDetailTO getAuthorization(final long authorizationId, final String userUid);

    List<AuthorizationShortSummary> getReceivedAuthorizations(final String userUid, final int startNum, final int maxNum);

    List<AuthorizationShortSummary> getSentAuthorizations(final String userUid, final int startNum, final int maxNum);
    
    AuthorizationSummary getAuthorizationSummary(final long authorizationId, final String userUid);

    void approveAuthorization(final long authorizationId, final String replierUid, final String comment);

    void declineAuthorization(final long authorizationId, final String replierUid, final String comment);

    void updateAuthorization(final long authorizationId, final String senderUid, final XMLGregorianCalendar endDate, final String comment);

    void revokeAuthorization(final long authorizationId, final String senderUid, final String comment);

    /**
     * @param userUid
     * @return
     */
    int getTotalSentAuthorizations(final String userUid);

    /**
     * @param userUid
     * @return
     */
    int getTotalReceivedAuthorizations(String userUid);

    /**
     * @param query
     * @return
     */
    List<AuthorizationShortSummary> getAuthorizationsByQuery(AuthorizationQuery query);

    /**
     * @param criteria
     * @return
     */
    int getTotalAuthorizationsByCriteria(AuthorizationCriteria criteria);

}
