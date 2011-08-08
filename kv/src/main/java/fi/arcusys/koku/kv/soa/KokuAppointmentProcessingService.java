package fi.arcusys.koku.kv.soa;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@WebService(targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public interface KokuAppointmentProcessingService {

	@WebResult(name = "appointment")
	AppointmentTO getAppointment(@WebParam(name = "appointmentId") final Long appointmentId);
	
	Long storeAppointment(@WebParam(name = "appointment") final AppointmentTO appointment);
	
//	@WebResult(name = "appointment")
//	AppointmentTO createAppointment(@WebParam(name = "appointment") final AppointmentTO appointment);
//	
//	void updateAppointment(@WebParam(name = "appointment") final AppointmentTO appointment);
//	
//	void removeAppointment(@WebParam(name = "appointmentId") final Long appointmentId);
	
	void approveAppointment(@WebParam(name = "user") final String userUid,
							@WebParam(name = "appointmentId") final Long appointmentId, 
							@WebParam(name = "slotNumber") final int slotNumber, 
							@WebParam(name = "comment") final String comment);

	void declineAppointment(@WebParam(name = "user") final String userUid,
							@WebParam(name = "appointmentId") final Long appointmentId, 
							@WebParam(name = "comment") final String comment);
}
