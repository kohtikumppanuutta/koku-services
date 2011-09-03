package fi.arcusys.koku.tiva.service;

import java.util.ArrayList;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.tiva.soa.ActionPermittedTO;
import fi.arcusys.koku.tiva.soa.ConsentForReplyTO;
import fi.arcusys.koku.tiva.soa.ConsentQuery;
import fi.arcusys.koku.tiva.soa.ConsentShortSummary;
import fi.arcusys.koku.tiva.soa.ConsentSummary;
import fi.arcusys.koku.tiva.soa.ConsentTO;
import fi.arcusys.koku.tiva.soa.ConsentTemplateSummary;
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
    List<ConsentTemplateSummary> searchConsentTemplates(String searchString, int limit);

    /**
     * @param templateId
     * @param isMandatory 
     * @param endDate 
     * @param string
     * @param string2
     * @param asList
     * @return
     */
    Long requestForConsent(final Long templateId, String senderUid, String targetPersonUid, List<String> receipients, XMLGregorianCalendar endDate, Boolean isMandatory);

    
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

    /**
     * @param consentId
     * @param parentForApprove
     * @param arrayList
     * @param xmlDate
     * @param string
     */
    void declineConsent(final Long consentId, final String userUid, final String string);

    /**
     * @param parentForApprove
     * @param i
     * @param j
     * @return
     */
    List<ConsentSummary> getOwnConsents(final String userUid, final int startNum, final int maxNum);

    /**
     * @param consentId
     * @param parentForApprove
     * @param newDate
     * @param string
     */
    void updateConsent(final Long consentId, final String user, final XMLGregorianCalendar newDate, final String comment);

    /**
     * @param consentId
     * @param parentForApprove
     * @return
     */
    ConsentTO getConsentById(final Long consentId, final String user);

    /**
     * @param consentId
     * @param parentForApprove
     * @param string
     */
    void revokeConsent(final Long consentId, final String user, final String comment);

    /**
     * @param parent
     * @return
     */
    int getTotalAssignedConsents(final String userUid);

    /**
     * @param parent
     * @return
     */
    int getTotalOwnConsents(final String userUid);

    /**
     * @param consentId
     */
    ConsentTO getCombinedConsentById(Long consentId);

    /**
     * @param employeeUid
     * @return
     */
    List<ConsentSummary> getProcessedConsents(final String employeeUid, final ConsentQuery query);

    /**
     * @param employee
     * @return
     */
    int getTotalProcessedConsents(final String userUid);

    /**
     * @param searchString
     * @param limit
     * @return
     */
    List<ConsentTemplateTO> getConsentTemplates(String searchString, int limit);
}
