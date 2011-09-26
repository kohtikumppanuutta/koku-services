package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.UUID;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.soa.UsersAndGroupsService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Stateless
public class UserDAOImpl extends AbstractEntityDAOImpl<User> implements UserDAO {

    private final static Logger logger = LoggerFactory.getLogger(UserDAOImpl.class);
    
    @EJB
    private UsersAndGroupsService usersService;
    
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
			logger.warn("Creation of the user by UID - should be used for test purposes only.");
		}
		return fromUser;
	}

    /**
     * @param kunpoName
     * @return
     */
    @Override
    public User getOrCreateUserByPortalName(String portalName) {
        if (portalName == null) {
            throw new IllegalArgumentException("Can't retrieve user with empty name.");
        }

        final User existingUser = getSingleResultOrNull("findUserByPortalName", Collections.singletonMap("portalName", portalName));
        if (existingUser != null) {
            return existingUser;
        }
        
        final String ssn = usersService.getSsnByLdapName(portalName);
        // get some extra info from CustomerService
        final User user = new User();
        user.setSsn(ssn);
        user.setUid(generateUid());
        user.setPortalName(portalName);
        return super.create(user);
    }
    
    private String generateUid() {
        return UUID.randomUUID().toString();
    }
}
