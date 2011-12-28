package fi.arcusys.koku.kv.soa;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.common.soa.UserInfo;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Oct 6, 2011
 */
public class RequestShortSummary {

    private long requestId;
    private String sender;
    private UserInfo senderUserInfo;
    private String subject;
    private XMLGregorianCalendar creationDate;
    private XMLGregorianCalendar endDate;

    /**
     * @return the senderUserInfo
     */
    public UserInfo getSenderUserInfo() {
        return senderUserInfo;
    }

    /**
     * @param senderUserInfo the senderUserInfo to set
     */
    public void setSenderUserInfo(UserInfo senderUserInfo) {
        this.senderUserInfo = senderUserInfo;
    }

    /**
     * 
     */
    public RequestShortSummary() {
        super();
    }

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

}