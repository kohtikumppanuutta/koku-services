package fi.arcusys.koku.av.soa;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fi.arcusys.koku.av.service.AppointmentServiceFacade;
import fi.arcusys.koku.common.service.datamodel.AppointmentStatus;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 27, 2011
 */
@Stateless
//@WebService(serviceName = "KokuAppointmentService", portName = "KokuAppointmentServicePort", 
//		endpointInterface = "fi.arcusys.koku.av.soa.KokuAppointmentService",
//		targetNamespace = "http://soa.av.koku.arcusys.fi/")
public class KokuAppointmentServiceImpl implements KokuAppointmentService {

	@EJB
	private AppointmentServiceFacade kvFacade;

	/**
	 * @param appointmentId
	 * @return
	 */
	@Override
	public AppointmentTO getAppointmentById(long appointmentId) {
		return kvFacade.getAppointment(appointmentId);
	}

	/**
	 * @param user
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<AppointmentSummary> getAssignedAppointments(final String user, final int startNum, final int maxNum) {
	    throw new UnsupportedOperationException();
//		return kvFacade.getAssignedAppointments(user, startNum, maxNum);
	}

	/**
	 * @param user
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<AppointmentSummary> getCreatedAppointments(final String user, final int startNum, final int maxNum) {
		return kvFacade.getAppointments(user, getCreatedStatuses(), startNum, maxNum);
	}

	/**
	 * @param user
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<AppointmentSummary> getRespondedAppointments(final String user, final int startNum, final int maxNum) {
		return kvFacade.getAppointments(user, getRespondedStatuses(), startNum, maxNum);
	}

	private Set<AppointmentStatus> getCreatedStatuses() {
		return Collections.singleton(AppointmentStatus.Created);
	}

	private Set<AppointmentStatus> getRespondedStatuses() {
		return new HashSet<AppointmentStatus>(Arrays.asList(AppointmentStatus.Approved));
	}

	/**
	 * @param user
	 * @return
	 */
	@Override
	public int getTotalAssignedAppointments(String user) {
		return kvFacade.getTotalAssignedAppointments(user);
	}

	/**
	 * @param user
	 * @return
	 */
	@Override
	public int getTotalCreatedAppointments(String user) {
		return kvFacade.getTotalAppointments(user, getCreatedStatuses());
	}

	/**
	 * @param user
	 * @return
	 */
	@Override
	public int getTotalRespondedAppointments(String user) {
		return kvFacade.getTotalAppointments(user, getRespondedStatuses());
	}

	/**
	 * @param appointmentId
	 */
	@Override
	public void removeAppointment(long appointmentId) {
		kvFacade.removeAppointment(appointmentId);
	}

}
