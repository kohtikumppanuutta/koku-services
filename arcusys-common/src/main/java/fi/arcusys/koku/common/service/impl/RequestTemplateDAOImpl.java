package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.ejb.Stateless;

import fi.arcusys.koku.common.service.RequestTemplateDAO;
import fi.arcusys.koku.common.service.datamodel.InformationRequest;
import fi.arcusys.koku.common.service.datamodel.InformationRequestCategory;
import fi.arcusys.koku.common.service.datamodel.Question;
import fi.arcusys.koku.common.service.datamodel.RequestTemplate;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi) Sep 2, 2011
 */
@Stateless
public class RequestTemplateDAOImpl extends
        AbstractEntityDAOImpl<RequestTemplate> implements RequestTemplateDAO {

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
        return super.getResultList("findRequestTemplatesByPrefix", Collections
                .<String, Object> singletonMap("prefix",
                        getPrefixLike(searchString)), FIRST_RESULT_NUMBER,
                limit);
    }

    /**
     * @param subject
     * @return
     */
    @Override
    public List<RequestTemplate> searchBySubject(String subject) {
        return super.getResultList("findRequestTemplatesByPrefix",
                Collections.<String, Object> singletonMap("prefix", subject),
                FIRST_RESULT_NUMBER, MAX_RESULTS_COUNT);
    }

    /**
     * @param entity
     * @return
     */
    @Override
    public RequestTemplate update(RequestTemplate entity) {
        if (entity.getId() == null) {
            return super.update(entity);
        }

        final RequestTemplate existingEntity = getById(entity.getId());
        final Set<Question> forDelete = new HashSet<Question>(existingEntity.getQuestions());
        // still valid categories
        for (final Question childEntity : entity.getQuestions()) {
            if (childEntity.getId() != null) {
                forDelete.remove(childEntity);
            }
        }
        
        for (final Question childEntity : forDelete) {
            em.remove(childEntity);
        }
        return super.update(entity);
    }
}
