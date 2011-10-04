package fi.arcusys.koku.kv.soa;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import fi.arcusys.koku.kv.service.MessageServiceFacade;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 15, 2011
 */
@Stateless
@WebService(serviceName = "KokuMessageProcessingService", portName = "KokuMessageProcessingServicePort", 
		endpointInterface = "fi.arcusys.koku.kv.soa.KokuMessageProcessingService",
		targetNamespace = "http://soa.kv.koku.arcusys.fi/")
@Interceptors(KokuMessageInterceptor.class)
public class KokuMessageProcessingServiceImpl implements KokuMessageProcessingService {

	@EJB
	private MessageServiceFacade kvFacade;
	
	/**
	 * @param toUserUid
	 * @param messageId
	 */
	@Override
	public void receiveMessage(final String toUserUid, final Long messageId) {
		kvFacade.receiveMessage(toUserUid, messageId);
	}

	/**
	 * @param fromUserUid
	 * @param subject
	 * @param receipients
	 * @param content
	 * @return
	 */
	@Override
	public Long sendMessage(String fromUserUid, String subject, Receipients receipients, String content) {
//		final List<String> userUids = new ArrayList<String>(receipients.size());
//		for (final Receipients rec : receipients) {
//			userUids.add(rec.getReceipient());
//		}
//		return kvFacade.sendNewMessage(fromUserUid, subject, userUids, content);
		return kvFacade.sendNewMessage(fromUserUid, subject, receipients.getReceipients(), content);
	}

    /**
     * @param fromUserUid
     * @param subject
     * @param toUserUid
     * @param content
     */
    @Override
    public Long receiveNewMessage(String fromUserUid, String subject, String toUserUid, String content) {
        return kvFacade.receiveNewMessage(fromUserUid, subject, toUserUid, content);
    }

}
