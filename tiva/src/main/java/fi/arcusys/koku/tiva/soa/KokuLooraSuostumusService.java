package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
@WebService(targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public interface KokuLooraSuostumusService {
    public int getTotalConsents(
            @WebParam(name = "user") final String user,
            @WebParam(name = "criteria") final ConsentCriteria criteria);
            
    public List<ConsentSummary> getConsents(
            @WebParam(name = "user") final String user,
            @WebParam(name = "query") final ConsentQuery query);
    
    public List<ConsentTemplateSummary> searchConsentTemplates(
            @WebParam(name = "searchString") final String searchString, 
            @WebParam(name = "limit") final int limit);
    
    public ConsentTO getConsentDetails(
            @WebParam(name = "suostumusId") final long suostumusId);
}
