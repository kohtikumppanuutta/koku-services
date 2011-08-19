package fi.arcusys.koku.av.soa;

import java.util.Arrays;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebParam;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@Stateless
@WebService(serviceName = "KokuAppointmentProcessingService", portName = "KokuAppointmentProcessingServicePort", 
		endpointInterface = "fi.arcusys.koku.kv.soa.KokuAppointmentProcessingService",
		targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public class KokuAppointmentProcessingServiceImpl implements KokuAppointmentProcessingService {
	private static final Logger log = LoggerFactory.getLogger(KokuAppointmentProcessingServiceImpl.class);
	
	@EJB
	private AppointmentServiceFacade serviceFacade;

	/**
	 * @param appointmentId
	 * @param slotNumber
	 * @param comment
	 */
	@Override
	public void approveAppointment(final String userUid, Long appointmentId, int slotNumber, String comment) {
		if (log.isDebugEnabled()) {
			log.debug("approveAppointment: [appointmentId:slotNumber:comment] = [" + appointmentId + ":" + slotNumber + ":" + comment + "]");
		}
		serviceFacade.approveAppointment(userUid, appointmentId, slotNumber, comment);
	}

	/**
	 * @param appointmentId
	 * @param comment
	 */
	@Override
	public void declineAppointment(final String userUid, Long appointmentId, String comment) {
		if (log.isDebugEnabled()) {
			log.debug("declineAppointment: [appointmentId:comment] = [" + appointmentId + ":" + comment + "]");
		}
		serviceFacade.declineAppointment(userUid, appointmentId, comment);
	}

	/**
	 * @param appointmentId
	 * @return
	 */
	@Override
	public AppointmentForEditTO getAppointment(Long appointmentId) {
		if (log.isDebugEnabled()) {
			log.debug("getAppointment: [appointmentId] = [" + appointmentId + "]");
		}
		// TODO: fix me
//		return serviceFacade.getAppointment(appointmentId);
		
		return getAppointment_StubVersion(appointmentId);
		
	}
	
	/**
	 * @param appointmentId
	 * @return
	 */
	@Override
	public AppointmentForReplyTO getAppointmentForReply(Long appointmentId) {
        // TODO: fix me
        if (appointmentId == 1L) {
            return createTestAppointment(1);
        } else  {
            throw new IllegalArgumentException("Appointment with ID " + appointmentId + " is not found.");
        }
	}

	private AppointmentForEditTO getAppointment_StubVersion(Long appointmentId) {
		// TODO Auto-generated method stub
		if (appointmentId == null) {
			return null;
		}

		if (appointmentId == 1L) {
			return createTestAppointment(1);
		} else if (appointmentId == 2L) {
			final AppointmentForEditTO appointment = createTestAppointment(2);
			appointment.setApprovedSlotNumber(1);
			appointment.setReplier("Kalle Kuntalainen");
			appointment.setStatus("Approved");
			appointment.setReplierComment("replier's comment here");
			return appointment;
		} else {
			return null;
		}
	}

	private AppointmentForEditTO createTestAppointment(int appointmentId) {
		final AppointmentForEditTO appointment = new AppointmentForEditTO();
		
		appointment.setAppointmentId(appointmentId);
		appointment.setSubject("Test appointment");
		appointment.setDescription("Static appointment from web service stub");
		appointment.setSender("Ville Virkamies");
		final AppointmentReceipientTO receipient = new AppointmentReceipientTO();
		receipient.setTargetPerson("Lassi Lapsi");
		receipient.setReceipients(Arrays.asList("Kalle Kuntalainen", "Kirsi Kuntalainen"));
        appointment.setReceipients(Arrays.asList(receipient));

		appointment.setSlots(Arrays.asList(createTestSlot(appointmentId, 1), createTestSlot(appointmentId, 2)));
		return appointment;
	}

	private AppointmentSlotTO createTestSlot(int appointmentId, int slotNumber) {
		final GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		
		final AppointmentSlotTO slotTO = new AppointmentSlotTO();
		slotTO.setAppointmentId(appointmentId);
		try {
			final DatatypeFactory datatypeFactory = DatatypeFactory.newInstance();
			slotTO.setAppointmentDate(datatypeFactory.newXMLGregorianCalendar(calendar));
			calendar.set(Calendar.HOUR_OF_DAY, 9 + slotNumber);
			slotTO.setStartTime(datatypeFactory.newXMLGregorianCalendar(calendar));
			calendar.set(Calendar.MINUTE, 15);
			slotTO.setEndTime(datatypeFactory.newXMLGregorianCalendar(calendar));
		} catch (DatatypeConfigurationException e) {
			log.error(null, e);
		}
		slotTO.setComment("test comment");
		slotTO.setLocation("room #" + slotNumber);
		slotTO.setSlotNumber(slotNumber);
		return slotTO;
	}

	/**
	 * @param appointment
	 * @return
	 */
	@Override
	public Long storeAppointment(AppointmentForEditTO appointment) {
		if (log.isDebugEnabled()) {
			log.debug("storeAppointment: [appointmentId] = [" + (appointment == null ? "NULL-appointment" : appointment.getAppointmentId()) + "]");
		}
		
		// TODO: fix me
//		return serviceFacade.storeAppointment(appointment);
		
		return 1L;
	}

}
