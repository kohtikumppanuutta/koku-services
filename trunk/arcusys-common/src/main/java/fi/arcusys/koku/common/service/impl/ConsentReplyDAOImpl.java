package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.ConsentReplyDAO;
import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentReply;
import fi.arcusys.koku.common.service.datamodel.ConsentReplyStatus;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@Stateless
public class ConsentReplyDAOImpl extends AbstractEntityDAOImpl<ConsentReply> implements ConsentReplyDAO {

    public ConsentReplyDAOImpl() {
        super(ConsentReply.class);
    }

    /**
     * @param consent
     * @param replier
     * @return
     */
    @Override
    public ConsentReply getReplyByConsentAndUser(Consent consent, User replier) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("consent", consent);
        params.put("user", replier);
        return getSingleResultOrNull("findReplyByConsentAndUser", params);
    }

    /**
     * @param replier
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ConsentReply> getRepliedConsents(final User user, final int startNum, final int maxNum) {
        return getResultList("findRepliedConsentsByUser", getReplyParams(user), startNum, maxNum);
    }

    protected Map<String, Object> getReplyParams(final User user) {
        final Map<String, Object> params = new HashMap<String, Object>(); 
        params.put("user", user);
        params.put("status_valid", ConsentReplyStatus.Given);
        return params;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalRepliedConsents(User user) {
        return getSingleResult("countRepliedConsentsByUser", getReplyParams(user));
    }

    /**
     * @param consent
     * @return
     */
    @Override
    public List<ConsentReply> getReplies(Consent consent) {
        return getResultList("findRepliesByConsent", Collections.singletonMap("consent", consent));
    }

    /**
     * @param replier
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<ConsentReply> getOldRepliedConsents(User user, int startNum, int maxNum) {
        return getResultList("findOldRepliedConsentsByUser", getReplyParams(user), startNum, maxNum);
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalOldRepliedConsents(User user) {
        return getSingleResult("countOldRepliedConsentsByUser", getReplyParams(user));
    }
}
