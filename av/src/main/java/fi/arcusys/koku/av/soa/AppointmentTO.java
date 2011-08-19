package fi.arcusys.koku.av.soa;

import java.util.List;

import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 21, 2011
 */
@XmlType (name = "appointment", namespace = "http://soa.kv.koku.arcusys.fi/",
		  propOrder={"slots", "status", "replier", "approvedSlotNumber", "replierComment" })
public class AppointmentTO extends AppointmentSummary {
	private List<AppointmentSlotTO> slots;

	private String status;
	private String replier;
	private Integer approvedSlotNumber;
	private String replierComment;
	
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
	 * @return the status
	 */
	public String getStatus() {
		return status;
	}
	/**
	 * @param status the status to set
	 */
	public void setStatus(String status) {
		this.status = status;
	}
	/**
	 * @return the replier
	 */
	public String getReplier() {
		return replier;
	}
	/**
	 * @param replier the replier to set
	 */
	public void setReplier(String replier) {
		this.replier = replier;
	}
	/**
	 * @return the approvedSlotNumber
	 */
	public Integer getApprovedSlotNumber() {
		return approvedSlotNumber;
	}
	/**
	 * @param approvedSlotNumber the approvedSlotNumber to set
	 */
	public void setApprovedSlotNumber(Integer approvedSlotNumber) {
		this.approvedSlotNumber = approvedSlotNumber;
	}
	/**
	 * @return the replierComment
	 */
	public String getReplierComment() {
		return replierComment;
	}
	/**
	 * @param replierComment the replierComment to set
	 */
	public void setReplierComment(String replierComment) {
		this.replierComment = replierComment;
	}
}
