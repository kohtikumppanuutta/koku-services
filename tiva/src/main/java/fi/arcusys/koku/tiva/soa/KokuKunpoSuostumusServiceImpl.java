package fi.arcusys.koku.tiva.soa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.service.common.CalendarUtil;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 11, 2011
 */
@Stateless
@WebService(serviceName = "KokuKunpoSuostumusService", portName = "KokuKunpoSuostumusServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuKunpoSuostumusService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public class KokuKunpoSuostumusServiceImpl implements KokuKunpoSuostumusService {

    private final static Logger logger = LoggerFactory.getLogger(KokuKunpoSuostumusServiceImpl.class);
    
    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalAssignedConsents(String user) {
        // TODO Auto-generated method stub
        logger.info("getTotalAssignedConsents: " + user);
        return 1;
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
        // TODO Auto-generated method stub
        logger.info("getAssignedConsents: " + user);
        final List<ConsentShortSummary> result = new ArrayList<ConsentShortSummary>();
        final ConsentShortSummary consent = new ConsentShortSummary();
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
        // TODO Auto-generated method stub
        logger.info("getTotalOwnConsents: " + user);
        return 1;
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
        // TODO Auto-generated method stub
        logger.info("getOwnConsents: " + user);
        final List<ConsentSummary> result = new ArrayList<ConsentSummary>();
        final ConsentSummary consent = new ConsentSummary();
        fillTestConsent(consent);
        result.add(consent);
        return result;
    }

    private void fillTestConsent(final ConsentSummary consent) {
        consent.setAnotherPermitterUid("Kirsi Kuntalainen");
        consent.setTemplateName("suostumuspohja #2");
        consent.setRequestor("Ville Virkamies");
        consent.setCreateType(ConsentCreateType.Electronic);
        consent.setGivenAt(CalendarUtil.getXmlDate(new Date()));
        consent.setStatus(ConsentStatus.Open);
        final XMLGregorianCalendar validTill = CalendarUtil.getXmlDate(new Date());
        validTill.setMonth(validTill.getMonth() + 1);
        consent.setValidTill(validTill);
    }

    /**
     * @param suostumusId
     * @return
     */
    @Override
    public ConsentTO getConsentById(long suostumusId) {
        // TODO Auto-generated method stub
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
    public void revokeOwnConsent(long suostumusId) {
        // TODO Auto-generated method stub
        logger.info("revokeConsent: " + suostumusId);
    }

}
