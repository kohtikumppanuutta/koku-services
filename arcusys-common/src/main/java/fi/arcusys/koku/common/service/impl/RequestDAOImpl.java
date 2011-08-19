package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.RequestDAO;
import fi.arcusys.koku.common.service.datamodel.Request;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@Stateless
public class RequestDAOImpl extends AbstractEntityDAOImpl<Request> implements RequestDAO {
	public RequestDAOImpl() {
		super(Request.class);
	}
	
	/**
	 * @param entityId
	 * @return
	 */
	@Override
	public Request getById(final Long requestId) {
//		final List<Request> requests = super.getListByIds(Collections.singletonList(requestId));
//		if (requests == null || requests.isEmpty()) {
//			return null;
//		}
//		return requests.get(0);
		return super.getById(requestId);
	}
	
	/**
	 * @return
	 */
	@Override
	protected String getListByIdsQueryName() {
		return Request.GET_REQUESTS_BY_IDS;
	}

	/**
	 * @param userId
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	@Override
	public List<Request> getRequestsByUser(User user, int startNum, int maxNum) {
		return super.getResultList(Request.GET_REQUESTS_BY_USER_UID, Collections.<String, Object>singletonMap("user", user), startNum, maxNum);
	}
}
