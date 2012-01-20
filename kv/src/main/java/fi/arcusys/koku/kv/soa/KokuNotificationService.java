package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * Interface of general notification service.
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Dec 20, 2011
 */
@WebService(targetNamespace = "http://soa.kv.koku.arcusys.fi/")
public interface KokuNotificationService {
    void sendNotification(
            @WebParam(name = "subject") final String subject, 
            @WebParam(name = "recipients") final List<String> recipients, 
            @WebParam(name = "content") final String content);
}
