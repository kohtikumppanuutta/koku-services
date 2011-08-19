package fi.arcusys.koku.common.service;

import static junit.framework.Assert.*;

import java.util.Collections;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.AppointmentDAO;
import fi.arcusys.koku.common.service.CommonTestUtil;
import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponse;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.Message;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class AppointmentDAOTest {

    @Autowired
    private AppointmentDAO service;

	@Autowired CommonTestUtil testUtil;

	@Test
	public void testCreateRetrieveDeleteAppointment() {
		final String testSubject = "new appointment";
		final String description = "appointment description";
		final int numberOfSlots = 3;
		
		final Appointment appointment = createTestAppointment(testSubject, description, numberOfSlots);
		assertNotNull("New appointment created: ", appointment);
		assertEquals("Correct subject: ", testSubject, appointment.getSubject());
		assertEquals("Correct content: ", description, appointment.getDescription());
		assertNotNull("Create date added: ", appointment.getCreatedDate());
		assertNotNull("Message have id: ", appointment.getId());
		assertEquals("Status added: ", AppointmentStatus.Created, appointment.getStatus());
		
		final Appointment apntFromService = service.getById(appointment.getId());
		assertNotNull("Appointment retreived by ID: ", apntFromService);
		assertEquals(appointment.getSubject(), apntFromService.getSubject());
		assertEquals(numberOfSlots, apntFromService.getSlots().size());
		
		service.delete(apntFromService);
		assertNull("Appointment removed: ", service.getById(appointment.getId()));
	}

	@Test
	public void listAppointmentsByStatus() {
		final Appointment appointment = createTestAppointment("new appointment", "appointment description", 3);
		
		final List<Appointment> appointments = service.getUserAppointments(appointment.getSender(), Collections.singleton(AppointmentStatus.Created));
		assertTrue(appointments.contains(appointment));
		assertEquals(appointments.size(), service.getTotalAppointments(appointment.getSender(), Collections.singleton(AppointmentStatus.Created)).intValue());
		
		assertFalse(service.getUserAppointments(appointment.getSender(), Collections.singleton(AppointmentStatus.Approved)).contains(appointment));
	}

	@Test
	public void listAssignedAppointments() {
		final Appointment appointment = createTestAppointment("new appointment", "appointment description", 3);
		
		final User receipient = appointment.getRecipients().iterator().next();
		final List<Appointment> appointments = service.getAssignedAppointments(receipient);
		assertFalse(appointments.isEmpty());
		assertTrue(appointments.contains(appointment));
		assertEquals(appointments.size(), service.getTotalAssignedAppointments(receipient).intValue());
	}
	
	private Appointment createTestAppointment(final String testSubject, final String description, int numberOfSlots) {
		return service.create(testUtil.createTestAppointment(testSubject, description, numberOfSlots));
	}
}
