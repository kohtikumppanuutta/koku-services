package fi.arcusys.koku.tiva.service;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.jws.WebParam;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.CommonTestUtil;
import fi.arcusys.koku.common.service.ConsentDAO;
import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.tiva.soa.ActionPermittedTO;
import fi.arcusys.koku.tiva.soa.ActionRequestStatus;
import fi.arcusys.koku.tiva.soa.ActionRequestSummary;
import fi.arcusys.koku.tiva.soa.ActionRequestTO;
import fi.arcusys.koku.tiva.soa.ConsentApprovalStatus;
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
import fi.arcusys.koku.tiva.soa.ConsentTemplateTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-tiva-context.xml"})
public class ConsentServiceTest {

    @Autowired
    private ConsentServiceFacade service;
    
    @Autowired
    private ConsentDAO consentDao;
    
    @Test
    public void createConsentTemplate() {
        final ConsentTemplateTO template = createTestTemplate("template");
        
        final Long templateId = service.createConsentTemplate(template);
        
        final ConsentTemplateTO templateById = service.getConsentTemplate(templateId);
        
        assertEquals(templateId, templateById.getConsentTemplateId());
        assertEquals(template.getTitle(), templateById.getTitle());
        assertEquals(template.getCreatorUid(), templateById.getCreatorUid());
        assertEquals(template.getDescription(), templateById.getDescription());
        assertEquals(template.getActions().size(), templateById.getActions().size());
    }
    
    @Test
    public void searchConsentTemplate() {
        service.createConsentTemplate(createTestTemplate("testSearch1"));
        service.createConsentTemplate(createTestTemplate("testSearch2"));
        
        assertEquals(1, service.searchConsentTemplates("testSearch", 1).size());
        assertEquals(2, service.searchConsentTemplates("testSearch", 2).size());
        assertEquals(1, service.searchConsentTemplates("testSearch1", 2).size());
        assertEquals(1, service.searchConsentTemplates("testSearch2", 2).size());
        assertEquals(0, service.searchConsentTemplates("testSearch12", 2).size());
    }
    
    @Test
    public void requestForConsentWithApprovalAndDeclining() {
        // new consent
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForApproveAndDecline"));
        final String parentForApprove = "Kalle Kuntalainen";
        final String parentForDecline = "Kirsi Kuntalainen";
        
        final String employeeUid = "Ville Virkamies";
        final Long consentId = service.requestForConsent(templateId, employeeUid, 
                "Lassi Lapsi", Arrays.asList(parentForApprove, parentForDecline), null, null, null, Boolean.FALSE, null);

        assertNull(getById(consentId, service.getProcessedConsents(employeeUid, new ConsentQuery(1, 10))));
        
        // first parent's approval
        final List<ConsentShortSummary> consentsForApprove = service.getAssignedConsents(parentForApprove, 1, 10);
        assertNotNull(getById(consentId, consentsForApprove));
        final ConsentForReplyTO consentForApprove = service.getConsentForReply(consentId, parentForApprove);
        assertNotNull(consentForApprove);
        service.giveConsent(consentForApprove.getConsentId(), parentForApprove, getTestActionsPermitted(), 
                CalendarUtil.getXmlDate(new Date()), "consent given");
        assertNull("already processed consent: ", getById(consentId, service.getAssignedConsents(parentForApprove, 1, 10)));
        
        assertNotNull(getById(consentId, service.getProcessedConsents(employeeUid, new ConsentQuery(1, 10))));
        final ConsentTO combinedAfterApprove = service.getCombinedConsentById(consentId);
        assertNotNull(combinedAfterApprove);
        assertEquals(ConsentStatus.PartiallyGiven, combinedAfterApprove.getStatus());
        
        // second parent's declining
        final List<ConsentShortSummary> consentsForDecline = service.getAssignedConsents(parentForDecline, 1, 10);
        assertNotNull(getById(consentId, consentsForDecline));
        final ConsentForReplyTO consentForDecline = service.getConsentForReply(consentId, parentForDecline);
        assertNotNull(consentForDecline);
        assertNull(getById(consentId, service.getOldConsents(parentForDecline, 1, 10)));
        service.declineConsent(consentForDecline.getConsentId(), parentForDecline, "consent declined");
        assertNull("already processed consent: ", getById(consentId, service.getAssignedConsents(parentForDecline, 1, 10)));
        assertNotNull(getById(consentId, service.getOldConsents(parentForDecline, 1, 10)));

        assertEquals(ConsentStatus.Declined, service.getCombinedConsentById(consentId).getStatus());
        
        // first parent's update
        final List<ConsentSummary> ownConsents = service.getOwnConsents(parentForApprove, 1, 10);
        assertNotNull(getById(consentId, ownConsents));
        final XMLGregorianCalendar newDate = CalendarUtil.getXmlDate(new Date());
        newDate.setYear(newDate.getYear() + 1);
        service.updateConsent(consentId, parentForApprove, newDate, "extended consent");
        assertEquals(newDate, getById(consentId, service.getOwnConsents(parentForApprove, 1, 10)).getValidTill());
        final ConsentTO replied = service.getConsentById(consentId, parentForApprove);
        assertEquals(ConsentApprovalStatus.Approved, replied.getApprovalStatus());
        final List<ActionRequestSummary> actionRequests = replied.getActionRequests();
        assertEquals(3, actionRequests.size());
        assertTrue("One action is declined by default: ", 
                actionRequests.get(0).getStatus() == ActionRequestStatus.Declined ||
                actionRequests.get(1).getStatus() == ActionRequestStatus.Declined ||
                actionRequests.get(2).getStatus() == ActionRequestStatus.Declined
                );
        assertTrue("Two actions are approved: ", 
                actionRequests.get(0).getStatus() == ActionRequestStatus.Given ||
                actionRequests.get(1).getStatus() == ActionRequestStatus.Given ||
                actionRequests.get(2).getStatus() == ActionRequestStatus.Given
                );
        
        // first parent's revoking
        assertNotNull(getById(consentId, service.getOwnConsents(parentForApprove, 1, 10)));
        assertNull(getById(consentId, service.getOldConsents(parentForApprove, 1, 10)));
        service.revokeConsent(consentId, parentForApprove, "revoked consent");
        assertNull(getById(consentId, service.getOwnConsents(parentForApprove, 1, 10)));
        assertNotNull(getById(consentId, service.getOldConsents(parentForApprove, 1, 10)));
        final ConsentTO revoked = service.getConsentById(consentId, parentForApprove);
        assertEquals(ConsentApprovalStatus.Declined, revoked.getApprovalStatus());
        assertEquals(ConsentStatus.Revoked, revoked.getStatus());
    }
    
    @Test
    public void testTotals() {
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForCountingTotals"));
        final String parent = "testTotalsParent";
        final String employee = "testTotalsEmployee";
        
        final Long consentId = service.requestForConsent(templateId, employee, 
                "Lassi Lapsi", Arrays.asList(parent), null, null, null, Boolean.FALSE, null);
        
        assertEquals(1, service.getTotalAssignedConsents(parent));
        assertEquals(0, service.getTotalOwnConsents(parent));
        assertEquals(0, service.getTotalProcessedConsents(employee, null));
        
        service.giveConsent(consentId, parent, Collections.<ActionPermittedTO>emptyList(), null, "");

        assertEquals(0, service.getTotalAssignedConsents(parent));
        assertEquals(1, service.getTotalOwnConsents(parent));
        assertEquals(1, service.getTotalProcessedConsents(employee, null));
    }
    
    @Test
    public void searchByTemplateAndUserUid() {
        // new consent
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForSearchTesting"));
        final String parent1 = "searchByTemplateParent1";
        final String parent2 = "searchByTemplateParent2";
        
        final String employeeUid = "Ville Virkamies";
        final Long consentId = service.requestForConsent(templateId, employeeUid, 
                "Lassi Lapsi", Arrays.asList(parent1, parent2), null, null, null, Boolean.FALSE, null);
        
        final ConsentQuery query = new ConsentQuery(1, 100);
        assertNull(getById(consentId, service.getProcessedConsents(employeeUid, query)));
        
        final ActionPermittedTO actionPermittedTO = new ActionPermittedTO();
        actionPermittedTO.setActionRequestNumber(1);
        actionPermittedTO.setPermitted(true);
        
        service.giveConsent(consentId, parent1, Collections.singletonList(actionPermittedTO), null, "");
        assertNotNull(getById(consentId, service.getProcessedConsents(employeeUid, query)));
        
        query.setCriteria(new ConsentCriteria());
        // filter by templateId
        query.getCriteria().setConsentTemplateId(0L);
        assertNull("not found by wrong template id: ", getById(consentId, service.getProcessedConsents(employeeUid, query)));
        query.getCriteria().setConsentTemplateId(templateId);
        assertNotNull("found by template id: ", getById(consentId, service.getProcessedConsents(employeeUid, query)));
        // filter by userid
        query.getCriteria().setReceipientUid(parent2);
        assertNull("not found by wrong receipient uid: ", getById(consentId, service.getProcessedConsents(employeeUid, query)));
        query.getCriteria().setReceipientUid(parent1);
        assertNotNull("found by receipient uid: ", getById(consentId, service.getProcessedConsents(employeeUid, query)));
    }
    
    @Test
    public void createConsentOnBehalf() {
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForCreationOnBehalf"));
        final String parent1 = "parent1";
        final List<String> recipients = Arrays.asList(parent1);
        
        final String employeeUid = "Ville Virkamies";
        
        final ConsentSourceInfo sourceInfo = new ConsentSourceInfo();
        sourceInfo.setRepository("testRepository");
        sourceInfo.setAttachmentUrl("http://attachment.org");
        final Long consentId = service.writeConsentOnBehalf(templateId, employeeUid,
                "Paper-based", "Lassi Lapsi", recipients, null, null, getTestActionsPermitted(), 
                sourceInfo, "given on behalf");
        
        final ConsentQuery query = new ConsentQuery(1, 100);
        final ConsentSummary autoApproved = getById(consentId, service.getProcessedConsents(employeeUid, query));
        assertNotNull("Already processed consent for employee: ", autoApproved);
        assertEquals(ConsentStatus.Valid, autoApproved.getStatus());
        
        assertNotNull("Already processed consent for parent1: ", getById(consentId, service.getOwnConsents(parent1, 1, 10)));

        service.revokeConsent(consentId, parent1, "no comments");
        assertEquals(ConsentStatus.Revoked, getById(consentId, service.getProcessedConsents(employeeUid, query)).getStatus());
    }
    
    @Test
    public void autoCancelConsent() {
        // request for consent
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForAutocancel"));
        final String parent = "testParentAutoCancel";
        final String parent2 = "testParentAutoCancel2";
        final String employee = "testEmployee";
        final List<String> parents = Arrays.asList(parent, parent2);

        final Calendar calendar = Calendar.getInstance();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        
        final Long consentId = service.requestForConsent(templateId, employee, 
                "Lassi Lapsi", parents, ConsentReceipientsType.AnyParent, CalendarUtil.getXmlDate(calendar.getTime()), null, Boolean.FALSE, null);
        
        final Long consentBothParentsId = service.requestForConsent(templateId, employee, 
                "Lassi Lapsi", parents, ConsentReceipientsType.BothParents, CalendarUtil.getXmlDate(calendar.getTime()), null, Boolean.FALSE, null);
        // auto cancel for skip
        service.cancellationOfOutdatedConsents();
        // check status
        assertEquals(ConsentStatus.Open, service.getCombinedConsentById(consentId).getStatus());
        assertEquals(ConsentStatus.Open, service.getCombinedConsentById(consentBothParentsId).getStatus());
        
        // update date
        updateReplyTillEqToday(consentId);
        updateReplyTillEqToday(consentBothParentsId);
        service.giveConsent(consentBothParentsId, parent, getTestActionsPermitted(), null, "valid from " + parent);
        
        // auto cancel for cancel
        assertEquals("Auto-cancelled both consents: ", 2, service.cancellationOfOutdatedConsents());

        // check status
        assertEquals(ConsentStatus.Declined, service.getCombinedConsentById(consentId).getStatus());
        assertEquals(ConsentStatus.Declined, service.getCombinedConsentById(consentBothParentsId).getStatus());
        
        assertEquals("Consent given: ", ConsentStatus.Valid, service.getConsentById(consentBothParentsId, parent).getStatus());
        assertEquals("Auto-declined consent: ", ConsentStatus.Declined, service.getConsentById(consentBothParentsId, parent2).getStatus());
    }

    private void updateReplyTillEqToday(final Long consentId) {
        final Consent consentDO = consentDao.getById(consentId);
        consentDO.setReplyTill(CalendarUtil.getXmlDate(new Date()).toGregorianCalendar().getTime());
        consentDao.update(consentDO);
    }
    
    @Test
    public void tivaToKksConsents() {
        // new consent
        final Long templateId = service.createConsentTemplate(createTestTemplate("kks"));
        final String parent1 = "kksParent1";
        final String parent2 = "kksParent2";
        final String targetPersonUid = "Lassi Lapsi";
        
        final String employeeUid = "Ville Virkamies";

        final ConsentKksExtraInfo extraInfo = new ConsentKksExtraInfo();
        final String informationTargetId = "info1";
        final ConsentExternalGivenTo givenToParty = new ConsentExternalGivenTo();
        givenToParty.setPartyId("partyId");
        givenToParty.setPartyName("partyName");
        final List<ConsentExternalGivenTo> givenTo = Collections.singletonList(givenToParty);
        extraInfo.setInformationTargetId(informationTargetId);
        extraInfo.setGivenTo(givenTo);

        final Long consentId = service.requestForConsent(templateId, employeeUid, 
                targetPersonUid, Arrays.asList(parent1, parent2), null, null, null, Boolean.FALSE, extraInfo);
        
        final ActionPermittedTO actionPermittedTO = new ActionPermittedTO();
        actionPermittedTO.setActionRequestNumber(1);
        actionPermittedTO.setPermitted(true);
        
        service.giveConsent(consentId, parent1, Collections.singletonList(actionPermittedTO), null, "");
        service.giveConsent(consentId, parent2, Collections.singletonList(actionPermittedTO), null, "");
        
        final ConsentSearchCriteria query = new ConsentSearchCriteria();
        query.setGivenTo(Collections.singletonList("partyId"));
        query.setInformationTargetId(informationTargetId);
        query.setTargetPerson(targetPersonUid);
        query.setTemplateNamePrefix("kks");
        assertNotNull(getById(consentId, service.searchConsents(query)));
    }

    private List<ActionPermittedTO> getTestActionsPermitted() {
        final List<ActionPermittedTO> actionPermits = new ArrayList<ActionPermittedTO>();
        for (int i = 1; i <= 2; i++) {
            final ActionPermittedTO actionPermittedTO = new ActionPermittedTO();
            actionPermittedTO.setActionRequestNumber(i);
            actionPermittedTO.setPermitted(true);
            actionPermits.add(actionPermittedTO);
        }
        return actionPermits;
    }

    /**
     * @param consentId
     * @param consentsForApprove
     * @return
     */
    private <V extends ConsentShortSummary> V getById(final Long objectId, final List<V> collection) {
        for (final V member : collection) {
            if (member.getConsentId().equals(objectId)) {
                return member;
            }
        }
        return null;
    }

    private ConsentTemplateTO createTestTemplate(final String title) {
        final ConsentTemplateTO template = new ConsentTemplateTO();
        template.setTitle(title);
        template.setCode(title);
        template.setCreatorUid("Ville Virkamies");
        template.setDescription("test template description");
        final List<ActionRequestTO> actions = new ArrayList<ActionRequestTO>();
        for (int i = 1; i <= 3; i++) {
            final ActionRequestTO actionRequest = new ActionRequestTO();
            actionRequest.setNumber(i);
            actionRequest.setName("action " + i);
            actionRequest.setDescription("description " + i);
            actions.add(actionRequest);
        }
        template.setActions(actions);
        return template;
    }
}
