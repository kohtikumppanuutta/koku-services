package fi.arcusys.koku.tiva.soa;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import javax.ejb.Stateless;
import javax.jws.WebService;
import javax.xml.datatype.XMLGregorianCalendar;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.service.common.CalendarUtil;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 16, 2011
 */
@Stateless
@WebService(serviceName = "KokuValtakirjaProcessingService", portName = "KokuValtakirjaProcessingServicePort", 
        endpointInterface = "fi.arcusys.koku.tiva.soa.KokuValtakirjaProcessingService",
        targetNamespace = "http://soa.tiva.koku.arcusys.fi/")
public class KokuValtakirjaProcessingServiceImpl implements KokuValtakirjaProcessingService {

    private final static Logger logger = LoggerFactory.getLogger(KokuValtakirjaProcessingServiceImpl.class);
    
    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<AuthorizationTemplateTO> getAuthorizationTemplates(String searchString, int limit) {
        // TODO Auto-generated method stub
        logger.info("getAuthorizationTemplates: " + searchString + ", " + limit);
        return Collections.singletonList(createTestTemplate());
    }

    private AuthorizationTemplateTO createTestTemplate() {
        final AuthorizationTemplateTO template = new AuthorizationTemplateTO();
        template.setTemplateId(1);
        template.setTemplateName("valtakirjapohja #1");
        template.setDescription("test template for valtakirja processing");
        return template;
    }

    /**
     * @param authorizationTemplateId
     * @param endDate
     * @param senderUid
     * @param receiverUid
     * @return
     */
    @Override
    public Long createAuthorization(long authorizationTemplateId,
            XMLGregorianCalendar endDate, String senderUid, String receiverUid) {
        // TODO Auto-generated method stub
        logger.info("createAuthorization: " + authorizationTemplateId + ", " + endDate + ", " + senderUid + ", " + receiverUid);
        return 123L;
    }

    /**
     * @param authorizationId
     * @param replierUid
     * @param comment
     */
    @Override
    public void approveAuthorization(long authorizationId, String replierUid, String comment) {
        // TODO Auto-generated method stub
        logger.info("approveAuthorization: " + authorizationId + ", " + replierUid + ", " + comment);

    }

    /**
     * @param authorizationId
     * @param replierUid
     * @param comment
     */
    @Override
    public void declineAuthorization(long authorizationId, String replierUid,
            String comment) {
        // TODO Auto-generated method stub
        logger.info("declineAuthorization: " + authorizationId + ", " + replierUid + ", " + comment);

    }

    /**
     * @param authorizationId
     * @param senderUid
     * @param endDate
     * @param comment
     */
    @Override
    public void updateAuthorization(long authorizationId, String senderUid, XMLGregorianCalendar endDate, String comment) {
        // TODO Auto-generated method stub
        logger.info("updateAuthorization: " + authorizationId + ", " + senderUid + ", " + endDate + ", " + comment);

    }

    /**
     * @param authorizationId
     * @param senderUid
     * @param comment
     */
    @Override
    public void revokeAuthorization(long authorizationId, String senderUid,
            String comment) {
        // TODO Auto-generated method stub
        logger.info("revokeAuthorization: " + authorizationId + ", " + senderUid + ", " + comment);

    }

    /**
     * @param authorizationId
     * @param userUid
     * @return
     */
    @Override
    public AuthorizationDetailTO getAuthorization(long authorizationId,
            String userUid) {
        // TODO Auto-generated method stub
        logger.info("getAuthorization: " + authorizationId + ", " + userUid);
        if (authorizationId != 123L) {
            throw new IllegalArgumentException("Authorization with ID " + authorizationId + " is not found.");
        }
        final AuthorizationDetailTO authorization = new AuthorizationDetailTO();
        authorization.setAuthorizationId(authorizationId);
        authorization.setCreateDate(CalendarUtil.getXmlDate(new Date()));
        authorization.setReceiverUid("Kirsi Kuntalainen");
        authorization.setSenderUid("Kalle Kuntalainen");
        authorization.setTemplate(createTestTemplate());
        final XMLGregorianCalendar validTill = CalendarUtil.getXmlDate(new Date());
        validTill.setMonth(validTill.getMonth() + 1);
        authorization.setValidTill(validTill);
        return authorization;
    }

}
