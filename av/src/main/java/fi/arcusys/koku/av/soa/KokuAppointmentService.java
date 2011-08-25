package fi.arcusys.koku.av.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebResult;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 27, 2011
 */
//@WebService(targetNamespace = "http://soa.av.koku.arcusys.fi/")
public interface KokuAppointmentService {
    public int getTotalCreatedAppointments(@WebParam(name = "user") final String user);

	public int getTotalRespondedAppointments(@WebParam(name = "user") final String user);

	public int getTotalAssignedAppointments(@WebParam(name = "user") final String user);

	public List<AppointmentSummary> getCreatedAppointments(
			@WebParam(name = "user") final String user,
			@WebParam(name = "startNum") int startNum, 
			@WebParam(name = "maxNum") int maxNum);
	
	public List<AppointmentSummary>  getRespondedAppointments(
			@WebParam(name = "user") final String user,
			@WebParam(name = "startNum") int startNum, 
			@WebParam(name = "maxNum") int maxNum);

	public List<AppointmentSummary> getAssignedAppointments(
			@WebParam(name = "user") final String user,
			@WebParam(name = "startNum") int startNum, 
			@WebParam(name = "maxNum") int maxNum);
	
	public AppointmentTO getAppointmentById(@WebParam(name = "appointmentId") final long appointmentId);
	
	public void removeAppointment(@WebParam(name = "appointmentId") final long appointmentId);
}
