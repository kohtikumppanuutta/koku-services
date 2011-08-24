package fi.arcusys.koku.common.service;

import static junit.framework.Assert.*;

import java.util.Date;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentActionReply;
import fi.arcusys.koku.common.service.datamodel.ConsentReply;
import fi.arcusys.koku.common.service.datamodel.ConsentReplyStatus;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class ConsentReplyDAOTest {

    @Autowired 
    private ConsentDAO consentService;

    @Autowired 
    private ConsentReplyDAO service;
    
    @Autowired 
    private CommonTestUtil testUtil;

    @Test
    public void testCreateRetrieveDeleteConsent() {
        final ConsentReply consentReply = new ConsentReply();
        final HashSet<ConsentActionReply> actions = new HashSet<ConsentActionReply>();
        for (int i = 1; i <= 3; i++) {
            final ConsentActionReply actionReply = new ConsentActionReply();
            actionReply.setActionRequestNumber(1);
            actionReply.setPermitted(true);
            actions.add(actionReply);
        }

        consentReply.setReplier(testUtil.getUserByUid("consentReplier"));
        consentReply.setActions(actions);
        consentReply.setStatus(ConsentReplyStatus.Approved);
        consentReply.setComment("reply to consent");
        consentReply.setConsent(consentService.create(new Consent()));
        consentReply.setValidTill(new Date());
        
        final Long replyId = service.create(consentReply).getId();

        final ConsentReply reply = service.getById(replyId);
        assertEquals(replyId, reply.getId());
        assertEquals(consentReply.getReplier().getUid(), reply.getReplier().getUid());
        assertEquals(consentReply.getActions().size(), reply.getActions().size());
        assertEquals(consentReply.getStatus(), reply.getStatus());
        assertEquals(consentReply.getComment(), reply.getComment());
        assertEquals(consentReply.getConsent().getId(), reply.getConsent().getId());
        assertEquals(consentReply.getValidTill(), reply.getValidTill());
        
        service.delete(reply);
        assertNull("Reply removed: ", service.getById(reply.getId()));
    }
}
