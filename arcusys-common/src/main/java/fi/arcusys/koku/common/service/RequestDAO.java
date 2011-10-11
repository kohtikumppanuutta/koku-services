package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.Request;
import fi.arcusys.koku.common.service.datamodel.RequestTemplate;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
public interface RequestDAO extends AbstractEntityDAO<Request> {

	/**
	 * @param userId
	 * @param startNum
	 * @param maxNum
	 * @return
	 */
	List<Request> getRequestsByUser(User user, int startNum, int maxResults);

    /**
     * @param template
     * @return
     */
    Long getTotalByTemplate(RequestTemplate template);

    /**
     * @param user
     * @return
     */
    Long getTotalRequestsByUser(User user);

    /**
     * @param userByUid
     * @param startNum
     * @param maxNum
     * @return
     */
    List<Request> getOldRequestsByUser(User userByUid, int startNum, int maxResults);

    /**
     * @param userByUid
     * @return
     */
    Long getTotalOldRequestsByUser(User userByUid);
}
