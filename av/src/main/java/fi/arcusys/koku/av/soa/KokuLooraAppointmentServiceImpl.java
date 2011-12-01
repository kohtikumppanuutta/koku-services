package fi.arcusys.koku.av.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 19, 2011
 */
@Stateless
@WebService(serviceName = "KokuLooraAppointmentService", portName = "KokuLooraAppointmentServicePort", 
        endpointInterface = "fi.arcusys.koku.av.soa.KokuLooraAppointmentService",
        targetNamespace = "http://soa.av.koku.arcusys.fi/")
@Interceptors(KokuAppointmentInterceptor.class)
public class KokuLooraAppointmentServiceImpl implements KokuLooraAppointmentService {

    @EJB
    private AppointmentServiceFacade serviceFacade;
    
    @EJB
    private UsersAndGroupsService userService;
    
    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalCreatedAppointments(String user, final AppointmentCriteria criteria) {
        return serviceFacade.getTotalCreatedAppointments(user, updateUserUid(criteria));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public int getTotalProcessedAppointments(String user, final AppointmentCriteria criteria) {
        return serviceFacade.getTotalProcessedAppointments(user, updateUserUid(criteria));
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentSummary> getCreatedAppointments(String user, int startNum, int maxNum, final AppointmentCriteria criteria) {
        return serviceFacade.getCreatedAppointments(user, startNum, maxNum, updateUserUid(criteria));
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<AppointmentSummary> getProcessedAppointments(String user,
            int startNum, int maxNum, final AppointmentCriteria criteria) {
        return serviceFacade.getProcessedAppointments(user, startNum, maxNum, updateUserUid(criteria));
    }

    /**
     * @param appointmentId
     * @return
     */
    @Override
    public AppointmentTO getAppointmentById(long appointmentId) {
        return serviceFacade.getAppointment(appointmentId);
    }

    /**
     * @param appointmentId
     */
    @Override
    public void cancelAppointment(long appointmentId, final String comment) {
        serviceFacade.cancelWholeAppointment(appointmentId, comment);
    }

    private AppointmentCriteria updateUserUid(final AppointmentCriteria criteria) {
        if (criteria == null) {
            return null;
        }
        criteria.setTargetPersonUid(userService.getUserUidByKunpoSsn(criteria.getTargetPersonHetu()));
        return criteria;
    }
}
