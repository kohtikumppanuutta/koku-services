package fi.arcusys.koku.av.service;

import static junit.framework.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.av.soa.AppointmentSlotTO;
import fi.arcusys.koku.av.soa.AppointmentSummary;
import fi.arcusys.koku.av.soa.AppointmentTO;
import fi.arcusys.koku.common.service.CommonTestUtil;
import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponse;
import fi.arcusys.koku.common.service.datamodel.AppointmentSlot;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 22, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-av-context.xml"})
public class AppointmentServiceTest {
	@Autowired
	private AppointmentServiceFacade serviceFacade;

	@Autowired
	private CommonTestUtil testUtil;
	
	@Test
	public void getUserAppointments() {
		final AppointmentTO newAppointment = createTestAppointment("new appointment", "appointment description", 1);
		final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
		
		for (final AppointmentSummary appointment : serviceFacade.getAppointments(newAppointment.getSender(), Collections.singleton(AppointmentStatus.Created))) {
			if (appointment.getAppointmentId() == appointmentId ) {
				return;
			}
		}
		fail("Appointment is not found in user's appointments");
	}
	
	@Test
	public void approveAndDecline() {
		final AppointmentTO newAppointmentForApprove = createTestAppointment("new appointment for approve", "appointment description", 1);
		final AppointmentTO newAppointmentForDecline = createTestAppointment("new appointment for decline", "appointment description", 1);
		final String receipient = newAppointmentForApprove.getRecipients().iterator().next();
		final Long approvedAppointmentId = serviceFacade.storeAppointment(newAppointmentForApprove);
		final Long declinedAppointmentId = serviceFacade.storeAppointment(newAppointmentForDecline);
		

		final List<AppointmentSummary> appointments = serviceFacade.getAssignedAppointments(receipient);
		assertFalse(appointments.isEmpty());
		
		final AppointmentSummary appointmentForApprove = getById(appointments, approvedAppointmentId);
		serviceFacade.approveAppointment(receipient, appointmentForApprove.getAppointmentId(), 1, "approved");
		assertEquals(AppointmentStatus.Approved.name(), serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getStatus());
		
		final AppointmentSummary appointmentForDecline = getById(appointments, declinedAppointmentId);
		serviceFacade.declineAppointment(receipient, appointmentForDecline.getAppointmentId(), "declined");
		assertEquals(AppointmentStatus.Declined.name(), serviceFacade.getAppointment(appointmentForDecline.getAppointmentId()).getStatus());

		for (final AppointmentSummary appointmentSummary : serviceFacade.getAssignedAppointments(receipient)) {
			if (appointmentSummary.getAppointmentId() == appointmentForApprove.getAppointmentId()) {
				fail("Appointment approved already and shouldn't be in list of assigned");
			} else if (appointmentSummary.getAppointmentId() == appointmentForDecline.getAppointmentId()) {
				fail("Appointment declined already and shouldn't be in list of assigned");
			}
		}
	}

	private AppointmentSummary getById(final List<AppointmentSummary> appointments, final Long appointmentId) {
		for (final AppointmentSummary appointment : appointments) {
			if (appointment.getAppointmentId() == appointmentId) {
				return appointment;
			}
		}
		throw new IllegalArgumentException("Appointment ID " + appointmentId + " not found.");
	}

	private AppointmentTO createTestAppointment(final String testSubject, final String description, int numberOfSlots) {
		final AppointmentTO appointment = new AppointmentTO();
		appointment.setSubject(testSubject);
		appointment.setDescription(description);
		appointment.setSender("testAppSender");
		appointment.setRecipients(Arrays.asList("testAppReceiver1", "testAppReceiver2"));
		appointment.setSlots(createTestSlots(numberOfSlots));
		return appointment;
	}

	private List<AppointmentSlotTO> createTestSlots(int numberOfSlots) {
		final List<AppointmentSlotTO> slots = new ArrayList<AppointmentSlotTO>();
		
		final GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 9);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		for (int i = 1; i <= numberOfSlots; i++) {
			final AppointmentSlotTO slotTO = new AppointmentSlotTO();
			slotTO.setSlotNumber(i);
			try {
				final DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
				slotTO.setAppointmentDate(datatypeFactory.newXMLGregorianCalendar(calendar));
				calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 30);
				slotTO.setStartTime(datatypeFactory.newXMLGregorianCalendar(calendar));
				calendar.set(Calendar.MINUTE, calendar.get(Calendar.MINUTE) + 30);
				slotTO.setEndTime(datatypeFactory.newXMLGregorianCalendar(calendar));
			} catch (DatatypeConfigurationException e) {
				throw new RuntimeException(e);
			}
			slotTO.setLocation("room" + i);
			slotTO.setComment("comment" + i);
			slots.add(slotTO);
		}
		
		return slots;
	}
}
