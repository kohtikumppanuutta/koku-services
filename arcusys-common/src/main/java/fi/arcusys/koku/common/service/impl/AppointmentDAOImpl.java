package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.AppointmentDAO;
import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
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
		final HashMap<String, Object> params = getCommonSearchParams(user, statuses);
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
}
