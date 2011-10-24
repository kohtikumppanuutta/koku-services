package fi.arcusys.koku.kv.soa;

import java.util.List;

import javax.ejb.EJB;
import javax.ejb.Stateless;
import javax.interceptor.Interceptors;
import javax.jws.WebService;

import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.dto.Criteria;
import fi.arcusys.koku.common.service.dto.MessageQuery;
import fi.arcusys.koku.kv.service.MessageServiceFacade;
import fi.arcusys.koku.kv.service.dto.MessageTO;


/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 9, 2011
 */
@Stateless
@WebService(serviceName = "KokuMessageService", portName = "KokuMessageServicePort", 
		endpointInterface = "fi.arcusys.koku.kv.soa.KokuMessageService",
		targetNamespace = "http://soa.kv.koku.arcusys.fi/")
@Interceptors(KokuMessageInterceptor.class)
public class KokuMessageServiceImpl implements KokuMessageService {

	@EJB
	private MessageServiceFacade kvFacade;

	/**
	 * @param messageIds
	 */
	@Override
	public void archiveMessages(List<Long> messageIds) {
		kvFacade.archiveMessages(messageIds);
	}

	/**
	 * @param messageIds
	 */
	@Override
	public void deleteMessages(List<Long> messageIds) {
		kvFacade.deleteMessages(messageIds);
	}

	/**
	 * @param messageId
	 * @return
	 */
	@Override
	public MessageTO getMessageById(final long messageId) {
		return kvFacade.getMessageById(messageId);
	}

	/**
	 * @param user
	 * @param folderType
	 * @param subQuery
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<MessageSummary> getMessagesOld(final String user, FolderType folderType, String subQuery, int startNum, int maxNum) {
		return kvFacade.getMessages(user, folderType, new MessageQuery(startNum, maxNum));
	}
	
	@Override
	public List<MessageSummary> getMessages(final String user, final FolderType folderType, final MessageQuery query) {
		return kvFacade.getMessages(user, folderType, query);
	}

//	@Override
//	public List<MessageSummary> getMessagesByQueryParams(
//			final String user, final FolderType folderType, 
//			final String keywords, 
//			final List<MessageQuery.Fields> fields, 
//			final MessageQuery.Fields orderByField,
//			final OrderBy.Type orderByType,
//			final int startNum, 
//			final int maxNum) {
//		final MessageQuery query = new MessageQuery(startNum, maxNum);
//		if (keywords != null && !keywords.trim().isEmpty()) {
//			final Criteria criteria = new Criteria();
//			criteria.setKeywords(new HashSet<String>(Arrays.asList(keywords.split(" "))));
//			criteria.setFields(new HashSet<MessageQuery.Fields>(fields));
//			query.setCriteria(criteria);
//		}
//
//		if (orderByType != null && orderByField != null) {
//			query.setOrderBy(Collections.singletonList(new OrderBy()))
//		}
//		
//		return kvFacade.getMessages(user, folderType, query);
//	}

	/**
	 * @param user
	 * @param messageType
	 * @param subQuery
	 * @return
	 */
	@Override
	public int getTotalMessagesOld(String user, FolderType folderType, String subQuery) {
		return getTotalMessages(user, folderType, null);
	}

	@Override
	public int getTotalMessages(String user, FolderType folderType, Criteria criteria) {
		return kvFacade.getTotalMessagesCount(user, folderType, criteria);
	}

	/**
	 * @param user
	 * @param messageType
	 * @return
	 */
	@Override
	public int getUnreadMessages(String user, FolderType messageType) {
		return kvFacade.getUnreadMessagesCount(user, messageType);
	}

	/**
	 * @param messageIds
	 * @param messageStatus
	 */
	@Override
	public void setMessagesStatus(final List<Long> messageIds, final MessageStatus messageStatus) {
		kvFacade.setMessageStatus(messageIds, messageStatus);
	}
}
