package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.TargetPersonDAO;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.TargetPerson;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 22, 2011
 */
@Stateless
public class TargetPersonDAOImpl extends AbstractEntityDAOImpl<TargetPerson> implements TargetPersonDAO {

    @EJB
    private UserDAO userDAO;
    
    public TargetPersonDAOImpl() {
        super(TargetPerson.class);
    }
    
    private List<TargetPerson> getTargetPersonsByUid(final String uid) {
        final Map<String, Object> params = Collections.<String,Object>singletonMap("uid", uid);
        return getResultList("findTargetPersonByUid", params);
    }

    /**
     * @param targetPerson
     * @param receipients
     * @return
     */
    @Override
    public TargetPerson getOrCreateTargetPerson(final String targetPersonUid, final List<String> receipients) {
        if (targetPersonUid == null) {
            return null;
        }
        List<TargetPerson> targetPersons = getTargetPersonsByUid(targetPersonUid);
        
        for (final TargetPerson targetPerson : targetPersons) {
            if (equalsByUids(receipients, targetPerson.getGuardians())) {
                return targetPerson;
            }
        }
        final TargetPerson targetPerson = new TargetPerson();
        targetPerson.setTargetUser(userDAO.getOrCreateUser(targetPersonUid));
        if (receipients != null) {
            for (final String userUid : receipients) {
                targetPerson.getGuardians().add(userDAO.getOrCreateUser(userUid));
            }
        }
        return super.create(targetPerson);
    }

    private boolean equalsByUids(final List<String> userUids, final Set<User> users) {
        final Set<String> uids = new HashSet<String>(userUids);
        for (final User user : users) {
            if (!uids.contains(user.getUid())) {
                return false;
            } else {
                uids.remove(user.getUid());
            }
        }
        return uids.isEmpty();
    }

}
