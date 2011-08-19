package fi.arcusys.koku.common.service;

import static junit.framework.Assert.assertNotNull;

import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentSlot;
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
		appointment.setRecipients(new HashSet<User>(
				Arrays.asList(getUserByUid("testAppReceiver1"),
							  getUserByUid("testAppReceiver2"))));
		appointment.setSlots(createTestSlots(numberOfSlots));
		return appointment;
	}
}
