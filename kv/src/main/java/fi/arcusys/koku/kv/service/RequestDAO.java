package fi.arcusys.koku.kv.service;

import java.util.List;

import fi.arcusys.koku.kv.service.datamodel.Request;
import fi.arcusys.koku.kv.service.datamodel.User;

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
	List<Request> getRequestsByUser(User user, int startNum, int maxNum);

}
