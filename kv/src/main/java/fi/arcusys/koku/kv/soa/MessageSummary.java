package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.soa.UserInfo;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi) Jun 9, 2011
 */
@XmlType (name = "messageSummary", namespace = "http://soa.kv.koku.arcusys.fi/")
public class MessageSummary {
	private long messageId;
	private String sender;
	private UserInfo senderUserInfo;
	private List<String> recipients;
	private List<UserInfo> recipientUserInfos;
	private String subject;
	private XMLGregorianCalendar creationDate;
	private FolderType messageType;
	private MessageStatus messageStatus;

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
     * @return the recipientUserInfos
     */
    public List<UserInfo> getRecipientUserInfos() {
        return recipientUserInfos;
    }

    /**
     * @param recipientUserInfos the recipientUserInfos to set
     */
    public void setRecipientUserInfos(List<UserInfo> recipientUserInfos) {
        this.recipientUserInfos = recipientUserInfos;
    }

    /**
	 * @return the messageId
	 */
	public long getMessageId() {
		return messageId;
	}

	/**
	 * @param messageId
	 *            the messageId to set
	 */
	public void setMessageId(long messageId) {
		this.messageId = messageId;
	}

	/**
	 * @return the sender
	 */
	public String getSender() {
		return sender;
	}

	/**
	 * @param sender
	 *            the sender to set
	 */
	public void setSender(String sender) {
		this.sender = sender;
	}

	/**
	 * @return the recipients
	 */
	public List<String> getRecipients() {
		return recipients;
	}

	/**
	 * @param recipients
	 *            the recipients to set
	 */
	public void setRecipients(List<String> recipients) {
		this.recipients = recipients;
	}

	/**
	 * @return the subject
	 */
	public String getSubject() {
		return subject;
	}

	/**
	 * @param subject
	 *            the subject to set
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
	 * @param creationDate
	 *            the creationDate to set
	 */
	public void setCreationDate(XMLGregorianCalendar creationDate) {
		this.creationDate = creationDate;
	}

	/**
	 * @return the messageType
	 */
	public FolderType getMessageType() {
		return messageType;
	}

	/**
	 * @param messageType
	 *            the messageType to set
	 */
	public void setMessageType(FolderType messageType) {
		this.messageType = messageType;
	}

	/**
	 * @return the messageStatus
	 */
	public MessageStatus getMessageStatus() {
		return messageStatus;
	}

	/**
	 * @param messageStatus
	 *            the messageStatus to set
	 */
	public void setMessageStatus(MessageStatus messageStatus) {
		this.messageStatus = messageStatus;
	}

	/**
	 * @return
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + (int) (messageId ^ (messageId >>> 32));
		return result;
	}

	/**
	 * @param obj
	 * @return
	 */
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		if (!(obj instanceof MessageSummary)) {
			return false;
		}
		MessageSummary other = (MessageSummary) obj;
		if (messageId == 0 || messageId != other.messageId) {
			return false;
		}
		return true;
	}
}
