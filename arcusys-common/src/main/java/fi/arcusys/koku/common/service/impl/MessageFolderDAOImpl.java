package fi.arcusys.koku.common.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.EJB;
import javax.ejb.Stateless;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import fi.arcusys.koku.common.service.MessageFolderDAO;
import fi.arcusys.koku.common.service.MessageRefDAO;
import fi.arcusys.koku.common.service.datamodel.Folder;
import fi.arcusys.koku.common.service.datamodel.FolderType;
import fi.arcusys.koku.common.service.datamodel.Message;
import fi.arcusys.koku.common.service.datamodel.MessageRef;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.dto.Criteria;
import fi.arcusys.koku.common.service.dto.MessageQuery;
import fi.arcusys.koku.common.service.dto.OrderBy;

/**
 * DAO implementation for CRUD operations with 'Folder' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
@Stateless
public class MessageFolderDAOImpl extends AbstractEntityDAOImpl<Folder> implements MessageFolderDAO {
	private static final Logger logger = LoggerFactory.getLogger(MessageFolderDAOImpl.class);
	
	@EJB
	private MessageRefDAO messageRefDao;
	
	public MessageFolderDAOImpl() {
		super(Folder.class);
	}

	/**
	 * @param user
	 * @param folderType
	 * @return
	 */
	public Folder getFolderByUserAndType(final User user, final FolderType folderType) {
		return getSingleResultOrNull("findFolderByUserAndType", getCommonQueryParams(user, folderType));
	}

	/**
	 * @param user
	 * @param folderType
	 * @param message
	 * @return
	 */
	public MessageRef storeMessage(final User user, final FolderType folderType, final Message message) {
		Folder folder = getFolderByUserAndType(user, folderType);
		
		if (folder == null) {
			folder = createNewFolderByUserAndType(user, folderType);
		}
		
		final MessageRef msgRef = new MessageRef();
		msgRef.setFolder(folder);
		msgRef.setMessage(message);
		return messageRefDao.create(msgRef);
	}

	/**
	 * @param user
	 * @param folderType
	 * @return
	 */
	@Override
	public List<MessageRef> getMessagesByUserAndFolderType(final User user, final FolderType folderType) {
		return getMessagesByUserAndFolderType(user, folderType, null, FIRST_RESULT_NUMBER, FIRST_RESULT_NUMBER + MAX_RESULTS_COUNT - 1);
	}

	/**
	 * @param user
	 * @param folderType
	 * @return
	 */
	@Override
	public List<MessageRef> getMessagesByUserAndFolderType(final User user, final FolderType folderType, final MessageQuery query, final int startNum, final int maxNum) {
		final Map<String, Object> params = getCommonQueryParams(user, folderType);
		if (query == null || query.getCriteria() == null && query.getOrderBy() == null) {
			return getResultList("findMessagesByUserAndFolderType", params, startNum, maxNum);
		} else {
			/*
			 * SELECT mr FROM MessageRef mr 
			 * WHERE mr.folder.folderType = :folderType AND mr.folder.user = :user
			 *   AND ((mr.message.subject LIKE '%test%' AND mr.message.subject LIKE '%sending%') OR
			 *        (mr.message.content LIKE '%test%' AND mr.message.content LIKE '%sending%')
			 *       )
			 * ORDER BY 
			 * */
			final Criteria criteria = query.getCriteria();
			final List<OrderBy> orderBys = query.getOrderBy();

			final StringBuilder queryString = new StringBuilder("SELECT DISTINCT mr FROM MessageRef mr, IN(mr.message.receipients) to_ ");
			// build "where" string, put params
			final StringBuilder whereString = getWhereStringByCriteria(criteria, folderType, params);
			// build "order by" string, put params
			final StringBuilder orderByString = new StringBuilder();
			if (orderBys != null && !orderBys.isEmpty()) {
				orderByString.append(" ORDER BY ");
				for (final OrderBy orderBy : orderBys) {
					orderByString.append(getFieldNameForQuery(orderBy.getField(), folderType)).append(" ").append(orderBy.getType().name()).append(", ");
				}
				orderByString.setLength(orderByString.length() - 2);
			}
			
			queryString.append(whereString);
			queryString.append(orderByString);
			
			if (logger.isDebugEnabled()) {
				logger.debug("Execute search query: " + queryString);
			}
			
			return executeQuery(queryString.toString(), params, startNum, maxNum);
		}
	}

	private StringBuilder getWhereStringByCriteria(final Criteria criteria,
			final FolderType folderType, final Map<String, Object> params) {
		final StringBuilder whereString = new StringBuilder("WHERE mr.folder.folderType = :folderType AND mr.folder.user = :user ");
		if (criteria != null && !criteria.getFields().isEmpty() && !criteria.getKeywords().isEmpty()) {
			// escape JPQL symbols
			final List<String> keywords = new ArrayList<String>();
			for (final String keyword : criteria.getKeywords()) {
				keywords.add(keyword.trim().replaceAll("'", "''"));
			}

			whereString.append(" AND (");
			
			for (final MessageQuery.Fields field : criteria.getFields()) {
				whereString.append(" (");

				for (int i = 0; i < keywords.size(); i++) {
					whereString.append(getFieldNameForQuery(field, folderType)).append(" LIKE :").append("keyword").append(i).append(" AND ");
				}
				// remove last 'AND ' in keywords concatenation 
				whereString.setLength(whereString.length() - 4);
				
				whereString.append(") OR ");
			}
			// remove last 'OR ' in keywords concatenation 
			whereString.setLength(whereString.length() - 3);
			
			whereString.append(")");
			
			for (int i = 0; i < keywords.size(); i++) {
                params.put("keyword" + i, getPrefixAndSuffixLike(keywords.get(i)));
			}
		}
		return whereString;
	}

	private String getFieldNameForQuery(final MessageQuery.Fields field,
			final FolderType folderType) {
		final String fieldName;
		if (field == MessageQuery.Fields.Subject) {
			fieldName = "mr.message.subject";
		} else if (field == MessageQuery.Fields.Content) {
			fieldName = "mr.message.text";
		} else if (field == MessageQuery.Fields.CreatedDate) {
			fieldName = "mr.createdDate";
		} else if (field == MessageQuery.Fields.Sender) {
			fieldName = "mr.message.fromUser.employeePortalName";
		} else if (field == MessageQuery.Fields.Receiver) {
			fieldName = "to_.citizenPortalName";
		} else {
			throw new IllegalArgumentException("Unsupported field: " + field);
		}
		return fieldName;
	}

	/**
	 * @param user
	 * @param folderType
	 * @return
	 */
	public Folder createNewFolderByUserAndType(final User user, final FolderType folderType) {
		final Folder newFolder = new Folder();
		newFolder.setFolderType(folderType);
		newFolder.setUser(user);
		return create(newFolder);
	}

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	@Override
	public Long getTotalMessagesCount(final User user, final FolderType folderType) {
		return getSingleResult("getTotalMessagesCount", getCommonQueryParams(user, folderType));
	}

	@Override
	public Long getTotalMessagesCount(final User user, final FolderType folderType, final Criteria criteria) {
		final Map<String, Object> params = getCommonQueryParams(user, folderType);

		final StringBuilder queryString = new StringBuilder("SELECT COUNT(DISTINCT mr) FROM MessageRef mr, IN (mr.message.receipients) to_ ");
		queryString.append(getWhereStringByCriteria(criteria, folderType, params));
		return executeQueryWithSingleResult(queryString.toString(), params);
	}

	/**
	 * @param userId
	 * @param folderType
	 * @return
	 */
	@Override
	public Long getUnreadMessagesCount(final User user, FolderType folderType) {
		final Map<String, Object> params = getCommonQueryParams(user, folderType);
		params.put("isRead", Boolean.FALSE);
		return getSingleResult("getMessagesCountByReadStatus", params);
	}

	private Map<String, Object> getCommonQueryParams(final User user, final FolderType folderType) {
		final Map<String, Object> params = new HashMap<String,Object>();
		params.put("user", user);
		params.put("folderType", folderType);
		return params;
	}
}
