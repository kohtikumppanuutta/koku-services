package fi.arcusys.koku.kv.service;

import java.util.List;
import java.util.Set;

import fi.arcusys.koku.kv.service.datamodel.AppointmentStatus;
import fi.arcusys.koku.kv.soa.AppointmentSummary;
import fi.arcusys.koku.kv.soa.AppointmentTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 22, 2011
 */
public interface AppointmentServiceFacade {
	AppointmentTO getAppointment(final Long appointmentId);
	
	Long storeAppointment(final AppointmentTO appointment);
	
	void approveAppointment(final String userUid, final Long appointmentId, final int slotNumber, final String comment);

	void declineAppointment(final String userUid, final Long appointmentId, final String comment);
	
	List<AppointmentSummary> getAssignedAppointments(final String userUid);
	
	List<AppointmentSummary> getAppointments(final String userUid, final Set<AppointmentStatus> statuses);

	List<AppointmentSummary> getAssignedAppointments(final String userUid, final int startNum, final int maxNum);
	
	List<AppointmentSummary> getAppointments(final String userUid, final Set<AppointmentStatus> statuses, final int startNum, final int maxNum);

	/**
	 * @param appointmentId
	 */
	void removeAppointment(long appointmentId);

	/**
	 * @param user
	 * @return
	 */
	int getTotalAssignedAppointments(String user);

	/**
	 * @param user
	 * @param createdStatuses
	 * @return
	 */
	int getTotalAppointments(String user, Set<AppointmentStatus> statuses);	
}
