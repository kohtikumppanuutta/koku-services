package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.arcusys.koku.tiva.service.AuthorizationServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 16, 2011
 */
@Stateless
@WebService(serviceName = "KokuLooraValtakirjaService", portName = "KokuLooraValtakirjaServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuLooraValtakirjaService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
@Interceptors(KokuValtakirjaInterceptor.class)
public class KokuLooraValtakirjaServiceImpl implements KokuLooraValtakirjaService {

    @EJB
    private AuthorizationServiceFacade serviceFacade;
    
    @EJB
    private UsersAndGroupsService userService;
    
    /**
     * @param updateUserUid(criteria)
     * @return
     */
    @Override
    public int getTotalAuthorizations(AuthorizationCriteria criteria) {
        return serviceFacade.getTotalAuthorizationsByCriteria(updateUserUid(criteria));
    }

    private AuthorizationCriteria updateUserUid(AuthorizationCriteria criteria) {
        if (criteria == null) {
            return null;
        }
        criteria.setReceipientUid(userService.getUserUidByKunpoSsn(criteria.getReceipientUid()));
        criteria.setSenderUid(userService.getUserUidByKunpoSsn(criteria.getSenderUid()));
        criteria.setTargetPersonUid(userService.getUserUidByKunpoSsn(criteria.getTargetPersonUid()));
        return criteria;
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<AuthorizationShortSummary> getAuthorizations(AuthorizationQuery query) {
        updateQuery(query);
        return serviceFacade.getAuthorizationsByQuery(query);
    }

    private void updateQuery(AuthorizationQuery query) {
        if (query != null) {
            query.setCriteria(updateUserUid(query.getCriteria()));
        }
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
