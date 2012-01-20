package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.AuthorizationTemplateDAO;
import fi.arcusys.koku.common.service.datamodel.AuthorizationTemplate;

/**
 * DAO implementation for CRUD operations with 'AuthorizationTemplate' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 12, 2011
 */
@Stateless
public class AuthorizationTemplateDAOImpl extends AbstractEntityDAOImpl<AuthorizationTemplate> implements AuthorizationTemplateDAO {

    public AuthorizationTemplateDAOImpl() {
        super(AuthorizationTemplate.class);
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<AuthorizationTemplate> searchAuthorizationTemplates(final String searchString, final int limit) {
        return super.getResultList("findAuthorizationTemplatesByPrefix", Collections.<String, Object>singletonMap("prefix", searchString + "%"), FIRST_RESULT_NUMBER, limit);
    }
}
