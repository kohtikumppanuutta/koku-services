package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.AuthorizationDAO;
import fi.arcusys.koku.common.service.datamodel.Authorization;
import fi.arcusys.koku.common.service.datamodel.AuthorizationReplyStatus;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.dto.AuthorizationDTOCriteria;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 12, 2011
 */
@Stateless
public class AuthorizationDAOImpl extends AbstractEntityDAOImpl<Authorization> implements AuthorizationDAO {

    public AuthorizationDAOImpl() {
        super(Authorization.class);
    }

    /**
     * @param orCreateUser
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<Authorization> getReceivedAuthorizations(User user, int startNum, int maxResults) {
        return getResultList("findReceivedAuthorizations", Collections.singletonMap("user", user), startNum, maxResults);
    }

    /**
     * @param orCreateUser
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<Authorization> getSentAuthorizations(User user, int startNum, int maxResults) {
        return getResultList("findSentAuthorizations", Collections.singletonMap("user", user), startNum, maxResults);
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public Long getTotalSentAuthorizations(User user) {
        return getSingleResult("countSentAuthorizations", Collections.singletonMap("user", user));
    }

    /**
     * @param userUid
     * @return
     */
    @Override
    public Long getTotalReceivedAuthorizations(User user) {
        return getSingleResult("countReceivedAuthorizations", Collections.singletonMap("user", user));
    }

    /**
     * @param query
     * @return
     */
    @Override
    public List<Authorization> getAuthorizations(AuthorizationDTOCriteria criteria, int startNum, int maxResults) {
        final StringBuilder query = new StringBuilder();
        // select
        query.append("SELECT DISTINCT auth FROM Authorization auth ");
        final Map<String, Object> params = processCriteria(criteria, query);
        // order by
        query.append(" ORDER BY auth.id DESC");
        return executeQuery(query.toString(), params, startNum, maxResults);
    }

    private Map<String, Object> processCriteria(AuthorizationDTOCriteria criteria, final StringBuilder query) {
        final Map<String, Object> params = new HashMap<String, Object>();

        query.append(" WHERE auth.replyStatus = :replyStatus ");
        params.put("replyStatus", AuthorizationReplyStatus.Approved);
        // criteria applied
        final Long templateId = criteria.getAuthorizationTemplateId();
        if (templateId != null) {
            query.append(" AND auth.template.id = :templateId " );
            params.put("templateId", templateId);
        }
        final String senderUid = criteria.getSenderUid();
        if (senderUid != null && !"".equals(senderUid.trim())) {
            query.append(" AND auth.fromUser.uid = :senderUid ");
            params.put("senderUid", senderUid);
        } 
        final String receipientUid = criteria.getReceipientUid();
        if (receipientUid != null && !"".equals(receipientUid.trim())) {
            query.append(" AND auth.toUser.uid = :receipientUid ");
            params.put("receipientUid", receipientUid);
        } 
        return params;
    }

    /**
     * @param authorizationDTOCriteria
     * @return
     */
    @Override
    public Long getTotalAuthorizations(AuthorizationDTOCriteria criteria) {
        final StringBuilder query = new StringBuilder();
        // select
        query.append("SELECT COUNT(DISTINCT auth) FROM Authorization auth ");
        final Map<String, Object> params = processCriteria(criteria, query);
        return executeQueryWithSingleResult(query.toString(), params);
    }
}
