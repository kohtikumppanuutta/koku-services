package fi.arcusys.koku.av.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 19, 2011
 */
@WebService(targetNamespace = "http://soa.av.koku.arcusys.fi/")
public interface KokuLooraAppointmentService {

    public int getTotalCreatedAppointments(@WebParam(name = "user") final String user);
    
    public int getTotalProcessedAppointments(@WebParam(name = "user") final String user);

    public List<AppointmentSummary> getCreatedAppointments(
            @WebParam(name = "user") final String user,
            @WebParam(name = "startNum") int startNum, 
            @WebParam(name = "maxNum") int maxNum);
    
    public List<AppointmentSummary>  getProcessedAppointments(
            @WebParam(name = "user") final String user,
            @WebParam(name = "startNum") int startNum, 
            @WebParam(name = "maxNum") int maxNum);

    public AppointmentTO getAppointmentById(@WebParam(name = "appointmentId") final long appointmentId);
}
