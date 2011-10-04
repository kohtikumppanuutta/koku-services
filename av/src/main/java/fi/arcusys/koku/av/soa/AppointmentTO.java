package fi.arcusys.koku.av.soa;

import java.util.List;
import java.util.Map;

import javax.xml.bind.annotation.XmlType;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@XmlType (name = "appointment", namespace = "http://soa.av.koku.arcusys.fi/",
		  propOrder={"slots", "recipients", "acceptedSlots", "usersRejected" })
public class AppointmentTO extends AppointmentSummary {
	private List<AppointmentSlotTO> slots;

	private List<AppointmentReceipientTO> recipients;
    private Map<Integer, String> acceptedSlots;
    private List<String> usersRejected;
	
	/**
     * @return the acceptedSlots
     */
    public Map<Integer, String> getAcceptedSlots() {
        return acceptedSlots;
    }
    /**
     * @param acceptedSlots the acceptedSlots to set
     */
    public void setAcceptedSlots(Map<Integer, String> acceptedSlots) {
        this.acceptedSlots = acceptedSlots;
    }
    /**
     * @return the rejectedUsers
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
