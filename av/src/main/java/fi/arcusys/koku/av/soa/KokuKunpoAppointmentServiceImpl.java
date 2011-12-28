package fi.arcusys.koku.av.soa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 19, 2011
 */
@Stateless
@WebService(serviceName = "KokuKunpoAppointmentService", portName = "KokuKunpoAppointmentServicePort", 
        endpointInterface = "fi.arcusys.koku.av.soa.KokuKunpoAppointmentService",
        targetNamespace = "http://soa.av.koku.arcusys.fi/")
@Interceptors(KokuAppointmentInterceptor.class)
public class KokuKunpoAppointmentServiceImpl implements KokuKunpoAppointmentService {

    @EJB
    private AppointmentServiceFacade serviceFacade;
    
    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalAssignedAppointments(String user) {
        return serviceFacade.getTotalAssignedAppointments(user);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalRespondedAppointments(String user) {
        return serviceFacade.getTotalRespondedAppointments(user);
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentWithTarget> getRespondedAppointments(String user, int startNum, int maxNum) {
        return serviceFacade.getRespondedAppointments(user, startNum, maxNum);
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentWithTarget> getAssignedAppointments(String user,
            int startNum, int maxNum) {
        return serviceFacade.getAssignedAppointments(user, startNum, maxNum);
//        return getAssignedAppointments_stubVersion();
    }

    private List<AppointmentWithTarget> getAssignedAppointments_stubVersion() {
        final List<AppointmentWithTarget> result = new ArrayList<AppointmentWithTarget>();
        final AppointmentWithTarget appointment = new AppointmentWithTarget();
        appointment.setAppointmentId(2L);
        appointment.setDescription("Assigned appointment");
        appointment.setSender("Ville Virkamies");
        appointment.setSubject("Appointment #2_1");
        appointment.setTargetPerson("Lassi Lapsi");
        result.add(appointment);
        return result;
    }

    /**
     * @param appointmentId
     * @return
     */
    @Override
    public AppointmentRespondedTO getAppointmentRespondedById(long appointmentId, final String targetUser) {
        return serviceFacade.getAppointmentRespondedById(appointmentId, targetUser);
    }

    /**
     * @param appointmentId
     * @param targetUser
     */
    @Override
    public void cancelRespondedAppointment(long appointmentId, String targetUser, final String user, final String comment) {
        serviceFacade.cancelAppointment(targetUser, user, appointmentId, comment);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalOldAppointments(String user) {
        return serviceFacade.getTotalOldAppointments(user);
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentWithTarget> getOldAppointments(String user,
            int startNum, int maxNum) {
        return serviceFacade.getOldAppointments(user, startNum, maxNum);
    }

}
