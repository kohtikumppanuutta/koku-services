package fi.arcusys.koku.tiva.soa;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.CalendarUtil;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 9, 2011
 */
@Stateless
@WebService(serviceName = "KokuSuostumusProcessingService", portName = "KokuSuostumusProcessingServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuSuostumusProcessingService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public class KokuSuostumusProcessingServiceImpl implements KokuSuostumusProcessingService {

    private static final Logger logger = LoggerFactory.getLogger(KokuSuostumusProcessingServiceImpl.class);
    
    /**
     * @param consentTemplate
     * @return
     */
    @Override
    public Long createConsentTemplate(ConsentTemplateTO consentTemplate) {
        // TODO Auto-generated method stub
        logger.info("createConsentTemplate: " + (consentTemplate != null ? consentTemplate.getTitle() : "null"));
        return 1L;
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<ConsentTemplateTO> getConsentTemplates(String searchString, int limit) {
        // TODO Auto-generated method stub
        logger.info("getConsentTemplates: " + searchString + ", limit = " + limit);
        final List<ConsentTemplateTO> result = new ArrayList<ConsentTemplateTO>();
        final ConsentTemplateTO template = createTestTemplate();
        result.add(template);
        return result;
    }

    private ConsentTemplateTO createTestTemplate() {
        final ConsentTemplateTO template = new ConsentTemplateTO();
        template.setConcentTemplateId(1L);
        template.setCreatorUid("Ville Virkamies");
        template.setDescription("Testing suostumus from service stub.");
        template.setTitle("Testing suostumus");
        final List<ActionRequestTO> actions = new ArrayList<ActionRequestTO>();
        for (int number = 1; number <= 2; number++ ) {
            final ActionRequestTO actionRequest = new ActionRequestTO();
            actionRequest.setNumber(number);
            actionRequest.setName("toimenpidepyyntö " + number);
            actionRequest.setDescription("descripton of toimenpidepyyntö " + number);
            actions.add(actionRequest);
        } 
        template.setActions(actions);
        return template;
    }

    /**
     * @param consentTemplateId
     * @param senderUid
     * @param receivers
     * @return
     */
    @Override
    public Long requestForConsent(long consentTemplateId, String senderUid,
            List<String> receivers) {
        // TODO Auto-generated method stub
        logger.info("requestForConsent: " + consentTemplateId + ", senderUid = " + senderUid + ", receivers = " + receivers);
        if (consentTemplateId != 1) {
            throw new IllegalArgumentException("There is no template with ID " + consentTemplateId);
        }
        return 123L;
    }

    /**
     * @param consentId
     * @param replierUid
     * @param endDate
     * @param comment
     */
    @Override
    public void giveConsent(long consentId, String replierUid, 
            final List<ActionPermittedTO> actions,
            XMLGregorianCalendar endDate,
            String comment) {        // TODO Auto-generated method stub
        logger.info("giveConsent: " + consentId + ", replierUid = " + replierUid + ", endDate = " + endDate + ", comment = " + comment);
    }

    /**
     * @param consentId
     * @param replierUid
     * @param comment
     */
    @Override
    public void declineConsent(long consentId, String replierUid, String comment) {
        // TODO Auto-generated method stub
        logger.info("declineConsent: " + consentId + ", replierUid = " + replierUid + ", comment = " + comment);
    }

    /**
     * @param consentId
     * @param replierUid
     * @param endDate
     * @param comment
     */
    @Override
    public void updateConsent(long consentId, String replierUid, XMLGregorianCalendar endDate,
            String comment) {
        // TODO Auto-generated method stub
        logger.info("giveConsent: " + consentId + ", replierUid = " + replierUid + ", endDate = " + endDate + ", comment = " + comment);
    }

    /**
     * @param consentId
     * @param replierUid
     * @param comment
     */
    @Override
    public void revokeConsent(long consentId, String replierUid, String comment) {
        // TODO Auto-generated method stub
        logger.info("revokeConsent: " + consentId + ", replierUid = " + replierUid + ", comment = " + comment);
    }

    /**
     * @param consentId
     * @param userUid
     * @return
     */
    @Override
    public ConsentForReplyTO getConsentForReply(long consentId, String userUid) {
        // TODO Auto-generated method stub
        if (consentId != 123) {
            throw new IllegalArgumentException("Consent with ID " + consentId + " is not found.");
        }
        final ConsentForReplyTO consent = new ConsentForReplyTO();
        consent.setConsentId(123L);
        final XMLGregorianCalendar endDate = CalendarUtil.getXmlDate(new Date());
        endDate.setDay(31);
        consent.setEndDate(endDate);
        consent.setReplierUid("Kalle Kuntalainen");
        consent.setReplyComment("Hyväksynyt suostumus");
        consent.setTemplate(createTestTemplate());
        return consent;
    }

}
