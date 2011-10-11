package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.Response;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Jun 23, 2011
 */
public interface ResponseDAO extends AbstractEntityDAO<Response>{

    /**
     * @param userByUid
     * @param startNum
     * @param i
     * @return
     */
    List<Response> getResponsesByUser(final User user, int startNum, int maxResults);

    /**
     * @param userByUid
     * @param startNum
     * @param i
     * @return
     */
    List<Response> getOldResponsesByUser(User userByUid, int startNum, int maxResults);

    /**
     * @param userByUid
     * @return
     */
    Long getTotalResponsesByUser(User user);

    /**
     * @param userByUid
     * @return
     */
    Long getTotalOldResponsesByUser(User user);

}
