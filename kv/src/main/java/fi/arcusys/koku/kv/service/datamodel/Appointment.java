package fi.arcusys.koku.kv.service.datamodel;

import java.util.Collections;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Embedded;
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

import fi.arcusys.koku.kv.soa.AppointmentSlotTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@Entity
@NamedQueries({
	@NamedQuery(name = "findAppointmentByUserAndStatuses", query = "SELECT DISTINCT ap FROM Appointment ap WHERE (ap.sender = :user OR :user MEMBER OF ap.recipients) AND ap.status in (:statuses)"),
	@NamedQuery(name = "countAppointmentsByUserAndStatuses", query = "SELECT COUNT(DISTINCT ap) FROM Appointment ap WHERE (ap.sender = :user OR :user MEMBER OF ap.recipients) AND ap.status in (:statuses)"),
	@NamedQuery(name = "findAssignedAppointments", query = "SELECT DISTINCT ap FROM Appointment ap WHERE :user MEMBER OF ap.recipients AND ap.status in (:statuses)"),
	@NamedQuery(name = "countAssignedAppointments", query = "SELECT COUNT(DISTINCT ap) FROM Appointment ap WHERE :user MEMBER OF ap.recipients AND ap.status in (:statuses)")
})
public class Appointment extends AbstractEntity {

	@ManyToOne
	private User sender;
	
	@ManyToMany(fetch = FetchType.EAGER)
	private Set<User> recipients;
	
	private String subject;
	
	@Enumerated(EnumType.STRING)
	private AppointmentStatus status;

	@Lob
	private String description;

	@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
	private Set<AppointmentSlot> slots;
	
	@Embedded
	private AppointmentResponse response;

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
	public Set<User> getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(Set<User> recipients) {
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
		if (this.slots == null) {
			return Collections.emptySet();
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
	 * @return the response
	 */
	public AppointmentResponse getResponse() {
		return response;
	}

	/**
	 * @param response the response to set
	 */
	public void setResponse(AppointmentResponse response) {
		this.response = response;
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

	public User getReceipientByUid(final String userUid) {
		for (final User receipient : this.getRecipients()) {
			if (receipient.getUid().equals(userUid)) {
				return receipient;
			}
		}
		return null;
	}
}