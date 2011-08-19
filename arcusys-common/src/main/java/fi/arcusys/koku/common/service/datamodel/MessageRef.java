package fi.arcusys.koku.common.service.datamodel;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
@Entity
@NamedQueries({
	@NamedQuery(name = MessageRef.GET_MESSAGE_REFS_BY_IDS, query = "SELECT mr FROM MessageRef mr WHERE mr.id in (:ids)"),
	@NamedQuery(name = MessageRef.DELETE_MESSAGE_REFS_BY_IDS, query = "DELETE FROM MessageRef mr WHERE mr.id in (:ids)")
})
public class MessageRef extends AbstractEntity {
	public static final String GET_MESSAGE_REFS_BY_IDS = "getMessageRefsByIds";
	public static final String DELETE_MESSAGE_REFS_BY_IDS = "deleteMessageRefsByIds";

	private boolean isRead;

	@ManyToOne
	private Folder folder;
	
	@ManyToOne
	private Message message;

	/**
	 * @return the isRead
	 */
	public boolean isRead() {
		return isRead;
	}

	/**
	 * @param isRead the isRead to set
	 */
	public void setRead(boolean isRead) {
		this.isRead = isRead;
	}

	/**
	 * @return the folder
	 */
	public Folder getFolder() {
		return folder;
	}

	/**
	 * @param folder the folder to set
	 */
	public void setFolder(Folder folder) {
		this.folder = folder;
	}

	/**
	 * @return the message
	 */
	public Message getMessage() {
		return message;
	}

	/**
	 * @param message the message to set
	 */
	public void setMessage(Message message) {
		this.message = message;
	}
}