package fi.arcusys.koku.tiva.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.AuthorizationDAO;
import fi.arcusys.koku.common.service.AuthorizationTemplateDAO;
import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.KokuSystemNotificationsService;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.Authorization;
import fi.arcusys.koku.common.service.datamodel.AuthorizationReplyStatus;
import fi.arcusys.koku.common.service.datamodel.AuthorizationTemplate;
import fi.arcusys.koku.common.service.datamodel.AuthorizationType;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.dto.AuthorizationDTOCriteria;
import fi.arcusys.koku.tiva.service.AuthorizationServiceFacade;
import fi.arcusys.koku.tiva.soa.AuthorizationCreateType;
import fi.arcusys.koku.tiva.soa.AuthorizationCriteria;
import fi.arcusys.koku.tiva.soa.AuthorizationDetailTO;
import fi.arcusys.koku.tiva.soa.AuthorizationQuery;
import fi.arcusys.koku.tiva.soa.AuthorizationShortSummary;
import fi.arcusys.koku.tiva.soa.AuthorizationStatus;
import fi.arcusys.koku.tiva.soa.AuthorizationSummary;
import fi.arcusys.koku.tiva.soa.AuthorizationTemplateTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 13, 2011
 */
@Stateless
public class AuthorizationServiceFacadeImpl implements AuthorizationServiceFacade {

    private final static String AUTHORIZATION_CREATED_SUBJECT = "authorization.created.subject";
    private final static String AUTHORIZATION_CREATED_BODY = "authorization.created.body";
    
    private final static String AUTHORIZATION_APPROVED_SUBJECT = "authorization.approved.subject";
    private final static String AUTHORIZATION_APPROVED_BODY = "authorization.approved.body";

    private final static String AUTHORIZATION_DECLINED_SUBJECT = "authorization.declinded.subject";
    private final static String AUTHORIZATION_DECLINED_BODY = "authorization.declinded.body";

    private final static String AUTHORIZATION_UPDATED_SUBJECT = "authorization.updated.subject";
    private final static String AUTHORIZATION_UPDATED_BODY = "authorization.updated.body";

    private final static String AUTHORIZATION_REVOKED_SUBJECT = "authorization.revoked.subject";
    private final static String AUTHORIZATION_REVOKED_BODY = "authorization.revoked.body";

    
    private final static Logger logger = LoggerFactory.getLogger(AuthorizationServiceFacadeImpl.class);

    @EJB
    private AuthorizationTemplateDAO templateDAO;

    @EJB
    private AuthorizationDAO authorizationDAO;
    
    @EJB
    private UserDAO userDAO;
    
    @EJB
    private KokuSystemNotificationsService notificationService;

    private String notificationsBundleName = "authorization.msg";
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

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<AuthorizationTemplateTO> getAuthorizationTemplates(String searchString, int limit) {
        final List<AuthorizationTemplateTO> result = new ArrayList<AuthorizationTemplateTO>();
        for (final AuthorizationTemplate template : templateDAO.searchAuthorizationTemplates(searchString, limit)) {
            result.add(getTemplateTObyDM(template));
        }
        return result;
    }

    private AuthorizationTemplateTO getTemplateTObyDM(
            final AuthorizationTemplate template) {
        final AuthorizationTemplateTO templateTO = new AuthorizationTemplateTO();
        templateTO.setTemplateId(template.getId());
        templateTO.setTemplateName(template.getName());
        templateTO.setDescription(template.getDescription());
        templateTO.setConsentsOnly(template.isConsentsOnly());
        templateTO.setToSecondGuardianOnly(template.isToSecondGuardianOnly());
        templateTO.setValidTillMandatory(template.isValidTillMandatory());
        return templateTO;
    }

    /**
     * @param authorizationTemplateId
     * @param endDate
     * @param senderUid
     * @param receiverUid
     * @param targetPersonUid
     * @return
     */
    @Override
    public Long createAuthorization(long authorizationTemplateId, XMLGregorianCalendar endDate, String senderUid, String receiverUid, String targetPersonUid) {
        final AuthorizationTemplate template = templateDAO.getById(authorizationTemplateId);
        if (template == null) {
            throw new IllegalArgumentException("Authorization template is not found by id: " + authorizationTemplateId);
        }
        
        final Authorization authorization = new Authorization();
        authorization.setFromUser(userDAO.getOrCreateUser(senderUid));
        authorization.setToUser(userDAO.getOrCreateUser(receiverUid));
        authorization.setTargetPerson(userDAO.getOrCreateUser(targetPersonUid));
        authorization.setTemplate(template);
        authorization.setCreationType(AuthorizationType.Electronic);
        authorization.setValidTill(getSafeDate(endDate));
        
        final Authorization newAuthorization = authorizationDAO.create(authorization);
        notificationService.sendNotification(getValueFromBundle(AUTHORIZATION_CREATED_SUBJECT), 
                Collections.singletonList(receiverUid), 
                MessageFormat.format(getValueFromBundle(AUTHORIZATION_CREATED_BODY), 
                        new Object[] {}));
        return newAuthorization.getId();
    }

    private Date getSafeDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        }
        return calendar.toGregorianCalendar().getTime();
    }

    /**
     * @param authorizationId
     * @param userUid
     * @return
     */
    @Override
    public AuthorizationDetailTO getAuthorization(long authorizationId, String userUid) {
        final Authorization authorization = loadAuthorization(authorizationId);
        
        final AuthorizationDetailTO authorizationTO = new AuthorizationDetailTO();
        authorizationTO.setAuthorizationId(authorization.getId());
        authorizationTO.setCreateDate(CalendarUtil.getXmlDate(authorization.getCreatedDate()));
        authorizationTO.setReceiverUid(getDisplayName(authorization.getToUser()));
        authorizationTO.setSenderUid(getDisplayName(authorization.getFromUser()));
        authorizationTO.setTargetPersonUid(getDisplayName(authorization.getTargetPerson()));
        authorizationTO.setTemplate(getTemplateTObyDM(authorization.getTemplate()));
        authorizationTO.setValidTill(CalendarUtil.getXmlDate(authorization.getValidTill()));
        return authorizationTO;
    }

    private Authorization loadAuthorization(long authorizationId) {
        final Authorization authorization = authorizationDAO.getById(authorizationId);
        if (authorization == null) {
            throw new IllegalStateException("Authorization not found with ID: " + authorizationId);
        }
        return authorization;
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AuthorizationShortSummary> getReceivedAuthorizations(final String userUid, final int startNum, final int maxNum) {
        return getAuthorizationTOs(authorizationDAO.getReceivedAuthorizations(userDAO.getOrCreateUser(userUid), startNum, maxNum - startNum + 1));
    }

    private List<AuthorizationShortSummary> getAuthorizationTOs(
            final List<Authorization> receivedAuthorizations) {
        final List<AuthorizationShortSummary> result = new ArrayList<AuthorizationShortSummary>();
        
        for (final Authorization authorization : receivedAuthorizations) {
            result.add(fillShortSummaryByAuthorization(authorization, new AuthorizationShortSummary()));
        }
        
        return result;
    }

    private AuthorizationSummary getAuthorizationTOByDM(final Authorization authorization) {
        final AuthorizationSummary authorizationTO = new AuthorizationSummary();
        fillShortSummaryByAuthorization(authorization, authorizationTO);
        authorizationTO.setCreatedAt(CalendarUtil.getXmlDate(authorization.getCreatedDate()));
        authorizationTO.setGivenAt(CalendarUtil.getXmlDate(authorization.getGivenAt()));
        authorizationTO.setReplyTill(CalendarUtil.getXmlDate(authorization.getReplyTill()));
        authorizationTO.setType(AuthorizationCreateType.valueOf(authorization.getCreationType()));
        return authorizationTO;
    }

    private AuthorizationShortSummary fillShortSummaryByAuthorization(final Authorization authorization, final AuthorizationShortSummary authorizationTO) {
        authorizationTO.setAuthorizationId(authorization.getId());
        authorizationTO.setReceiverUid(authorization.getToUser().getUid());
        authorizationTO.setSenderUid(authorization.getFromUser().getUid());
        authorizationTO.setTargetPersonUid(authorization.getTargetPerson().getUid());
        authorizationTO.setTemplate(getTemplateTObyDM(authorization.getTemplate()));
        final XMLGregorianCalendar validTill = CalendarUtil.getXmlDate(authorization.getValidTill());
        authorizationTO.setValidTill(validTill);
        final AuthorizationStatus status; // Open, Valid, Expired, Revoked, Declined;
        if (authorization.getStatus() == AuthorizationReplyStatus.Declined) {
            status = AuthorizationStatus.Declined;
        } else if (authorization.getStatus() == AuthorizationReplyStatus.Revoked) {
            status = AuthorizationStatus.Revoked;
        } else if (authorization.getStatus() == AuthorizationReplyStatus.Approved) {
            if (validTill != null && CalendarUtil.getXmlDate(new Date()).compare(validTill) > 0) {
                status = AuthorizationStatus.Expired;
            } else {
                status = AuthorizationStatus.Valid;
            }
        } else {
            status = AuthorizationStatus.Open;
        } 
        authorizationTO.setStatus(status);
        return authorizationTO;
    }
    
    @Override
    public List<AuthorizationShortSummary> getSentAuthorizations(final String userUid, final int startNum, final int maxNum) {
        return getAuthorizationTOs(authorizationDAO.getSentAuthorizations(userDAO.getOrCreateUser(userUid), startNum, maxNum - startNum + 1));
    }

    /**
     * @param authorizationId
     * @param replierUid
     * @param comment
     */
    @Override
    public void approveAuthorization(long authorizationId, String replierUid, String comment) {
        final Authorization authorization = replyToAuthorization(authorizationId, replierUid, comment, AuthorizationReplyStatus.Approved);
        notificationService.sendNotification(getValueFromBundle(AUTHORIZATION_APPROVED_SUBJECT), 
                Collections.singletonList(authorization.getFromUser().getUid()), 
                MessageFormat.format(getValueFromBundle(AUTHORIZATION_APPROVED_BODY), 
                        new Object[] {authorization.getTemplate().getName()}));
    }

    private Authorization replyToAuthorization(long authorizationId, String replierUid,
            String comment, final AuthorizationReplyStatus replyStatus) {
        final Authorization authorization = loadAuthorization(authorizationId);

        if (!authorization.getToUser().getUid().equals(replierUid)) {
            throw new IllegalArgumentException("Incorrect user '" + replierUid + "' is trying to reply to authorization request ID : " + authorizationId);
        }
        
        if (authorization.getStatus() != null) {
            throw new IllegalStateException("Authorization ID " + authorizationId + " is already replied. Status can't be changed from " +
                    authorization.getStatus() + " to " + replyStatus );
        }
        
        authorization.setStatus(replyStatus);
        authorization.setReplyComment(comment);
        return authorizationDAO.update(authorization);
    }

    /**
     * @param authorizationId
     * @param replierUid
     * @param comment
     */
    @Override
    public void declineAuthorization(long authorizationId, String replierUid, String comment) {
        final Authorization authorization = replyToAuthorization(authorizationId, replierUid, comment, AuthorizationReplyStatus.Declined);
        notificationService.sendNotification(getValueFromBundle(AUTHORIZATION_DECLINED_SUBJECT), 
                Collections.singletonList(authorization.getFromUser().getUid()), 
                MessageFormat.format(getValueFromBundle(AUTHORIZATION_DECLINED_BODY), 
                        new Object[] {authorization.getTemplate().getName()}));
    }

    /**
     * @param authorizationId
     * @param senderUid
     * @param endDate
     * @param comment
     */
    @Override
    public void updateAuthorization(long authorizationId, String senderUid, XMLGregorianCalendar endDate, String comment) {
        final Authorization authorization = loadAuthorizationForUpdate(authorizationId, senderUid);
        
        authorization.setValidTill(getSafeDate(endDate));
        authorization.setComment(comment);
        authorizationDAO.update(authorization);
        
        notificationService.sendNotification(getValueFromBundle(AUTHORIZATION_UPDATED_SUBJECT), 
                Collections.singletonList(authorization.getToUser().getUid()), 
                MessageFormat.format(getValueFromBundle(AUTHORIZATION_UPDATED_BODY), 
                        new Object[] {authorization.getTemplate().getName()}));
    }

    private Authorization loadAuthorizationForUpdate(long authorizationId, String senderUid) {
        final Authorization authorization = loadAuthorization(authorizationId);
        
        if (!authorization.getFromUser().getUid().equals(senderUid)) {
            throw new IllegalArgumentException("User '" + senderUid + "' can't update authorization ID " + authorizationId + ". Only owner can update it.");
        }
        return authorization;
    }

    /**
     * @param authorizationId
     * @param senderUid
     * @param comment
     */
    @Override
    public void revokeAuthorization(long authorizationId, String senderUid, String comment) {
        final Authorization authorization = loadAuthorizationForUpdate(authorizationId, senderUid);
        
        authorization.setStatus(AuthorizationReplyStatus.Revoked);
        authorizationDAO.update(authorization);

        notificationService.sendNotification(getValueFromBundle(AUTHORIZATION_REVOKED_SUBJECT), 
                Collections.singletonList(authorization.getToUser().getUid()), 
                MessageFormat.format(getValueFromBundle(AUTHORIZATION_REVOKED_BODY), 
                        new Object[] {authorization.getTemplate().getName()}));
    }

    /**
     * @param authorizationId
     * @param userUid
     * @return
     */
    @Override
    public AuthorizationSummary getAuthorizationSummary(long authorizationId, String userUid) {
        return getAuthorizationTOByDM(loadAuthorization(authorizationId));
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalSentAuthorizations(String userUid) {
        return getIntValue(authorizationDAO.getTotalSentAuthorizations(userDAO.getOrCreateUser(userUid)));
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public int getTotalReceivedAuthorizations(String userUid) {
        return getIntValue(authorizationDAO.getTotalReceivedAuthorizations(userDAO.getOrCreateUser(userUid)));
    }
    
    private int getIntValue(final Long longValue) {
        if (longValue != null) {
            return longValue.intValue();
        }
        return 0;
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<AuthorizationShortSummary> getAuthorizationsByQuery(AuthorizationQuery query) {
        return getAuthorizationTOs(authorizationDAO.getAuthorizations(query.getCriteria() != null ? query.getCriteria().toDtoCriteria() : new AuthorizationDTOCriteria(), 
                query.getStartNum(), query.getMaxNum() - query.getStartNum() + 1));
    }

    /**
     * @param criteria
     * @return
     */
    @Override
    public int getTotalAuthorizationsByCriteria(AuthorizationCriteria criteria) {
        return getIntValue(authorizationDAO.getTotalAuthorizations(criteria != null ? criteria.toDtoCriteria() : new AuthorizationDTOCriteria()));
    }

}
