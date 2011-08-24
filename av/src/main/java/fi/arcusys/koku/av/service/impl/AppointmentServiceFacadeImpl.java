package fi.arcusys.koku.av.service.impl;

import static fi.arcusys.koku.common.service.AbstractEntityDAO.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.av.soa.AppointmentForEditTO;
import fi.arcusys.koku.av.soa.AppointmentForReplyTO;
import fi.arcusys.koku.av.soa.AppointmentReceipientTO;
import fi.arcusys.koku.av.soa.AppointmentSlotTO;
import fi.arcusys.koku.av.soa.AppointmentSummary;
import fi.arcusys.koku.av.soa.AppointmentTO;
import fi.arcusys.koku.common.service.AbstractEntityDAO;
import fi.arcusys.koku.common.service.AppointmentDAO;
import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.TargetPersonDAO;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponse;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponseStatus;
import fi.arcusys.koku.common.service.datamodel.AppointmentSlot;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.TargetPerson;
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
	
	@EJB
	private TargetPersonDAO targetPersonDao;

	/**
	 * @param appointmentId
	 * @param slotNumber
	 * @param comment
	 */
	@Override
	public void approveAppointment(final String targetPersonUid, final String userUid, final Long appointmentId, final int slotNumber, final String comment) {
		final Appointment appointment = appointmentDAO.getById(appointmentId);
        if (appointment == null) {
        	throw new IllegalArgumentException("Appointment is not found by id: " + appointmentId);
        }
        
        if(appointment.getSlotByNumber(slotNumber) == null) {
            throw new IllegalStateException("There is no slot with number " + slotNumber + " in appointment id = " + appointmentId);
        }

        final AppointmentResponse response = processReply(targetPersonUid, userUid, comment, appointment);
        response.setSlotNumber(slotNumber);
        response.setStatus(AppointmentResponseStatus.Accepted);
		
		appointmentDAO.update(appointment);
	}

	/**
	 * @param appointmentId
	 * @param comment
	 */
	@Override
	public void declineAppointment(final String targetPersonUid, final String userUid, final Long appointmentId, final String comment) {
		final Appointment appointment = appointmentDAO.getById(appointmentId);
        if (appointment == null) {
        	throw new IllegalArgumentException("Appointment is not found by id: " + appointmentId);
        }
        
        final AppointmentResponse response = processReply(targetPersonUid, userUid, comment, appointment);
        response.setStatus(AppointmentResponseStatus.Rejected);
		
		appointmentDAO.update(appointment);
	}

	private AppointmentResponse processReply(final String targetPersonUid,
            final String userUid, final String comment,
            final Appointment appointment) {
        if (appointment.getStatus() != AppointmentStatus.Created) {
			throw new IllegalStateException("Appointment for approval (id = " + appointment.getId() + ") should be in status 'Created' but it has status " + appointment.getStatus());
		}
        
        for (final AppointmentResponse response : appointment.getResponses() ) {
            if (response.getTarget().getTargetUser().getUid().equals(targetPersonUid)) {
                throw new IllegalStateException("Apppointment id " + appointment.getId() + " already have response for targetPerson " + targetPersonUid);
            }
        }
		
		final TargetPerson targetPerson = appointment.getTargetPersonByUid(targetPersonUid);
		if(targetPerson == null) {
			throw new IllegalStateException("There is no target person with uid '" + userUid + "' in appointment id = " + appointment.getId());
		}
		
		final User replier = targetPerson.getGuardianByUid(userUid);
        if(replier == null) {
            throw new IllegalStateException("There is no guardian with uid '" + userUid + "' for target person " + targetPersonUid);
        }

		final AppointmentResponse response = new AppointmentResponse();
		response.setReplier(replier);
		response.setComment(comment);
		response.setTarget(targetPerson);
		response.setAppointment(appointment);
		appointment.getResponses().add(response);
        return response;
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

        appointmentTO.setRecipients(getReceipientsDTOByAppointment(appointment));

        appointmentTO.setStatus(appointment.getStatus().name());

		final HashMap<Integer, String> acceptedSlots = new HashMap<Integer, String>();
        final List<String> usersRejected = new ArrayList<String>();
		
		for (final AppointmentResponse response : appointment.getResponses()) {
		    final String targetPersonUid = response.getTarget().getTargetUser().getUid();
            if (response.getStatus() == AppointmentResponseStatus.Accepted) {
	            acceptedSlots.put(response.getSlotNumber(), targetPersonUid);
		    } else {
		        usersRejected.add(targetPersonUid);
		    }
		}
        appointmentTO.setAcceptedSlots(acceptedSlots);
        appointmentTO.setUsersRejected(usersRejected);
		
		appointmentTO.setSlots(getSlotTOsByAppointment(appointment));
		
		return appointmentTO;
	}

    private List<AppointmentReceipientTO> getReceipientsDTOByAppointment(
            final Appointment appointment) {
        final List<AppointmentReceipientTO> recipients = new ArrayList<AppointmentReceipientTO>();
        for (final TargetPerson receipient : appointment.getRecipients()) {
            final AppointmentReceipientTO receipientTO = new AppointmentReceipientTO();
            receipientTO.setTargetPerson(receipient.getTargetUser().getUid());
            final List<String> guardians = new ArrayList<String>();
            for (final User guardian : receipient.getGuardians()) {
                guardians.add(guardian.getUid());
            }
            receipientTO.setReceipients(guardians);
            recipients.add(receipientTO);
        }
        return recipients;
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
		
		return appointmentSummary;
	}
	
	private Appointment fillAppointmentByDto(final AppointmentForEditTO appointmentTO, final Appointment appointment) {
		appointment.setDescription(appointmentTO.getDescription());
		appointment.setSubject(appointmentTO.getSubject());
		appointment.setSender(userDao.getOrCreateUser(appointmentTO.getSender()));
		final Set<TargetPerson> recipients = new HashSet<TargetPerson>();
		for (final AppointmentReceipientTO receipient : appointmentTO.getReceipients()) {
			recipients.add(targetPersonDao.getOrCreateTargetPerson(receipient.getTargetPerson(), receipient.getReceipients()));
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
	public Long storeAppointment(AppointmentForEditTO appointmentTO) {
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

    /**
     * @param appointmentId
     * @return
     */
    @Override
    public AppointmentForEditTO getAppointmentForEdit(Long appointmentId) {
        final AppointmentForEditTO appointmentTO = new AppointmentForEditTO();
        final Appointment appointment = fillAppointmentTOForReply(appointmentId, appointmentTO);
        
        appointmentTO.setReceipients(getReceipientsDTOByAppointment(appointment));

        return appointmentTO;
    }

    private Appointment fillAppointmentTOForReply(final Long appointmentId, final AppointmentForReplyTO appointmentTO) {
        final Appointment appointment = appointmentDAO.getById(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment id " + appointmentId + " not found.");
        }
        
        convertAppointmentToDTO(appointment, appointmentTO);
        appointmentTO.setSlots(getSlotTOsByAppointment(appointment));
        return appointment;
    }

    /**
     * @param appointmentId
     * @param targetPersonUid
     * @return
     */
    @Override
    public AppointmentForReplyTO getAppointmentForReply(Long appointmentId, String targetPersonUid) {
        final AppointmentForReplyTO appointmentTO = new AppointmentForReplyTO();
        final Appointment appointment = fillAppointmentTOForReply(appointmentId, appointmentTO);
        
        if (appointment.getTargetPersonByUid(targetPersonUid) == null) {
            throw new IllegalArgumentException("Appointment id " + appointmentId + " doesn't have target person " + targetPersonUid);
        }
        
        if (appointment.getResponseForTargetPerson(targetPersonUid) != null) {
            throw new IllegalArgumentException("Appointment id " + appointmentId + " already have response for target person " + targetPersonUid);
        }
        
        return appointmentTO;
    }
}
