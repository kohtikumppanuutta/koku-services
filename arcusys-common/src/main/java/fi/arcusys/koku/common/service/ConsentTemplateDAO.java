package fi.arcusys.koku.common.service;

import java.util.List;

import fi.arcusys.koku.common.service.datamodel.ConsentTemplate;

/**
 * DAO interface for CRUD operations with 'ConsentTemplate' Entity
 * 
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * Aug 23, 2011
 */
public interface ConsentTemplateDAO extends AbstractEntityDAO<ConsentTemplate> {

    /**
     * @param searchString
     * @param limit
     * @return
     */
    List<ConsentTemplate> searchTemplates(final String searchString, final int limit);
}
