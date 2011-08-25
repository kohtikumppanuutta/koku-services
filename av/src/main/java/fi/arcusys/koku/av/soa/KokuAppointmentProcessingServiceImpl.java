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
		endpointInterface = "fi.arcusys.koku.av.soa.KokuAppointmentProcessingService",
		targetNamespace = "http://soa.av.koku.arcusys.fi/")
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
	public void approveAppointment(final String targetUserUid, final String userUid, Long appointmentId, int slotNumber, String comment) {
		if (log.isDebugEnabled()) {
			log.debug("approveAppointment: [appointmentId:slotNumber:comment] = [" + appointmentId + ":" + slotNumber + ":" + comment + "]");
		}
		serviceFacade.approveAppointment(targetUserUid, userUid, appointmentId, slotNumber, comment);
	}

	/**
	 * @param appointmentId
	 * @param comment
	 */
	@Override
	public void declineAppointment(final String targetUserUid, final String userUid, Long appointmentId, String comment) {
		if (log.isDebugEnabled()) {
			log.debug("declineAppointment: [appointmentId:comment] = [" + appointmentId + ":" + comment + "]");
		}
		serviceFacade.declineAppointment(targetUserUid, userUid, appointmentId, comment);
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
		return serviceFacade.getAppointmentForEdit(appointmentId);
	}
	
	/**
	 * @param appointmentId
	 * @return
	 */
	@Override
	public AppointmentForReplyTO getAppointmentForReply(Long appointmentId, final String targetUserUid) {
        return serviceFacade.getAppointmentForReply(appointmentId, targetUserUid);
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
		
		return serviceFacade.storeAppointment(appointment);
	}

}
