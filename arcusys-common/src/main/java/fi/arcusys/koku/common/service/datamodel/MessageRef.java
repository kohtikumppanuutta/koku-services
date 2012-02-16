package fi.arcusys.koku.common.service.datamodel;

import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;


/**
 * Entity for representing reference to message in KV-Messages functionality.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
@Entity
@NamedQueries({
	@NamedQuery(name = MessageRef.GET_MESSAGE_REFS_BY_IDS, query = "SELECT mr FROM MessageRef mr WHERE mr.id in (:ids) ORDER BY mr.id DESC"),
    @NamedQuery(name = "findOldMessagesByFolderType", query = "SELECT mr FROM MessageRef mr WHERE mr.folder.folderType in (:folderTypes) AND mr.createdDate < :olderThen"),
    @NamedQuery(name = "findOldMessagesByUserAndFolderType", query = "SELECT mr FROM MessageRef mr WHERE mr.folder.folderType in (:folderTypes) AND mr.folder.user = :user AND mr.createdDate < :olderThen"),
	@NamedQuery(name = MessageRef.DELETE_MESSAGE_REFS_BY_IDS, query = "DELETE FROM MessageRef mr WHERE mr.id in (:ids)"),
	@NamedQuery(name = "deleteOldMessages", query = "DELETE FROM MessageRef mr WHERE mr.createdDate < :olderThen")
})
public class MessageRef extends AbstractEntity {
	public static final String GET_MESSAGE_REFS_BY_IDS = "getMessageRefsByIds";
	public static final String DELETE_MESSAGE_REFS_BY_IDS = "deleteMessageRefsByIds";

	private boolean isRead;

	@ManyToOne
	private Folder folder;
	
	@ManyToOne
	private Message message;
	
	@ManyToMany(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
	private Set<User> deliveryFailedTo; 

	/**
     * @return the deliveryFailedTo
     */
    public Set<User> getDeliveryFailedTo() {
        return deliveryFailedTo;
    }

    /**
     * @param deliveryFailedTo the deliveryFailedTo to set
     */
    public void setDeliveryFailedTo(Set<User> deliveryFailedTo) {
        this.deliveryFailedTo = deliveryFailedTo;
    }

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
