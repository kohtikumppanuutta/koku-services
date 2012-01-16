package fi.arcusys.koku.common.service.impl;

import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityManager;
import javax.persistence.NonUniqueResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

import fi.arcusys.koku.common.service.AbstractEntityDAO;
import fi.arcusys.koku.common.service.datamodel.AbstractEntity;
import fi.arcusys.koku.common.service.datamodel.RequestTemplate;

/**
 * @author Dmitry Kudinov (dmitry.kudinov@arcusys.fi)
 * May 18, 2011
 */
public abstract class AbstractEntityDAOImpl<T extends AbstractEntity> implements AbstractEntityDAO<T> {
    public static final String IDS_PARAMETER_NAME = "ids";

	protected EntityManager em;

	private Class<T> clazz;
	
	/**
	 * 
	 */
	protected AbstractEntityDAOImpl(final Class<T> clazz) {
		this.clazz = clazz;
	}

	@PersistenceContext
	public void setEntityManager(final EntityManager em) {
		this.em = em;
	}
	
	/**
	 * @param entity
	 * @return
	 */
	public T create(final T entity) {
		em.persist(entity);
		return entity;
	}

	protected T getSingleResultOrNull(final String queryName, final Map<String, ?> params) {
		final Query query = em.createNamedQuery(queryName);
		for (final Map.Entry<String, ?> entry : params.entrySet()) {
			query.setParameter(entry.getKey(), entry.getValue());
		}
		final List<T> result = (List<T>)query.getResultList();
		if (result.size() == 0) {
			return null;
		} else if (result.size() == 1) {
			return result.get(0);
		} else {
			throw new NonUniqueResultException("Multiple results found. Query: " + queryName + ", params: " + params);
		}
	}

	/**
	 * @param entityId
	 * @return
	 */
	public T getById(final Long entityId) {
		return em.find(this.clazz, entityId);
	}
	
	/**
	 * @param ids
	 * @return
	 */
	@Override
	public List<T> getListByIds(final List<Long> ids) {
		if (ids == null || ids.isEmpty()) {
			return Collections.emptyList();
		}
		
		return getResultList(getListByIdsQueryName(), Collections.<String, Object>singletonMap(IDS_PARAMETER_NAME, ids));
	}

	protected String getListByIdsQueryName() {
		throw new UnsupportedOperationException("This method should be overrided in subclass.");
	}

	/**
	 * @param entity
	 */
	public void delete(final T entity) {
		em.remove(em.merge(entity));
	}
	
	@Override
	public int deleteAll(List<Long> messageRefs) {
		final Query query = em.createNamedQuery(getDeleteByIdsQueryName());
		query.setParameter(IDS_PARAMETER_NAME, messageRefs);
		return query.executeUpdate();
	}
	
	protected String getDeleteByIdsQueryName() {
		throw new UnsupportedOperationException("This method should be overrided in subclass.");
	}

	public T update(final T entity) {
		return em.merge(entity);
	}

	protected <E> List<E> getResultList(final String queryName, final Map<String, ? extends Object> params) {
		return getResultList(queryName, params, FIRST_RESULT_NUMBER, MAX_RESULTS_COUNT);
	}

	protected <E> List<E> getResultList(final String queryName, final Map<String, ?> params, final int firstResult, final int maxResults) {
		final Query query = em.createNamedQuery(queryName);
		for (final Map.Entry<String, ?> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		query.setFirstResult(firstResult - 1);
		query.setMaxResults(maxResults);
		return (List<E>)query.getResultList();
	}

	protected <E> List<E> executeQuery(final String queryString, final Map<String, ?> params, final int firstResult, final int maxResults) {
		final Query query = em.createQuery(queryString);
		for (final Map.Entry<String, ?> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		query.setFirstResult(firstResult - 1);
		query.setMaxResults(maxResults);
		return (List<E>)query.getResultList();
	}

	protected <E> E executeQueryWithSingleResult(final String queryString, final Map<String, ?> params) {
		final Query query = em.createQuery(queryString);
		for (final Map.Entry<String, ?> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		return (E)query.getSingleResult();
	}

	protected <E> E getSingleResult(final String queryName, final Map<String, ?> params) {
		final Query query = em.createNamedQuery(queryName);
		for (final Map.Entry<String, ?> param : params.entrySet()) {
			query.setParameter(param.getKey(), param.getValue());
		}
		return (E)query.getSingleResult();
	}
	
    protected String getPrefixLike(String searchString) {
        if (searchString == null) {
            return "%";
        } else {
            return searchString + "%";
        }
    }

    protected String getPrefixAndSuffixLike(final String searchString) {
        if (searchString == null) {
            return "%";
        } else {
            return "%" + searchString + "%";
        }
    }

    /**
     * @param string
     * @param singletonMap
     */
    protected int executeBulkOperation(String string, Map<String, ?> params) {
        final Query query = em.createNamedQuery(string);
        for (final Map.Entry<String, ?> param : params.entrySet()) {
            query.setParameter(param.getKey(), param.getValue());
        }
        return query.executeUpdate();
    }

}