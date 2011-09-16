package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.tiva.service.AuthorizationServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 16, 2011
 */
@Stateless
@WebService(serviceName = "KokuLooraValtakirjaService", portName = "KokuLooraValtakirjaServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuLooraValtakirjaService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public class KokuLooraValtakirjaServiceImpl implements KokuLooraValtakirjaService {

    @EJB
    private AuthorizationServiceFacade serviceFacade;
    
    /**
     * @param criteria
     * @return
     */
    @Override
    public int getTotalAuthorizations(AuthorizationCriteria criteria) {
        return serviceFacade.getTotalAuthorizationsByCriteria(criteria);
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<AuthorizationShortSummary> getAuthorizations(AuthorizationQuery query) {
        return serviceFacade.getAuthorizationsByQuery(query);
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<AuthorizationTemplateTO> searchAuthorizationTemplates(String searchString, int limit) {
        return serviceFacade.getAuthorizationTemplates(searchString, limit);
    }

    /**
     * @param valtakirjaId
     * @return
     */
    @Override
    public AuthorizationSummary getAuthorizationDetails(long valtakirjaId) {
        return serviceFacade.getAuthorizationSummary(valtakirjaId, null);
    }
}
