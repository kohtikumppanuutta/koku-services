package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;
import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.TargetPerson;
import fi.arcusys.koku.common.service.datamodel.User;

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
		return getSingleResultOrNull("findUserByUid", Collections.singletonMap("uid", uid));
	}

	/**
	 * @param uid
	 * @return
	 */
	@Override
	public User getOrCreateUser(final String uid) {
		if (uid == null) {
			throw new IllegalArgumentException("Can't retrieve user with empty UID.");
		}
		User fromUser = getUserByUid(uid);
		
		if (fromUser == null) {
			fromUser = new User();
			fromUser.setUid(uid);
			fromUser = super.create(fromUser);
		}
		return fromUser;
	}

    /**
     * @param kunpoName
     * @return
     */
    @Override
    public User getOrCreateUserByKunpoName(String kunpoName) {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param looraName
     * @return
     */
    @Override
    public User getOrCreateUserByLooraName(String looraName) {
        // TODO Auto-generated method stub
        return null;
    }
}
