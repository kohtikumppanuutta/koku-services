package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentReply;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * DAO interface for CRUD operations with 'ConsentReply' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
public interface ConsentReplyDAO extends AbstractEntityDAO<ConsentReply> {

    /**
     * @param consent
     * @param orCreateUser
     * @return
     */
    ConsentReply getReplyByConsentAndUser(final Consent consent, final User replier);

    /**
     * @param replier
     * @param startNum
     * @param i
     * @return
     */
    List<ConsentReply> getRepliedConsents(final User replier, final int startNum, final int maxNum);

    List<ConsentReply> getOldRepliedConsents(final User replier, final int startNum, final int maxNum);

    /**
     * @param orCreateUser
     * @return
     */
    Long getTotalRepliedConsents(final User user);

    Long getTotalOldRepliedConsents(final User user);

    /**
     * @param consent
     * @return
     */
    List<ConsentReply> getReplies(final Consent consent);

}
