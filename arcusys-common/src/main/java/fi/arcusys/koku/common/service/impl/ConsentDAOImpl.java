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
import fi.arcusys.koku.common.service.dto.ConsentDTOCriteria;

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
    public List<Consent> getProcessedConsents(User user, ConsentDTOCriteria criteria, int startNum, int maxNum) {
        if (criteria == null || criteria.isEmpty()) {
            return getResultList("findProcessedConsentsBySender", Collections.singletonMap("sender", user), startNum, maxNum);
        } else {
            /* "SELECT DISTINCT cn FROM Consent cn WHERE " +
             * "(EXISTS (SELECT cr FROM ConsentReply cr WHERE cr.consent = cn))" +
             * " AND cn.creator.uid = :senderUid ORDER BY cn.id DESC"
             * */
            final StringBuilder query = new StringBuilder();
            // select
            query.append("SELECT DISTINCT cn FROM Consent cn ");
            final Map<String, Object> params = processCriteria(user, criteria, query);
            // order by
            query.append(" ORDER BY cn.id DESC");
            return executeQuery(query.toString(), params, startNum, maxNum);
        }
    }

    private Map<String, Object> processCriteria(User user,
            ConsentDTOCriteria criteria, final StringBuilder query) {
        final Map<String, Object> params = new HashMap<String, Object>();
        // where
        query.append(" WHERE cn.creator = :sender ");
        params.put("sender", user);
        // criteria applied
        final Long templateId = criteria.getConsentTemplateId();
        if (templateId != null) {
            query.append(" AND cn.template.id = :templateId" );
            params.put("templateId", templateId);
        }
        final String receipientUid = criteria.getReceipientUid();
        if (receipientUid != null && !"".equals(receipientUid.trim())) {
            query.append(" AND (EXISTS (SELECT cr FROM ConsentReply cr WHERE cr.consent = cn and cr.replier.uid = :replierUid )) ");
            params.put("replierUid", receipientUid);
        } else {
            query.append(" AND (EXISTS (SELECT cr FROM ConsentReply cr WHERE cr.consent = cn)) ");
        }
        return params;
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalProcessedConsents(User user, ConsentDTOCriteria criteria) {
        final StringBuilder query = new StringBuilder();
        query.append("SELECT COUNT (DISTINCT cn) FROM Consent cn ");
        final Map<String, Object> params = processCriteria(user, criteria, query);
        return executeQueryWithSingleResult(query.toString(), params);
    }
}
