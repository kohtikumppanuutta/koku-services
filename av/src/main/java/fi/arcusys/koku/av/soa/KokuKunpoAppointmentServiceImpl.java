package fi.arcusys.koku.av.soa;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 19, 2011
 */
@Stateless
@WebService(serviceName = "KokuKunpoAppointmentService", portName = "KokuKunpoAppointmentServicePort", 
        endpointInterface = "fi.arcusys.koku.av.soa.KokuKunpoAppointmentService",
        targetNamespace = "http://soa.av.koku.arcusys.fi/")
public class KokuKunpoAppointmentServiceImpl implements KokuKunpoAppointmentService {

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalAssignedAppointments(String user) {
        // TODO Auto-generated method stub
        return 1;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalRespondedAppointments(String user) {
        // TODO Auto-generated method stub
        return 2;
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentSummary> getRespondedAppointments(String user, int startNum, int maxNum) {
        // TODO Auto-generated method stub
        final List<AppointmentSummary> result = new ArrayList<AppointmentSummary>();
        final AppointmentSummary appointment = new AppointmentSummary();
        appointment.setAppointmentId(1L);
        appointment.setDescription("Approved appointment");
        appointment.setSender("Ville Virkamies");
        appointment.setSubject("Appointment #1");
        result.add(appointment);
        final AppointmentSummary appointmentRejected = new AppointmentSummary();
        appointmentRejected.setAppointmentId(3L);
        appointmentRejected.setDescription("Rejected appointment");
        appointmentRejected.setSender("Ville Virkamies");
        appointmentRejected.setSubject("Appointment #3");
        result.add(appointmentRejected);
        return result;
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentSummary> getAssignedAppointments(String user,
            int startNum, int maxNum) {
        // TODO Auto-generated method stub
        final List<AppointmentSummary> result = new ArrayList<AppointmentSummary>();
        final AppointmentSummary appointment = new AppointmentSummary();
        appointment.setAppointmentId(2L);
        appointment.setDescription("Assigned appointment");
        appointment.setSender("Ville Virkamies");
        appointment.setSubject("Appointment #2");
        result.add(appointment);
        return result;
    }

    /**
     * @param appointmentId
     * @return
     */
    @Override
    public AppointmentRespondedTO getAppointmentRespondedById(long appointmentId) {
        // TODO Auto-generated method stub
        final AppointmentRespondedTO appointment = new AppointmentRespondedTO();
        appointment.setAppointmentId(appointmentId);
        appointment.setSender("Ville Virkamies");
        appointment.setSubject("Appointment #" + appointmentId);
        appointment.setTargetPerson("Lassi Lapsi");
        appointment.setReplier("Kalle Kuntalainen");
        if (appointmentId == 1) {
            appointment.setStatus("Approved");
            appointment.setReplierComment("Will come");
            appointment.setDescription("Approved appointment");
            final GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            calendar.set(Calendar.MINUTE, 0);
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);
            final AppointmentSlotTO slotTO = new AppointmentSlotTO();
            DatatypeFactory datatypeFactory;
            try {
                datatypeFactory = DatatypeFactory.newInstance();
            } catch (DatatypeConfigurationException e) {
                throw new RuntimeException(e);
            }
            slotTO.setAppointmentDate(datatypeFactory.newXMLGregorianCalendar(calendar));
            calendar.set(Calendar.HOUR_OF_DAY, 9);
            slotTO.setStartTime(datatypeFactory.newXMLGregorianCalendar(calendar));
            calendar.set(Calendar.MINUTE, 15);
            slotTO.setEndTime(datatypeFactory.newXMLGregorianCalendar(calendar));
            slotTO.setLocation("Office");
            appointment.setApprovedSlot(slotTO);
        } else if (appointmentId == 3) {
            appointment.setStatus("Rejected");
            appointment.setDescription("Rejected appointment");
            appointment.setReplierComment("Will not come");
        } else {
            throw new IllegalArgumentException("Appointment id=" + appointmentId + " is not found");
        }
        return appointment;
    }

}
