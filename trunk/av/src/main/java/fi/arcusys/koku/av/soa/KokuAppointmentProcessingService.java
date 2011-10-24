package fi.arcusys.koku.av.soa;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@WebService(targetNamespace = "http://soa.av.koku.arcusys.fi/")
public interface KokuAppointmentProcessingService {

	@WebResult(name = "appointment")
	AppointmentForEditTO getAppointment(@WebParam(name = "appointmentId") final Long appointmentId);
	
	Long storeAppointment(@WebParam(name = "appointment") final AppointmentForEditTO appointment);
	
    AppointmentForReplyTO getAppointmentForReply(@WebParam(name = "appointmentId") final Long appointmentId, final String targetUserUid);

    void approveAppointment(
            @WebParam(name = "targetUser") final String targetUserUid,
            @WebParam(name = "user") final String userUid,
			@WebParam(name = "appointmentId") final Long appointmentId, 
			@WebParam(name = "slotNumber") final int slotNumber, 
			@WebParam(name = "comment") final String comment);

	void declineAppointment(
            @WebParam(name = "targetUser") final String targetUserUid,
	        @WebParam(name = "user") final String userUid,
			@WebParam(name = "appointmentId") final Long appointmentId, 
			@WebParam(name = "comment") final String comment);
}
