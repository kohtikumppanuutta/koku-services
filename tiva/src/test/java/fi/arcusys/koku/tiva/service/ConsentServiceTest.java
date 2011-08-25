package fi.arcusys.koku.tiva.service;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
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
import fi.arcusys.koku.tiva.soa.ActionPermittedTO;
import fi.arcusys.koku.tiva.soa.ActionRequestStatus;
import fi.arcusys.koku.tiva.soa.ActionRequestSummary;
import fi.arcusys.koku.tiva.soa.ActionRequestTO;
import fi.arcusys.koku.tiva.soa.ConsentApprovalStatus;
import fi.arcusys.koku.tiva.soa.ConsentForReplyTO;
import fi.arcusys.koku.tiva.soa.ConsentShortSummary;
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
    private CommonTestUtil testUtil;
    
    @Test
    public void createConsentTemplate() {
        final ConsentTemplateTO template = createTestTemplate("template");
        
        final Long templateId = service.createConsentTemplate(template);
        
        final ConsentTemplateTO templateById = service.getConsentTemplate(templateId);
        
        assertEquals(templateId.longValue(), templateById.getConsentTemplateId());
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
                "Lassi Lapsi", Arrays.asList(parentForApprove, parentForDecline));

        assertNull(getById(consentId, service.getProcessedConsents(employeeUid, 1, 10)));
        
        // first parent's approval
        final List<ConsentShortSummary> consentsForApprove = service.getAssignedConsents(parentForApprove, 1, 10);
        assertNotNull(getById(consentId, consentsForApprove));
        final ConsentForReplyTO consentForApprove = service.getConsentForReply(consentId, parentForApprove);
        assertNotNull(consentForApprove);
        final List<ActionPermittedTO> actionPermits = new ArrayList<ActionPermittedTO>();
        // approve only 2 actions, the last one is declined if otherwise is not specified
        for (int i = 1; i <= 2; i++) {
            final ActionPermittedTO actionPermittedTO = new ActionPermittedTO();
            actionPermittedTO.setActionRequestNumber(i);
            actionPermittedTO.setPermitted(true);
            actionPermits.add(actionPermittedTO);
        }
        service.giveConsent(consentForApprove.getConsentId(), parentForApprove, actionPermits, 
                CalendarUtil.getXmlDate(new Date()), "consent given");
        assertNull("already processed consent: ", getById(consentId, service.getAssignedConsents(parentForApprove, 1, 10)));
        
        assertNotNull(getById(consentId, service.getProcessedConsents(employeeUid, 1, 10)));
        final ConsentTO combinedAfterApprove = service.getCombinedConsentById(consentId);
        assertNotNull(combinedAfterApprove);
        assertEquals(ConsentStatus.PartiallyGiven, combinedAfterApprove.getStatus());
        
        // second parent's declining
        final List<ConsentShortSummary> consentsForDecline = service.getAssignedConsents(parentForDecline, 1, 10);
        assertNotNull(getById(consentId, consentsForDecline));
        final ConsentForReplyTO consentForDecline = service.getConsentForReply(consentId, parentForDecline);
        assertNotNull(consentForDecline);
        service.declineConsent(consentForDecline.getConsentId(), parentForDecline, "consent declined");
        assertNull("already processed consent: ", getById(consentId, service.getAssignedConsents(parentForDecline, 1, 10)));

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
        service.revokeConsent(consentId, parentForApprove, "revoked consent");
        final ConsentTO revoked = service.getConsentById(consentId, parentForApprove);
        assertEquals(ConsentApprovalStatus.Revoked, revoked.getApprovalStatus());
    }
    
    @Test
    public void testTotals() {
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForApproveAndDecline"));
        final String parent = "testTotalsParent";
        final String employee = "testTotalsEmployee";
        
        final Long consentId = service.requestForConsent(templateId, employee, 
                "Lassi Lapsi", Arrays.asList(parent));
        
        assertEquals(1, service.getTotalAssignedConsents(parent));
        assertEquals(0, service.getTotalOwnConsents(parent));
        assertEquals(0, service.getTotalProcessedConsents(employee));
        
        service.giveConsent(consentId, parent, Collections.<ActionPermittedTO>emptyList(), null, "");

        assertEquals(0, service.getTotalAssignedConsents(parent));
        assertEquals(1, service.getTotalOwnConsents(parent));
        assertEquals(1, service.getTotalProcessedConsents(employee));
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
