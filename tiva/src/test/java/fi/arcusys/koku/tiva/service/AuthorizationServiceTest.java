package fi.arcusys.koku.tiva.service;

import static junit.framework.Assert.*;

import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.AuthorizationTemplateDAO;
import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.CommonTestUtil;
import fi.arcusys.koku.common.service.datamodel.AuthorizationArea;
import fi.arcusys.koku.common.service.datamodel.AuthorizationTemplate;
import fi.arcusys.koku.tiva.soa.AuthorizationCriteria;
import fi.arcusys.koku.tiva.soa.AuthorizationDetailTO;
import fi.arcusys.koku.tiva.soa.AuthorizationQuery;
import fi.arcusys.koku.tiva.soa.AuthorizationShortSummary;
import fi.arcusys.koku.tiva.soa.AuthorizationStatus;
import fi.arcusys.koku.tiva.soa.AuthorizationSummary;
import fi.arcusys.koku.tiva.soa.AuthorizationTemplateTO;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 12, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-tiva-context.xml"})
public class AuthorizationServiceTest {

    @Autowired
    private AuthorizationServiceFacade serviceFacade;
    
    @Autowired
    private CommonTestUtil testUtil;
    
    @Autowired
    private AuthorizationTemplateDAO templateService;
    
    @Test
    public void createAuthorizationRequest() {
        final String senderUid = "fromUser";
        final String receiverUid = "toUser";
        final String targetPersonUid = "theirChild";
        
        final Long authorizationId = createNewAuthorization(senderUid, receiverUid, targetPersonUid);
        
        assertNotNull(serviceFacade.getAuthorization(authorizationId, "fromUser"));
        assertNotNull(getById(authorizationId, serviceFacade.getSentAuthorizations("fromUser", 1, 10)));
    }

    private Long createNewAuthorization(final String senderUid,
            final String receiverUid, final String targetPersonUid) {
        final AuthorizationTemplate newTemplate = testUtil.createAuthorization(AuthorizationArea.TIVA);
        if (serviceFacade.getAuthorizationTemplates(newTemplate.getName(), 1).isEmpty()) {
            templateService.create(newTemplate);
        }
        final AuthorizationTemplateTO template = serviceFacade.getAuthorizationTemplates(newTemplate.getName(), 1).get(0);
        
        final Long authorizationId = serviceFacade.createAuthorization(template.getTemplateId(), CalendarUtil.getXmlDate(new Date()), senderUid, receiverUid, targetPersonUid);
        return authorizationId;
    }

    @Test
    public void approveAuthorization() {
        final String receiverUid = "toUserForApprove";
        final String senderUid = "fromUserForApprove";
        final Long authorizationId = createNewAuthorization(senderUid, receiverUid, "child");

        // check receipient's part
        final AuthorizationShortSummary assignedAuthorization = getById(authorizationId, serviceFacade.getReceivedAuthorizations(receiverUid, 1, 10));
        assertNull("should be visible in Intalio as task: ", assignedAuthorization);
        assertEquals(AuthorizationStatus.Open, serviceFacade.getAuthorizationSummary(authorizationId, senderUid).getStatus());
        
        serviceFacade.approveAuthorization(authorizationId, receiverUid, "approved");
        assertEquals(AuthorizationStatus.Valid, getById(authorizationId, serviceFacade.getReceivedAuthorizations(receiverUid, 1, 10)).getStatus());
    }

    @Test
    public void declineAuthorization() {
        final String receiverUid = "toUserForDecline";
        final Long authorizationId = createNewAuthorization("fromUser", receiverUid, "child");

        serviceFacade.declineAuthorization(authorizationId, receiverUid, "declined");
        assertEquals(AuthorizationStatus.Declined, getById(authorizationId, serviceFacade.getReceivedAuthorizations(receiverUid, 1, 10)).getStatus());
    }

    @Test
    public void editAuthorization() {
        final String receiverUid = "toUser";
        final String senderUid = "fromUser";
        final String targetPersonUid = "child";
        final Long authorizationId = createNewAuthorization(senderUid, receiverUid, targetPersonUid);
        
        final AuthorizationDetailTO authorizationTO = serviceFacade.getAuthorization(authorizationId, senderUid);
        final XMLGregorianCalendar validTill = authorizationTO.getValidTill();
        assertNotNull(validTill);
        
        validTill.setYear(validTill.getYear() + 1);
        
        serviceFacade.updateAuthorization(authorizationId, senderUid, validTill, "valid for one year");
        assertEquals(validTill, serviceFacade.getAuthorization(authorizationId, senderUid).getValidTill());

        serviceFacade.approveAuthorization(authorizationId, receiverUid, "approved");
        assertEquals(AuthorizationStatus.Valid, getById(authorizationId, serviceFacade.getReceivedAuthorizations(receiverUid, 1, 10)).getStatus());

        serviceFacade.updateAuthorization(authorizationId, senderUid, CalendarUtil.getXmlDate(new Date()), "valid for today");
        assertNotNull(serviceFacade.getAuthorization(authorizationId, senderUid).getValidTill());
        assertEquals(AuthorizationStatus.Valid, getById(authorizationId, serviceFacade.getReceivedAuthorizations(receiverUid, 1, 10)).getStatus());
    }
    
    @Test
    public void revokeAuthorization() {
        final String receiverUid = "toUser";
        final String senderUid = "fromUser";
        final String targetPersonUid = "child";
        final Long authorizationId = createNewAuthorization(senderUid, receiverUid, targetPersonUid);
        
        serviceFacade.revokeAuthorization(authorizationId, senderUid, "revoked");
        
        assertEquals(AuthorizationStatus.Revoked, getById(authorizationId, serviceFacade.getSentAuthorizations(senderUid, 1, 10)).getStatus());

        try {
            serviceFacade.approveAuthorization(authorizationId, receiverUid, "approve revoked");
            fail();
        } catch (IllegalStateException e) {
            // expected to be
        }

        assertEquals(AuthorizationStatus.Revoked, getById(authorizationId, serviceFacade.getSentAuthorizations(senderUid, 1, 10)).getStatus());

        try {
            serviceFacade.declineAuthorization(authorizationId, receiverUid, "reject revoked");
            fail();
        } catch (IllegalStateException e) {
            // expected to be
        }
        assertEquals(AuthorizationStatus.Revoked, getById(authorizationId, serviceFacade.getSentAuthorizations(senderUid, 1, 10)).getStatus());
    }
    
    @Test
    public void browseAuthorizations() {
        final String receiverUid = "toUser";
        final String senderUid = "fromUser";
        final String targetPersonUid = "child";
        final Long authorizationId = createNewAuthorization(senderUid, receiverUid, targetPersonUid);
        
        final AuthorizationQuery query = new AuthorizationQuery(1, 10);
        final AuthorizationCriteria criteria = new AuthorizationCriteria();
        criteria.setSenderUid(senderUid);
        criteria.setReceipientUid(receiverUid);
        query.setCriteria(criteria);
        assertNull("Only approved authorizations are visible to employee: ", getById(authorizationId, serviceFacade.getAuthorizationsByQuery(query)));

        final int oldCounts = serviceFacade.getTotalAuthorizationsByCriteria(criteria);
        serviceFacade.approveAuthorization(authorizationId, receiverUid, "approved");
        
        assertEquals(oldCounts + 1, serviceFacade.getTotalAuthorizationsByCriteria(criteria));

        assertNotNull("Approved authorizations are visible to employee: ", getById(authorizationId, serviceFacade.getAuthorizationsByQuery(query)));

        criteria.setAuthorizationTemplateId(-1L);
        assertNull("Incorrect template id: ", getById(authorizationId, serviceFacade.getAuthorizationsByQuery(query)));

        criteria.setAuthorizationTemplateId(serviceFacade.getAuthorization(authorizationId, senderUid).getTemplate().getTemplateId());
        assertNotNull("Correct template id: ", getById(authorizationId, serviceFacade.getAuthorizationsByQuery(query)));

        serviceFacade.revokeAuthorization(authorizationId, senderUid, "revoked");
        assertNull("Revoked authorizations are not visible to employee: ", getById(authorizationId, serviceFacade.getAuthorizationsByQuery(query)));
    }

    private <V extends AuthorizationShortSummary> V getById(final long authorizationId, final List<V> authorizations) {
        for (final V summary : authorizations) {
            if (authorizationId == summary.getAuthorizationId()) {
                return summary;
            }
        }
        return null;
    }
}
