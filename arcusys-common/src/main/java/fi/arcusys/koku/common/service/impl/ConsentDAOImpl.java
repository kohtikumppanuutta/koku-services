package fi.arcusys.koku.common.service.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.ConsentDAO;
import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentReply;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@Stateless
public class ConsentDAOImpl extends AbstractEntityDAOImpl<Consent> implements ConsentDAO {

    public ConsentDAOImpl() {
        super(Consent.class);
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<Consent> getAssignedConsents(User user, int startNum, int maxNum) {
        return getResultList("findAssignedConsentsByUser", Collections.singletonMap("userUid", user.getUid()), startNum, maxNum);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalAssignedConsents(User user) {
        return getSingleResult("countAssignedConsentsByUser", Collections.singletonMap("userUid", user.getUid()));
    }

    /**
     * @param user
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<Consent> getProcessedConsents(User user, int startNum, int maxNum) {
        return getResultList("findProcessedConsentsBySender", Collections.singletonMap("senderUid", user.getUid()), startNum, maxNum);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalProcessedConsents(User user) {
        return getSingleResult("countProcessedConsentsBySender", Collections.singletonMap("senderUid", user.getUid()));
    }
}
