package fi.arcusys.koku.av.service;

import java.util.List;
import java.util.Set;

import javax.jws.WebParam;

import fi.arcusys.koku.av.soa.AppointmentForEditTO;
import fi.arcusys.koku.av.soa.AppointmentForReplyTO;
import fi.arcusys.koku.av.soa.AppointmentRespondedTO;
import fi.arcusys.koku.av.soa.AppointmentSummary;
import fi.arcusys.koku.av.soa.AppointmentTO;
import fi.arcusys.koku.av.soa.AppointmentWithTarget;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 22, 2011
 */
public interface AppointmentServiceFacade {
	AppointmentTO getAppointment(final Long appointmentId);
	
    AppointmentForEditTO getAppointmentForEdit(final Long appointmentId);

    AppointmentForReplyTO getAppointmentForReply(final Long appointmentId, final String targetPersonUid);

    Long storeAppointment(final AppointmentForEditTO appointment);
	
	void approveAppointment(final String targetPersonUid, final String userUid, final Long appointmentId, final int slotNumber, final String comment);

	void declineAppointment(final String targetPersonUid, final String userUid, final Long appointmentId, final String comment);
	
	List<AppointmentWithTarget> getAssignedAppointments(final String userUid);
	
    /**
     * @deprecated
     */
	List<AppointmentSummary> getAppointments(final String userUid, final Set<AppointmentStatus> statuses);

	List<AppointmentWithTarget> getAssignedAppointments(final String userUid, final int startNum, final int maxNum);
	
	/**
	 * @deprecated
	 */
	List<AppointmentSummary> getAppointments(final String userUid, final Set<AppointmentStatus> statuses, final int startNum, final int maxNum);

    List<AppointmentWithTarget> getRespondedAppointments(final String userUid, final int startNum, final int maxNum);

    /**
	 * @param appointmentId
	 */
	void removeAppointment(long appointmentId);

	/**
	 * @param user
	 * @return
	 */
	int getTotalAssignedAppointments(String user);

    int getTotalRespondedAppointments(String user);

    /**
	 * @param user
	 * @param createdStatuses
	 * @return
	 * @deprecated
	 */
	int getTotalAppointments(String user, Set<AppointmentStatus> statuses);	
	
	int getTotalCreatedAppointments(String user);
	
	int getTotalProcessedAppointments(final String user);
	
	List<AppointmentSummary> getCreatedAppointments(final String user, int startNum, int maxNum);
    
    List<AppointmentSummary> getProcessedAppointments(final String user, int startNum, int maxNum);

    /**
     * @param appointmentId
     * @return
     */
    AppointmentRespondedTO getAppointmentRespondedById(long appointmentId, final String targetPerson);
}
