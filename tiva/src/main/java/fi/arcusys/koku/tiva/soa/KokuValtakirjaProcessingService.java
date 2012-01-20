package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.jws.WebMethod;
import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * Interface with TIVA-Valtakirja-processing operations, called from the TIVA-Valtakirja Intalio process.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 16, 2011
 */
@WebService(targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public interface KokuValtakirjaProcessingService {

    @WebResult(name = "valtakirjapohjat")
    @WebMethod(operationName = "selaaValtakirjapohjat")
    List<AuthorizationTemplateTO> getAuthorizationTemplates(
            @WebParam(name = "searchString") final String searchString,
            @WebParam(name = "limit") final int limit);

    @WebResult(name = "valtakirjaId")
    @WebMethod(operationName = "annaValtakirja")
    Long createAuthorization(
            @WebParam(name = "valtakirjapohjaId") final long authorizationTemplateId,
            @WebParam(name = "vastausmennessa") final XMLGregorianCalendar replyTillDate,
            @WebParam(name = "maaraaika") final XMLGregorianCalendar endDate,
            @WebParam(name = "lahettaja") final String senderUid,
            @WebParam(name = "vastaanottaja") final String receiverUid, 
            @WebParam(name = "kohdehenkilo") final String targetPersonUid);

    @WebMethod(operationName = "hyvaksyValtakirja")
    void approveAuthorization(
            @WebParam(name = "valtakirjaId") final long authorizationId,
            @WebParam(name = "valtuutettu") final String replierUid,
            @WebParam(name = "kommentti") final String comment);

    @WebMethod(operationName = "hylkaaValtakirja")
    void declineAuthorization(
            @WebParam(name = "valtakirjaId") final long authorizationId,
            @WebParam(name = "valtuutettu") final String replierUid,
            @WebParam(name = "kommentti") final String comment);

    @WebMethod(operationName = "muokkaaValtakirja")
    void updateAuthorization(
            @WebParam(name = "valtakirjaId") final long authorizationId,
            @WebParam(name = "valtuuttaja") final String senderUid,
            @WebParam(name = "maaraaika") final XMLGregorianCalendar endDate,
            @WebParam(name = "kommentti") final String comment);

    @WebMethod(operationName = "mitatoiValtakirja")
    void revokeAuthorization(
            @WebParam(name = "valtakirjaId") final long authorizationId,
            @WebParam(name = "valtuuttaja") final String senderUid,
            @WebParam(name = "kommentti") final String comment);

    @WebResult(name = "valtakirja")
    @WebMethod(operationName = "getValtakirja")
    AuthorizationDetailTO getAuthorization(
            @WebParam(name = "valtakirjaId") final long authorizationId,
            @WebParam(name = "kayttaja") final String userUid);
}
