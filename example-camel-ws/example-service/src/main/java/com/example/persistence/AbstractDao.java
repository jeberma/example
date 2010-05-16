package com.example.persistence;

import java.io.Serializable;
import java.lang.reflect.ParameterizedType;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import org.springframework.beans.factory.annotation.Required;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.DataRetrievalFailureException;
import org.springframework.stereotype.Repository;
import org.springframework.util.Assert;

@Repository
public abstract class AbstractDao<T, ID extends Serializable> {
	
	private Class<T> clazz;
	
	private SessionFactory sessionFactory;
	
	public AbstractDao(Class<T> clazz) {
		Assert.notNull(clazz);
		this.clazz = clazz;
	}
	
	@Resource
	@Required
	public void setSessionFactory(SessionFactory value) {
		sessionFactory = value;
	}
	
	protected Session getCurrentSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public void delete(ID id) {
		T entity = find(id);
		if(entity != null) {
			getCurrentSession().delete(entity);
		}
	}
	
	public void evict(T entity) {
		getCurrentSession().evict(entity);
	}
	
	@SuppressWarnings("unchecked")
	public T find(ID id) {
		return (T)getCurrentSession().get(clazz, id);
	}
	
	public T findOrThrow(ID id) throws DataAccessException {
		T result = find(id);
		if(result == null) {
			throw new DataRetrievalFailureException("Failed to find entity " + clazz.getName() + " with id= " +id);
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getCurrentSession().createQuery("from " + clazz.getName()).list();
	}
	
	public void flush() {
		getCurrentSession().flush();
	}
	
	public void save(T entity) {
		getCurrentSession().saveOrUpdate(entity);
	}

	/**
	 * Mainly useful for testing
	 */
	public void saveFlushEvict(T entity) {
		save(entity);
		flush();
		evict(entity);
	}
}
