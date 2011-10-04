package fi.arcusys.koku.tiva.soa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;
import fi.arcusys.koku.tiva.service.ConsentServiceFacade;

/**
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
        criteria.setReceipientUid(userService.getUserUid(criteria.getReceipientUid()));
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
//        return getConsents_stubVersion();
    }

    private void updateQuery(ConsentQuery query) {
        if (query != null) {
            query.setCriteria(updateUserUid(query.getCriteria()));
        }
    }

    private List<ConsentSummary> getConsents_stubVersion() {
        final List<ConsentSummary> result = new ArrayList<ConsentSummary>();
        final ConsentSummary consentSummary = new ConsentSummary();
        fillTestConsent(consentSummary);
        result.add(consentSummary);
        return result;
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
//        return searchTemplates_stubVersion();
    }

    private List<ConsentTemplateSummary> searchTemplates_stubVersion() {
        final List<ConsentTemplateSummary> result = new ArrayList<ConsentTemplateSummary>();
        final ConsentTemplateSummary template = new ConsentTemplateSummary();
        template.setConsentTemplateId(1L);
        template.setTitle("suostumuspohja #1");
        result.add(template);
        return result;
    }

    /**
     * @param suostumusId
     * @return
     */
    @Override
    public ConsentTO getConsentDetails(long suostumusId) {
        return serviceFacade.getCombinedConsentById(suostumusId);
//        final ConsentTO consent = new ConsentTO();
//        fillTestConsent(consent);
//        final List<ActionRequestSummary> actionRequests = new ArrayList<ActionRequestSummary>();
//        final ActionRequestSummary actionRequest = new ActionRequestSummary();
//        actionRequest.setDescription("Some action given");
//        actionRequest.setStatus(ActionRequestStatus.Given);
//        actionRequests.add(actionRequest);
//        final ActionRequestSummary anotherActionRequest = new ActionRequestSummary();
//        anotherActionRequest.setDescription("Another action declined");
//        anotherActionRequest.setStatus(ActionRequestStatus.Declined);
//        actionRequests.add(anotherActionRequest);
//        consent.setActionRequests(actionRequests);
//        return consent;
    }

    private void fillTestConsent(final ConsentSummary consent) {
        consent.setConsentId(123L);
        consent.setTemplateName("suostumuspohja #1");
        consent.setRequestor("Ville Virkamies");
        consent.setCreateType(ConsentCreateType.Electronic);
        consent.setGivenAt(CalendarUtil.getXmlDate(new Date()));
        consent.setStatus(ConsentStatus.Open);
        final XMLGregorianCalendar validTill = CalendarUtil.getXmlDate(new Date());
        validTill.setMonth(validTill.getMonth() + 1);
        consent.setValidTill(validTill);
    }

}
