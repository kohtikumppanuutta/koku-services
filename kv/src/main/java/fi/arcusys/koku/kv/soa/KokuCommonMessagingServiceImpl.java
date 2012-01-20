package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.kv.service.MessageServiceFacade;

/**
 * Implementation for general notification service, used by external components.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 19, 2011
 */
@Stateless
@WebService(serviceName = "KokuCommonMessagingService", portName = "KokuCommonMessagingServicePort", 
        endpointInterface = "fi.arcusys.koku.kv.soa.KokuCommonMessagingService",
        targetNamespace = "http://services.koku.fi/entity/kv/v1")
public class KokuCommonMessagingServiceImpl implements KokuCommonMessagingService {

    @EJB
    private MessageServiceFacade serviceFacade;
    
    /**
     * @param fromUser
     * @param toUsers
     * @param subject
     * @param content
     */
    @Override
    public void deliverMessage(String fromUser, List<String> toUsers, String subject, String content) {
        serviceFacade.deliverMessage(fromUser, toUsers, subject, content);
    }

}
