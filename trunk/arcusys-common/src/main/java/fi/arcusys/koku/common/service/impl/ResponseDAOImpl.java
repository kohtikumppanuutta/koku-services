package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.ResponseDAO;
import fi.arcusys.koku.common.service.datamodel.Response;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
@Stateless
public class ResponseDAOImpl extends AbstractEntityDAOImpl<Response> implements ResponseDAO {
	public ResponseDAOImpl() {
		super(Response.class);
	}

    /**
     * @param user
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<Response> getResponsesByUser(User user, int startNum, int maxResults) {
        return getResultList("findRequestResponsesByUser", Collections.singletonMap("user", user), startNum, maxResults); 
    }

    /**
     * @param userByUid
     * @param startNum
     * @param maxResults
     * @return
     */
    @Override
    public List<Response> getOldResponsesByUser(User user, int startNum, int maxResults) {
        return getResultList("findOldRequestResponsesByUser", Collections.singletonMap("user", user), startNum, maxResults); 
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalResponsesByUser(User user) {
        return getSingleResult("countRequestResponsesByUser", Collections.singletonMap("user", user));
    }

    /**
     * @param user
     * @return
     */
    @Override
    public Long getTotalOldResponsesByUser(User user) {
        return getSingleResult("countOldRequestResponsesByUser", Collections.singletonMap("user", user));
    }
}
