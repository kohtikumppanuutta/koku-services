package fi.arcusys.koku.kv.service;

import fi.arcusys.koku.kv.service.datamodel.User;

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

}