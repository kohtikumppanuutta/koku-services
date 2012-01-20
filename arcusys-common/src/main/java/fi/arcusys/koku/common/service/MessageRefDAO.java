package fi.arcusys.koku.common.service;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.datamodel.MessageRef;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * DAO interface for CRUD operations with 'MessageRef' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
public interface MessageRefDAO extends AbstractEntityDAO<MessageRef>{

	/**
	 * @param messageRefs
	 */
	void updateAll(final List<MessageRef> messageRefs);

    /**
     * @param time
     */
    int deleteOldMessages(Date olderThen);

    /**
     * @param asList
     * @param time
     * @return
     */
    List<MessageRef> getMessagesByFolderTypeAndCreateDate(Collection<FolderType> folderTypes, Date time);

    /**
     * @param userUid
     * @param singleton
     * @param time
     * @return
     */
    List<MessageRef> getMessagesByUserAndFolderTypeAndCreateDate(User user, Collection<FolderType> folderTypes, Date time);

}