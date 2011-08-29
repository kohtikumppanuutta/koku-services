package fi.arcusys.koku.tiva.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.ConsentDAO;
import fi.arcusys.koku.common.service.ConsentReplyDAO;
import fi.arcusys.koku.common.service.ConsentTemplateDAO;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentActionReply;
import fi.arcusys.koku.common.service.datamodel.ConsentActionRequest;
import fi.arcusys.koku.common.service.datamodel.ConsentReply;
import fi.arcusys.koku.common.service.datamodel.ConsentReplyStatus;
import fi.arcusys.koku.common.service.datamodel.ConsentTemplate;
import fi.arcusys.koku.common.service.datamodel.ConsentType;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.tiva.service.ConsentServiceFacade;
import fi.arcusys.koku.tiva.soa.ActionPermittedTO;
import fi.arcusys.koku.tiva.soa.ActionRequestStatus;
import fi.arcusys.koku.tiva.soa.ActionRequestSummary;
import fi.arcusys.koku.tiva.soa.ActionRequestTO;
import fi.arcusys.koku.tiva.soa.ConsentApprovalStatus;
import fi.arcusys.koku.tiva.soa.ConsentCreateType;
import fi.arcusys.koku.tiva.soa.ConsentForReplyTO;
import fi.arcusys.koku.tiva.soa.ConsentShortSummary;
import fi.arcusys.koku.tiva.soa.ConsentStatus;
import fi.arcusys.koku.tiva.soa.ConsentSummary;
import fi.arcusys.koku.tiva.soa.ConsentTO;
import fi.arcusys.koku.tiva.soa.ConsentTemplateSummary;
import fi.arcusys.koku.tiva.soa.ConsentTemplateTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@Stateless
public class ConsentServiceFacadeImpl implements ConsentServiceFacade {
    
    private static final Logger logger = LoggerFactory.getLogger(ConsentServiceFacadeImpl.class); 

    @EJB
    private ConsentTemplateDAO templateDao;
    
    @EJB
    private ConsentDAO consentDao;

    @EJB
    private ConsentReplyDAO consentReplyDao;

    @EJB
    private UserDAO userDao;

    /**
     * @param template
     * @return
     */
    @Override
    public Long createConsentTemplate(ConsentTemplateTO templateTO) {
        if (logger.isDebugEnabled()) {
            logger.debug("Create new consent template by user " + templateTO.getCreatorUid() + " with title " + templateTO.getTitle() + 
                    " and " + (templateTO.getActions() != null ? templateTO.getActions().size() : 0) + " number of action requests.");
        }
        return templateDao.create(convertDTOtoConsentTemplate(templateTO)).getId();
    }

    /**
     * @param templateId
     * @return
     */
    @Override
    public ConsentTemplateTO getConsentTemplate(Long templateId) {
        if (logger.isDebugEnabled()) {
            logger.debug("Get consent template by ID: " + templateId);
        }
        final ConsentTemplate template = templateDao.getById(templateId);
        return convertConsentTemplateToDTO(template);
    }

    private ConsentTemplateTO convertConsentTemplateToDTO(final ConsentTemplate template) {
        if (template == null) {
            return null;
        }        
        final ConsentTemplateTO templateTO = new ConsentTemplateTO();
        templateTO.setConsentTemplateId(template.getId());
        templateTO.setCreatorUid(getSafeUserUid(template.getCreator()));
        templateTO.setDescription(template.getDescription() == null ? "" : template.getDescription());
        templateTO.setTitle(template.getTitle());
        final List<ActionRequestTO> actionTOs = new ArrayList<ActionRequestTO>();
        for (final ConsentActionRequest action : template.getActions()) {
            final ActionRequestTO actionRequestTO = new ActionRequestTO();
            actionRequestTO.setNumber(action.getNumber());
            actionRequestTO.setName(action.getName());
            actionRequestTO.setDescription(action.getDescription());
            actionTOs.add(actionRequestTO);
        }
        
        templateTO.setActions(actionTOs);
        return templateTO;
    }

    private String getSafeUserUid(final User user) {
        if (user == null || user.getUid() == null) {
            return "";
        }
        return user.getUid();
    }

    private ConsentTemplate convertDTOtoConsentTemplate(
            ConsentTemplateTO templateTO) {
        final ConsentTemplate template = new ConsentTemplate();
        template.setCreator(userDao.getOrCreateUser(templateTO.getCreatorUid()));
        template.setDescription(templateTO.getDescription());
        template.setTitle(templateTO.getTitle());
        final Set<ConsentActionRequest> actions = new HashSet<ConsentActionRequest>();
        for (final ActionRequestTO actionTO : templateTO.getActions()) {
            final ConsentActionRequest actionRequest = new ConsentActionRequest();
            actionRequest.setNumber(actionTO.getNumber());
            actionRequest.setName(actionTO.getName());
            actionRequest.setDescription(actionTO.getDescription());
            actions.add(actionRequest);
        }
        
        template.setActions(actions);
        return template;
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<ConsentTemplateSummary> searchConsentTemplates(final String searchString, int limit) {
        final List<ConsentTemplateSummary> result = new ArrayList<ConsentTemplateSummary>();
        for (final ConsentTemplate template : templateDao.searchTemplates(searchString, limit)) {
            final ConsentTemplateSummary templateTO = new ConsentTemplateSummary();
            templateTO.setConsentTemplateId(template.getId());
            templateTO.setTitle(template.getTitle());
            result.add(templateTO);
        }
        return result;
    }

    @Override
    public List<ConsentTemplateTO> getConsentTemplates(final String searchString, int limit) {
        final List<ConsentTemplateTO> result = new ArrayList<ConsentTemplateTO>();
        for (final ConsentTemplate template : templateDao.searchTemplates(searchString, limit)) {
            result.add(convertConsentTemplateToDTO(template));
        }
        return result;
    }

    /**
     * @param templateId
     * @param senderUid
     * @param targetPersonUid
     * @param receipients
     * @return
     */
    @Override
    public Long requestForConsent(final Long templateId, final String senderUid, final String targetPersonUid, final List<String> receipientUids) {

        if (receipientUids == null || receipientUids.isEmpty()) {
            throw new IllegalArgumentException("Consent is requested from empty list of receipients.");
        } 
        
        final ConsentTemplate template = templateDao.getById(templateId);
        if (template == null) {
            throw new IllegalArgumentException("Consent template with ID " + templateId + " is not found");
        }
        
        final Consent consent = new Consent();
        consent.setCreationType(ConsentType.Electronic);
        consent.setCreator(userDao.getOrCreateUser(senderUid));
        consent.setTargetPerson(userDao.getOrCreateUser(targetPersonUid));
        consent.setTemplate(template);
        final Set<User> receipients = new HashSet<User>();
        for (final String userUid : receipientUids) {
            receipients.add(userDao.getOrCreateUser(userUid));
        }
        consent.setReceipients(receipients);
        return consentDao.create(consent).getId();
    }

    /**
     * @param consentId
     * @return
     */
    @Override
    public ConsentForReplyTO getConsentForReply(final Long consentId, final String replierUid) {
        final Consent consent = consentDao.getById(consentId);
        final ConsentForReplyTO consentTO = new ConsentForReplyTO();
        consentTO.setConsentId(consent.getId());
        consentTO.setReplierUid(replierUid);

        final ConsentReply reply = consentReplyDao.getReplyByConsentAndUser(consent, userDao.getOrCreateUser(replierUid));
        if (reply != null) {
            consentTO.setReplyComment(reply.getComment());
            consentTO.setEndDate(CalendarUtil.getXmlDate(reply.getValidTill()));
        }
        
        consentTO.setTemplate(convertConsentTemplateToDTO(consent.getTemplate()));
        return consentTO;
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ConsentShortSummary> getAssignedConsents(final String userUid, final int startNum, final int maxNum) {
        final List<Consent> consents = consentDao.getAssignedConsents(userDao.getOrCreateUser(userUid), startNum, maxNum - startNum + 1);
        final List<ConsentShortSummary> result = new ArrayList<ConsentShortSummary>();
        for (final Consent consent : consents) {
            final ConsentShortSummary consentTO = new ConsentShortSummary();
            fillShortSummaryByConsent(userUid, consent, consentTO);
            result.add(consentTO);
        }
        return result;
    }

    private void fillShortSummaryByConsent(final String userUid,
            final Consent consent, final ConsentShortSummary consentTO) {
        consentTO.setConsentId(consent.getId());
        consentTO.setRequestor(getSafeUserUid(consent.getCreator()));
        consentTO.setTemplateName(consent.getTemplate().getTitle());
        consentTO.setAnotherPermitterUid(getAnotherUser(userUid, consent.getReceipients()));
        consentTO.setCreateType(ConsentCreateType.valueOf(consent.getCreationType()));
    }

    private String getAnotherUser(final String userUid, final Set<User> users) {
        for (final User user : users) {
            if (!getSafeUserUid(user).equals(userUid)) {
                return getSafeUserUid(user);
            }
        }
        return "";
    }

    /**
     * @param consentId
     * @param userUid
     * @param actions
     * @param validTill
     * @param comment
     */
    @Override
    public void giveConsent( Long consentId, String userUid, List<ActionPermittedTO> actions, XMLGregorianCalendar validTill, String comment) {
        final Consent consent = loadConsent(consentId);
        
        final User replier = userDao.getOrCreateUser(userUid);
        
        final ConsentReply reply = new ConsentReply();
        reply.setConsent(consent);
        reply.setReplier(replier);
        reply.setComment(comment);
        reply.setStatus(ConsentReplyStatus.Given);
        reply.setValidTill(validTill == null ? null : validTill.toGregorianCalendar().getTime());
        
        final Map<Integer, ConsentActionRequest> actionRequests = consent.getTemplate().getNumberToActionMap();
        
        final Set<ConsentActionReply> actionReplies = new HashSet<ConsentActionReply>();
        for (final ActionPermittedTO actionResponse : actions) {
            if (!actionRequests.containsKey(actionResponse.getActionRequestNumber())) {
                throw new IllegalArgumentException("Incorrect action number " + actionResponse.getActionRequestNumber() + " in response for consent ID " + consentId);
            }
            
            final ConsentActionReply actionReply = new ConsentActionReply();
            actionReply.setActionRequestNumber(actionResponse.getActionRequestNumber());
            actionReply.setPermitted(actionResponse.isPermitted());
            actionReplies.add(actionReply);
        }
        reply.setActions(actionReplies);
        
        consentReplyDao.create(reply);
    }

    /**
     * @param consentId
     * @param userUid
     * @param string
     */
    @Override
    public void declineConsent(Long consentId, String userUid, String comment) {
        final Consent consent = loadConsent(consentId);
        
        final User replier = userDao.getOrCreateUser(userUid);
        
        final ConsentReply reply = new ConsentReply();
        reply.setConsent(consent);
        reply.setReplier(replier);
        reply.setComment(comment);
        reply.setStatus(ConsentReplyStatus.Declined);

        consentReplyDao.create(reply);
    }

    private Consent loadConsent(Long consentId) {
        final Consent consent = consentDao.getById(consentId);
        if (consent == null) {
            throw new IllegalArgumentException("Consent with ID " + consentId + " is not found.");
        }
        return consent;
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ConsentSummary> getOwnConsents(final String userUid, final int startNum, final int maxNum) {
        final User replier = userDao.getOrCreateUser(userUid);

        final List<ConsentSummary> consents = new ArrayList<ConsentSummary>();
        for (final ConsentReply reply : consentReplyDao.getRepliedConsents(replier, startNum, maxNum - startNum + 1)) {
            final ConsentSummary consentSummary = new ConsentSummary();
            fillSummaryByReply(userUid, reply, consentSummary);

            consents.add(consentSummary);
        }
        return consents;
    }

    private void fillSummaryByReply(final String userUid,
            final ConsentReply reply, final ConsentSummary consentSummary) {
        final Consent consent = reply.getConsent();
        fillShortSummaryByConsent(userUid, consent, consentSummary);
        consentSummary.setReceipients(convertUsersToUids(consent.getReceipients()));
        consentSummary.setApprovalStatus(ConsentApprovalStatus.valueOf(reply.getStatus()));
        consentSummary.setGivenAt(CalendarUtil.getXmlDate(reply.getCreatedDate()));
        consentSummary.setValidTill(CalendarUtil.getXmlDate(reply.getValidTill()));
    }

    private List<String> convertUsersToUids(final Set<User> users) {
        final List<String> receipients = new ArrayList<String>();
        for (final User user : users) {
            receipients.add(user.getUid());
        }
        return receipients;
    }

    /**
     * @param consentId
     * @param user
     * @param newDate
     * @param comment
     */
    @Override
    public void updateConsent(Long consentId, String user, XMLGregorianCalendar newDate, String comment) {
        final Consent consent = loadConsent(consentId);
        
        final ConsentReply reply = consentReplyDao.getReplyByConsentAndUser(consent, userDao.getOrCreateUser(user));
        
        if (reply == null) {
            throw new IllegalStateException("Consent id " + consentId + " is not yet approved or declined by user " + user );
        }
        
        reply.setValidTill(newDate == null ? null : newDate.toGregorianCalendar().getTime());
        reply.setComment(comment);
        
        consentReplyDao.update(reply);
    }

    /**
     * @param consentId
     * @param user
     * @return
     */
    @Override
    public ConsentTO getConsentById(Long consentId, String user) {
        final Consent consent = loadConsent(consentId);
        
        final ConsentReply reply = consentReplyDao.getReplyByConsentAndUser(consent, userDao.getOrCreateUser(user));
        
        final ConsentTO consentTO = new ConsentTO();
        fillSummaryByReply(user, reply, consentTO);
        
        final Set<Integer> actionsApproved = new HashSet<Integer>();
        for (final ConsentActionReply actionReply : reply.getActions()) {
            if (actionReply.isPermitted()) {
                actionsApproved.add(actionReply.getActionRequestNumber());
            }
        }
        
        final List<ActionRequestSummary> actionRequests = new ArrayList<ActionRequestSummary>();
        for (final ConsentActionRequest actionRequest : consent.getTemplate().getActions()) {
            final ActionRequestSummary actionRequestSummary = new ActionRequestSummary();
            actionRequestSummary.setName(actionRequest.getName());
            actionRequestSummary.setDescription(actionRequest.getDescription());
            if (actionsApproved.contains(actionRequest.getNumber())) {
                actionRequestSummary.setStatus(ActionRequestStatus.Given);
            } else {
                actionRequestSummary.setStatus(ActionRequestStatus.Declined);
            }
            actionRequests.add(actionRequestSummary);
        }
        consentTO.setActionRequests(actionRequests);
        
        return consentTO;
    }

    /**
     * @param consentId
     * @param user
     * @param comment
     */
    @Override
    public void revokeConsent(Long consentId, String user, String comment) {
        final Consent consent = loadConsent(consentId);
        
        final ConsentReply reply = consentReplyDao.getReplyByConsentAndUser(consent, userDao.getOrCreateUser(user));
        
        reply.setStatus(ConsentReplyStatus.Revoked);
        
        consentReplyDao.update(reply);
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalAssignedConsents(String userUid) {
        return getIntValue(consentDao.getTotalAssignedConsents(userDao.getOrCreateUser(userUid)));
    }

    private int getIntValue(final Long value) {
        return value != null ? value.intValue() : 0;
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalOwnConsents(String userUid) {
        return getIntValue(consentReplyDao.getTotalRepliedConsents(userDao.getOrCreateUser(userUid)));
    }

    /**
     * @param consentId
     * @return
     */
    @Override
    public ConsentTO getCombinedConsentById(Long consentId) {
        final Consent consent = loadConsent(consentId);
        
        final List<ConsentReply> replies = consentReplyDao.getReplies(consent);
        final ConsentTO consentTO = new ConsentTO();
        fillConsentSummaryCombined(consent, replies, consentTO);
        
        consentTO.setActionRequests(getActionResponsesCombined(consent, replies));
        
        return consentTO;
    }

    private void fillConsentSummaryCombined(final Consent consent, final List<ConsentReply> replies, final ConsentSummary consentTO) {
        fillShortSummaryByConsent(null, consent, consentTO);
        consentTO.setReceipients(convertUsersToUids(consent.getReceipients()));

        // default values
        consentTO.setStatus(ConsentStatus.Open);
        
        // calculated values
        for (final ConsentReply reply : replies) {
            updateStatusByReply(consentTO, reply);
            final XMLGregorianCalendar replyDate = CalendarUtil.getXmlDate(reply.getCreatedDate());
            if (consentTO.getGivenAt() == null || consentTO.getGivenAt().compare(replyDate) < 0) {
                consentTO.setGivenAt(replyDate);
            }
            final XMLGregorianCalendar validTill = CalendarUtil.getXmlDate(reply.getValidTill());
            if (consentTO.getValidTill() != null && validTill != null && consentTO.getValidTill().compare(validTill) < 0) {
                consentTO.setValidTill(validTill);
            }
        }

        if (consent.getReceipients().size() > replies.size() && consentTO.getStatus() == ConsentStatus.Valid) {
            consentTO.setStatus(ConsentStatus.PartiallyGiven);
        }
    }

    private List<ActionRequestSummary> getActionResponsesCombined(
            final Consent consent, final List<ConsentReply> replies) {
        final Map<Integer, ConsentActionRequest> numberToActionMap = consent.getTemplate().getNumberToActionMap();
                final Map<Integer, ActionRequestSummary> actionResponses = new HashMap<Integer, ActionRequestSummary>();
                for (final ConsentReply reply : replies) {
                    for (final ConsentActionReply actionReply : reply.getActions()) {
                        if (!actionResponses.containsKey(actionReply.getActionRequestNumber())) {
                            final ActionRequestSummary actionRequestSummary = new ActionRequestSummary();
                            final ConsentActionRequest consentActionRequest = numberToActionMap.get(actionReply.getActionRequestNumber());
                            actionRequestSummary.setName(consentActionRequest.getName());
                            actionRequestSummary.setDescription(consentActionRequest.getDescription());
                            actionRequestSummary.setStatus(ActionRequestStatus.Given);
                            actionResponses.put(actionReply.getActionRequestNumber(), actionRequestSummary);
                        }
                        final ActionRequestSummary actionResponse = actionResponses.get(actionReply.getActionRequestNumber());
                        if (!actionReply.isPermitted()) {
                            actionResponse.setStatus(ActionRequestStatus.Declined);
                        }
                    }
                }
                for (final Integer actionRequestedNumber : numberToActionMap.keySet()) {
                    if (!actionResponses.containsKey(actionRequestedNumber)) {
                        final ActionRequestSummary actionRequestSummary = new ActionRequestSummary();
                        final ConsentActionRequest consentActionRequest = numberToActionMap.get(actionRequestedNumber);
                        actionRequestSummary.setName(consentActionRequest.getName());
                        actionRequestSummary.setDescription(consentActionRequest.getDescription());
                        actionRequestSummary.setStatus(ActionRequestStatus.Declined);
                        actionResponses.put(actionRequestedNumber, actionRequestSummary);
                    }
                }
        return new ArrayList<ActionRequestSummary>(actionResponses.values());
    }

    private void updateStatusByReply(final ConsentSummary consentTO,
            final ConsentReply reply) {
        if (reply.getStatus() == ConsentReplyStatus.Declined) {
            consentTO.setStatus(ConsentStatus.Declined);
        } else if (reply.getStatus() == ConsentReplyStatus.Revoked 
                && consentTO.getStatus() != ConsentStatus.Declined ) {
            consentTO.setStatus(ConsentStatus.Revoked);
        } else if (reply.getStatus() == ConsentReplyStatus.Given
                && consentTO.getStatus() != ConsentStatus.Declined 
                && consentTO.getStatus() != ConsentStatus.Revoked) {
            if (reply.getValidTill() != null && CalendarUtil.getXmlDate(reply.getValidTill()).compare(CalendarUtil.getXmlDate(new Date())) < 0) {
                consentTO.setStatus(ConsentStatus.Expired);
            } else {
                consentTO.setStatus(ConsentStatus.Valid);
            }
        } else {
            // no need for update
        }
    }

    /**
     * @param employeeUid
     * @return
     */
    @Override
    public List<ConsentSummary> getProcessedConsents(String employeeUid, final int startNum, final int maxNum) {
        final User replier = userDao.getOrCreateUser(employeeUid);

        final List<ConsentSummary> consents = new ArrayList<ConsentSummary>();
        for (final Consent consent : consentDao.getProcessedConsents(replier, startNum, maxNum - startNum + 1)) {
            final ConsentSummary consentSummary = new ConsentSummary();
            fillConsentSummaryCombined(consent, consentReplyDao.getReplies(consent), consentSummary);

            consents.add(consentSummary);
        }
        return consents;
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalProcessedConsents(String userUid) {
        return getIntValue(consentDao.getTotalProcessedConsents(userDao.getOrCreateUser(userUid)));
    }
}
