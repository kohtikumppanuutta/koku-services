package fi.arcusys.koku.av.service.impl;

import static fi.arcusys.koku.common.service.AbstractEntityDAO.FIRST_RESULT_NUMBER;
import static fi.arcusys.koku.common.service.AbstractEntityDAO.MAX_RESULTS_COUNT;

import java.io.IOException;
import java.io.InputStream;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Set;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.EJBException;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.av.soa.AppointmentForEditTO;
import fi.arcusys.koku.av.soa.AppointmentForReplyTO;
import fi.arcusys.koku.av.soa.AppointmentReceipientTO;
import fi.arcusys.koku.av.soa.AppointmentRespondedTO;
import fi.arcusys.koku.av.soa.AppointmentSlotTO;
import fi.arcusys.koku.av.soa.AppointmentSummary;
import fi.arcusys.koku.av.soa.AppointmentSummaryStatus;
import fi.arcusys.koku.av.soa.AppointmentTO;
import fi.arcusys.koku.av.soa.AppointmentUserRejected;
import fi.arcusys.koku.av.soa.AppointmentWithTarget;
import fi.arcusys.koku.common.service.AppointmentDAO;
import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.KokuSystemNotificationsService;
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

//    private static final String APPOINTMENT_CANCELLED_FOR_TARGET_BODY = "Aihe: {0}. Henkilön {1} tapaaminen on peruuttu käyttäjän {2} toimesta";
//    private static final String APPOINTMENT_CANCELLED_WHOLE_BODY = "Tapaaminen \"{0}\" on peruutettu";
//    private static final String APPOINTMENT_CANCELLED_SUBJECT = "Tapaaminen on peruutettu";
//
//    private static final String APPOINTMENT_APPROVED_BODY = "Aihe: {0}. Henkilön {1} tapaaminen on hyväksytty käyttäjän {2} toimesta.";
//    private static final String APPOINTMENT_APPROVED_SUBJECT = "Tapaaminen on hyväksytty";
//
//    private static final String APPOINTMENT_DECLINED_BODY = "Aihe: {0}. Henkilön {1} tapaaminen on hylätty käyttäjän {2} toimesta.";
//    private static final String APPOINTMENT_DECLINED_SUBJECT = "Tapaaminen on hylätty";
//
//    private static final String APPOINTMENT_RECEIVED_BODY = "Sinulle on uusi tapaaminen. Aihe: {0}.";
//    private static final String APPOINTMENT_RECEIVED_SUBJECT = "Uusi tapaaminen";

    private static final String APPOINTMENT_CANCELLED_FOR_TARGET_BODY = "appointment.cancelled_for_target.body";
    private static final String APPOINTMENT_CANCELLED_WHOLE_BODY = "appointment.cancelled_whole.body";
    private static final String APPOINTMENT_CANCELLED_SUBJECT = "appointment.cancelled.subject";

    private static final String APPOINTMENT_APPROVED_BODY = "appointment.approved.body";
    private static final String APPOINTMENT_APPROVED_SUBJECT = "appointment.approved.subject";

    private static final String APPOINTMENT_DECLINED_BODY = "appointment.declined.body";
    private static final String APPOINTMENT_DECLINED_SUBJECT = "appointment.declined.subject";

    private static final String APPOINTMENT_RECEIVED_BODY = "appointment.received.body";
    private static final String APPOINTMENT_RECEIVED_SUBJECT = "appointment.received.subject";

    private final static Logger logger = LoggerFactory.getLogger(AppointmentServiceFacadeImpl.class);
    
	@EJB
	private AppointmentDAO appointmentDAO;
	
	@EJB
	private UserDAO userDao;
	
	@EJB
	private TargetPersonDAO targetPersonDao;
	
	@EJB
	private KokuSystemNotificationsService notificationService;

	private String notificationsBundleName = "appointment.msg";
	private Properties messageTemplates;
	
	@PostConstruct
	public void init() {
	    messageTemplates = new Properties();
	    try {
	        final InputStream in = getClass().getClassLoader().getResourceAsStream(notificationsBundleName + ".properties");
	        try {
	            messageTemplates.load(in);
	        } finally {
	            in.close();
	        }
	    } catch (IOException e) {
	        throw new EJBException("Incorrect configuration, failed to load message templates:", e);
	    }
	} 
	
	/**
	 * @param appointmentId
	 * @param slotNumber
	 * @param comment
	 */
	@Override
	public void approveAppointment(final String targetPersonUid, final String userUid, final Long appointmentId, final int slotNumber, final String comment) {
		final Appointment appointment = loadAppointment(appointmentId);
        
        if(appointment.getSlotByNumber(slotNumber) == null) {
            throw new IllegalStateException("There is no slot with number " + slotNumber + " in appointment id = " + appointmentId);
        }

        final AppointmentResponse response = processReply(targetPersonUid, userUid, comment, appointment);
        response.setSlotNumber(slotNumber);
        response.setStatus(AppointmentResponseStatus.Accepted);
		
		appointmentDAO.update(appointment);
        notificationService.sendNotification(getValueFromBundle(APPOINTMENT_APPROVED_SUBJECT), 
                Collections.singletonList(response.getAppointment().getSender().getUid()), 
                MessageFormat.format(getValueFromBundle(APPOINTMENT_APPROVED_BODY), 
                        new Object[] {response.getAppointment().getSubject(), 
                    response.getTarget().getTargetUser().getCitizenPortalName(), response.getReplier().getCitizenPortalName()}));
	}

	/**
	 * @param appointmentId
	 * @param comment
	 */
	@Override
	public void declineAppointment(final String targetPersonUid, final String userUid, final Long appointmentId, final String comment) {
		final Appointment appointment = loadAppointment(appointmentId);
        
        final AppointmentResponse response = processReply(targetPersonUid, userUid, comment, appointment);
        response.setStatus(AppointmentResponseStatus.Rejected);
		
		appointmentDAO.update(appointment);
        notificationService.sendNotification(getValueFromBundle(APPOINTMENT_DECLINED_SUBJECT), 
                Collections.singletonList(response.getAppointment().getSender().getUid()), 
                MessageFormat.format(getValueFromBundle(APPOINTMENT_DECLINED_BODY), 
                        new Object[] {response.getAppointment().getSubject(), 
                    response.getTarget().getTargetUser().getCitizenPortalName(), response.getReplier().getCitizenPortalName()}));
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
		final Appointment appointment = loadAppointment(appointmentId);
		
		final AppointmentTO appointmentTO = new AppointmentTO();
		convertAppointmentToDTO(appointment, appointmentTO);
		
		appointmentTO.setStatus(AppointmentSummaryStatus.valueOf(getSummaryAppointmentStatus(appointment)));
		if (appointment.getStatus() == AppointmentStatus.Cancelled) {
		    appointmentTO.setCancelComment(appointment.getCancelComment());
		}

        appointmentTO.setRecipients(getReceipientsDTOByAppointment(appointment, true));

		final HashMap<Integer, String> acceptedSlots = new HashMap<Integer, String>();
        final List<String> usersRejected = new ArrayList<String>();
        final List<AppointmentUserRejected> usersRejectedWithComments = new ArrayList<AppointmentUserRejected>();
		
		for (final AppointmentResponse response : appointment.getResponses()) {
		    final User targetUser = response.getTarget().getTargetUser();
            final String targetPersonUid = getDisplayName(targetUser);
            if (response.getStatus() == AppointmentResponseStatus.Accepted) {
	            acceptedSlots.put(response.getSlotNumber(), targetPersonUid);
		    } else {
		        usersRejected.add(targetPersonUid);
		        final AppointmentUserRejected userRejected = new AppointmentUserRejected();
		        userRejected.setRejectComment(response.getComment());
                userRejected.setUserDisplayName(getDisplayName(targetUser));
                userRejected.setUserUid(targetUser.getUid());
                usersRejectedWithComments.add(userRejected);
		    }
		}
        appointmentTO.setAcceptedSlots(acceptedSlots);
        appointmentTO.setUsersRejected(usersRejected);
        appointmentTO.setUsersRejectedWithComments(usersRejectedWithComments);
		
		appointmentTO.setSlots(getSlotTOsByAppointment(appointment));
		
		return appointmentTO;
	}

    private String getDisplayName(final User user) {
        if (user == null) {
            return "";
        }
        if (user.getCitizenPortalName() != null && !user.getCitizenPortalName().isEmpty()) {
            return user.getCitizenPortalName();
        } else {
            return user.getEmployeePortalName();
        }
    }

    private List<AppointmentReceipientTO> getReceipientsDTOByAppointment(
            final Appointment appointment, final boolean displayName) {
        final List<AppointmentReceipientTO> recipients = new ArrayList<AppointmentReceipientTO>();
        for (final TargetPerson receipient : appointment.getRecipients()) {
            final AppointmentReceipientTO receipientTO = new AppointmentReceipientTO();
            if (displayName) {
                receipientTO.setTargetPerson(getDisplayName(receipient.getTargetUser()));
            } else {
                receipientTO.setTargetPerson(receipient.getTargetUser().getUid());
            }
            final List<String> guardians = new ArrayList<String>();
            for (final User guardian : receipient.getGuardians()) {
                if (displayName) {
                    guardians.add(getDisplayName(guardian));
                } else {
                    guardians.add(guardian.getUid());
                }
            }
            receipientTO.setReceipients(guardians);
            recipients.add(receipientTO);
        }
        return recipients;
    }

	private List<AppointmentSlotTO> getSlotTOsByAppointment(final Appointment appointment) {
		final List<AppointmentSlotTO> result = new ArrayList<AppointmentSlotTO>();
		for (final AppointmentSlot slot : appointment.getSlots()) {
			result.add(getSlotTOBySlot(slot));
		}
		Collections.sort(result, new Comparator<AppointmentSlotTO>() {

            @Override
            public int compare(AppointmentSlotTO o1, AppointmentSlotTO o2) {
                final int datesCompare = o1.getAppointmentDate().compare(o2.getAppointmentDate());
                if (datesCompare != 0) {
                    return datesCompare;
                } else {
                    return o1.getStartTime().compare(o2.getStartTime());
                }
            }
        });
		return result;
	}

    private AppointmentSlotTO getSlotTOBySlot(final AppointmentSlot slot) {
        final AppointmentSlotTO slotTO = new AppointmentSlotTO();
        slotTO.setSlotNumber(slot.getSlotNumber());
        slotTO.setAppointmentDate(CalendarUtil.getXmlDate(slot.getAppointmentDate()));
        slotTO.setStartTime(CalendarUtil.getXmlTime(slot.getAppointmentDate(), slot.getStartTime()));
        slotTO.setEndTime(CalendarUtil.getXmlTime(slot.getAppointmentDate(), slot.getEndTime()));
        slotTO.setLocation(slot.getLocation());
        slotTO.setComment(slot.getComment());
        return slotTO;
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
			final AppointmentSummary appointmentTO = convertAppointmentToDTO(appointment, new AppointmentSummary());
			appointmentTO.setStatus(AppointmentSummaryStatus.valueOf(getSummaryAppointmentStatus(appointment)));
            result.add(appointmentTO);
		}
		return result;
	}

	/**
	 * @param userUid
	 * @return
	 */
	@Override
	public List<AppointmentWithTarget> getAssignedAppointments(final String userUid) {
		return getAssignedAppointments(userUid, FIRST_RESULT_NUMBER, FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);		
	}
	
	@Override
	public List<AppointmentWithTarget> getAssignedAppointments(final String userUid, final int startNum, final int maxNum) {
		final List<AppointmentWithTarget> result = new ArrayList<AppointmentWithTarget>();
		for (final Appointment appointment : appointmentDAO.getAssignedAppointments(userDao.getOrCreateUser(userUid), startNum, maxNum - startNum + 1)) {
		    for (final TargetPerson target : appointment.getRecipients()) {
		        if (target.getGuardianByUid(userUid) != null) {
		            final AppointmentWithTarget appointmentTO = convertAppointmentToDTO(appointment, new AppointmentWithTarget());
		            appointmentTO.setTargetPerson(target.getTargetUser().getUid());
                    result.add(appointmentTO);
		        }
		    }
		}
		return result;
	}

	private <AS extends AppointmentSummary> AS convertAppointmentToDTO(final Appointment appointment, final AS appointmentSummary) {
		appointmentSummary.setAppointmentId(appointment.getId());
		appointmentSummary.setDescription(appointment.getDescription());
		appointmentSummary.setSubject(appointment.getSubject());
		appointmentSummary.setSender(getDisplayName(appointment.getSender()));
		appointmentSummary.setStatus(AppointmentSummaryStatus.valueOf(appointment.getStatus()));
		
		return appointmentSummary;
	}

    private AppointmentStatus getSummaryAppointmentStatus(
            final Appointment appointment) {
        if (appointment.getStatus() != AppointmentStatus.Cancelled && appointment.getResponses() != null && !appointment.getResponses().isEmpty()) {
            for (final AppointmentResponse response : appointment.getResponses()) {
                if (response.getStatus() == AppointmentResponseStatus.Accepted) {
                    return AppointmentStatus.Approved;
                }
            }
            return AppointmentStatus.Cancelled;
        }
        return appointment.getStatus();
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
			final long appointmentId = appointmentTO.getAppointmentId();
            appointment = loadAppointment(appointmentId);
		} else {
			appointment = new Appointment();
		}
		fillAppointmentByDto(appointmentTO, appointment);
		final Appointment result;
		if (appointment.getId() == null) {
		    result = appointmentDAO.create(appointment);
		} else {
			result = appointmentDAO.update(appointment);
		}
		
        notificationService.sendNotification(getValueFromBundle(APPOINTMENT_RECEIVED_SUBJECT), 
                getReceipienUids(appointment.getRecipients()), 
                MessageFormat.format(getValueFromBundle(APPOINTMENT_RECEIVED_BODY), new Object[] {result.getSubject()}));

        return result.getId();
	}

    protected List<String> getReceipienUids(final Set<TargetPerson> recipients) {
        final Set<String> result = new HashSet<String>();
        for (final TargetPerson person : recipients) {
            for (final User user : person.getGuardians()) {
                result.add(user.getUid());
            }
        }
        return new ArrayList<String>(result);
    }

    /**
	 * @param appointmentId
	 */
	@Override
	public void removeAppointment(long appointmentId) {
		final Appointment appointment = loadAppointment(appointmentId);
		appointment.setStatus(AppointmentStatus.Cancelled);
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
        
        appointmentTO.setReceipients(getReceipientsDTOByAppointment(appointment, false));

        return appointmentTO;
    }

    private Appointment fillAppointmentTOForReply(final Long appointmentId, final AppointmentForReplyTO appointmentTO) {
        final Appointment appointment = loadAppointment(appointmentId);
        
        convertAppointmentToDTO(appointment, appointmentTO);
        appointmentTO.setSlots(getSlotTOsByAppointment(appointment));
        //userUid fix
//        appointmentTO.setSender(appointment.getSender().getUid());
        
        return appointment;
    }

    private Appointment loadAppointment(final Long appointmentId) {
        final Appointment appointment = appointmentDAO.getById(appointmentId);
        if (appointment == null) {
            throw new IllegalArgumentException("Appointment id " + appointmentId + " not found.");
        }
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
        
        final Set<Integer> acceptedSlots = new HashSet<Integer>();
        for (final AppointmentResponse response : appointment.getResponses()) {
            if (response.getStatus() == AppointmentResponseStatus.Accepted) {
                acceptedSlots.add(response.getSlotNumber());
            }
        }
        
        for (final Iterator<AppointmentSlotTO> iter = appointmentTO.getSlots().iterator(); iter.hasNext();) {
            if (acceptedSlots.contains(iter.next().getSlotNumber())) {
                iter.remove();
            }
        }
        
        return appointmentTO;
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentWithTarget> getRespondedAppointments(String userUid, int startNum, int maxNum) {
        return convertResponsesToAppointmentWithTarget(appointmentDAO.getAppointmentResponses(userDao.getOrCreateUser(userUid), startNum, maxNum - startNum + 1));
    }

    protected List<AppointmentWithTarget> convertResponsesToAppointmentWithTarget(
            final List<AppointmentResponse> appointmentResponses) {
        final List<AppointmentWithTarget> result = new ArrayList<AppointmentWithTarget>();
        for (final AppointmentResponse response : appointmentResponses) {
            final AppointmentWithTarget appointmentTO = new AppointmentWithTarget();
            convertAppointmentToDTO(response.getAppointment(), appointmentTO);
            appointmentTO.setTargetPerson(response.getTarget().getTargetUser().getUid());
            appointmentTO.setStatus(getAppointmentStatusByResponse(response));
            result.add(appointmentTO);
        }
        return result;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalRespondedAppointments(String user) {
        return getIntValue(appointmentDAO.getTotalRespondedAppointments(userDao.getOrCreateUser(user)));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalCreatedAppointments(String user) {
        return getIntValue(appointmentDAO.getTotalCreatedAppointments(userDao.getOrCreateUser(user)));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalProcessedAppointments(String user) {
        return getIntValue(appointmentDAO.getTotalProcessedAppointments(userDao.getOrCreateUser(user)));
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentSummary> getCreatedAppointments(String user, int startNum, int maxNum) {
        return getEmployeeSummaryByAppointments(appointmentDAO.getCreatedAppointments(userDao.getOrCreateUser(user), startNum, maxNum - startNum + 1));
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentSummary> getProcessedAppointments(String user, int startNum, int maxNum) {
        return getEmployeeSummaryByAppointments(appointmentDAO.getProcessedAppointments(userDao.getOrCreateUser(user), startNum, maxNum - startNum + 1));
    }

    private List<AppointmentSummary> getEmployeeSummaryByAppointments(List<Appointment> appointments) {
        final List<AppointmentSummary> result = new ArrayList<AppointmentSummary>();
        for (final Appointment appointment : appointments) {
            final AppointmentSummary appointmentTO = convertAppointmentToDTO(appointment, new AppointmentSummary());
            appointmentTO.setStatus(AppointmentSummaryStatus.valueOf(getSummaryAppointmentStatus(appointment)));
            result.add(appointmentTO);
        }
        return result;
    }

    /**
     * @param appointmentId
     * @return
     */
    @Override
    public AppointmentRespondedTO getAppointmentRespondedById(long appointmentId, final String targetPerson) {
        final AppointmentResponse response = getAppointmentResponse(appointmentId, targetPerson);
        
        final Appointment appointment = response.getAppointment();
        
        final AppointmentRespondedTO appointmentTO = new AppointmentRespondedTO();
        convertAppointmentToDTO(appointment, appointmentTO);
        appointmentTO.setTargetPerson(getDisplayName(response.getTarget().getTargetUser()));
        appointmentTO.setStatus(getAppointmentStatusByResponse(response));
        appointmentTO.setReplier(getDisplayName(response.getReplier()));
        appointmentTO.setReplierComment(response.getComment());
        if (appointment.getStatus() == AppointmentStatus.Cancelled) {
            appointmentTO.setEmployeesCancelComent(appointment.getCancelComment());
        }
        if (appointmentTO.getStatus() == AppointmentSummaryStatus.Approved) {
            appointmentTO.setApprovedSlot(getSlotTOBySlot(appointment.getSlotByNumber(response.getSlotNumber())));
        }
        
        return appointmentTO;
    }

    private AppointmentSummaryStatus getAppointmentStatusByResponse(
            final AppointmentResponse response) {
        return response.getStatus() == AppointmentResponseStatus.Accepted && response.getAppointment().getStatus() != AppointmentStatus.Cancelled ?
                AppointmentSummaryStatus.Approved :
                AppointmentSummaryStatus.Cancelled;
    }

    private AppointmentResponse getAppointmentResponse(long appointmentId,
            final String targetPerson) {
        final Appointment appointment = loadAppointment(appointmentId);
        
        final AppointmentResponse response = appointment.getResponseForTargetPerson(targetPerson);
        if (response == null) {
            throw new IllegalArgumentException("Can't find response to appointment ID " + appointmentId + " for target person " + targetPerson);
        }
        return response;
    }

    /**
     * @param targetUser
     * @param user
     * @param appointmentId
     * @param comment
     */
    @Override
    public void cancelAppointment(String targetUser, String user, long appointmentId, String comment) {
        final AppointmentResponse response = getAppointmentResponse(appointmentId, targetUser);
        final String replierUid = response.getReplier().getUid();
        if (!replierUid.equals(user)) {
            logger.warn("Appointment id " + appointmentId + " replied by " + replierUid + " but cancelled by " + user);
        }
        
        response.setStatus(AppointmentResponseStatus.Rejected);
        response.setComment(comment);
        appointmentDAO.update(response.getAppointment());
        notificationService.sendNotification(getValueFromBundle(APPOINTMENT_CANCELLED_SUBJECT), 
                Collections.singletonList(response.getAppointment().getSender().getUid()), 
                MessageFormat.format(getValueFromBundle(APPOINTMENT_CANCELLED_FOR_TARGET_BODY), new Object[] {
                    response.getAppointment().getSubject(), response.getTarget().getTargetUser().getCitizenPortalName(), 
                    userDao.getOrCreateUser(user).getCitizenPortalName()}));
    }

    protected String getValueFromBundle(final String bundleKey) {
        return messageTemplates.getProperty(bundleKey);
    }

    /**
     * @param appointmentId
     * @param comment
     */
    @Override
    public void cancelWholeAppointment(long appointmentId, String comment) {
        final Appointment appointment = loadAppointment(appointmentId);
        
        appointment.setStatus(AppointmentStatus.Cancelled);
        appointment.setCancelComment(comment);
        appointmentDAO.update(appointment);

        final Set<String> notificationReceivers = new HashSet<String>();
        for (final TargetPerson target : appointment.getRecipients()) {
            final AppointmentResponse response = appointment.getResponseForTargetPerson(target.getTargetUser().getUid());
            if (response != null) {
                notificationReceivers.add(response.getReplier().getUid());
            } else {
                for (final User guardian : target.getGuardians()) {
                    notificationReceivers.add(guardian.getUid());
                }
            }
        }
        notificationService.sendNotification(getValueFromBundle(APPOINTMENT_CANCELLED_SUBJECT), 
                new ArrayList<String>(notificationReceivers), 
                MessageFormat.format(getValueFromBundle(APPOINTMENT_CANCELLED_WHOLE_BODY), new Object[] {appointment.getSubject()}));
    }

    /**
     * @param userUid
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentWithTarget> getOldAppointments(String userUid, int startNum, int maxNum) {
        return convertResponsesToAppointmentWithTarget(appointmentDAO.getOldAppointmentResponses(userDao.getOrCreateUser(userUid), startNum, maxNum - startNum + 1));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalOldAppointments(String user) {
        return getIntValue(appointmentDAO.getTotalOldRespondedAppointments(userDao.getOrCreateUser(user)));
    }
}
