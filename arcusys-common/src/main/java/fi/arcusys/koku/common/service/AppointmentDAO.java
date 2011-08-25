package fi.arcusys.koku.common.service;

import java.util.List;
import java.util.Set;

import fi.arcusys.koku.common.service.datamodel.Appointment;
import fi.arcusys.koku.common.service.datamodel.AppointmentResponse;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
public interface AppointmentDAO extends AbstractEntityDAO<Appointment>{

	/**
	 * @param string
	 * @param created
	 * @return
	 */
	List<Appointment> getUserAppointments(final User user, final Set<AppointmentStatus> statuses);

	List<Appointment> getUserAppointments(final User user, final Set<AppointmentStatus> statuses, final int startNum, final int maxNum);

	/**
	 * @param next
	 * @return
	 */
	List<Appointment> getAssignedAppointments(final User user);

	List<Appointment> getAssignedAppointments(final User user, final int startNum, final int maxNum);

	/**
	 * @param orCreateUser
	 * @param statuses
	 * @return
	 */
	Long getTotalAppointments(final User user, final Set<AppointmentStatus> statuses);

	/**
	 * @param orCreateUser
	 * @return
	 */
	Long getTotalAssignedAppointments(final User user);

    /**
     * @param orCreateUser
     * @return
     */
    List<Appointment> getProcessedAppointments(final User user, final int startNum, final int maxResults);

    /**
     * @param orCreateUser
     * @return
     */
    Long getTotalProcessedAppointments(User user);

    /**
     * @param orCreateUser
     * @param startNum
     * @param i
     * @return
     */
    List<Appointment> getCreatedAppointments(User user, int startNum, int maxResults);

    /**
     * @param orCreateUser
     * @return
     */
    Long getTotalCreatedAppointments(User user);

    /**
     * @param orCreateUser
     * @param startNum
     * @param i
     * @return
     */
    List<AppointmentResponse> getAppointmentResponses(User user, int startNum, int maxResults);

    /**
     * @param orCreateUser
     * @return
     */
    Long getTotalRespondedAppointments(User user);
}
