package fi.arcusys.koku.av.service.impl;

import static fi.arcusys.koku.common.service.AbstractEntityDAO.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.av.soa.AppointmentSlotTO;
import fi.arcusys.koku.av.soa.AppointmentSummary;
import fi.arcusys.koku.av.soa.AppointmentTO;
import fi.arcusys.koku.common.service.AbstractEntityDAO;
import fi.arcusys.koku.common.service.AppointmentDAO;
import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponse;
import fi.arcusys.koku.common.service.datamodel.AppointmentSlot;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 22, 2011
 */
@Stateless
public class AppointmentServiceFacadeImpl implements AppointmentServiceFacade {

	@EJB
	private AppointmentDAO appointmentDAO;
	
	@EJB
	private UserDAO userDao;

	/**
	 * @param appointmentId
	 * @param slotNumber
	 * @param comment
	 */
	@Override
	public void approveAppointment(final String userUid, final Long appointmentId, final int slotNumber, final String comment) {
		final Appointment appointment = processReply(userUid, appointmentId, comment);

		if(appointment.getSlotByNumber(slotNumber) == null) {
			throw new IllegalStateException("There is no slot with number " + slotNumber + " in appointment id = " + appointmentId);
		}
		appointment.getResponse().setSlotNumber(slotNumber);
		
		appointment.setStatus(AppointmentStatus.Approved);
		appointmentDAO.update(appointment);
	}

	/**
	 * @param appointmentId
	 * @param comment
	 */
	@Override
	public void declineAppointment(final String userUid, final Long appointmentId, final String comment) {
		final Appointment appointment = processReply(userUid, appointmentId, comment);
		
		appointment.setStatus(AppointmentStatus.Declined);
		appointmentDAO.update(appointment);
	}

	private Appointment processReply(final String userUid,
			final Long appointmentId, final String comment) {
		final Appointment appointment = appointmentDAO.getById(appointmentId);
		if (appointment == null) {
			throw new IllegalArgumentException("Appointment is not found by id: " + appointmentId);
		}
		
		if (appointment.getStatus() != AppointmentStatus.Created) {
			throw new IllegalStateException("Appointment for approval (id = " + appointmentId + ") should be in status 'Created' but it has status " + appointment.getStatus());
		}
		
		final User replier = appointment.getReceipientByUid(userUid);
		if(replier == null) {
			throw new IllegalStateException("There is no receipient with uid '" + userUid + "' in appointment id = " + appointmentId);
		}

		final AppointmentResponse response = new AppointmentResponse();
		response.setReplier(replier);
		response.setComment(comment);
		appointment.setResponse(response);
		return appointment;
	}

	/**
	 * @param appointmentId
	 * @return
	 */
	@Override
	public AppointmentTO getAppointment(final Long appointmentId) {
		final Appointment appointment = appointmentDAO.getById(appointmentId);
		if (appointment == null) {
			throw new IllegalArgumentException("Appointment id " + appointmentId + " not found.");
		}
		
		final AppointmentTO appointmentTO = new AppointmentTO();
		convertAppointmentToDTO(appointment, appointmentTO);

		appointmentTO.setStatus(appointment.getStatus().name());
		if (appointment.getResponse() != null) {
			appointmentTO.setApprovedSlotNumber(appointment.getResponse().getSlotNumber());
			appointmentTO.setReplierComment(appointment.getResponse().getComment());
			final User replier = appointment.getResponse().getReplier();
			appointmentTO.setReplier(replier != null ? replier.getUid() : "");
		}
		
		appointmentTO.setSlots(getSlotTOsByAppointment(appointment));
		
		return appointmentTO;
	}

	private List<AppointmentSlotTO> getSlotTOsByAppointment(final Appointment appointment) {
		final List<AppointmentSlotTO> result = new ArrayList<AppointmentSlotTO>();
		for (final AppointmentSlot slot : appointment.getSlots()) {
			final AppointmentSlotTO slotTO = new AppointmentSlotTO();
			slotTO.setAppointmentId(appointment.getId());
			slotTO.setSlotNumber(slot.getSlotNumber());
			slotTO.setAppointmentDate(CalendarUtil.getXmlDate(slot.getAppointmentDate()));
			slotTO.setStartTime(CalendarUtil.getXmlTime(slot.getAppointmentDate(), slot.getStartTime()));
			slotTO.setEndTime(CalendarUtil.getXmlTime(slot.getAppointmentDate(), slot.getEndTime()));
			slotTO.setLocation(slot.getLocation());
			slotTO.setComment(slot.getComment());
			result.add(slotTO);
		}
		return result;
	}

	/**
	 * @param userUid
	 * @param statuses
	 * @return
	 */
	@Override
	public List<AppointmentSummary> getAppointments(final String userUid, final Set<AppointmentStatus> statuses) {
		return getAppointments(userUid, statuses, FIRST_RESULT_NUMBER, FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);
	}

	@Override
	public List<AppointmentSummary> getAppointments(final String userUid, final Set<AppointmentStatus> statuses, final int startNum, final int maxNum) {
		final List<AppointmentSummary> result = new ArrayList<AppointmentSummary>();
		for (final Appointment appointment : appointmentDAO.getUserAppointments(userDao.getOrCreateUser(userUid), statuses, startNum, maxNum - startNum + 1)) {
			result.add(convertAppointmentToDTO(appointment, new AppointmentSummary()));
		}
		return result;
	}

	/**
	 * @param userUid
	 * @return
	 */
	@Override
	public List<AppointmentSummary> getAssignedAppointments(final String userUid) {
		return getAssignedAppointments(userUid, FIRST_RESULT_NUMBER, FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);		
	}
	
	@Override
	public List<AppointmentSummary> getAssignedAppointments(final String userUid, final int startNum, final int maxNum) {
		final List<AppointmentSummary> result = new ArrayList<AppointmentSummary>();
		for (final Appointment appointment : appointmentDAO.getAssignedAppointments(userDao.getOrCreateUser(userUid), startNum, maxNum - startNum + 1)) {
			result.add(convertAppointmentToDTO(appointment, new AppointmentSummary()));
		}
		return result;
	}

	private AppointmentSummary convertAppointmentToDTO(final Appointment appointment, final AppointmentSummary appointmentSummary) {
		appointmentSummary.setAppointmentId(appointment.getId());
		appointmentSummary.setDescription(appointment.getDescription());
		appointmentSummary.setSubject(appointment.getSubject());
		appointmentSummary.setSender(appointment.getSender().getUid());
		final List<String> recipients = new ArrayList<String>();
		for (final User receipient : appointment.getRecipients()) {
			recipients.add(receipient.getUid());
		}
		appointmentSummary.setRecipients(recipients);
		
		return appointmentSummary;
	}
	
	private Appointment fillAppointmentByDto(final AppointmentTO appointmentTO, final Appointment appointment) {
		appointment.setDescription(appointmentTO.getDescription());
		appointment.setSubject(appointmentTO.getSubject());
		appointment.setSender(userDao.getOrCreateUser(appointmentTO.getSender()));
		final Set<User> recipients = new HashSet<User>();
		for (final String receipient : appointmentTO.getRecipients()) {
			recipients.add(userDao.getOrCreateUser(receipient));
		}
		appointment.setRecipients(recipients);
		
		final Map<Integer, AppointmentSlot> oldSlots = new HashMap<Integer, AppointmentSlot>();
		for (final AppointmentSlot slot : appointment.getSlots()) {
			oldSlots.put(slot.getSlotNumber(), slot);
		}
		final Set<AppointmentSlot> slots = new HashSet<AppointmentSlot>();
		for (final AppointmentSlotTO slotTO : appointmentTO.getSlots()) {
			final AppointmentSlot slot;
			if (oldSlots.containsKey(slotTO.getSlotNumber())) {
				slot = oldSlots.get(slotTO.getSlotNumber());
				oldSlots.remove(slotTO.getSlotNumber());
			} else {
				slot = new AppointmentSlot();
			}
			slot.setAppointmentDate(slotTO.getAppointmentDate().toGregorianCalendar().getTime());
			slot.setComment(slotTO.getComment());
			slot.setStartTime(getMinutes(slotTO.getStartTime()));
			slot.setEndTime(getMinutes(slotTO.getEndTime()));
			slot.setLocation(slotTO.getLocation());
			slot.setSlotNumber(slotTO.getSlotNumber());
			slots.add(slot);
		}
		appointment.setSlots(slots);
		
		return appointment;
	}

	private int getMinutes(final XMLGregorianCalendar calendar) {
		return calendar.getHour() * 60 + calendar.getMinute();
	}

	/**
	 * @param appointment
	 * @return
	 */
	@Override
	public Long storeAppointment(AppointmentTO appointmentTO) {
		final Appointment appointment;
		if (appointmentTO.getAppointmentId() > 0) {
			appointment = appointmentDAO.getById(appointmentTO.getAppointmentId());
			if (appointment == null) {
				throw new IllegalArgumentException("Appointment with ID " + appointmentTO.getAppointmentId() + " not found.");
			}
		} else {
			appointment = new Appointment();
		}
		fillAppointmentByDto(appointmentTO, appointment);
		if (appointment.getId() == null) {
			return appointmentDAO.create(appointment).getId();
		} else {
			appointmentDAO.update(appointment);
			return appointment.getId();
		}
	}

	/**
	 * @param appointmentId
	 */
	@Override
	public void removeAppointment(long appointmentId) {
		final Appointment appointment = appointmentDAO.getById(appointmentId);
		if (appointment == null) {
			throw new IllegalArgumentException("Appointment with ID " + appointmentId + " not found.");
		}
		appointment.setStatus(AppointmentStatus.Deleted);
		appointmentDAO.update(appointment);
	}

	/**
	 * @param user
	 * @param statuses
	 * @return
	 */
	@Override
	public int getTotalAppointments(String user, Set<AppointmentStatus> statuses) {
		return getIntValue(appointmentDAO.getTotalAppointments(userDao.getOrCreateUser(user), statuses));
	}

	/**
	 * @param user
	 * @return
	 */
	@Override
	public int getTotalAssignedAppointments(String user) {
		return getIntValue(appointmentDAO.getTotalAssignedAppointments(userDao.getOrCreateUser(user)));
	}

	private int getIntValue(final Long value) {
		return value != null ? value.intValue() : 0;
	}
}
