package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.RequestTemplate;
import fi.arcusys.koku.common.service.datamodel.User;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
public interface RequestTemplateDAO extends AbstractEntityDAO<RequestTemplate> {

    List<RequestTemplate> searchTemplates(final User user, String searchString, final int limit);

    /**
     * @param subject
     * @return
     */
    List<RequestTemplate> searchBySubject(final String subject);
}
