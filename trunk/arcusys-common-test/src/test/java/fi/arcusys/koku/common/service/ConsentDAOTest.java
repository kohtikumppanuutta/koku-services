package fi.arcusys.koku.common.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentType;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class ConsentDAOTest {

    @Autowired
    private ConsentDAO service;
    
    @Autowired
    private ConsentTemplateDAO templateService;

    @Autowired 
    private CommonTestUtil testUtil;

    @Test
    public void testCreateRetrieveDeleteConsent() {
        final Consent newConsent = new Consent();
        newConsent.setTemplate(templateService.create(testUtil.createTestConsentTemplate()));
        newConsent.setCreationType(ConsentType.Electronic);
        newConsent.setCreator(testUtil.getUserByUid("consentCreator"));
        newConsent.setReceipients(new HashSet<User>(Arrays.asList(testUtil.getUserByUid("Kalle Kuntalainen"), testUtil.getUserByUid("Kirsi Kuntalainen"))));
        newConsent.setTargetPerson(testUtil.getUserByUid("Lassi Lapsi"));
        newConsent.setValidTill(new Date());
        
        final Long consentId = service.create(newConsent).getId();
        
        final Consent consent = service.getById(consentId);
        
        assertEquals(consentId, consent.getId());
        assertEquals(newConsent.getTemplate(), consent.getTemplate());
        assertEquals(newConsent.getCreationType(), consent.getCreationType());
        assertEquals(newConsent.getCreator(), consent.getCreator());
        assertEquals(newConsent.getReceipients().size(), consent.getReceipients().size());
        assertEquals(newConsent.getTargetPerson().getUid(), consent.getTargetPerson().getUid());
        assertEquals(newConsent.getValidTill(), consent.getValidTill());

        service.delete(consent);
        assertNull("Consent removed: ", service.getById(consent.getId()));
    }
}
