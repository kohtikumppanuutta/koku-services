package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * UI service interface for employee-related operations in TIVA-Valtakirja functional area.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 16, 2011
 */
@WebService(targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public interface KokuLooraValtakirjaService {
    public int getTotalAuthorizations(
            @WebParam(name = "criteria") final AuthorizationCriteria criteria);
            
    public List<AuthorizationShortSummary> getAuthorizations(
            @WebParam(name = "query") final AuthorizationQuery query);
    
    public List<AuthorizationTemplateTO> searchAuthorizationTemplates(
            @WebParam(name = "searchString") final String searchString, 
            @WebParam(name = "limit") final int limit);
    
    public AuthorizationSummary getAuthorizationDetails(
            @WebParam(name = "valtakirjaId") final long valtakirjaId);
}
