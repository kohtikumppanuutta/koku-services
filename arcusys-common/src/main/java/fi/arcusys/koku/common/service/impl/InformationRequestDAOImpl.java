package fi.arcusys.koku.common.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.InformationRequestDAO;
import fi.arcusys.koku.common.service.datamodel.InformationRequest;
import fi.arcusys.koku.common.service.datamodel.InformationRequestCategory;
import fi.arcusys.koku.common.service.dto.InformationRequestDTOCriteria;

/**
 * DAO implementation for CRUD operations with 'InformationRequest' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
@Stateless
public class InformationRequestDAOImpl extends AbstractEntityDAOImpl<InformationRequest> implements InformationRequestDAO {

    /* 
     * InformationRequest text fields:
        - title;
        - description;
        - requestPurpose;
        - legislationInfo;
        - additionalInfo;
     * InformationRequestReply text fields:
        - replyDescription;
        - informationDetails;
        - additionalReplyInfo;
     * */
    private final static String[] FREE_TEXT_SEARCHABLE_FIELDS = new String[] {
        "request.title",
        "request.description",
        "request.requestPurpose",
        "request.legislationInfo",
        "request.additionalInfo",
        
        "request.reply.replyDescription",
        "request.reply.informationDetails",
        "request.reply.additionalReplyInfo"
    };
    
    public InformationRequestDAOImpl() {
        super(InformationRequest.class);
    }
    
    /**
     * @param entity
     */
    @Override
    public InformationRequest update(InformationRequest entity) {
        if (entity.getId() == null) {
            return super.update(entity);
        }
        
        final InformationRequest existingEntity = getById(entity.getId());
        final Set<InformationRequestCategory> forDelete = new HashSet<InformationRequestCategory>(existingEntity.getCategories());
        // still valid categories
        for (final InformationRequestCategory category : entity.getCategories()) {
            if (category.getId() != null) {
                forDelete.remove(category);
            }
        }
        
        for (final InformationRequestCategory category : forDelete) {
            em.remove(category);
        }
        final InformationRequest mergedEntity = super.update(entity);
        return mergedEntity;
    }

    /**
     * @param criteria
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<InformationRequest> getRepliedRequests(InformationRequestDTOCriteria criteria, int startNum, int maxResults) {
        final StringBuilder query = new StringBuilder();
        // select
        query.append("SELECT DISTINCT request FROM InformationRequest request WHERE request.reply.replyStatus IS NOT NULL ");
        final Map<String, Object> params = processCriteria(criteria, query);
        // order by
        query.append(" ORDER BY request.id DESC");
        return executeQuery(query.toString(), params, startNum, maxResults);
    }

    private Map<String, Object> processCriteria(InformationRequestDTOCriteria criteria, final StringBuilder query) {
        final Map<String, Object> params = new HashMap<String, Object>();

        final StringBuilder where = new StringBuilder(); 
        // user based criteria applied
        final String senderUid = criteria.getSenderUid();
        if (senderUid != null && !"".equals(senderUid.trim())) {
            appendAnd(where, " request.sender.uid = :senderUid ");
            params.put("senderUid", senderUid);
        } 
        final String receiverUid = criteria.getReceiverUid();
        if (receiverUid != null && !"".equals(receiverUid.trim())) {
            appendAnd(where, " request.receiver.uid = :receiverUid ");
            params.put("receiverUid", receiverUid);
        } 
        final String targetPersonUid = criteria.getTargetPersonUid();
        if (targetPersonUid != null && !"".equals(targetPersonUid.trim())) {
            appendAnd(where, " request.targetPerson.uid = :targetPersonUid ");
            params.put("targetPersonUid", targetPersonUid);
        } 
        
        // date based criteria applied
        final Date createdFromDate = criteria.getCreatedFromDate();
        if (createdFromDate != null) {
            appendAnd(where, " request.createdDate >= :createdFromDate ");
            params.put("createdFromDate", createdFromDate);
        }
        final Date createdToDate = criteria.getCreatedToDate();
        if (createdToDate != null) {
            appendAnd(where, " request.createdDate <= :createdToDate ");
            params.put("createdToDate", createdToDate);
        }
        final Date repliedFromDate = criteria.getRepliedFromDate();
        if (repliedFromDate != null) {
            appendAnd(where, " request.reply.replyCreatedDate >= :repliedFromDate ");
            params.put("repliedFromDate", repliedFromDate);
        }
        final Date repliedToDate = criteria.getRepliedToDate();
        if (repliedFromDate != null) {
            appendAnd(where, " request.reply.replyCreatedDate <= :repliedToDate ");
            params.put("repliedToDate", repliedToDate);
        }
        
        // text search criteria applied
        final String informationContent = criteria.getInformationContent();
        if (informationContent != null && !informationContent.isEmpty()) {
            appendAnd(where, " request.reply.informationDetails LIKE :informationContent ");
            params.put("informationContent", getPrefixAndSuffixLike(informationContent));
        }
        final String freeText = criteria.getFreeText();
        if (freeText != null && !freeText.isEmpty() && FREE_TEXT_SEARCHABLE_FIELDS.length > 0) {
            final StringBuilder freeTextQuery = new StringBuilder();
            freeTextQuery.append(" (");
            for (final String freeTextField : FREE_TEXT_SEARCHABLE_FIELDS) {
                freeTextQuery.append(" ").append(freeTextField).append(" LIKE :freeText OR ");
            }
            freeTextQuery.setLength(freeTextQuery.length() - " OR ".length());
            freeTextQuery.append(" )");
            appendAnd(where, freeTextQuery.toString());
            params.put("freeText", getPrefixAndSuffixLike(freeText));
        }

        appendAnd(query, where.toString());
        return params;
    }

    private void appendAnd(final StringBuilder where, final String append) {
        if (append == null || "".equals(append)) {
            return;
        }
        
        if (where.length() > 0) {
            where.append(" AND ");
        }
        where.append(append);
    }

    /**
     * @param criteria
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<InformationRequest> getSentRequests(
            InformationRequestDTOCriteria criteria, int startNum, int maxResults) {
        final StringBuilder query = new StringBuilder();
        // select
        query.append("SELECT DISTINCT request FROM InformationRequest request ");
        final StringBuilder where = new StringBuilder();
        final Map<String, Object> params = processCriteria(criteria, where);
        if (where.length() > 0) {
            query.append(" WHERE ").append(where);
        }
        // order by
        query.append(" ORDER BY request.id DESC");
        return executeQuery(query.toString(), params, startNum, maxResults);
    }

    /**
     * @param criteria
     * @return
     */
    @Override
    public Long getTotalRepliedRequests(InformationRequestDTOCriteria criteria) {
        final StringBuilder query = new StringBuilder();
        // select
        query.append("SELECT COUNT(DISTINCT request) FROM InformationRequest request WHERE request.reply.replyStatus IS NOT NULL ");
        final Map<String, Object> params = processCriteria(criteria, query);
        return executeQueryWithSingleResult(query.toString(), params);
    }

    /**
     * @param criteria
     * @return
     */
    @Override
    public Long getTotalSentRequests(InformationRequestDTOCriteria criteria) {
        final StringBuilder query = new StringBuilder();
        // select
        query.append("SELECT COUNT(DISTINCT request) FROM InformationRequest request ");
        final StringBuilder where = new StringBuilder();
        final Map<String, Object> params = processCriteria(criteria, where);
        if (where.length() > 0) {
            query.append(" WHERE ").append(where);
        }
        return executeQueryWithSingleResult(query.toString(), params);
    }
}
