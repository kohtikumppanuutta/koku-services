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

import fi.arcusys.koku.common.service.datamodel.Authorization;
import fi.arcusys.koku.common.service.datamodel.AuthorizationArea;
import fi.arcusys.koku.common.service.datamodel.AuthorizationReplyStatus;
import fi.arcusys.koku.common.service.datamodel.AuthorizationTemplate;
import fi.arcusys.koku.common.service.datamodel.AuthorizationType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 12, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class AuthorizationDAOTest {

    @Autowired
    private AuthorizationTemplateDAO templateService;
    
    @Autowired
    private AuthorizationDAO service;
    
    @Autowired
    private CommonTestUtil testUtil;
    
    @Test
    public void testCreateRetrieveDeleteAuthorizationTemplate() {
        final AuthorizationTemplate newTemplate = createNewTemplate(AuthorizationArea.HAK);
        final String fromUserUid = "fromUser";
        final String toUserUid = "toUser";
        final String targetPersonUid = "targetPerson";
        
        final Authorization authorization = new Authorization();
        authorization.setFromUser(testUtil.getUserByUid(fromUserUid));
        authorization.setToUser(testUtil.getUserByUid(toUserUid));
        authorization.setTargetPerson(testUtil.getUserByUid(targetPersonUid));
        authorization.setCreationType(AuthorizationType.Electronic);
        authorization.setTemplate(newTemplate);
        authorization.setValidTill(new Date());
        authorization.setReplyComment("some comment");
        authorization.setStatus(AuthorizationReplyStatus.Approved);
        service.create(authorization);
        
        assertNotNull("New authorization created: ", authorization);
        assertEquals("Correct template: ", newTemplate.getId(), authorization.getTemplate().getId());
        assertEquals("Correct from user: ", fromUserUid, authorization.getFromUser().getUid());
        assertEquals("Correct to user: ", toUserUid, authorization.getToUser().getUid());
        assertEquals("Correct target user: ", targetPersonUid, authorization.getTargetPerson().getUid());
        assertNotNull("Create date added: ", authorization.getCreatedDate());
        assertNotNull("Template have id: ", authorization.getId());

        assertNotNull(authorization.getValidTill());
        assertNotNull(authorization.getReplyComment());
        
        
        final Authorization fromService = service.getById(authorization.getId());
        assertNotNull("Authorization retreived by ID: ", fromService);
        assertEquals(authorization.getTemplate(), fromService.getTemplate());
        assertNotNull(fromService.getValidTill());
        assertNotNull(fromService.getReplyComment());
        assertEquals(authorization.getStatus(), fromService.getStatus());
        
//        fromService.setValidTill(null);
//        fromService.setReplyComment(null);
        fromService.setStatus(AuthorizationReplyStatus.Declined);
        service.update(fromService);
        
        assertEquals(AuthorizationReplyStatus.Declined, service.getById(fromService.getId()).getStatus());

//        assertNull(service.getById(fromService.getId()).getReplyComment());
//        assertNull(service.getById(fromService.getId()).getValidTill());
        
        service.delete(fromService);
        assertNull("Authorization removed: ", service.getById(authorization.getId()));
    }


    private AuthorizationTemplate createNewTemplate(final AuthorizationArea area) {
        return templateService.create(testUtil.createAuthorization(area));
    }
}
