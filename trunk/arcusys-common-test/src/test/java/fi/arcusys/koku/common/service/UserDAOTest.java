package fi.arcusys.koku.common.service;

import static junit.framework.Assert.assertNotNull;
import static junit.framework.Assert.assertNull;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.impl.UserDAOImpl;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-common-context.xml"})
public class UserDAOTest {
	
	@Autowired
	private UserDAO userDao;
	
	@Test
	public void testUserCRUD() {
		final String testUserUid = "testUser123";
		final User testUser;
		if ((testUser = userDao.getUserByUid(testUserUid)) != null) {
			userDao.delete(testUser);
		}
		assertNull("No user should be for uid " + testUserUid, userDao.getUserByUid(testUserUid));
		
		final User newUser = new User();
		newUser.setUid(testUserUid);
		userDao.create(newUser);
		
		final User createdUser = userDao.getUserByUid(testUserUid);
		assertNotNull("New user found:", createdUser);
	}
}
