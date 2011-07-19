package fi.arcusys.koku.kv.service;

import static junit.framework.Assert.assertNotNull;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import fi.arcusys.koku.kv.service.datamodel.User;
import fi.arcusys.koku.kv.service.impl.UserDAOImpl;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Component
public class CommonTestUtil {
	@Autowired
	private UserDAO userDao;
	
	public User getUserByUid(final String userUid) {
		User user = userDao.getUserByUid(userUid);
		if (user == null) {
			final User newUser = new User();
			newUser.setUid(userUid);
			userDao.create(newUser);
			user = userDao.getUserByUid(userUid);
		}
		assertNotNull("User found by uid: " + userUid, user);
		return user;
	}
}
