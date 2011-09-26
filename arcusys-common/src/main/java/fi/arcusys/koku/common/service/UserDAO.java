package fi.arcusys.koku.common.service;

import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 20, 2011
 */
public interface UserDAO extends AbstractEntityDAO<User>{

	/**
	 * @param string
	 * @return
	 */
	User getUserByUid(final String uid);
	
	User getOrCreateUser(final String uid);
	
    User getOrCreateUserByPortalName(final String kunpoName);
}