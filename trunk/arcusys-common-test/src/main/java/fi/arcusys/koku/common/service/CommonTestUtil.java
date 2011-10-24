package fi.arcusys.koku.common.service;

import static junit.framework.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentSlot;
import fi.arcusys.koku.common.service.datamodel.AuthorizationArea;
import fi.arcusys.koku.common.service.datamodel.AuthorizationTemplate;
import fi.arcusys.koku.common.service.datamodel.ConsentActionRequest;
import fi.arcusys.koku.common.service.datamodel.ConsentTemplate;
import fi.arcusys.koku.common.service.datamodel.TargetPerson;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.impl.UserDAOImpl;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Component
public class CommonTestUtil {
	@Autowired
	private UserDAO userDao;
	
    @Autowired
    private TargetPersonDAO targetPersonDao;

    public User getUserByUid(final String userUid) {
		User user = userDao.getUserByUid(userUid);
		if (user == null) {
			final User newUser = new User();
			newUser.setUid(userUid);
			userDao.create(newUser);
			user = userDao.getUserByUid(userUid);
		}
		assertNotNull("User found by uid: " + userUid, user);
		return user;
	}

	private Set<AppointmentSlot> createTestSlots(int numberOfSlots) {
		final HashSet<AppointmentSlot> slots = new HashSet<AppointmentSlot>();
		
		for (int i = 1; i <= numberOfSlots; i++) {
			final AppointmentSlot slot = new AppointmentSlot();
			slot.setAppointmentDate(new Date());
			slot.setSlotNumber(i);
			slot.setStartTime(60 * i);
			slot.setEndTime(slot.getStartTime() + 15);
			slot.setLocation("room" + i);
			slot.setComment("comment" + i);
			slots.add(slot);
		}
		
		return slots;
	}

	public Appointment createTestAppointment(final String testSubject, final String description, int numberOfSlots) {
		final Appointment appointment = new Appointment();
		appointment.setSubject(testSubject);
		appointment.setDescription(description);
		appointment.setSender(getUserByUid("testAppSender"));
		appointment.setRecipients(new HashSet<TargetPerson>(
				Arrays.asList(
				        targetPersonDao.getOrCreateTargetPerson("testAppReceiver1", 
				                Arrays.asList("testGuardian1", "testGuardian2")),
				        targetPersonDao.getOrCreateTargetPerson("testAppReceiver2", 
				                Arrays.asList("testGuardian3", "testGuardian4")))));
		appointment.setSlots(createTestSlots(numberOfSlots));
		return appointment;
	}
	
    public ConsentTemplate createTestConsentTemplate() {
        final ConsentTemplate consentTemplate = new ConsentTemplate();
        final String title = "testConsent";
        final String description = "consent for testing";
        final String senderUid = "consentCreator";
        final Set<ConsentActionRequest> actions = new HashSet<ConsentActionRequest>();
        for (int i = 1; i <= 3; i++) {
            final ConsentActionRequest action = new ConsentActionRequest();
            action.setNumber(i);
            action.setName("action" + i);
            action.setDescription("description " + i);
            actions.add(action);
        }

        consentTemplate.setTitle(title);
        consentTemplate.setDescription(description);
        consentTemplate.setCreator(getUserByUid(senderUid));
        consentTemplate.setActions(actions);
        return consentTemplate;
    }

    public AuthorizationTemplate createAuthorization(final AuthorizationArea area) {
        final String testName = "new authorization template for " + area.name();
        final String description = "template description for " + area.name();
        final AuthorizationTemplate template = new AuthorizationTemplate();
        template.setName(testName);
        template.setDescription(description);
        template.setAuthorizationArea(area);
        return template;
    }
}
