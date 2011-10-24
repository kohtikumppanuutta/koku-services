package fi.arcusys.koku.palvelukanava.palvelut.service.impl;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

import fi.arcusys.koku.palvelukanava.palvelut.service.Service;

public abstract class AbstractService<T> implements Service<T>  {
	
	@PersistenceContext
	protected EntityManager entityManager;
	protected Class<T> entityClass;
	
	public AbstractService(Class<T> entityClass) {
		this(entityClass, null);
	}
	
	public AbstractService(Class<T> entityClass, EntityManager entityManager) {
		this.entityClass = entityClass;
		this.entityManager = entityManager;
	}
	
	public EntityManager getEntityManager() {
		return entityManager;
	}

	public void setEntityManager(EntityManager entityManager) {
		this.entityManager = entityManager;
	}
	
	public Class<T> getEntityClass() {
		return entityClass;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return entityManager.createQuery("select o from "+getEntityClass().getName()+" o)").getResultList();
	}

	public T findById(Object primaryKey) {
		return entityManager.find(entityClass, primaryKey);
	}

	public T update(T entity) {
		return entityManager.merge(entity);
	}
	
	public void create(Object entity) {
		entityManager.persist(entity);
	}

	public void remove(Object entity) {
		entityManager.remove(entity);
	}

}
