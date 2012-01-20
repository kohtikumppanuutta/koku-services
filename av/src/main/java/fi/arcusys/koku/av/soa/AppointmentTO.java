package fi.arcusys.koku.av.soa;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

import fi.arcusys.koku.common.soa.UserInfo;

/**
 * Data transfer object for communication with UI/Intalio process. Holds data about appointment.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@XmlType (name = "appointment", namespace = "http://soa.av.koku.arcusys.fi/",
		  propOrder={"slots", "recipients", "acceptedSlots", "usersRejected", "usersRejectedWithComments", "cancelComment" })
public class AppointmentTO extends AppointmentSummary {
	private List<AppointmentSlotTO> slots;

	private List<AppointmentReceipientTO> recipients;
    private Map<Integer, UserInfo> acceptedSlots;
    private List<String> usersRejected;
    private List<AppointmentUserRejected> usersRejectedWithComments;
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
     * @return the acceptedSlots
     */
    public Map<Integer, UserInfo> getAcceptedSlots() {
        return acceptedSlots;
    }
    /**
     * @param acceptedSlots the acceptedSlots to set
     */
    public void setAcceptedSlots(Map<Integer, UserInfo> acceptedSlots) {
        this.acceptedSlots = acceptedSlots;
    }
    /**
     * @return the rejectedUsers
     * @deprecated use getUsersRejectedWithComments instead
     */
    public List<String> getUsersRejected() {
        return usersRejected;
    }
    /**
     * @param rejectedUsers the rejectedUsers to set
     */
    public void setUsersRejected(List<String> usersRejected) {
        this.usersRejected = usersRejected;
    }
    
    /**
     * @return the usersRejectedWithComments
     */
    public List<AppointmentUserRejected> getUsersRejectedWithComments() {
        return usersRejectedWithComments;
    }
    /**
     * @param usersRejectedWithComments the usersRejectedWithComments to set
     */
    public void setUsersRejectedWithComments(
            List<AppointmentUserRejected> usersRejectedWithComments) {
        this.usersRejectedWithComments = usersRejectedWithComments;
    }
    /**
	 * @return the slots
	 */
	public List<AppointmentSlotTO> getSlots() {
		return slots;
	}
	/**
	 * @param slots the slots to set
	 */
	public void setSlots(List<AppointmentSlotTO> slots) {
		this.slots = slots;
	}
    /**
     * @return the recipients
     */
    public List<AppointmentReceipientTO> getRecipients() {
    	return recipients;
    }
    /**
     * @param recipients the recipients to set
     */
    public void setRecipients(List<AppointmentReceipientTO> recipients) {
    	this.recipients = recipients;
    }
}
