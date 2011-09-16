package fi.arcusys.koku.av.service;

import static junit.framework.Assert.*;

import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.av.soa.AppointmentForEditTO;
import fi.arcusys.koku.av.soa.AppointmentReceipientTO;
import fi.arcusys.koku.av.soa.AppointmentSlotTO;
import fi.arcusys.koku.av.soa.AppointmentSummary;
import fi.arcusys.koku.av.soa.AppointmentSummaryStatus;
import fi.arcusys.koku.av.soa.AppointmentTO;
import fi.arcusys.koku.av.soa.AppointmentWithTarget;
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
	public void testMessageTemplate() {
	    assertEquals("Sinulle on tietopyynto \"ABC\"", MessageFormat.format("Sinulle on tietopyynto \"{0}\"", "ABC"));
	}
	
	@Test
	public void getUserAppointments() {
		final AppointmentForEditTO newAppointment = createTestAppointment("new appointment", "appointment description", 1);
		final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
		
		for (final AppointmentSummary appointment : serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 10)) {
			if (appointment.getAppointmentId() == appointmentId ) {
				return;
			}
		}
		fail("Appointment is not found in user's appointments");
	}
	
	@Test
	public void approveAndDecline() {
		final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for approve & decline", "appointment description", 1);

		final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String receipient = appointmentReceipient.getReceipients().get(0);
        final String targetPerson = appointmentReceipient.getTargetPerson();
        final String targetPersonForDecline = newAppointment.getReceipients().get(1).getTargetPerson();
		final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
		

		List<AppointmentWithTarget> appointments = serviceFacade.getAssignedAppointments(receipient);
		assertFalse(appointments.isEmpty());
		
		final AppointmentSummary appointmentForApprove = getById(appointments, appointmentId);
        assertTrue(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getAcceptedSlots().isEmpty());
		serviceFacade.approveAppointment(targetPerson, receipient, appointmentForApprove.getAppointmentId(), 1, "approved");
        assertFalse(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getAcceptedSlots().isEmpty());
        assertEquals(AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentRespondedById(appointmentForApprove.getAppointmentId(), targetPerson).getStatus());
		
        appointments = serviceFacade.getAssignedAppointments(receipient);
        assertFalse(appointments.isEmpty());

        final AppointmentSummary appointmentForDecline = getById(appointments, appointmentId);
        assertTrue(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getUsersRejected().isEmpty());
		serviceFacade.declineAppointment(targetPersonForDecline, receipient, appointmentForDecline.getAppointmentId(), "declined");
        assertFalse(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getUsersRejected().isEmpty());
        assertTrue(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getUsersRejected().contains(targetPersonForDecline));

        try {
            getById(serviceFacade.getAssignedAppointments(receipient), appointmentId);
            fail("All appointments should be processed already");
        } catch (IllegalArgumentException e) {
            // do nothing, exception expected
        }
        
        // cancel appointment
        serviceFacade.cancelAppointment(targetPerson, receipient, appointmentForApprove.getAppointmentId(), "cancelled");
        assertEquals(AppointmentSummaryStatus.Cancelled, serviceFacade.getAppointmentRespondedById(appointmentForApprove.getAppointmentId(), targetPerson).getStatus());
	}
	
	@Test
	public void countTotals() {
        final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for counts", "appointment description", 1);

        final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String uniqReceipient = appointmentReceipient.getReceipients().get(1);
        final String targetPerson = appointmentReceipient.getTargetPerson();
        final int oldAssignedTotal = serviceFacade.getTotalAssignedAppointments(uniqReceipient);
        
        final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
        
        assertEquals(oldAssignedTotal + 1, serviceFacade.getTotalAssignedAppointments(uniqReceipient));

        serviceFacade.approveAppointment(targetPerson, uniqReceipient, appointmentId, 1, "approved");
        assertEquals(oldAssignedTotal, serviceFacade.getTotalAssignedAppointments(uniqReceipient));
	}

	private <AS extends AppointmentSummary> AS getById(final List<AS> appointments, final Long appointmentId) {
		for (final AS appointment : appointments) {
			if (appointment.getAppointmentId() == appointmentId) {
				return appointment;
			}
		}
		throw new IllegalArgumentException("Appointment ID " + appointmentId + " not found.");
	}

	private AppointmentForEditTO createTestAppointment(final String testSubject, final String description, int numberOfSlots) {
		final AppointmentForEditTO appointment = new AppointmentForEditTO();
		appointment.setSubject(testSubject);
		appointment.setDescription(description);
		appointment.setSender("testAppSender");
		final AppointmentReceipientTO receipientTO = new AppointmentReceipientTO();
		receipientTO.setTargetPerson("testAppReceiver1");
		receipientTO.setReceipients(Arrays.asList("testGuardian1", "testGuardian2"));
        final AppointmentReceipientTO receipientTO2 = new AppointmentReceipientTO();
        receipientTO2.setTargetPerson("testAppReceiver2");
        receipientTO2.setReceipients(Arrays.asList("testGuardian1", "testGuardian3"));
        appointment.setReceipients(
		        Arrays.asList(receipientTO, receipientTO2));
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
