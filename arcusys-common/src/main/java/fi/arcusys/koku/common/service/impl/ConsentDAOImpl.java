package fi.arcusys.koku.common.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.ConsentDAO;
import fi.arcusys.koku.common.service.datamodel.Consent;
import fi.arcusys.koku.common.service.datamodel.ConsentReplyStatus;
import fi.arcusys.koku.common.service.datamodel.ReceipientsType;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.dto.ConsentDTOCriteria;
import fi.arcusys.koku.common.service.dto.ConsentExtDtoCriteria;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
@Stateless
public class ConsentDAOImpl extends AbstractEntityDAOImpl<Consent> implements ConsentDAO {

    /**
     * 
     */
    private static final int MAX_CONSENTS_RETREIVED = 50;

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

    /**
     * @param dtoCriteria
     * @return
     */
    @Override
    public List<Consent> searchConsents(ConsentExtDtoCriteria criteria) {
        final StringBuilder query = new StringBuilder();
        // select
        query.append("SELECT DISTINCT cn FROM Consent cn LEFT JOIN cn.givenTo givenTo_ ");
        final Map<String, Object> params = new HashMap<String, Object>();
        // where
        
        // only replied and valid consents will be retreived
//        query.append(" WHERE (NOT (:given_status <> ANY (SELECT cr.status FROM ConsentReply cr WHERE cr.consent = cn ))) ");
        query.append(" WHERE (EXISTS (SELECT DISTINCT cr.id FROM ConsentReply cr WHERE cr.consent = cn AND cr.status = :given_status)) ");
        params.put("given_status", ConsentReplyStatus.Given);
        // criteria applied
        final String templatePrefix = criteria.getTemplateNamePrefix();
        if (templatePrefix != null && !"".equals(templatePrefix.trim())) {
            query.append(" AND (cn.template.code IS NULL AND cn.template.title LIKE :templateNamePrefix OR cn.template.code LIKE :templateNamePrefix ) " );
            params.put("templateNamePrefix", getPrefixLike(templatePrefix));
        }
        final String targetPersonUid = criteria.getTargetPerson();
        if (targetPersonUid != null && !"".equals(targetPersonUid.trim())) {
            query.append(" AND cn.targetPerson.uid = :targetPersonUid ");
            params.put("targetPersonUid", targetPersonUid);
        } 
        // getGivenTo
        final List<String> givenTo = criteria.getGivenTo();
        if (givenTo != null && !givenTo.isEmpty()) {
            query.append(" AND givenTo_.partyId IN (:givenTo) ");
            params.put("givenTo", givenTo);
        }
        // getInformationTargetId
        final String informationTargetId = criteria.getInformationTargetId();
        if (informationTargetId != null && !"".equals(informationTargetId.trim())) {
            query.append(" AND (cn.informationTargetId = :informationTargetId OR cn.informationTargetId IS NULL) ");
            params.put("informationTargetId", informationTargetId);
        } else {
            query.append(" AND cn.informationTargetId IS NULL ");
        }
        
        // order by
        query.append(" ORDER BY cn.id DESC");
        return executeQuery(query.toString(), params, 1, ConsentDAOImpl.MAX_CONSENTS_RETREIVED);
    }

    /**
     * @param replyTill
     * @return
     */
    @Override
    public List<Consent> getOpenConsentsByReplyTillDate(Date replyTill) {
        final Map<String, Object> params = new HashMap<String, Object>();
        final Calendar calendar = CalendarUtil.getXmlDate(replyTill).toGregorianCalendar();
        params.put("replyTillDateFrom", calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
        params.put("replyTillDateTo", calendar.getTime());
        params.put("receipientsTypeBoth", ReceipientsType.BothParents);
        return getResultList("findOpenConsentsByReplyTillDate", params);
    }
}
