package fi.arcusys.koku.common.service.impl;

import java.util.Calendar;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.CalendarUtil;
import fi.arcusys.koku.common.service.RequestDAO;
import fi.arcusys.koku.common.service.datamodel.Request;
import fi.arcusys.koku.common.service.datamodel.RequestTemplate;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * DAO implementation for CRUD operations with 'Request' Entity
 * 
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
     * @param template
     * @return
     */
    @Override
    public Long getTotalByTemplate(RequestTemplate template) {
        return getSingleResult("countRequestsByTemplate", Collections.singletonMap("template", template));
    }

    /**
     * @param userId
     * @param startNum
     * @param maxNum
     * @return
     */
    @Override
    public List<Request> getRequestsByUserAndRoles(User user, List<String> userRoles, int startNum, int maxNum) {
        if (userRoles != null && !userRoles.isEmpty()) {
            return super.getResultList("findRequestsByUserUidOrRoles", getUserAndRolesParams(user, userRoles), startNum, maxNum);
        } else {
            return super.getResultList("findRequestsByUserUid", Collections.singletonMap("user", user), startNum, maxNum);
        }
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalRequestsByUserAndRoles(User user, List<String> userRoles) {
        if (userRoles != null && !userRoles.isEmpty()) {
            return getSingleResult("countRequestsByUserUidOrRoles", getUserAndRolesParams(user, userRoles));
        } else {
            return getSingleResult("countRequestsByUserUid", Collections.singletonMap("user", user));
        }
    }

    /**
     * @param userByUid
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<Request> getOldRequestsByUserAndRoles(User user, List<String> userRoles, int startNum, int maxResults) {
        if (userRoles != null && !userRoles.isEmpty()) {
            return super.getResultList("findOldRequestsByUserUidOrRoles", getUserAndRolesParams(user, userRoles), startNum, maxResults);
        } else {
            return super.getResultList("findOldRequestsByUserUid", Collections.singletonMap("user", user), startNum, maxResults);
        }
    }

    /**
     * @param userByUid
     * @return
     */
    @Override
    public Long getTotalOldRequestsByUserAndRoles(User user, List<String> userRoles) {
        if (userRoles != null && !userRoles.isEmpty()) {
            return getSingleResult("countOldRequestsByUserUidOrRoles", getUserAndRolesParams(user, userRoles));
        } else {
            return getSingleResult("countOldRequestsByUserUid", Collections.singletonMap("user", user));
        }
    }

    private Map<String, Object> getUserAndRolesParams(User user, List<String> userRoles) {
        final Map<String, Object> params = new HashMap<String, Object>();
        params.put("user", user);
        params.put("userRoles", userRoles);
        return params;
    }

    /**
     * @param time
     * @return
     */
    @Override
    public List<Request> getOpenRequestsByNotifyDate(Date time) {
        final Map<String, Object> params = new HashMap<String, Object>();
        final Calendar calendar = CalendarUtil.getXmlDate(time).toGregorianCalendar();
        params.put("notifyDateFrom", calendar.getTime());
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + 1);
        calendar.set(Calendar.SECOND, calendar.get(Calendar.SECOND) - 1);
        params.put("notifyDateTo", calendar.getTime());
        return getResultList("findOpenRequestsByNotificationDate", params);
    }
}
