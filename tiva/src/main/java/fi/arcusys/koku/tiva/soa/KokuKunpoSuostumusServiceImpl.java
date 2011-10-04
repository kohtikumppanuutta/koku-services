package fi.arcusys.koku.tiva.soa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.tiva.service.ConsentServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
@Stateless
@WebService(serviceName = "KokuKunpoSuostumusService", portName = "KokuKunpoSuostumusServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuKunpoSuostumusService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
@Interceptors(KokuSuostumusInterceptor.class)
public class KokuKunpoSuostumusServiceImpl implements KokuKunpoSuostumusService {

    private final static Logger logger = LoggerFactory.getLogger(KokuKunpoSuostumusServiceImpl.class);
    
    @EJB
    private ConsentServiceFacade serviceFacade;
    
    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalAssignedConsents(String user) {
        return serviceFacade.getTotalAssignedConsents(user);
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ConsentShortSummary> getAssignedConsents(String user, int startNum,
            int maxNum) {
        return serviceFacade.getAssignedConsents(user, startNum, maxNum);
//        return getAssignedConsents_stubVersion(user);
    }

    private List<ConsentShortSummary> getAssignedConsents_stubVersion(
            String user) {
        logger.info("getAssignedConsents: " + user);
        final List<ConsentShortSummary> result = new ArrayList<ConsentShortSummary>();
        final ConsentShortSummary consent = new ConsentShortSummary();
        consent.setConsentId(123L);
        consent.setAnotherPermitterUid("Kirsi Kuntalainen");
        consent.setTemplateName("suostumuspohja #1");
        consent.setRequestor("Ville Virkamies");
        result.add(consent);
        return result;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalOwnConsents(String user) {
        return serviceFacade.getTotalOwnConsents(user);
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ConsentSummary> getOwnConsents(String user, int startNum,
            int maxNum) {
        if (logger.isDebugEnabled()) {
            logger.debug("getOwnConsents: " + user);
        }
        return serviceFacade.getOwnConsents(user, startNum, maxNum);
//        return Collections.singletonList(fillTestConsent(new ConsentSummary()));
    }

    private ConsentSummary fillTestConsent(final ConsentSummary consent) {
        consent.setConsentId(123L);
        consent.setAnotherPermitterUid("Kirsi Kuntalainen");
        consent.setTemplateName("suostumuspohja #2");
        consent.setRequestor("Ville Virkamies");
        consent.setCreateType(ConsentCreateType.Electronic);
        consent.setGivenAt(CalendarUtil.getXmlDate(new Date()));
        consent.setStatus(ConsentStatus.Open);
        final XMLGregorianCalendar validTill = CalendarUtil.getXmlDate(new Date());
        validTill.setMonth(validTill.getMonth() + 1);
        consent.setValidTill(validTill);
        return consent;
    }

    /**
     * @param suostumusId
     * @return
     */
    @Override
    public ConsentTO getConsentById(long suostumusId, final String userUid) {
        return serviceFacade.getConsentById(suostumusId, userUid);
//        return getConsentById_stubVersion(suostumusId);
    }

    private ConsentTO getConsentById_stubVersion(long suostumusId) {
        logger.info("getConsentById: " + suostumusId);
        final ConsentTO consent = new ConsentTO();
        fillTestConsent(consent);
        final List<ActionRequestSummary> actionRequests = new ArrayList<ActionRequestSummary>();
        final ActionRequestSummary actionRequest = new ActionRequestSummary();
        actionRequest.setDescription("Some action given");
        actionRequest.setStatus(ActionRequestStatus.Given);
        actionRequests.add(actionRequest);
        final ActionRequestSummary anotherActionRequest = new ActionRequestSummary();
        anotherActionRequest.setDescription("Another action declined");
        anotherActionRequest.setStatus(ActionRequestStatus.Declined);
        actionRequests.add(anotherActionRequest);
        consent.setActionRequests(actionRequests);
        return consent;
    }

    /**
     * @param suostumusId
     */
    @Override
    public void revokeOwnConsent(long suostumusId, final String userUid) {
        if (logger.isDebugEnabled()) {
            logger.debug("revokeConsent: " + suostumusId);
        }
        serviceFacade.revokeConsent(suostumusId, userUid, "");
    }

}
