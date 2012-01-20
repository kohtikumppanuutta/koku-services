package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.Authorization;
import fi.arcusys.koku.common.service.datamodel.User;
import fi.arcusys.koku.common.service.dto.AuthorizationDTOCriteria;

/**
 * DAO interface for CRUD operations with 'Authorization' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 12, 2011
 */
public interface AuthorizationDAO extends AbstractEntityDAO<Authorization> {

    /**
     * @param orCreateUser
     * @param startNum
     * @param i
     * @return
     */
    List<Authorization> getReceivedAuthorizations(User user, int startNum, int maxResults);

    /**
     * @param orCreateUser
     * @param startNum
     * @param i
     * @return
     */
    List<Authorization> getSentAuthorizations(User user, int startNum, int maxResults);

    /**
     * @param userUid
     * @return
     */
    Long getTotalSentAuthorizations(final User user);

    Long getTotalReceivedAuthorizations(final User user);

    /**
     * @param query
     * @return
     */
    List<Authorization> getAuthorizations(AuthorizationDTOCriteria criteria, int startNum, int maxResults);

    /**
     * @param authorizationDTOCriteria
     * @return
     */
    Long getTotalAuthorizations(final AuthorizationDTOCriteria authorizationDTOCriteria);
}
