package fi.arcusys.koku.tiva.service;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
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
import fi.arcusys.koku.tiva.soa.ActionRequestTO;
import fi.arcusys.koku.tiva.soa.ConsentForReplyTO;
import fi.arcusys.koku.tiva.soa.ConsentShortSummary;
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
        
        assertEquals(templateId, templateById.getConcentTemplateId());
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
    
//    @Test
    public void requestForConsentWithApprovalAndDeclining() {
        // new consent
        final Long templateId = service.createConsentTemplate(createTestTemplate("templateForApproveAndDecline"));
        final String parentForApprove = "Kalle Kuntalainen";
        final Long consentId = service.requestForConsent(templateId, "Ville Virkamies", 
                "Lassi Lapsi", Arrays.asList(parentForApprove, "Kirsi Kuntalainen"));
        // one approval
        final List<ConsentShortSummary> consentsForApprove = service.getAssignedConsents(parentForApprove, 1, 10);
        assertNotNull(getById(consentId, consentsForApprove));
        final ConsentForReplyTO consentForApprove = service.getConsentForReply(consentId, parentForApprove);
        assertNotNull(consentForApprove);
        service.giveConsent(consentForApprove.getConsentId(), parentForApprove, new ArrayList<ActionPermittedTO>(), 
                CalendarUtil.getXmlDate(new Date()), "consent given");
        assertNull("already processed consent: ", getById(consentId, service.getAssignedConsents(parentForApprove, 1, 10)));
        
        // TODO: test employee's part also
        
        // one declining
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
