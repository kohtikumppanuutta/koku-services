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
import java.util.ResourceBundle;
import java.util.Set;
import java.util.TimeZone;

import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.av.soa.AppointmentCriteria;
import fi.arcusys.koku.av.soa.AppointmentForEditTO;
import fi.arcusys.koku.av.soa.AppointmentForReplyTO;
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
        assertEquals("'app_inbox_citizen'", MessageFormat.format("''app_inbox_citizen''", new Object[] {}));
	}
	
	@Test
	public void getUserAppointments() {
		final AppointmentForEditTO newAppointment = createTestAppointment("new appointment", "appointment description", 1);
        final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String targetPerson = appointmentReceipient.getTargetPerson();
		final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
		
		assertNotNull("Appointment is not found in user's appointments", getById(serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 10, null), appointmentId));
		
        final AppointmentCriteria criteria = new AppointmentCriteria();
        assertNotNull("Appointment is not found in user's appointments", getById(serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 10, criteria), appointmentId));

        criteria.setTargetPersonUid(targetPerson);
        assertNotNull("Search by target person:", getById(serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 10, criteria), appointmentId));

        criteria.setTargetPersonUid("unknown");
        assertNull("Search by wrong target person:", getById(serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 10, criteria), appointmentId));
	}
	
	@Test
	public void approveAndDecline() {
		final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for approve & decline", "appointment description", 1);

		final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String receipient = appointmentReceipient.getReceipients().get(0);
        final String targetPerson = appointmentReceipient.getTargetPerson();
        final String targetPersonForDecline = newAppointment.getReceipients().get(1).getTargetPerson();
        final String receipientForDecline = newAppointment.getReceipients().get(1).getReceipients().get(1);
		final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
		

		List<AppointmentWithTarget> appointments = serviceFacade.getAssignedAppointments(receipient);
		assertFalse(appointments.isEmpty());
		
		final AppointmentSummary appointmentForApprove = getById(appointments, appointmentId);
        assertTrue(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getAcceptedSlots().isEmpty());
		serviceFacade.approveAppointment(targetPerson, receipient, appointmentForApprove.getAppointmentId(), 1, "approved");
        assertFalse(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getAcceptedSlots().isEmpty());
        assertEquals(AppointmentSummaryStatus.Approved, serviceFacade.getAppointmentRespondedById(appointmentForApprove.getAppointmentId(), targetPerson).getStatus());
        assertNotNull(getById(serviceFacade.getRespondedAppointments(receipient, 1, 10), appointmentId));
        assertNull(getById(serviceFacade.getOldAppointments(receipient, 1, 10), appointmentId));
		
        appointments = serviceFacade.getAssignedAppointments(receipient);
        assertFalse(appointments.isEmpty());

        final AppointmentSummary appointmentForDecline = getById(appointments, appointmentId);
        assertTrue(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getUsersRejected().isEmpty());
		serviceFacade.declineAppointment(targetPersonForDecline, receipientForDecline, appointmentForDecline.getAppointmentId(), "declined");
        assertFalse(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getUsersRejected().isEmpty());
        assertTrue(serviceFacade.getAppointment(appointmentForApprove.getAppointmentId()).getUsersRejected().contains(getKunpoName(targetPersonForDecline)));
        assertNull(getById(serviceFacade.getRespondedAppointments(receipientForDecline, 1, 10), appointmentId));
        assertNotNull(getById(serviceFacade.getOldAppointments(receipientForDecline, 1, 10), appointmentId));

        assertNull("All appointments should be processed already", getById(serviceFacade.getAssignedAppointments(receipientForDecline), appointmentId));
        
        // cancel appointment
        serviceFacade.cancelAppointment(targetPerson, receipient, appointmentForApprove.getAppointmentId(), "cancelled");
        assertEquals(AppointmentSummaryStatus.Cancelled, serviceFacade.getAppointmentRespondedById(appointmentForApprove.getAppointmentId(), targetPerson).getStatus());
	}
	
	@Test
	public void cancelAppointment() {
        final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for cancel", "appointment description", 1);

        final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
        serviceFacade.cancelWholeAppointment(appointmentId, "cancelled");
        
        assertNull(getById(serviceFacade.getCreatedAppointments(newAppointment.getSender(), 1, 5, null), appointmentId));
        assertNotNull(getById(serviceFacade.getProcessedAppointments(newAppointment.getSender(), 1, 5, null), appointmentId));
	}

    private String getKunpoName(final String targetPersonForDecline) {
        return testUtil.getUserByUid(targetPersonForDecline).getCitizenPortalName();
    }
	
	@Test
	public void countTotals() {
        final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for counts", "appointment description", 3);

        final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String uniqReceipient = appointmentReceipient.getReceipients().get(1);
        final String targetPerson = appointmentReceipient.getTargetPerson();
        final int oldAssignedTotal = serviceFacade.getTotalAssignedAppointments(uniqReceipient);
        
        final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
        
        assertEquals(oldAssignedTotal + 1, serviceFacade.getTotalAssignedAppointments(uniqReceipient));

        serviceFacade.approveAppointment(targetPerson, uniqReceipient, appointmentId, 1, "approved");
        assertEquals(oldAssignedTotal, serviceFacade.getTotalAssignedAppointments(uniqReceipient));
	}
	
	@Test
	public void testSkipApprovedSlots() {
	    // create appointment
        final AppointmentForEditTO newAppointment = createTestAppointment("new appointment for counts", "appointment description", 1);
        final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
	    
        final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String receipient = appointmentReceipient.getReceipients().get(0);
        final String targetPerson = appointmentReceipient.getTargetPerson();
        final String targetPersonForCheck = newAppointment.getReceipients().get(1).getTargetPerson();
        assertFalse(targetPerson.equals(targetPersonForCheck));

        // reply to appointment
        final int approvedSlotNumber = 1;
        serviceFacade.approveAppointment(targetPerson, receipient, appointmentId, approvedSlotNumber, "approved");

        // get for reply - approved slot is absent
        final AppointmentForReplyTO appointmentForReply = serviceFacade.getAppointmentForReply(appointmentId, targetPersonForCheck);
        for (final AppointmentSlotTO slot : appointmentForReply.getSlots()) {
            if (slot.getSlotNumber() == approvedSlotNumber) {
                fail("Already approved slot shouldn't be available for reply.");
            }
        }
	}

    @Test
    public void testTimeOffset() {
        final AppointmentForEditTO newAppointment = createTestAppointment("new appointment", "appointment description", 1);
        final XMLGregorianCalendar startTime = newAppointment.getSlots().get(0).getStartTime();
        
        assertEquals(10, startTime.getHour());
        assertEquals(0, startTime.getMinute());

        final AppointmentReceipientTO appointmentReceipient = newAppointment.getReceipients().get(0);
        final String receipient = appointmentReceipient.getReceipients().get(0);
        final String targetPerson = appointmentReceipient.getTargetPerson();
        final Long appointmentId = serviceFacade.storeAppointment(newAppointment);
        
        List<AppointmentWithTarget> appointments = serviceFacade.getAssignedAppointments(receipient);
        assertNotNull(getById(appointments, appointmentId));

        final AppointmentForReplyTO forReply = serviceFacade.getAppointmentForReply(appointmentId, targetPerson);
        final XMLGregorianCalendar forReplyStartTime = forReply.getSlots().get(0).getStartTime();
        
        assertEquals(startTime.getHour(), forReplyStartTime.getHour());
        assertEquals(startTime.getMinute(), forReplyStartTime.getMinute());
        assertEquals("timezone offset", 0, forReplyStartTime.getTimezone());
    }
    
	private <AS extends AppointmentSummary> AS getById(final List<AS> appointments, final Long appointmentId) {
		for (final AS appointment : appointments) {
			if (appointment.getAppointmentId() == appointmentId) {
				return appointment;
			}
		}
		return null;
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
