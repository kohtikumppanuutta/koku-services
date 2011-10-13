package fi.arcusys.koku.hak.service;

import static org.junit.Assert.*;

import java.util.Date;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.CommonTestUtil;
import fi.arcusys.koku.hak.soa.DaycareRequestTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 13, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-hak-context.xml"})
public class DaycareRequestServiceTest {

    @Autowired
    private DaycareRequestServiceFacade serviceFacade;
    
    @Autowired
    private CommonTestUtil testUtil;
    
    @Test
    public void createRequestAndGetForReply() {
        final String creator = "hakRequestor";
        final String targetPerson = "hakTargetPerson";
        final String formContent = "form information";
        
        final DaycareRequestTO newRequest = new DaycareRequestTO();
        newRequest.setCreatorUid(creator);
        newRequest.setDaycareNeededFromDate(CalendarUtil.getXmlDate(new Date()));
        newRequest.setFormContent(formContent);
        newRequest.setTargetPersonUid(targetPerson);

        final long requestId = serviceFacade.requestForDaycare(newRequest);

        final DaycareRequestTO requestTO = serviceFacade.getDaycareRequestById(requestId);
        
        assertNotNull(requestTO);
        assertEquals(creator, requestTO.getCreatorUid());
        assertEquals(targetPerson, requestTO.getTargetPersonUid());
    }

}
