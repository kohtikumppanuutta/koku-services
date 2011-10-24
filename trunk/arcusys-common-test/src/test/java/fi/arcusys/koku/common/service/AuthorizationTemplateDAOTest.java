package fi.arcusys.koku.common.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.AuthorizationArea;
import fi.arcusys.koku.common.service.datamodel.AuthorizationTemplate;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 12, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class AuthorizationTemplateDAOTest {

    @Autowired
    private AuthorizationTemplateDAO service;
    
    @Test
    public void testCreateRetrieveDeleteAuthorizationTemplate() {
        final String testName = "new authorization template";
        final String description = "template description";
        final AuthorizationArea area = AuthorizationArea.HAK; 
        
        final AuthorizationTemplate template = new AuthorizationTemplate();
        template.setName(testName);
        template.setDescription(description);
        template.setAuthorizationArea(area);
        service.create(template);
        
        assertNotNull("New template created: ", template);
        assertEquals("Correct name: ", testName, template.getName());
        assertEquals("Correct description: ", description, template.getDescription());
        assertEquals("Correct area: ", area, template.getAuthorizationArea());
        assertNotNull("Create date added: ", template.getCreatedDate());
        assertNotNull("Template have id: ", template.getId());
        
        final AuthorizationTemplate tmpFromService = service.getById(template.getId());
        assertNotNull("Template retreived by ID: ", tmpFromService);
        assertEquals(template.getName(), tmpFromService.getName());
        
        service.delete(tmpFromService);
        assertNull("Template removed: ", service.getById(template.getId()));
    }
}
