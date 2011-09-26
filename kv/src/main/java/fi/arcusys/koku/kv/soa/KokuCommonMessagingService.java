package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.jws.WebParam;
import javax.jws.WebService;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 19, 2011
 */
@WebService(targetNamespace = "http://services.koku.fi/entity/kv/v1")
public interface KokuCommonMessagingService {
    void deliverMessage(
            @WebParam(name = "fromUser") final String fromUser, 
            @WebParam(name = "toUser") final List<String> toUsers, 
            @WebParam(name = "subject") final String subject, 
            @WebParam(name = "content") final String content);
}
