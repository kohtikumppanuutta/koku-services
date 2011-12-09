package fi.arcusys.koku.tiva.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.AuthorizationTemplateDAO;
import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.ConsentDAO;
import fi.arcusys.koku.common.service.ConsentReplyDAO;
import fi.arcusys.koku.common.service.ConsentTemplateDAO;
import fi.arcusys.koku.common.service.KokuSystemNotificationsService;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.AuthorizationTemplate;
import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentActionReply;
import fi.arcusys.koku.common.service.datamodel.ConsentActionRequest;
import fi.arcusys.koku.common.service.datamodel.ConsentGivenTo;
import fi.arcusys.koku.common.service.datamodel.ConsentReply;
import fi.arcusys.koku.common.service.datamodel.ConsentReplyStatus;
import fi.arcusys.koku.common.service.datamodel.ConsentTemplate;
import fi.arcusys.koku.common.service.datamodel.ConsentType;
import fi.arcusys.koku.common.service.datamodel.ReceipientsType;
import fi.arcusys.koku.common.service.datamodel.SourceInfo;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.dto.ConsentDTOCriteria;
import fi.arcusys.koku.tiva.service.ConsentServiceFacade;
import fi.arcusys.koku.tiva.soa.ActionPermittedTO;
import fi.arcusys.koku.tiva.soa.ActionRequestStatus;
import fi.arcusys.koku.tiva.soa.ActionRequestSummary;
import fi.arcusys.koku.tiva.soa.ActionRequestTO;
import fi.arcusys.koku.tiva.soa.AuthorizationTemplateTO;
import fi.arcusys.koku.tiva.soa.ConsentApprovalStatus;
import fi.arcusys.koku.tiva.soa.ConsentCreateType;
import fi.arcusys.koku.tiva.soa.ConsentCriteria;
import fi.arcusys.koku.tiva.soa.ConsentExternalGivenTo;
import fi.arcusys.koku.tiva.soa.ConsentForReplyTO;
import fi.arcusys.koku.tiva.soa.ConsentKksExtraInfo;
import fi.arcusys.koku.tiva.soa.ConsentQuery;
import fi.arcusys.koku.tiva.soa.ConsentReceipientsType;
import fi.arcusys.koku.tiva.soa.ConsentSearchCriteria;
import fi.arcusys.koku.tiva.soa.ConsentShortSummary;
import fi.arcusys.koku.tiva.soa.ConsentSourceInfo;
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
    
    private static final String NEW_CONSENT_REQUEST_BODY = "new_consent.request.body";
    private static final String NEW_CONSENT_REQUEST_SUBJECT = "new_consent.request.subject";

    private static final String CONSENT_GIVEN_BODY = "consent.given.body";
    private static final String CONSENT_GIVEN_SUBJECT = "consent.given.subject";

    private static final String CONSENT_DECLINED_BODY = "consent.declined.body";
    private static final String CONSENT_DECLINED_SUBJECT = "consent.declined.subject";

    private static final String CONSENT_REVOKED_BODY = "consent.revoked.body";
    private static final String CONSENT_REVOKED_SUBJECT = "consent.revoked.subject";

    private static final String CONSENT_UPDATED_BODY = "consent.updated.body";
    private static final String CONSENT_UPDATED_SUBJECT = "consent.updated.subject";

    private static final Logger logger = LoggerFactory.getLogger(ConsentServiceFacadeImpl.class); 

    @EJB
    private ConsentTemplateDAO templateDao;
    
    @EJB
    private AuthorizationTemplateDAO authorizationTemplateDao;

    @EJB
    private ConsentDAO consentDao;

    @EJB
    private ConsentReplyDAO consentReplyDao;

    @EJB
    private UserDAO userDao;

    @EJB
    private KokuSystemNotificationsService notificationService;

    private String notificationsBundleName = "consent.msg";
    private Properties messageTemplates;
    
    @PostConstruct
    public void init() {
        messageTemplates = new Properties();
        try {
            final InputStream in = getClass().getClassLoader().getResourceAsStream(notificationsBundleName + ".properties");
            try {
                messageTemplates.load(in);
            } finally {
                in.close();
            }
        } catch (IOException e) {
            throw new EJBException("Incorrect configuration, failed to load message templates:", e);
        }
    } 

    private String getValueFromBundle(final String key) {
        return messageTemplates.getProperty(key);
    }

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
        templateTO.setCreatorUid(template.getCreator().getUid());
        templateTO.setDescription(template.getDescription() == null ? "" : template.getDescription());
        templateTO.setTitle(template.getTitle());
        final AuthorizationTemplate authorizationTemplate = template.getAuthorizationTemplate();
        if (authorizationTemplate != null) {
            final AuthorizationTemplateTO templateType = new AuthorizationTemplateTO();
            templateType.setTemplateId(authorizationTemplate.getId());
            templateType.setTemplateName(authorizationTemplate.getName());
            templateType.setDescription(authorizationTemplate.getDescription());
            templateTO.setTemplateType(templateType);
        }
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

    private String getDisplayName(final User user) {
        if (user == null) {
            return "";
        }
        if (user.getCitizenPortalName() != null && !user.getCitizenPortalName().isEmpty()) {
            return user.getCitizenPortalName();
        } else {
            return user.getEmployeePortalName();
        }
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
        final AuthorizationTemplateTO templateType = templateTO.getTemplateType();
        if (templateType != null && templateType.getTemplateId() != 0) {
            template.setAuthorizationTemplate(authorizationTemplateDao.getById(templateType.getTemplateId()));
        }
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

    private Date getSafeDate(final XMLGregorianCalendar endDate) {
        if (endDate == null) {
            return null;
        } else {
            return endDate.toGregorianCalendar().getTime();
        }
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
    public Long requestForConsent(final Long templateId, final String senderUid, final String targetPersonUid, 
            final List<String> receipientUids, ConsentReceipientsType type, XMLGregorianCalendar replyTillDate, 
            XMLGregorianCalendar endDate, Boolean isMandatory, final ConsentKksExtraInfo extraInfo) {

        final Consent newConsent = doConsentCreation(templateId, ConsentType.Electronic,
                senderUid, targetPersonUid, receipientUids, type, replyTillDate, endDate, isMandatory, null, extraInfo);
        notificationService.sendNotification(getValueFromBundle(NEW_CONSENT_REQUEST_SUBJECT), receipientUids, 
                MessageFormat.format(getValueFromBundle(NEW_CONSENT_REQUEST_BODY), new Object[] {newConsent.getTemplate().getTitle()}));
        return newConsent.getId();
    }

    private Consent doConsentCreation(final Long templateId,
            ConsentType consentType, final String senderUid,
            final String targetPersonUid, final List<String> receipientUids,
            ConsentReceipientsType type, XMLGregorianCalendar replyTillDate, XMLGregorianCalendar endDate, Boolean isMandatory, ConsentSourceInfo sourceInfo, ConsentKksExtraInfo extraInfo) {
        if (receipientUids == null || receipientUids.isEmpty()) {
            throw new IllegalArgumentException("Consent is requested from empty list of receipients.");
        } 
        
        final ConsentTemplate template = templateDao.getById(templateId);
        if (template == null) {
            throw new IllegalArgumentException("Consent template with ID " + templateId + " is not found");
        }
        
        final Consent consent = new Consent();
        consent.setCreationType(consentType);
        consent.setCreator(userDao.getOrCreateUser(senderUid));
        consent.setTargetPerson(userDao.getOrCreateUser(targetPersonUid));
        consent.setTemplate(template);
        consent.setValidTill(getSafeDate(endDate));
        consent.setReplyTill(getSafeDate(replyTillDate));
        consent.setEndDateMandatory(isMandatory);
        consent.setSourceInfo(convertSourceInfoToDm(sourceInfo));
        
        if (extraInfo != null) {
            consent.setInformationTargetId(extraInfo.getInformationTargetId());
            consent.setMetaInfo(extraInfo.getMetaInfo());
            final Set<ConsentGivenTo> givenTo = new HashSet<ConsentGivenTo>();
            for (final ConsentExternalGivenTo party : extraInfo.getGivenTo()) {
                final ConsentGivenTo consentGivenTo = new ConsentGivenTo();
                consentGivenTo.setConsent(consent);
                consentGivenTo.setPartyId(party.getPartyId());
                consentGivenTo.setPartyName(party.getPartyName());
                givenTo.add(consentGivenTo);
            }
            consent.setGivenTo(givenTo);
        }
        
        final Set<User> receipients = new HashSet<User>();
        for (final String userUid : receipientUids) {
            receipients.add(userDao.getOrCreateUser(userUid));
        }
        consent.setReceipients(receipients);
        consent.setReceipientsType(ConsentReceipientsType.toDmType(type == null ? ConsentReceipientsType.BothParents : type));
        return consentDao.create(consent);
    }

    protected SourceInfo convertSourceInfoToDm(ConsentSourceInfo sourceInfo) {
        if (sourceInfo == null) {
            return null;
        }
        final SourceInfo info = new SourceInfo();
        info.setAdditionalInfo(sourceInfo.getAdditionalInfo());
        info.setAttachmentUrl(sourceInfo.getAttachmentUrl());
        info.setRepository(sourceInfo.getRepository());
        return info;
    }

    /**
     * @param consentId
     * @return
     */
    @Override
    public ConsentForReplyTO getConsentForReply(final Long consentId, final String replierUid) {
        final Consent consent = loadConsent(consentId);
        final User replier = userDao.getOrCreateUser(replierUid);
        if (!consent.getReceipients().contains(replier)) {
            throw new IllegalStateException("Replier " + replierUid + " is absent in receipients list for consent " + consentId);
        }
        
        final ConsentForReplyTO consentTO = new ConsentForReplyTO();
        consentTO.setConsentId(consent.getId());
        consentTO.setReplierUid(replierUid);
        consentTO.setTargetPersonUid(consent.getTargetPerson().getUid());
        consentTO.setTemplate(convertConsentTemplateToDTO(consent.getTemplate()));
        consentTO.setEndDateMandatory(consent.getEndDateMandatory());
        
        consentTO.setInformationTarget(consent.getInformationTargetId());
        consentTO.setGivenToParties(getGivenToParties(consent));

        final ConsentReply reply = consentReplyDao.getReplyByConsentAndUser(consent, replier);
        if (reply != null) {
            consentTO.setReplyComment(reply.getComment());
            consentTO.setEndDate(CalendarUtil.getXmlDate(reply.getValidTill()));
            consentTO.setAlreadyReplied(true);
            final Set<Integer> approvedActions = new HashSet<Integer>();
            for (final ConsentActionReply action : reply.getActions()) {
                if (action.isPermitted()) {
                    approvedActions.add(action.getActionRequestNumber());
                }
            }
            final List<ActionPermittedTO> actionReplies = new ArrayList<ActionPermittedTO>();
            for (final ActionRequestTO action : consentTO.getTemplate().getActions()) {
                final ActionPermittedTO actionPermittedTO = new ActionPermittedTO();
                actionPermittedTO.setActionRequestNumber(action.getNumber());
                actionPermittedTO.setPermitted(approvedActions.contains(action.getNumber()));
                actionReplies.add(actionPermittedTO);
            }
            consentTO.setActionReplies(actionReplies);
        } else {
            consentTO.setEndDate(CalendarUtil.getXmlDate(consent.getValidTill()));
            consentTO.setAlreadyReplied(false);
            consentTO.setReplyTillDate(CalendarUtil.getXmlDate(consent.getReplyTill()));
        }
        
        return consentTO;
    }

    protected List<ConsentExternalGivenTo> getGivenToParties(final Consent consent) {
        final List<ConsentExternalGivenTo> givenToParties = new ArrayList<ConsentExternalGivenTo>();
        for (final ConsentGivenTo givenTo : consent.getGivenTo()) {
            final ConsentExternalGivenTo consentExternalGivenTo = new ConsentExternalGivenTo();
            consentExternalGivenTo.setPartyId(givenTo.getPartyId()); 
            consentExternalGivenTo.setPartyName(givenTo.getPartyName());
            givenToParties.add(consentExternalGivenTo);
        }
        return givenToParties;
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
        consentTO.setRequestor(getDisplayName(consent.getCreator()));
        consentTO.setTargetPersonUid(getDisplayName(consent.getTargetPerson()));
        consentTO.setTemplateId(consent.getTemplate().getId());
        consentTO.setTemplateName(consent.getTemplate().getTitle());
        consentTO.setTemplateDescription(consent.getTemplate().getDescription());
        final AuthorizationTemplate templateType = consent.getTemplate().getAuthorizationTemplate();
        if (templateType != null) {
            consentTO.setTemplateTypeId(templateType.getId());
            consentTO.setTemplateTypeName(templateType.getName());
        }
        consentTO.setAnotherPermitterUid(getAnotherUserName(userUid, consent.getReceipients()));
        consentTO.setCreateType(ConsentCreateType.valueOf(consent.getCreationType()));
        consentTO.setReplyTill(CalendarUtil.getXmlDate(consent.getReplyTill()));
        
        consentTO.setInformationTargetId(consent.getInformationTargetId());
        consentTO.setMetaInfo(consent.getMetaInfo());
        consentTO.setGivenToParties(getGivenToParties(consent));
    }

    private String getAnotherUserName(final String userUid, final Set<User> users) {
        for (final User user : users) {
            if (!getSafeUserUid(user).equals(userUid)) {
                return getDisplayName(user);
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
        
        doGiveConsent(actions, validTill, null, comment, consent, replier);
        notificationService.sendNotification(getValueFromBundle(CONSENT_GIVEN_SUBJECT), Collections.singletonList(consent.getCreator().getUid()), 
                MessageFormat.format(getValueFromBundle(CONSENT_GIVEN_BODY), new Object[] {consent.getTemplate().getTitle(),
                    consent.getTargetPerson().getCitizenPortalName(), replier.getCitizenPortalName()}));
    }

    private void doGiveConsent(List<ActionPermittedTO> actions,
            XMLGregorianCalendar validTill, XMLGregorianCalendar givenDate, String comment,
            final Consent consent, final User replier) {
        final ConsentReply oldReply = consentReplyDao.getReplyByConsentAndUser(consent, replier);
        if (oldReply != null) {
            logger.warn("'giveConsent' used for update of existing consent. Use 'updateConsent' instead.");
            updateConsent(validTill, comment, oldReply);
            return;
        }
        
        final ConsentReply reply = new ConsentReply();
        reply.setConsent(consent);
        reply.setReplier(replier); 
        reply.setCreatedDate(CalendarUtil.getSafeDate(givenDate));
                
        reply.setComment(comment);
        reply.setStatus(ConsentReplyStatus.Given);
        reply.setValidTill(getSafeDate(validTill));
        
        final Map<Integer, ConsentActionRequest> actionRequests = consent.getTemplate().getNumberToActionMap();
        
        final Set<ConsentActionReply> actionReplies = new HashSet<ConsentActionReply>();
        for (final ActionPermittedTO actionResponse : actions) {
            if (!actionRequests.containsKey(actionResponse.getActionRequestNumber())) {
                throw new IllegalArgumentException("Incorrect action number " + actionResponse.getActionRequestNumber() + " in response for consent ID " + consent.getId());
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
        
        final ConsentReply oldReply = consentReplyDao.getReplyByConsentAndUser(consent, replier);
        if (oldReply != null) {
            oldReply.setComment(comment);
            oldReply.setStatus(ConsentReplyStatus.Declined);

            consentReplyDao.update(oldReply);
            notificationService.sendNotification(getValueFromBundle(CONSENT_DECLINED_SUBJECT), Collections.singletonList(consent.getCreator().getUid()), 
                    MessageFormat.format(getValueFromBundle(CONSENT_DECLINED_BODY), new Object[] {consent.getTemplate().getTitle(),
                        consent.getTargetPerson().getCitizenPortalName(), replier.getCitizenPortalName()}));
        } else {
            final ConsentReply reply = new ConsentReply();
            reply.setConsent(consent);
            reply.setReplier(replier);
            reply.setComment(comment);
            reply.setStatus(ConsentReplyStatus.Declined);

            consentReplyDao.create(reply);
            notificationService.sendNotification(getValueFromBundle(CONSENT_DECLINED_SUBJECT), Collections.singletonList(consent.getCreator().getUid()), 
                    MessageFormat.format(getValueFromBundle(CONSENT_DECLINED_BODY), new Object[] {consent.getTemplate().getTitle(),
                        consent.getTargetPerson().getCitizenPortalName(), replier.getCitizenPortalName()}));
        }
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
        return convertRepliesToConsentSummary(replier, consentReplyDao.getRepliedConsents(replier, startNum, maxNum - startNum + 1));
    }

    protected List<ConsentSummary> convertRepliesToConsentSummary(
            final User replier, final List<ConsentReply> repliedConsents) {
        final List<ConsentSummary> consents = new ArrayList<ConsentSummary>();
        for (final ConsentReply reply : repliedConsents) {
            final ConsentSummary consentSummary = new ConsentSummary();
            fillSummaryByReply(replier.getUid(), reply, consentSummary);

            consents.add(consentSummary);
        }
        return consents;
    }

    private void fillSummaryByReply(final String userUid,
            final ConsentReply reply, final ConsentSummary consentSummary) {
        final Consent consent = reply.getConsent();
        fillShortSummaryByConsent(userUid, consent, consentSummary);
        consentSummary.setReceipients(convertUsersToNames(consent.getReceipients()));
        consentSummary.setApprovalStatus(ConsentApprovalStatus.valueOf(reply.getStatus()));
        consentSummary.setGivenAt(CalendarUtil.getXmlDate(reply.getCreatedDate()));
        consentSummary.setValidTill(CalendarUtil.getXmlDate(reply.getValidTill()));
        updateStatusByReply(consentSummary, reply);
        if (consentSummary.getStatus() == null) {
            consentSummary.setStatus(ConsentStatus.Open);
        }
    }

    private List<String> convertUsersToNames(final Set<User> users) {
        final List<String> receipients = new ArrayList<String>();
        for (final User user : users) {
            receipients.add(getDisplayName(user));
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
        
        updateConsent(newDate, comment, reply);
    }

    private void updateConsent(XMLGregorianCalendar newDate, String comment, final ConsentReply reply) {
        reply.setValidTill(newDate == null ? null : getSafeDate(newDate));
        reply.setComment(comment);
        
        consentReplyDao.update(reply);
        notificationService.sendNotification(getValueFromBundle(CONSENT_UPDATED_SUBJECT), Collections.singletonList(reply.getConsent().getCreator().getUid()), 
                MessageFormat.format(getValueFromBundle(CONSENT_UPDATED_BODY), new Object[] {reply.getConsent().getTemplate().getTitle(),
                    reply.getConsent().getTargetPerson().getCitizenPortalName(), reply.getReplier().getCitizenPortalName()}));
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
        consentTO.setComment(reply.getComment());
        
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
        notificationService.sendNotification(getValueFromBundle(CONSENT_REVOKED_SUBJECT), Collections.singletonList(consent.getCreator().getUid()), 
                MessageFormat.format(getValueFromBundle(CONSENT_REVOKED_BODY), new Object[] {consent.getTemplate().getTitle(),
                    consent.getTargetPerson().getCitizenPortalName(), reply.getReplier().getCitizenPortalName()}));
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
        
        return convertConsentToConsentTO(consent);
    }

    private ConsentTO convertConsentToConsentTO(final Consent consent) {
        final List<ConsentReply> replies = consentReplyDao.getReplies(consent);
        final ConsentTO consentTO = new ConsentTO();
        fillConsentSummaryCombined(consent, replies, consentTO);
        
        consentTO.setActionRequests(getActionResponsesCombined(consent, replies));
        consentTO.setComment(getCommentsCombined(replies));
        
        return consentTO;
    }

    private String getCommentsCombined(final List<ConsentReply> replies) {
        final StringBuilder result = new StringBuilder();
        for (final ConsentReply reply : replies) {
            if (reply.getComment() != null && !reply.getComment().isEmpty()) {
                result.append(reply.getComment()).append(';');
            }
        }
        if (result.length() > 0) {
            result.setLength(result.length() - 1);
        }
        return result.toString();
    }

    private void fillConsentSummaryCombined(final Consent consent, final List<ConsentReply> replies, final ConsentSummary consentTO) {
        fillShortSummaryByConsent(null, consent, consentTO);
        consentTO.setReceipients(convertUsersToNames(consent.getReceipients()));

        // default values
        consentTO.setApprovalStatus(ConsentApprovalStatus.Approved);
        consentTO.setStatus(ConsentStatus.Open);
        consentTO.setValidTill(CalendarUtil.getXmlDate(consent.getValidTill()));
        consentTO.setReplyTill(CalendarUtil.getXmlDate(consent.getReplyTill()));
        
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

        if (consent.getReceipients().size() > replies.size() && consentTO.getStatus() == ConsentStatus.Valid && consent.getReceipientsType() == ReceipientsType.BothParents) {
            consentTO.setStatus(ConsentStatus.PartiallyGiven);
        }
        
        if (consentTO.getStatus() == ConsentStatus.Declined) {
            consentTO.setApprovalStatus(ConsentApprovalStatus.Declined);
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
        } // otherwise no need for update
    }

    /**
     * @param employeeUid
     * @return
     */
    @Override
    public List<ConsentSummary> getProcessedConsents(String employeeUid, final ConsentQuery query) {
        final User replier = userDao.getOrCreateUser(employeeUid);

        final List<ConsentSummary> consents = new ArrayList<ConsentSummary>();
        for (final Consent consent : consentDao.getProcessedConsents(replier,
                query.getCriteria() != null ? query.getCriteria().toDtoCriteria() : new ConsentDTOCriteria(), 
                query.getStartNum(), query.getMaxNum() - query.getStartNum() + 1)) {
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
    public int getTotalProcessedConsents(String userUid, ConsentCriteria criteria) {
        return getIntValue(consentDao.getTotalProcessedConsents(userDao.getOrCreateUser(userUid), criteria != null ? criteria.toDtoCriteria() : new ConsentDTOCriteria()));
    }

    /**
     * @param templateId
     * @param employeeUid
     * @param consentType
     * @param targetPersonUid
     * @param receipientUids
     * @param endDate
     * @return
     */
    @Override
    public Long writeConsentOnBehalf(Long templateId, String employeeUid,
            String consentType, String targetPersonUid,
            List<String> receipientUids, XMLGregorianCalendar endDate, XMLGregorianCalendar givenDate,
            List<ActionPermittedTO> actions, ConsentSourceInfo sourceInfo, final String comment) {
        final Consent consent = doConsentCreation(templateId, ConsentType.PaperBased, employeeUid, 
                targetPersonUid, receipientUids, ConsentReceipientsType.BothParents, 
                null, endDate, Boolean.TRUE, sourceInfo, null);
        for (final User receipient : consent.getReceipients() ) {
            doGiveConsent(actions, endDate, givenDate, comment, consent, receipient);
        }
        return consent.getId();
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalOldConsents(String userUid) {
        return getIntValue(consentReplyDao.getTotalOldRepliedConsents(userDao.getOrCreateUser(userUid)));
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ConsentSummary> getOldConsents(String userUid, int startNum,
            int maxNum) {
        final User replier = userDao.getOrCreateUser(userUid);
        return convertRepliesToConsentSummary(replier, consentReplyDao.getOldRepliedConsents(replier, startNum, maxNum - startNum + 1));
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<ConsentTO> searchConsents(ConsentSearchCriteria criteria) {
        if (criteria == null) {
            throw new IllegalArgumentException("Can't search consents without criteria specified.");
        }
        if (criteria.getTargetPerson() == null || criteria.getTargetPerson().equals("")) {
            throw new IllegalArgumentException("Can't search consents without target person specified.");
        }
        
        // currently it was agreed to ignore statuses filter - only Valid consents are retreived
        final Set<ConsentStatus> statuses = new HashSet<ConsentStatus>();
        statuses.add(ConsentStatus.Valid);
                
        final List<ConsentTO> result = new ArrayList<ConsentTO>();
        for (final Consent consent : consentDao.searchConsents(criteria.toDtoCriteria())) {
            final ConsentTO consentTO = convertConsentToConsentTO(consent);
            if (statuses.contains(consentTO.getStatus())) {
                result.add(consentTO);
            }
        }
        return result;
    }
}
