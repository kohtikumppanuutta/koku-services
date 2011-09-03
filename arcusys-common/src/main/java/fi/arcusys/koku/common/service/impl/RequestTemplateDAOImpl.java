package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.List;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.RequestTemplateDAO;
import fi.arcusys.koku.common.service.datamodel.RequestTemplate;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
@Stateless
public class RequestTemplateDAOImpl extends AbstractEntityDAOImpl<RequestTemplate> implements RequestTemplateDAO {

    public RequestTemplateDAOImpl() {
        super(RequestTemplate.class);
    }

    /**
     * @param searchString
     * @param limit
     * @return
     */
    @Override
    public List<RequestTemplate> searchTemplates(String searchString, int limit) {
        return super.getResultList("findRequestTemplatesByPrefix", Collections.<String, Object>singletonMap("prefix", getPrefixLike(searchString)), FIRST_RESULT_NUMBER, limit);
    }
}
