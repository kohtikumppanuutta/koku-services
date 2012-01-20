package fi.arcusys.koku.common.external;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.naming.InitialContext;
import javax.naming.NamingException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.datamodel.User;
import fi.koku.services.entity.customercommunication.v1.CustomerCommunicationServiceFactory;
import fi.koku.settings.KoKuPropertiesUtil;
import fi.tampere.contract.municipalityportal.ccs.CustomerCommunicationServicePortType;
import fi.tampere.schema.municipalityportal.ccs.ObjectFactory;
import fi.tampere.schema.municipalityportal.ccs.SendEmailMessageResponseType;
import fi.tampere.schema.municipalityportal.ccs.SendEmailMessageType;

/**
 * DAO implementation for sending email notifications to citizens through Gofore's provided email service.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jan 2, 2012
 */
@Stateless
public class EmailServiceDAOImpl implements EmailServiceDAO {

    private static final Logger logger = LoggerFactory.getLogger(EmailServiceDAOImpl.class);
    
    @EJB
    CustomerServiceDAO customerDao;
    
    private CustomerCommunicationServicePortType communicationService;
    private final ObjectFactory objectFactory = new ObjectFactory();
    
    private String communicationServiceUserUid;
    private String communicationServiceUserPwd;
    private String communicationServiceEndpoint = KoKuPropertiesUtil.get("customercommunication.service.endpointaddress.full.url");

    @PostConstruct
    public void init() {
        try {
            final InitialContext ctx = new InitialContext();
            communicationServiceEndpoint = (String) ctx.lookup("koku/urls/customercommunicationservice-baseurl");
            logger.debug("Overwrite communicationServiceEndpoint with " + communicationServiceEndpoint);
        } catch (NamingException e) {
            logger.error(null, e);
        }
        try {
            CustomerCommunicationServiceFactory communicationServiceFactory = new CustomerCommunicationServiceFactory(
                    communicationServiceUserUid, communicationServiceUserPwd, communicationServiceEndpoint);
            communicationService = communicationServiceFactory.getCustomerCommunicationService();
        } catch(Exception re) {
            logger.error(null, re);
        } 
    }

    /**
     * @param toUser
     * @param subject
     * @param content
     */
    @Override
    public boolean sendMessage(User toUser, String subject, String content) {
        if (toUser == null) {
            return false;
        }
        
        if (toUser.getEmployeePortalName() != null && !toUser.getEmployeePortalName().isEmpty()) {
            logger.info("Sending of the emails to employees is not implemented. Skip email sending to user " + toUser.getUid());
            return false;
        }
        
        try {
            final SendEmailMessageType createSendEmailMessageType = objectFactory.createSendEmailMessageType();
            createSendEmailMessageType.setSsn(customerDao.getSsnByKunpoName(toUser.getCitizenPortalName()));
            createSendEmailMessageType.setSubject(subject);
            createSendEmailMessageType.setContent(content);
            final SendEmailMessageResponseType sendingResult = communicationService.sendEmailMessage(createSendEmailMessageType);
            if (sendingResult == SendEmailMessageResponseType.EMAIL_SENT) {
                logger.debug("EMail delivered successully to user " + toUser.getUid());
                return true;
            } else {
                logger.info("Failed to send email to user " + toUser.getUid() + ". Return code from service: " + sendingResult);
                return false;
            }
        } catch (Exception e) {
            logger.info("Failed to send email to user " + toUser.getUid() + ": " + e.getMessage());
            return false;
        }
    }

}
