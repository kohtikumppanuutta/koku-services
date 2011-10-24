package fi.arcusys.koku.common.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.DaycareRequest;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 13, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class DaycareRequestDAOTest {

    @Autowired
    private DaycareRequestDAO service;
    
    @Autowired 
    private CommonTestUtil testUtil;

    @Test
    public void testCreateRetrieveDeleteDaycareRequest() {
        final String creator = "hakRequestor";
        final String targetPerson = "hakTargetPerson";
        final String formContent = "form information";

        final DaycareRequest newRequest = new DaycareRequest();
        newRequest.setCreator(testUtil.getUserByUid(creator));
        newRequest.setFormContent(formContent);
        newRequest.setNeededFromDate(new Date());
        newRequest.setTargetPerson(testUtil.getUserByUid(targetPerson));
        final DaycareRequest request = service.create(newRequest);
        
        assertNotNull("New request created: ", request);
        assertEquals("Correct creator: ", creator, request.getCreator().getUid());
        assertEquals("Correct targetPerson: ", targetPerson, request.getTargetPerson().getUid());
        assertEquals("Correct formContent: ", formContent, request.getFormContent());
        assertNotNull("Correct date: ", request.getNeededFromDate());
        assertNotNull("Create date added: ", request.getCreatedDate());
        assertNotNull("Entity have id: ", request.getId());
        
        final DaycareRequest fromService = service.getById(request.getId());
        assertNotNull("DaycareRequest retreived by ID: ", fromService);
        assertEquals(request.getCreator(), fromService.getCreator());
        assertEquals(request.getFormContent(), fromService.getFormContent());
        
        service.delete(fromService);
        assertNull("DaycareRequest removed: ", service.getById(request.getId()));
    }
}
