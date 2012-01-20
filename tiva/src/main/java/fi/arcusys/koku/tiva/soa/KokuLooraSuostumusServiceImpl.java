package fi.arcusys.koku.tiva.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.arcusys.koku.tiva.service.ConsentServiceFacade;

/**
 * UI service implementation for employee-related operations in TIVA-Suostumus functional area.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
@Stateless
@WebService(serviceName = "KokuLooraSuostumusService", portName = "KokuLooraSuostumusServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuLooraSuostumusService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
@Interceptors(KokuSuostumusInterceptor.class)
public class KokuLooraSuostumusServiceImpl implements KokuLooraSuostumusService {

    @EJB
    private ConsentServiceFacade serviceFacade;
    
    @EJB
    private UsersAndGroupsService userService;
    
    /**
     * @param user
     * @param updateUserUid(query)
     * @return
     */
    @Override
    public int getTotalConsents(String user, final ConsentCriteria query) {
        return serviceFacade.getTotalProcessedConsents(user, updateUserUid(query));
    }

    private ConsentCriteria updateUserUid(final ConsentCriteria criteria) {
        if (criteria == null) {
            return null;
        }
        criteria.setReceipientUid(userService.getUserUidByKunpoSsn(criteria.getReceipientUid()));
        return criteria;
    }

    /**
     * @param user
     * @param query
     * @return
     */
    @Override
    public List<ConsentSummary> getConsents(String user, ConsentQuery query) {
        updateQuery(query);
        return serviceFacade.getProcessedConsents(user, query);
    }

    private void updateQuery(ConsentQuery query) {
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
    public List<ConsentTemplateSummary> searchConsentTemplates(
            String searchString, int limit) {
        return serviceFacade.searchConsentTemplates(searchString, limit);
    }

    /**
     * @param suostumusId
     * @return
     */
    @Override
    public ConsentTO getConsentDetails(long suostumusId) {
        return serviceFacade.getCombinedConsentById(suostumusId);
    }
}
