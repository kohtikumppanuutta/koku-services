package fi.arcusys.koku.kv.service.impl;

import java.util.Collections;
import java.util.Map;

import javax.ejb.Stateless;

import fi.arcusys.koku.kv.service.datamodel.User;
import fi.arcusys.koku.kv.service.UserDAO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Stateless
public class UserDAOImpl extends AbstractEntityDAOImpl<User> implements UserDAO {
	public UserDAOImpl() {
		super(User.class);
	}
	
	/**
	 * @param uid
	 * @return
	 */
	public User getUserByUid(final String uid) {
		final Map<String, Object> params = Collections.<String,Object>singletonMap("uid", uid);
		return getSingleResultOrNull("findUserByUid", params);
	}
}
