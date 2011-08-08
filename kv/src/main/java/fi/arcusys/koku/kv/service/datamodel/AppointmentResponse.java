package fi.arcusys.koku.kv.service.datamodel;

import javax.persistence.Embeddable;
import javax.persistence.ManyToOne;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jul 22, 2011
 */
@Embeddable
public class AppointmentResponse {
	private String comment;
	private Integer slotNumber;
	
	@ManyToOne
	private User replier;

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
	/**
	 * @return the slotNumber
	 */
	public Integer getSlotNumber() {
		return slotNumber;
	}
	/**
	 * @param slotNumber the slotNumber to set
	 */
	public void setSlotNumber(Integer slotNumber) {
		this.slotNumber = slotNumber;
	}
	/**
	 * @return the replier
	 */
	public User getReplier() {
		return replier;
	}
	/**
	 * @param replier the replier to set
	 */
	public void setReplier(User replier) {
		this.replier = replier;
	}
}
