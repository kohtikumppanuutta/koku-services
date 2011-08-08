package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 22, 2011
 */
@XmlType (name = "appointmentSummary", namespace = "http://soa.kv.koku.arcusys.fi/",
		propOrder={"appointmentId", "sender" , "recipients", "subject", "description"})
public class AppointmentSummary {

	private long appointmentId;
	private String sender;
	private String subject;
	private String description;
	private List<String> recipients;

	/**
	 * @return the appointmentId
	 */
	public long getAppointmentId() {
		return appointmentId;
	}

	/**
	 * @param appointmentId the appointmentId to set
	 */
	public void setAppointmentId(long appointmentId) {
		this.appointmentId = appointmentId;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
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
	 * @return the recipients
	 */
	public List<String> getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients the recipients to set
	 */
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

}
