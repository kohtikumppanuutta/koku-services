package fi.arcusys.koku.tiva.service;

import static junit.framework.Assert.*;

import java.util.Arrays;
import java.util.Date;
import java.util.List;

import javax.xml.datatype.XMLGregorianCalendar;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.CommonTestUtil;
import fi.arcusys.koku.common.service.datamodel.InformationReplyStatus;
import fi.arcusys.koku.common.service.datamodel.InformationRequest;
import fi.arcusys.koku.tiva.soa.InformationAccessType;
import fi.arcusys.koku.tiva.soa.InformationRequestCriteria;
import fi.arcusys.koku.tiva.soa.InformationRequestQuery;
import fi.arcusys.koku.tiva.soa.InformationRequestReplyTO;
import fi.arcusys.koku.tiva.soa.InformationRequestStatus;
import fi.arcusys.koku.tiva.soa.InformationRequestSummary;
import fi.arcusys.koku.tiva.soa.InformationRequestTO;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 22, 2011
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations={"/test-tiva-context.xml"})
public class InformationRequestServiceTest {

    @Autowired
    private InformationRequestServiceFacade service;
    
    @Autowired
    private CommonTestUtil testUtil;
    
    @Test
    public void createInformationRequestAndReply() {
        // create new
        final String senderUid = "tpSender";
        final String receiverUid = "tpReceiver";
        final String targetPersonUid = "tpTargetPerson";
        final Long requestId = createInformationRequest(senderUid, receiverUid, targetPersonUid);
        
        final InformationRequestQuery query = new InformationRequestQuery();
        query.setStartNum(1);
        query.setMaxNum(10);
        final InformationRequestCriteria criteria = new InformationRequestCriteria();
        query.setCriteria(criteria);

        // check in sender's list
        final List<InformationRequestSummary> sent = service.getSentRequests(senderUid, query);
        final InformationRequestSummary sentRequest = getById(sent, requestId);
        assertNotNull(sentRequest);
        assertEquals(InformationRequestStatus.Open, sentRequest.getStatus());

        // check in replier's list
        assertNull(getById(service.getRepliedRequests(receiverUid, query), requestId));

        // reply
        final InformationRequestReplyTO reply = new InformationRequestReplyTO();
        reply.setAdditionalInfo("additional info");
        reply.setAttachmentURL("attachment URL");
        reply.setCategoryIds(Arrays.asList("3", "4"));
        reply.setDescription("description");
        reply.setInformationAccessType(InformationAccessType.Portal);
        reply.setInformationDetails("information details");
        reply.setRequestId(requestId);
        reply.setValidTill(CalendarUtil.getXmlDate(new Date()));
        service.approveRequest(reply);
        
        // check in replier's list
        final List<InformationRequestSummary> replied = service.getRepliedRequests(receiverUid, query);
        assertNotNull(getById(replied, requestId));
        
        assertEquals("Check reply details: ", InformationAccessType.Portal, service.getRequestDetails(requestId).getAccessType());

        // check status
        assertEquals(InformationRequestStatus.Valid, getById(service.getSentRequests(senderUid, query), requestId).getStatus());
    }

    private Long createInformationRequest(final String senderUid,
            final String receiverUid, final String targetPersonUid) {
        final InformationRequestTO request = new InformationRequestTO();
        request.setAdditionalInfo("some additional info");
        request.setDescription("description");
        request.setReceiverUid(receiverUid);
        request.setSenderUid(senderUid);
        request.setTargetPersonUid(targetPersonUid);
        request.setValidTill(CalendarUtil.getXmlDate(new Date()));
        request.setCategories(Arrays.asList("1", "2"));
        final Long requestId = service.createInformationRequest(request);
        return requestId;
    }
    
    @Test
    public void rejectRequest() {
        final String senderUid = "tpSender";
        final String receiverUid = "tpReceiver";
        final String targetPersonUid = "tpTargetPerson";
        final Long requestId = createInformationRequest(senderUid, receiverUid, targetPersonUid);
        
        service.declineRequest(requestId, receiverUid, "rejected");
        
        final InformationRequestSummary rejectedRequest = getById(service.getSentRequests(senderUid, new InformationRequestQuery(1, 10)), requestId);
        assertEquals(InformationRequestStatus.Declined, rejectedRequest.getStatus());
    }
    
    @Test
    public void testTotals() {
        final String senderUid = "tpSender";
        final String receiverUid = "tpReceiver";
        final String targetPersonUid = "tpTargetPerson";
        
        final InformationRequestCriteria criteria = new InformationRequestCriteria();
        final int oldSentTotal = service.getTotalSentRequests(senderUid, criteria);
        final int oldRepliedTotal = service.getTotalRepliedRequests(receiverUid, criteria);
        
        final Long requestId = createInformationRequest(senderUid, receiverUid, targetPersonUid);
        
        assertEquals(oldSentTotal + 1, service.getTotalSentRequests(senderUid, criteria));
        assertEquals(oldRepliedTotal, service.getTotalRepliedRequests(receiverUid, criteria));
        
        service.declineRequest(requestId, receiverUid, "rejected");

        assertEquals(oldSentTotal + 1, service.getTotalSentRequests(senderUid, criteria));
        assertEquals(oldRepliedTotal + 1, service.getTotalRepliedRequests(receiverUid, criteria));
    }
    
    @Test
    public void testFilters() {
        final String senderUid = "tpSender";
        final String receiverUid = "tpReceiver";
        final String targetPersonUid = "tpTargetPerson";
        
        final InformationRequestQuery query = new InformationRequestQuery();
        query.setStartNum(1);
        query.setMaxNum(10);
        final InformationRequestCriteria criteria = new InformationRequestCriteria();
        query.setCriteria(criteria);
        criteria.setTargetPersonUid(targetPersonUid);
        final int oldTotal = service.getTotalRequests(criteria);
        
        final Long requestId = createInformationRequest(senderUid, receiverUid, targetPersonUid);
        
        assertEquals(oldTotal, service.getTotalRequests(criteria));
        assertNull(getById(service.getRequests(query), requestId));
        
        service.declineRequest(requestId, receiverUid, "rejected");

        assertEquals(oldTotal + 1, service.getTotalRequests(criteria));
        assertNotNull(getById(service.getRequests(query), requestId));
        
        final XMLGregorianCalendar xmlDate = CalendarUtil.getXmlDate(new Date());
        criteria.setCreatedFromDate(xmlDate);
        assertNotNull(getById(service.getRequests(query), requestId));

        xmlDate.setYear(xmlDate.getYear() + 1);
        criteria.setCreatedFromDate(xmlDate);
        assertNull(getById(service.getRequests(query), requestId));
        
        criteria.setRepliedToDate(xmlDate);
        criteria.setCreatedFromDate(null);
        assertNotNull(getById(service.getRequests(query), requestId));
    }
    
    private <IR extends InformationRequestSummary> IR getById(final List<IR> requests, final Long requestId) {
        for (final IR request : requests) {
            if (request.getRequestId().equals(requestId) ) {
                return request;
            }
        }
        return null;
    }
}
