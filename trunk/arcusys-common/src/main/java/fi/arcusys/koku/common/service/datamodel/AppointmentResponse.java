package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.ManyToOne;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 22, 2011
 */
@Entity
public class AppointmentResponse extends AbstractEntity {
	private String comment;
	private Integer slotNumber;

	@ManyToOne
	private Appointment appointment;

	@Enumerated(EnumType.STRING)
    private AppointmentResponseStatus status;
	
	@ManyToOne
	private TargetPerson target;
	
	@ManyToOne
	private User replier;

	/**
     * @return the appointment
     */
    public Appointment getAppointment() {
        return appointment;
    }
    /**
     * @param appointment the appointment to set
     */
    public void setAppointment(Appointment appointment) {
        this.appointment = appointment;
    }
    /**
     * @return the status
     */
    public AppointmentResponseStatus getStatus() {
        return status;
    }
    /**
     * @param status the status to set
     */
    public void setStatus(AppointmentResponseStatus status) {
        this.status = status;
    }
    /**
     * @return the target
     */
    public TargetPerson getTarget() {
        return target;
    }
    /**
     * @param target the target to set
     */
    public void setTarget(TargetPerson target) {
        this.target = target;
    }
    /**
	 * @return the comment
	 */
	public String getComment() {
		return comment;
	}
	/**
	 * @param comment the comment to set
	 */
	public void setComment(String comment) {
		this.comment = comment;
	}
	/**
	 * @return the slotNumber
	 */
	public Integer getSlotNumber() {
		return slotNumber;
	}
	/**
	 * @param slotNumber the slotNumber to set
	 */
	public void setSlotNumber(Integer slotNumber) {
		this.slotNumber = slotNumber;
	}
	/**
	 * @return the replier
	 */
	public User getReplier() {
		return replier;
	}
	/**
	 * @param replier the replier to set
	 */
	public void setReplier(User replier) {
		this.replier = replier;
	}
}
