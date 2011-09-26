package fi.arcusys.koku.common.service;

import static junit.framework.Assert.assertEquals;
import static junit.framework.Assert.assertNull;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentType;
import fi.arcusys.koku.common.service.datamodel.InformationRequest;
import fi.arcusys.koku.common.service.datamodel.InformationRequestCategory;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class InformationRequestDAOTest {
    @Autowired
    private InformationRequestDAO service;
    
    @Autowired 
    private CommonTestUtil testUtil;

    @Test
    public void testCreateRetrieveDeleteConsent() {

        final InformationRequest newEntity = new InformationRequest();
        newEntity.setAdditionalInfo("some additional info");
        newEntity.setDescription("description");
        newEntity.setReceiver(testUtil.getUserByUid("tpReceiver"));
        newEntity.setSender(testUtil.getUserByUid("tpSender"));
        newEntity.setTargetPerson(testUtil.getUserByUid("tpTargetPerson"));
        newEntity.setValidTill(new Date());
        final Set<InformationRequestCategory> categories = createNewCategories(newEntity, 3);
        
        final Long entityId = service.create(newEntity).getId();
        
        final InformationRequest request = service.getById(entityId);
        
        assertEquals(entityId, request.getId());
        assertEquals(newEntity.getTargetPerson().getUid(), request.getTargetPerson().getUid());
        assertEquals(newEntity.getValidTill(), request.getValidTill());
        assertEquals(getUids(categories), getUids(request.getCategories()));
        
        final Set<InformationRequestCategory> newCategories = createNewCategories(request, 2);

        final InformationRequest updated = service.update(request);
        assertEquals(getUids(newCategories), getUids(updated.getCategories()));
        
        final InformationRequest updatedEntity = service.getById(entityId);
        assertEquals(getUids(newCategories), getUids(updatedEntity.getCategories()));
        
        service.delete(updatedEntity);
        assertNull("InformationRequest removed: ", service.getById(entityId));
    }

    private Set<InformationRequestCategory> createNewCategories(
            final InformationRequest request, final int limit) {
        final Set<InformationRequestCategory> categories = new HashSet<InformationRequestCategory>();
        for (int i = 0; i < limit; i++) {
            final InformationRequestCategory category = new InformationRequestCategory();
            category.setCategoryUid("" + (long)(Math.random() * 100));
            category.setRequest(request);
            categories.add(category);
        }
        request.setCategories(categories);
        return categories;
    }

    private Set<String> getUids(final Set<InformationRequestCategory> categories) {
        final Set<String> result = new HashSet<String>(categories.size());
        for (final InformationRequestCategory category : categories) {
            result.add(category.getCategoryUid());
        }
        return result;
    }
}
