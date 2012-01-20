package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.jws.WebService;

import fi.arcusys.koku.common.service.KokuSystemNotificationsService;

/**
 * Implementation of general notification service.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 20, 2011
 */
@Stateless
@WebService(serviceName = "KokuNotificationService", portName = "KokuNotificationServicePort", 
        endpointInterface = "fi.arcusys.koku.kv.soa.KokuNotificationService",
        targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public class KokuNotificationServiceImpl implements KokuNotificationService {

    @EJB
    private KokuSystemNotificationsService notificationService;

    /**
     * @param subject
     * @param receipients
     * @param content
     */
    @Override
    public void sendNotification(String subject, List<String> receipients,
            String content) {
        notificationService.sendNotification(subject, receipients, content);
    }
    
}
