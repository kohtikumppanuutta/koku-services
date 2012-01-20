package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.AuthorizationTemplate;

/**
 * DAO interface for CRUD operations with 'AuthorizationTemplate' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 12, 2011
 */
public interface AuthorizationTemplateDAO extends AbstractEntityDAO<AuthorizationTemplate> {

    /**
     * @param searchString
     * @param limit
     * @return
     */
    List<AuthorizationTemplate> searchAuthorizationTemplates(final String searchString, final int limit);
}
