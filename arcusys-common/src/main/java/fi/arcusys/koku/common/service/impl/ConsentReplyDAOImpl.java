package fi.arcusys.koku.common.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fi.arcusys.koku.common.service.ConsentReplyDAO;
import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentReply;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
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
}
