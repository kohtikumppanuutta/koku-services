package fi.arcusys.koku.av.service;

import java.util.List;
import java.util.Set;

import fi.arcusys.koku.av.soa.AppointmentCriteria;
import fi.arcusys.koku.av.soa.AppointmentForEditTO;
import fi.arcusys.koku.av.soa.AppointmentForReplyTO;
import fi.arcusys.koku.av.soa.AppointmentRespondedTO;
import fi.arcusys.koku.av.soa.AppointmentSummary;
import fi.arcusys.koku.av.soa.AppointmentTO;
import fi.arcusys.koku.av.soa.AppointmentWithTarget;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;

/**
 * Service facade interface for all business methods related to AV function area.
 * 
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

    List<AppointmentWithTarget> getOldAppointments(final String userUid, final int startNum, final int maxNum);

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

    int getTotalOldAppointments(String user);

    /**
	 * @param user
	 * @param createdStatuses
	 * @return
	 * @deprecated
	 */
	int getTotalAppointments(String user, Set<AppointmentStatus> statuses);	
	
	int getTotalCreatedAppointments(String user, AppointmentCriteria criteria);
	
	int getTotalProcessedAppointments(final String user, AppointmentCriteria criteria);
	
    List<AppointmentSummary> getCreatedAppointments(final String user, int startNum, int maxNum, AppointmentCriteria criteria);
    
    List<AppointmentSummary> getProcessedAppointments(final String user, int startNum, int maxNum, AppointmentCriteria criteria);

    /**
     * @param appointmentId
     * @return
     */
    AppointmentRespondedTO getAppointmentRespondedById(long appointmentId, final String targetPerson);

    /**
     * @param targetUser
     * @param user
     * @param appointmentId
     * @param comment
     */
    void cancelAppointment(final String targetUser, final String user, final long appointmentId, final String comment);

    /**
     * @param appointmentId
     * @param comment
     */
    void cancelWholeAppointment(long appointmentId, String comment);
}
