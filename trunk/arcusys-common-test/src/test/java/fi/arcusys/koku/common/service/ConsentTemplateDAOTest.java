package fi.arcusys.koku.common.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.ConsentActionRequest;
import fi.arcusys.koku.common.service.datamodel.ConsentTemplate;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class ConsentTemplateDAOTest {

    @Autowired
    private ConsentTemplateDAO service;
    
    @Autowired 
    private CommonTestUtil testUtil;

    @Test
    public void testCreateRetrieveDeleteConsentTemplate() {
        final ConsentTemplate consentTemplate = testUtil.createTestConsentTemplate();
        
        final Long templateId = service.create(consentTemplate).getId();
        
        final ConsentTemplate template = service.getById(templateId);
        assertEquals(templateId, template.getId());
        assertEquals(consentTemplate.getTitle(), template.getTitle());
        assertEquals(consentTemplate.getDescription(), template.getDescription());
        assertEquals(consentTemplate.getCreator().getUid(), template.getCreator().getUid());
        assertEquals(3, template.getActions().size());
        
        service.delete(template);
        assertNull("Template removed: ", service.getById(template.getId()));
    }
}
