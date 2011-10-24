package fi.arcusys.koku.palvelukanava.palvelut.service;

import java.util.List;

public interface Service<T>  {
	public List<T> findAll();
	public T findById(Object primaryKey);
	public T update(T entity);
	public void create(T entity);
	public void remove(T entity);
}
