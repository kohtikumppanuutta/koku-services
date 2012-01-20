package fi.arcusys.koku.common.service;

import fi.arcusys.koku.common.service.datamodel.User;

/**
 * DAO interface for CRUD operations with 'User' Entity
 * 
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
	
    User getOrCreateUserByEmployeePortalName(final String looraName);

    User getOrCreateUserByCitizenPortalName(final String kunpoName);

    User getUserByCitizenPortalNameOrNull(final String kunpoName);
}