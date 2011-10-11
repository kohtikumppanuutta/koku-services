package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.RequestDAO;
import fi.arcusys.koku.common.service.datamodel.Request;
import fi.arcusys.koku.common.service.datamodel.RequestTemplate;
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

    /**
     * @param template
     * @return
     */
    @Override
    public Long getTotalByTemplate(RequestTemplate template) {
        return getSingleResult("countRequestsByTemplate", Collections.singletonMap("template", template));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalRequestsByUser(User user) {
        return getSingleResult("countRequestsByUserUid", Collections.singletonMap("user", user));
    }

    /**
     * @param userByUid
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<Request> getOldRequestsByUser(User user, int startNum, int maxResults) {
        return super.getResultList("findOldRequestsByUserUid", Collections.<String, Object>singletonMap("user", user), startNum, maxResults);
    }

    /**
     * @param userByUid
     * @return
     */
    @Override
    public Long getTotalOldRequestsByUser(User user) {
        return getSingleResult("countOldRequestsByUserUid", Collections.singletonMap("user", user));
    }
}
