package fi.arcusys.koku.tiva.service.impl;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
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
import fi.arcusys.koku.common.service.datamodel.ConsentActionRequest;
import fi.arcusys.koku.common.service.datamodel.ConsentReply;
import fi.arcusys.koku.common.service.datamodel.ConsentTemplate;
import fi.arcusys.koku.common.service.datamodel.ConsentType;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.tiva.service.ConsentServiceFacade;
import fi.arcusys.koku.tiva.soa.ActionPermittedTO;
import fi.arcusys.koku.tiva.soa.ActionRequestTO;
import fi.arcusys.koku.tiva.soa.ConsentForReplyTO;
import fi.arcusys.koku.tiva.soa.ConsentShortSummary;
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
        templateTO.setConcentTemplateId(template.getId());
        templateTO.setCreatorUid(getSafeUserUid(template.getCreator()));
        templateTO.setDescription(template.getDescription());
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
    public List<ConsentTemplateTO> searchConsentTemplates(final String searchString, int limit) {
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
        // TODO Auto-generated method stub
        final List<Consent> consents = consentDao.getAssignedConsents(userDao.getOrCreateUser(userUid), startNum, maxNum - startNum + 1);
        final List<ConsentShortSummary> result = new ArrayList<ConsentShortSummary>();
        for (final Consent consent : consents) {
            final ConsentShortSummary consentTO = new ConsentShortSummary();
            consentTO.setConsentId(consent.getId());
            consentTO.setRequestor(getSafeUserUid(consent.getCreator()));
            consentTO.setTemplateName(consent.getTemplate().getTitle());
            final Set<User> receipients = consent.getReceipients();
            consentTO.setAnotherPermitterUid(getAnotherUser(userUid, receipients));
            result.add(consentTO);
        }
        return result;
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
    public void giveConsent(Long consentId, String userUid,
            List<ActionPermittedTO> actions, XMLGregorianCalendar validTill,
            String comment) {
        // TODO Auto-generated method stub
        
    }
}
