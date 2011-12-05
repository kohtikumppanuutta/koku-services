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
		    if (allowCreationByUid()) {
	            fromUser = new User();
	            fromUser.setUid(uid);
	            fromUser.setCitizenPortalName(uid);
                fromUser.setEmployeePortalName(uid);
	            fromUser = super.create(fromUser);
		    } else {
	            throw new IllegalArgumentException("Creation of the user by UID '" + uid + "' - should be used for test purposes only.");
		    }
		}
		return fromUser;
	}
	
	protected boolean allowCreationByUid() {
	    return false;
	}

    private String generateUid() {
        return UUID.randomUUID().toString();
    }

    /**
     * @param looraName
     * @return
     */
    @Override
    public User getOrCreateUserByEmployeePortalName(String looraName) {
        final User existingUser = getUserByPortalName(looraName, "findUserByEmployeePortalName");
        if (existingUser != null) {
            return existingUser;
        } else {
            return createNewUser(looraName, null);
        }
    }

    private User createNewUser(String looraName, final String kunpoName) {
        final User user = new User();
        user.setUid(generateUid());
        user.setEmployeePortalName(looraName);
        user.setCitizenPortalName(kunpoName);
        return super.create(user);
    }

    private User getUserByPortalName(String looraName, final String queryName) {
        if (looraName == null) {
            throw new IllegalArgumentException("Can't retrieve user with empty name.");
        }

        final User existingUser = getSingleResultOrNull(queryName, Collections.singletonMap("portalName", looraName));
        return existingUser;
    }

    /**
     * @param kunpoName
     * @return
     */
    @Override
    public User getOrCreateUserByCitizenPortalName(String kunpoName) {
        final User existingUser = getUserByPortalName(kunpoName, "findUserByCitizenPortalName");
        if (existingUser != null) {
            return existingUser;
        } else {
            return createNewUser(null, kunpoName);
        }
    }

    /**
     * @param kunpoName
     * @return
     */
    @Override
    public User getUserByCitizenPortalNameOrNull(String kunpoName) {
        return getUserByPortalName(kunpoName, "findUserByCitizenPortalName");
    }
}
