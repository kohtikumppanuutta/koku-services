package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import java.util.Arrays;

import fi.arcusys.koku.common.service.AppointmentDAO;
import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponse;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponseStatus;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.TargetPerson;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@Stateless
public class AppointmentDAOImpl extends AbstractEntityDAOImpl<Appointment> implements AppointmentDAO {
	/**
	 * @param clazz
	 */
	public AppointmentDAOImpl() {
		super(Appointment.class);
	}
	
	@Override
	public Appointment create(Appointment entity) {
	    final Set<TargetPerson> receipients = new HashSet<TargetPerson>(); 
	    for (final TargetPerson receipient : entity.getRecipients()) {
	        receipients.add(em.merge(receipient));
	    } 
	    entity.setRecipients(receipients);
	    return super.create(entity);
	}
	
	public List<Appointment> getUserAppointments(final User user, final Set<AppointmentStatus> statuses) {
		return getUserAppointments(user, statuses, FIRST_RESULT_NUMBER, FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);
	}

	public List<Appointment> getUserAppointments(final User user, final Set<AppointmentStatus> statuses, final int startNum, final int maxNum) {
		return getAppointmentsByUserAndStatus(user, statuses, "findAppointmentByUserAndStatuses", startNum, maxNum);
	}

	public List<Appointment> getAssignedAppointments(final User user) {
		return getAssignedAppointments(user, FIRST_RESULT_NUMBER, FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);
	}

	public List<Appointment> getAssignedAppointments(final User user, final int startNum, final int maxNum) {
		return getAppointmentsByUserAndStatus(user, getAssignedAppointmentsStatus(), "findAssignedAppointments", startNum, maxNum);
	}

	private Set<AppointmentStatus> getAssignedAppointmentsStatus() {
		return Collections.singleton(AppointmentStatus.Created);
	}

	private List<Appointment> getAppointmentsByUserAndStatus(final User user,
			final Set<AppointmentStatus> statuses, final String queryName,
			final int startNum, final int maxResults) {
		if (user == null || statuses == null || statuses.isEmpty()) {
			return Collections.emptyList();
		}
		final Map<String, Object> params = getCommonSearchParams(user, statuses);
		return getResultList(queryName, params, startNum, maxResults);
	}

	private HashMap<String, Object> getCommonSearchParams(final User user, final Set<AppointmentStatus> statuses) {
		final HashMap<String, Object> params = new HashMap<String, Object>();
		params.put("user", user);
		params.put("statuses", statuses);
		return params;
	}

	/**
	 * @param user
	 * @param statuses
	 * @return
	 */
	@Override
	public Long getTotalAppointments(final User user, final Set<AppointmentStatus> statuses) {
		return getSingleResult("countAppointmentsByUserAndStatuses", getCommonSearchParams(user, statuses));
	}

	/**
	 * @param user
	 * @return
	 */
	@Override
	public Long getTotalAssignedAppointments(User user) {
		return getSingleResult("countAssignedAppointments", getCommonSearchParams(user, getAssignedAppointmentsStatus()));
	}

    /**
     * @param user
     * @return
     */
    @Override
    public List<Appointment> getProcessedAppointments(User user, final int startNum, final int maxResults) {
        return getResultList("findProcessedAppointmentsBySender", Collections.singletonMap("sender", user), startNum, maxResults);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalProcessedAppointments(User user) {
        return getSingleResult("countProcessedAppointmentsBySender", Collections.singletonMap("sender", user));
    }

    /**
     * @param user
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<Appointment> getCreatedAppointments(User user, int startNum, int maxResults) {
        return getResultList("findCreatedAppointmentsBySender", Collections.singletonMap("sender", user), startNum, maxResults);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalCreatedAppointments(User user) {
        return getSingleResult("countCreatedAppointmentsBySender", Collections.singletonMap("sender", user));
    }

    /**
     * @param user
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<AppointmentResponse> getAppointmentResponses(User user,
            int startNum, int maxResults) {
        return getResultList("findAppointmentResponsesByUser", getRespondedAppointmentsParams(user), startNum, maxResults);
    }

    protected Map<String, Object> getRespondedAppointmentsParams(User user) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("user", user);
        params.put("appointment_approved", Arrays.<AppointmentStatus>asList(AppointmentStatus.Created, AppointmentStatus.Approved));
        params.put("reply_approved", AppointmentResponseStatus.Accepted);
        return params;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalRespondedAppointments(User user) {
        return getSingleResult("countAppointmentResponsesByUser", getRespondedAppointmentsParams(user));
    }

    /**
     * @param user
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<AppointmentResponse> getOldAppointmentResponses(User user, int startNum, int maxResults) {
        return getResultList("findOldAppointmentResponsesByUser", getRespondedAppointmentsParams(user), startNum, maxResults);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalOldRespondedAppointments(User user) {
        return getSingleResult("countOldAppointmentResponsesByUser", getRespondedAppointmentsParams(user));
    }
}
