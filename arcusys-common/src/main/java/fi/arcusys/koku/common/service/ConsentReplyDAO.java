package fi.arcusys.koku.common.service;

import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentReply;
import fi.arcusys.koku.common.service.datamodel.User;

/**
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

}
