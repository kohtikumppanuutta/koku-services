package fi.arcusys.koku.tiva.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.tiva.soa.ActionPermittedTO;
import fi.arcusys.koku.tiva.soa.ConsentForReplyTO;
import fi.arcusys.koku.tiva.soa.ConsentShortSummary;
import fi.arcusys.koku.tiva.soa.ConsentTemplateTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
public interface ConsentServiceFacade {

    /**
     * @param template
     * @return
     */
    Long createConsentTemplate(final ConsentTemplateTO template);

    /**
     * @param templateId
     * @return
     */
    ConsentTemplateTO getConsentTemplate(final Long templateId);

    /**
     * @param string
     * @param i
     * @return
     */
    List<ConsentTemplateTO> searchConsentTemplates(String searchString, int limit);

    /**
     * @param templateId
     * @param string
     * @param string2
     * @param asList
     * @return
     */
    Long requestForConsent(final Long templateId, String senderUid, String targetPersonUid, List<String> receipients);

    
    /**
     * @param consentId
     * @return
     */
    ConsentForReplyTO getConsentForReply(final Long consentId, final String replierUid);

    /**
     * @param parentForApprove
     * @param i
     * @param j
     * @return
     */
    List<ConsentShortSummary> getAssignedConsents(final String userUid, final int startNum, final int maxNum);

    /**
     * @param consentId
     * @param parentForApprove
     * @param arrayList
     * @param xmlDate
     * @param string
     */
    void giveConsent(final Long consentId, final String userUid,
            final List<ActionPermittedTO> actions,
            final XMLGregorianCalendar validTill, final String comment);
}
