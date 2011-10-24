package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;
import javax.xml.bind.annotation.XmlElement;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 3, 2011
 */
@WebService(targetNamespace = "http://services.koku.fi/entity/tiva/v1")
public interface KokuTivaToKksService {
    @WebResult(name = "consentTemplate")
    List<ConsentTemplateShort> queryConsentTemplates(
            @WebParam(name = "prefix") final String prefix, 
            @WebParam(name = "limit") final Integer limit);
    
    void createConsent(
            @WebParam(name = "consent") final ConsentExternal consent);
    
    List<ConsentExternal> queryConsents(
            @WebParam(name = "consentSearchQuery") final ConsentSearchCriteria query);
}
