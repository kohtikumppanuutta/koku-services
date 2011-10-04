package fi.arcusys.koku.tiva.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.xml.datatype.XMLGregorianCalendar;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.InformationRequestDAO;
import fi.arcusys.koku.common.service.UserDAO;
import fi.arcusys.koku.common.service.datamodel.InformationReplyAccessType;
import fi.arcusys.koku.common.service.datamodel.InformationReplyStatus;
import fi.arcusys.koku.common.service.datamodel.InformationRequest;
import fi.arcusys.koku.common.service.datamodel.InformationRequestCategory;
import fi.arcusys.koku.common.service.datamodel.InformationRequestReply;
import fi.arcusys.koku.common.service.dto.InformationRequestDTOCriteria;
import fi.arcusys.koku.tiva.service.InformationRequestServiceFacade;
import fi.arcusys.koku.tiva.soa.InformationAccessType;
import fi.arcusys.koku.tiva.soa.InformationCategoryTO;
import fi.arcusys.koku.tiva.soa.InformationRequestCriteria;
import fi.arcusys.koku.tiva.soa.InformationRequestDetail;
import fi.arcusys.koku.tiva.soa.InformationRequestQuery;
import fi.arcusys.koku.tiva.soa.InformationRequestReplyTO;
import fi.arcusys.koku.tiva.soa.InformationRequestStatus;
import fi.arcusys.koku.tiva.soa.InformationRequestSummary;
import fi.arcusys.koku.tiva.soa.InformationRequestTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
@Stateless
public class InformationRequestServiceFacadeImpl implements InformationRequestServiceFacade {

    @EJB
    private InformationRequestDAO informationRequestDao;
    
    @EJB
    private UserDAO userDao; 
    
    /**
     * @param request
     * @return
     */
    @Override
    public Long createInformationRequest(InformationRequestTO requestTO) {
        final InformationRequest request = new InformationRequest();
        request.setAdditionalInfo(requestTO.getAdditionalInfo());
        final Set<InformationRequestCategory> categories = new HashSet<InformationRequestCategory>();
        for (final Long categoryUid : requestTO.getCategories()) {
            final InformationRequestCategory category = new InformationRequestCategory();
            category.setCategoryUid("" + categoryUid);
            category.setRequest(request);
            categories.add(category);
        }
        request.setCategories(categories);
        request.setDescription(requestTO.getDescription());
        request.setLegislationInfo(requestTO.getLegislationInfo());
        request.setReceiver(userDao.getOrCreateUser(requestTO.getReceiverUid()));
        request.setRequestPurpose(requestTO.getRequestPurpose());
        request.setSender(userDao.getOrCreateUser(requestTO.getSenderUid()));
        request.setTargetPerson(userDao.getOrCreateUser(requestTO.getTargetPersonUid()));
        request.setTitle(requestTO.getTitle());
        request.setValidTill(getSafeDate(requestTO.getValidTill()));
        return informationRequestDao.create(request).getId();
    }

    private Date getSafeDate(XMLGregorianCalendar calendar) {
        if (calendar == null) {
            return null;
        } else {
            return calendar.toGregorianCalendar().getTime();
        }
    }

    /**
     * @param replyTO
     */
    @Override
    public void approveRequest(InformationRequestReplyTO replyTO) {
        final InformationRequest request = loadRequestForReply(replyTO.getRequestId());
        
        final InformationRequestReply reply = new InformationRequestReply();
        reply.setAccessType(replyTO.getInformationAccessType() != null ? replyTO.getInformationAccessType().toDmType() : null);
        reply.setAdditionalReplyInfo(replyTO.getAdditionalInfo());
        reply.setAttachmentURL(replyTO.getAttachmentURL());
        reply.setReplyDescription(replyTO.getDescription());
        reply.setInformationDetails(replyTO.getInformationDetails());
        reply.setReplyStatus(InformationReplyStatus.Approved);
        reply.setReplyCreatedDate(new Date());
        request.setValidTill(getSafeDate(replyTO.getValidTill()));
        final Set<InformationRequestCategory> categories = new HashSet<InformationRequestCategory>();
        for (final Long categoryUid : replyTO.getCategoryIds()) {
            final InformationRequestCategory category = new InformationRequestCategory();
            category.setCategoryUid("" + categoryUid);
            category.setRequest(request);
            categories.add(category);
        }
        request.setCategories(categories);
        request.setReply(reply);
        informationRequestDao.update(request);
    }

    private InformationRequest loadRequestForReply(final Long requestId) {
        final InformationRequest request = loadRequest(requestId);
        
        if (request.getReply() != null && request.getReply().getReplyStatus() != null) {
            throw new IllegalStateException("InformationRequest with ID " + requestId + " is already processed.");
        }
        return request;
    }

    private InformationRequest loadRequest(final Long requestId) {
        final InformationRequest request = informationRequestDao.getById(requestId);
        if (request == null) {
            throw new IllegalArgumentException("InformationRequest with ID " + requestId + " is not found.");
        }
        return request;
    }

    /**
     * @param requestId
     * @param explanation
     */
    @Override
    public void declineRequest(Long requestId, String explanation) {
        final InformationRequest request = loadRequestForReply(requestId);
        final InformationRequestReply reply = new InformationRequestReply();
        reply.setReplyStatus(InformationReplyStatus.Declined);
        reply.setReplyDescription(explanation);
        request.setReply(reply);
        informationRequestDao.update(request);
    }

    /**
     * @return
     */
    @Override
    public InformationCategoryTO getCategories() {
        // TODO Auto-generated method stub
        return null;
    }

    /**
     * @param receiverUid
     * @param informationRequestQuery
     * @return
     */
    @Override
    public List<InformationRequestSummary> getRepliedRequests(String receiverUid, InformationRequestQuery query) {
        final InformationRequestDTOCriteria criteria = getDtoCriteria(query != null ? query.getCriteria() : null);
        criteria.setReceiverUid(receiverUid);
        
        final List<InformationRequestSummary> result = new ArrayList<InformationRequestSummary>();
        for (final InformationRequest request : informationRequestDao.getRepliedRequests(criteria, query.getStartNum(), query.getMaxNum() - query.getStartNum() + 1)) {
            final InformationRequestSummary requestTO = new InformationRequestSummary();
            result.add(fillRequestSummaryByDO(request, requestTO));
        }
        return result;
    }

    private InformationRequestSummary fillRequestSummaryByDO(InformationRequest request, final InformationRequestSummary requestTO) {
        requestTO.setReceiverUid(request.getReceiver().getUid());
        requestTO.setRequestId(request.getId());
        requestTO.setSenderUid(request.getSender().getUid());
        requestTO.setTargetPersonUid(request.getTargetPerson().getUid());
        requestTO.setTitle(request.getTitle());
        requestTO.setValidTill(CalendarUtil.getXmlDate(request.getValidTill()));
        final InformationRequestReply reply = request.getReply();
        if (reply == null || reply.getReplyStatus() == null) {
            requestTO.setStatus(InformationRequestStatus.Open);
        } else {
            if (reply.getReplyStatus() == InformationReplyStatus.Declined) {
                requestTO.setStatus(InformationRequestStatus.Declined);
            } else { // status is Approved
                if (requestTO.getValidTill() == null || requestTO.getValidTill().compare(CalendarUtil.getXmlDate(new Date())) >= 0) {
                    requestTO.setStatus(InformationRequestStatus.Valid);
                } else {
                    requestTO.setStatus(InformationRequestStatus.Expired);
                }
            }
        } 
        return requestTO;
    }

    /**
     * @param senderUid
     * @param query
     * @return
     */
    @Override
    public List<InformationRequestSummary> getSentRequests(String senderUid, InformationRequestQuery query) {
        final InformationRequestDTOCriteria criteria = getDtoCriteria(query.getCriteria());
        criteria.setSenderUid(senderUid);
        
        final List<InformationRequestSummary> result = new ArrayList<InformationRequestSummary>();
        for (final InformationRequest request : informationRequestDao.getSentRequests(criteria, query.getStartNum(), query.getMaxNum() - query.getStartNum() + 1)) {
            final InformationRequestSummary requestTO = new InformationRequestSummary();
            result.add(fillRequestSummaryByDO(request, requestTO));
        }
        return result;
    }

    /**
     * @param receiverUid
     * @return
     */
    @Override
    public int getTotalRepliedRequests(String receiverUid, InformationRequestCriteria criteria) {
        final InformationRequestDTOCriteria dtoCriteria = getDtoCriteria(criteria);
        dtoCriteria.setReceiverUid(receiverUid);
        return getIntValue(informationRequestDao.getTotalRepliedRequests(dtoCriteria));
    }

    private int getIntValue(final Long longValue) {
        if (longValue == null) {
            return 0;
        }
        return longValue.intValue();
    }

    /**
     * @param senderUid
     * @return
     */
    @Override
    public int getTotalSentRequests(String senderUid, final InformationRequestCriteria criteria) {
        final InformationRequestDTOCriteria dtoCriteria = getDtoCriteria(criteria);
        dtoCriteria.setSenderUid(senderUid);
        return getIntValue(informationRequestDao.getTotalSentRequests(dtoCriteria));
    }

    private InformationRequestDTOCriteria getDtoCriteria(final InformationRequestCriteria criteria) {
        return criteria == null ? new InformationRequestDTOCriteria() : criteria.toDtoCriteria();
    }

    /**
     * @param requestId
     * @return
     */
    @Override
    public InformationRequestDetail getRequestDetails(long requestId) {
        final InformationRequest request = loadRequest(requestId);
        
        final InformationRequestDetail requestTO = new InformationRequestDetail();
        fillRequestSummaryByDO(request, requestTO);
        
        requestTO.setDescription(request.getDescription());
        requestTO.setRequestPurpose(request.getRequestPurpose());
        requestTO.setLegislationInfo(request.getLegislationInfo());
        requestTO.setAdditionalInfo(request.getAdditionalInfo());
        requestTO.setCreatedDate(CalendarUtil.getXmlDate(request.getCreatedDate()));
        
        // fill reply info
        final InformationRequestReply reply = request.getReply();
        if (reply != null && reply.getReplyStatus() != null) {
            requestTO.setReplyDescription(reply.getReplyDescription());
            requestTO.setInformationDetails(reply.getInformationDetails());
            requestTO.setAdditionalReplyInfo(reply.getAdditionalReplyInfo());
            requestTO.setAttachmentURL(reply.getAttachmentURL());
            requestTO.setAccessType(InformationAccessType.valueOf(reply.getAccessType()));
        }

        final List<String> categories = new ArrayList<String>();
        for (final InformationRequestCategory category : request.getCategories()) {
            categories.add(category.getCategoryUid());
        }
        requestTO.setCategories(categories);

        return requestTO;
    }

    /**
     * @param criteria
     * @return
     */
    @Override
    public int getTotalRequests(InformationRequestCriteria criteria) {
        return getIntValue(informationRequestDao.getTotalRepliedRequests(getDtoCriteria(criteria)));
    }

    /**
     * @param criteria
     * @return
     */
    @Override
    public List<InformationRequestSummary> getRequests(InformationRequestQuery query) {
        final InformationRequestDTOCriteria criteria = getDtoCriteria(query.getCriteria());
        
        final List<InformationRequestSummary> result = new ArrayList<InformationRequestSummary>();
        for (final InformationRequest request : informationRequestDao.getRepliedRequests(criteria, query.getStartNum(), query.getMaxNum() - query.getStartNum() + 1)) {
            final InformationRequestSummary requestTO = new InformationRequestSummary();
            result.add(fillRequestSummaryByDO(request, requestTO));
        }
        return result;
    }

}
