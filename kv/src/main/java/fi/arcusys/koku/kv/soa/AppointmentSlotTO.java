package fi.arcusys.koku.kv.soa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@XmlType (name = "appointmentSlot", namespace = "http://soa.kv.koku.arcusys.fi/",
		  propOrder={"appointmentId", "slotNumber" , "appointmentDate", "startTime", "endTime", "location", "comment" })
public class AppointmentSlotTO {
	private long appointmentId;
	private int slotNumber;
	private XMLGregorianCalendar appointmentDate;
	private XMLGregorianCalendar startTime;
	private XMLGregorianCalendar endTime;
	private String location;
	private String comment;
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
	 * @return the slotNumber
	 */
	public int getSlotNumber() {
		return slotNumber;
	}
	/**
	 * @param slotNumber the slotNumber to set
	 */
	public void setSlotNumber(int slotNumber) {
		this.slotNumber = slotNumber;
	}
	/**
	 * @return the appointmentDate
	 */
	@XmlElement
	@XmlSchemaType(name = "date")
	public XMLGregorianCalendar getAppointmentDate() {
		return appointmentDate;
	}
	/**
	 * @param appointmentDate the appointmentDate to set
	 */
	public void setAppointmentDate(XMLGregorianCalendar appointmentDate) {
		this.appointmentDate = appointmentDate;
	}
	/**
	 * @return the startTime
	 */
	@XmlElement
	@XmlSchemaType(name = "time")
	public XMLGregorianCalendar getStartTime() {
		return startTime;
	}
	/**
	 * @param startTime the startTime to set
	 */
	public void setStartTime(XMLGregorianCalendar startTime) {
		this.startTime = startTime;
	}
	/**
	 * @return the endTime
	 */
	@XmlElement
	@XmlSchemaType(name = "time")
	public XMLGregorianCalendar getEndTime() {
		return endTime;
	}
	/**
	 * @param endTime the endTime to set
	 */
	public void setEndTime(XMLGregorianCalendar endTime) {
		this.endTime = endTime;
	}
	/**
	 * @return the location
	 */
	public String getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(String location) {
		this.location = location;
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
	
	
}
