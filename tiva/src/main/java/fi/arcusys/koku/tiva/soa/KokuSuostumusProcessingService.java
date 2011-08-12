package fi.arcusys.koku.tiva.soa;

import java.util.Date;
import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi) Aug 9, 2011
 */
@WebService(targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public interface KokuSuostumusProcessingService {

    @WebResult(name = "suostumuspohjaId")
    @WebMethod(operationName = "luoSuostumuspohja")
    Long createConsentTemplate(
            @WebParam(name = "suostumuspohja") final ConsentTemplateTO consentTemplate);

    @WebResult(name = "suostumuspohjat")
    @WebMethod(operationName = "selaaSuostumuspohjat")
    List<ConsentTemplateTO> getConsentTemplates(
            @WebParam(name = "searchString") final String searchString,
            @WebParam(name = "limit") final int limit);

    @WebResult(name = "suostumusId")
    @WebMethod(operationName = "lahetaSuostumus")
    Long requestForConsent(
            @WebParam(name = "suostumuspohjaId") final long consentTemplateId,
            @WebParam(name = "lahettaja") final String senderUid,
            @WebParam(name = "vastaanottaja") final List<String> receivers);

    @WebMethod(operationName = "annaSuostumus")
    void giveConsent(
            @WebParam(name = "suostumusId") final long consentId,
            @WebParam(name = "suostuja") final String replierUid,
            @WebParam(name = "vastaukset") final List<ActionPermittedTO> actions,
            @WebParam(name = "maaraaika") final XMLGregorianCalendar endDate,
            @WebParam(name = "kommentti") final String comment);

    @WebMethod(operationName = "evaaSuostumus")
    void declineConsent(
            @WebParam(name = "suostumusId") final long consentId,
            @WebParam(name = "suostuja") final String replierUid,
            @WebParam(name = "kommentti") final String comment);

    @WebMethod(operationName = "muokkaaSuostumus")
    void updateConsent(
            @WebParam(name = "suostumusId") final long consentId,
            @WebParam(name = "suostuja") final String replierUid,
            @WebParam(name = "maaraaika") final XMLGregorianCalendar endDate,
            @WebParam(name = "kommentti") final String comment);

    @WebMethod(operationName = "mitatoiSuostumus")
    void revokeConsent(
            @WebParam(name = "suostumusId") final long consentId,
            @WebParam(name = "suostuja") final String replierUid,
            @WebParam(name = "kommentti") final String comment);

    @WebResult(name = "suostumus")
    @WebMethod(operationName = "getSuostumusForReply")
    ConsentForReplyTO getConsentForReply(
            @WebParam(name = "suostumusId") final long consentId,
            @WebParam(name = "suostuja") final String replierUid);

    // ConsentTO getConsent(final long consentId);
}
