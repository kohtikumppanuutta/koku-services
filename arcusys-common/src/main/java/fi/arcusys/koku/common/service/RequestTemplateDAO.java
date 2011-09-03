package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.RequestTemplate;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Sep 2, 2011
 */
public interface RequestTemplateDAO extends AbstractEntityDAO<RequestTemplate> {

    List<RequestTemplate> searchTemplates(final String searchString, final int limit);
}
