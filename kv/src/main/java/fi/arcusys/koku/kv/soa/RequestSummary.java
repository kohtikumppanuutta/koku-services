package fi.arcusys.koku.kv.soa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 22, 2011
 */
@XmlType (name = "requestSummary", namespace = "http://soa.kv.koku.arcusys.fi/")
public class RequestSummary {
	private long requestId;
	private String sender;
	private String subject;
	private XMLGregorianCalendar creationDate;
	private XMLGregorianCalendar endDate;
	private int respondedAmount;
	private int missedAmout;
	/**
	 * @return the requestId
	 */
	public long getRequestId() {
		return requestId;
	}
	/**
	 * @param requestId the requestId to set
	 */
	public void setRequestId(long requestId) {
		this.requestId = requestId;
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
	 * @return the creationDate
	 */
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	public XMLGregorianCalendar getCreationDate() {
		return creationDate;
	}
	/**
	 * @param creationDate the creationDate to set
	 */
	public void setCreationDate(XMLGregorianCalendar creationDate) {
		this.creationDate = creationDate;
	}
	/**
	 * @return the endDate
	 */
	@XmlElement
	@XmlSchemaType(name = "dateTime")
	public XMLGregorianCalendar getEndDate() {
		return endDate;
	}
	/**
	 * @param endDate the endDate to set
	 */
	public void setEndDate(XMLGregorianCalendar endDate) {
		this.endDate = endDate;
	}
	/**
	 * @return the respondedAmount
	 */
	public int getRespondedAmount() {
		return respondedAmount;
	}
	/**
	 * @param respondedAmount the respondedAmount to set
	 */
	public void setRespondedAmount(int respondedAmount) {
		this.respondedAmount = respondedAmount;
	}
	/**
	 * @return the missedAmout
	 */
	public int getMissedAmout() {
		return missedAmout;
	}
	/**
	 * @param missedAmout the missedAmout to set
	 */
	public void setMissedAmout(int missedAmout) {
		this.missedAmout = missedAmout;
	}
}
