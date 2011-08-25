package fi.arcusys.koku.av.soa;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 19, 2011
 */
@Stateless
@WebService(serviceName = "KokuLooraAppointmentService", portName = "KokuLooraAppointmentServicePort", 
        endpointInterface = "fi.arcusys.koku.av.soa.KokuLooraAppointmentService",
        targetNamespace = "http://soa.av.koku.arcusys.fi/")
public class KokuLooraAppointmentServiceImpl implements KokuLooraAppointmentService {

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalCreatedAppointments(String user) {
        // TODO Auto-generated method stub
        return 1;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalProcessedAppointments(String user) {
        // TODO Auto-generated method stub
        return 1;
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentSummary> getCreatedAppointments(String user, int startNum, int maxNum) {
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
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentSummary> getProcessedAppointments(String user,
            int startNum, int maxNum) {
        // TODO Auto-generated method stub
        final List<AppointmentSummary> result = new ArrayList<AppointmentSummary>();
        final AppointmentSummary appointment = new AppointmentSummary();
        appointment.setAppointmentId(1L);
        appointment.setDescription("Approved appointment");
        appointment.setSender("Ville Virkamies");
        appointment.setSubject("Appointment #1");
        result.add(appointment);
        return result;
    }

    /**
     * @param appointmentId
     * @return
     */
    @Override
    public AppointmentTO getAppointmentById(long appointmentId) {
        // TODO Auto-generated method stub
        final AppointmentTO appointment = new AppointmentTO();
        appointment.setAppointmentId(appointmentId);
        appointment.setSender("Ville Virkamies");
        appointment.setSubject("Appointment #" + appointmentId);
        if (appointmentId == 1) {
            appointment.setDescription("Processed appointment");
            appointment.setAcceptedSlots(Collections.singletonMap(1, "Lassi Lapsi"));
            final List<AppointmentReceipientTO> recipients = new ArrayList<AppointmentReceipientTO>();
            final AppointmentReceipientTO receipient1 = new AppointmentReceipientTO();
            receipient1.setTargetPerson("Lassi Lapsi");
            receipient1.setReceipients(Arrays.asList("Kalle Kuntalainen", "Kirsi Kuntalainen"));
            recipients.add(receipient1);
            final AppointmentReceipientTO receipient2 = new AppointmentReceipientTO();
            receipient2.setTargetPerson("Toinen Lapsi");
            receipient2.setReceipients(Arrays.asList("Kalle Kuntalainen", "Keijo Kuntalainen"));
            recipients.add(receipient2);
            appointment.setRecipients(recipients);
            final List<AppointmentSlotTO> slots = new ArrayList<AppointmentSlotTO>();
            slots.add(createTestSlot(appointmentId, 1));
            slots.add(createTestSlot(appointmentId, 2));
            appointment.setSlots(slots); 
            appointment.setStatus("Partially Processed");
            appointment.setUsersRejected(Collections.singletonList("Toinen Lapsi"));
        } else {
            throw new IllegalArgumentException("Appointment id=" + appointmentId + " is not found");
        }
        return appointment;
    }

    private AppointmentSlotTO createTestSlot(long appointmentId,
            final int slotNumber) {
        final AppointmentSlotTO slotTO = new AppointmentSlotTO();
        DatatypeFactory datatypeFactory;
        try {
            datatypeFactory = DatatypeFactory.newInstance();
        } catch (DatatypeConfigurationException e) {
            throw new RuntimeException(e);
        }
        slotTO.setSlotNumber(slotNumber);
        final GregorianCalendar calendar = (GregorianCalendar)GregorianCalendar.getInstance();
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        slotTO.setAppointmentDate(datatypeFactory.newXMLGregorianCalendar(calendar));
        calendar.set(Calendar.HOUR_OF_DAY, 9 + slotNumber);
        slotTO.setStartTime(datatypeFactory.newXMLGregorianCalendar(calendar));
        calendar.set(Calendar.MINUTE, 15);
        slotTO.setEndTime(datatypeFactory.newXMLGregorianCalendar(calendar));
        slotTO.setLocation("Office");
        return slotTO;
    }

}
