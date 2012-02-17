package fi.arcusys.koku.common.service.datamodel;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.Lob;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PrePersist;

/**
 * Entity for representing appointment request in AV functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "findAppointmentByUserAndStatuses", query = "SELECT DISTINCT ap FROM Appointment ap LEFT OUTER JOIN ap.recipients as tp " +
			"WHERE (ap.sender = :user OR :user MEMBER OF tp.guardians) AND ap.status in (:statuses) ORDER BY ap.id DESC"),
	@NamedQuery(name = "countAppointmentsByUserAndStatuses", query = "SELECT COUNT(DISTINCT ap) FROM Appointment ap LEFT OUTER JOIN ap.recipients as tp " +
			"WHERE (ap.sender = :user OR :user MEMBER OF tp.guardians) AND ap.status in (:statuses)"),
	@NamedQuery(name = "findAssignedAppointments", query = "SELECT DISTINCT ap FROM Appointment ap LEFT OUTER JOIN ap.recipients as tp " +
			" WHERE tp.id not in ( SELECT ar.target.id FROM AppointmentResponse ar WHERE " +
			" ar.appointment = ap " +
			")" +
			"    AND :user MEMBER OF tp.guardians " +
			"    AND ap.status in (:statuses) " +
			" ORDER BY ap.id DESC"),
	@NamedQuery(name = "countAssignedAppointments", query = "SELECT COUNT(DISTINCT ap) FROM Appointment ap LEFT OUTER JOIN ap.recipients as tp " +
            " WHERE tp.id not in ( SELECT ar.target.id FROM AppointmentResponse ar WHERE " +
            " ar.appointment = ap " +
            ")" +
            "    AND :user MEMBER OF tp.guardians " +
            "    AND ap.status in (:statuses) "),
    @NamedQuery(name = "findProcessedAppointmentsBySender", query = "SELECT DISTINCT ap FROM Appointment ap " +
            " WHERE (ap.status = :status_cancelled OR EXISTS ( SELECT ar FROM AppointmentResponse ar WHERE ar.appointment = ap ))" +
            "    AND :sender = ap.sender  ORDER BY ap.id DESC"),
    @NamedQuery(name = "countProcessedAppointmentsBySender", query = "SELECT COUNT(DISTINCT ap) FROM Appointment ap " +
            " WHERE (ap.status = :status_cancelled OR EXISTS ( SELECT ar FROM AppointmentResponse ar WHERE ar.appointment = ap ))" +
            "    AND :sender = ap.sender "),

// Criteria search            
    @NamedQuery(name = "findProcessedAppointmentsBySenderAndTarget", query = "SELECT DISTINCT ap FROM Appointment ap JOIN ap.recipients AS tp " +
            " WHERE (ap.status = :status_cancelled OR EXISTS ( SELECT ar FROM AppointmentResponse ar WHERE ar.appointment = ap ))" +
            "    AND :sender = ap.sender " +
            "    AND tp.targetUser.uid = :targetUserUid " +
            " ORDER BY ap.id DESC"),
    @NamedQuery(name = "countProcessedAppointmentsBySenderAndTarget", query = "SELECT COUNT(DISTINCT ap) FROM Appointment ap JOIN ap.recipients AS tp " +
            " WHERE (ap.status = :status_cancelled OR EXISTS ( SELECT ar FROM AppointmentResponse ar WHERE ar.appointment = ap ))" +
            "    AND :sender = ap.sender " +
            "    AND tp.targetUser.uid = :targetUserUid "),
                    
    @NamedQuery(name = "findCreatedAppointmentsBySender", query = "SELECT DISTINCT ap FROM Appointment ap " +
            " WHERE ap.status <> :status_cancelled AND NOT EXISTS ( SELECT ar FROM AppointmentResponse ar WHERE ar.appointment = ap )" +
            "    AND :sender = ap.sender  ORDER BY ap.id DESC"),
    @NamedQuery(name = "countCreatedAppointmentsBySender", query = "SELECT COUNT(DISTINCT ap) FROM Appointment ap " +
            " WHERE ap.status <> :status_cancelled AND NOT EXISTS ( SELECT ar FROM AppointmentResponse ar WHERE ar.appointment = ap )" +
            "    AND :sender = ap.sender "),

// Criteria search            
    @NamedQuery(name = "findCreatedAppointmentsBySenderAndTarget", query = "SELECT DISTINCT ap FROM Appointment ap JOIN ap.recipients AS tp " +
            " WHERE ap.status <> :status_cancelled AND NOT EXISTS ( SELECT ar FROM AppointmentResponse ar WHERE ar.appointment = ap )" +
            "    AND :sender = ap.sender " +
            "    AND tp.targetUser.uid = :targetUserUid " +
            " ORDER BY ap.id DESC"),
    @NamedQuery(name = "countCreatedAppointmentsBySenderAndTarget", query = "SELECT COUNT(DISTINCT ap) FROM Appointment ap JOIN ap.recipients AS tp " +
            " WHERE ap.status <> :status_cancelled AND NOT EXISTS ( SELECT ar FROM AppointmentResponse ar WHERE ar.appointment = ap )" +
            "    AND :sender = ap.sender " +
            "    AND tp.targetUser.uid = :targetUserUid "),
    
    @NamedQuery(name = "findAppointmentResponsesByUser", query = "SELECT DISTINCT ar FROM AppointmentResponse ar " +
            " WHERE ar.replier = :user " +
            " AND ar.status = :reply_approved AND ar.appointment.status IN (:appointment_approved)" +
            " ORDER BY ar.id DESC"),
    @NamedQuery(name = "countAppointmentResponsesByUser", query = "SELECT COUNT(DISTINCT ar) FROM AppointmentResponse ar " +
            " WHERE ar.replier = :user " +
            " AND ar.status = :reply_approved AND ar.appointment.status IN (:appointment_approved)"),
    @NamedQuery(name = "findOldAppointmentResponsesByUser", query = "SELECT DISTINCT ar FROM AppointmentResponse ar " +
            " WHERE ar.replier = :user " +
            " AND NOT (ar.status = :reply_approved AND ar.appointment.status IN (:appointment_approved))" +
            " ORDER BY ar.id DESC"),
    @NamedQuery(name = "countOldAppointmentResponsesByUser", query = "SELECT COUNT(DISTINCT ar) FROM AppointmentResponse ar " +
            " WHERE ar.replier = :user " +
            " AND NOT (ar.status = :reply_approved AND ar.appointment.status IN (:appointment_approved))")
})
public class Appointment extends AbstractEntity {

	@ManyToOne
	private User sender;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<TargetPerson> recipients;
	
	private String subject;
	
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;

	@Lob
	private String description;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<AppointmentSlot> slots;
	
	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "appointment")
	private Set<AppointmentResponse> responses;
	
	private String cancelComment;
	
	/**
     * @return the cancelComment
     */
    public String getCancelComment() {
        return cancelComment;
    }

    /**
     * @param cancelComment the cancelComment to set
     */
    public void setCancelComment(String cancelComment) {
        this.cancelComment = cancelComment;
    }

    /**
	 * @return the sender
	 */
	public User getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(User sender) {
		this.sender = sender;
	}

	/**
	 * @return the recipients
	 */
	public Set<TargetPerson> getRecipients() {
	    if (recipients == null) {
	        this.recipients = new HashSet<TargetPerson>();
	    }
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(Set<TargetPerson> recipients) {
		this.recipients = recipients;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject the subject to set
	 */
	public void setSubject(String subject) {
		this.subject = subject;
	}

	/**
	 * @return the status
	 */
	public AppointmentStatus getStatus() {
		return status;
	}

	/**
	 * @param status the status to set
	 */
	public void setStatus(AppointmentStatus status) {
		this.status = status;
	}

	/**
	 * @return the description
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description the description to set
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/**
	 * @return the slots
	 */
	public Set<AppointmentSlot> getSlots() {
        if (slots == null) {
            this.slots = new HashSet<AppointmentSlot>();
        }
		return slots;
	}

	/**
	 * @param slots the slots to set
	 */
	public void setSlots(Set<AppointmentSlot> slots) {
		this.slots = slots;
	}
	
	/**
     * @return the responses
     */
    public Set<AppointmentResponse> getResponses() {
        if (responses == null) {
            this.responses = new HashSet<AppointmentResponse>();
        }
        return responses;
    }

    /**
     * @param responses the responses to set
     */
    public void setResponses(Set<AppointmentResponse> responses) {
        this.responses = responses;
    }

    /**
	 * 
	 */
	@Override
	@PrePersist
	public void setDefaults() {
		super.setDefaults();
		if (this.status == null) {
			this.status = AppointmentStatus.Created;
		}
	}
	
	public AppointmentSlot getSlotByNumber(final int slotNumber) {
		for (final AppointmentSlot slot : this.getSlots()) {
			if (slot.getSlotNumber() == slotNumber) {
				return slot;
			}
		}
		return null;
	}

	public TargetPerson getTargetPersonByUid(final String userUid) {
		for (final TargetPerson receipient : this.getRecipients()) {
			if (receipient.getTargetUser().getUid().equals(userUid)) {
				return receipient;
			}
		}
		return null;
	}

    public AppointmentResponse getResponseForTargetPerson(final String userUid) {
        for (final AppointmentResponse response : this.getResponses()) {
            if (response.getTarget().getTargetUser().getUid().equals(userUid)) {
                return response;
            }
        }
        return null;
    }
}
