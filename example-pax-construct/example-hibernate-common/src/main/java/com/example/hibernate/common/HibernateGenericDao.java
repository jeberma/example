package com.example.hibernate.common;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.UnresolvableObjectException;

public abstract class HibernateGenericDao<T, ID extends Serializable> {
	
	private Class<T> clazz;
	
	private SessionFactory sessionFactory;
	
	public void setSessionFactory(SessionFactory value) {
		sessionFactory = value;
	}
	
	public Session getSession() {
		return sessionFactory.getCurrentSession();
	}
	
	public HibernateGenericDao(Class<T> clazz) {
		if(clazz == null) {
			throw new IllegalArgumentException("class cannot be null");
		}
		this.clazz = clazz;
	}
	
	public void delete(ID id) {
		T entity = find(id);
		if(entity != null) {
			getSession().delete(entity);
		}
	}
	
	public void deleteAll() {
		getSession().createQuery("delete from " + clazz.getName()).executeUpdate();
	}
	
	public void evict(T entity) {
		getSession().evict(entity);
	}
	
	public long count() {
		return (Long)getSession()
			.createQuery("select count(*) from " + clazz.getName()).uniqueResult();
	}
	
	@SuppressWarnings("unchecked")
	public T find(ID id) {
		return (T) getSession().get(clazz, id);
	}
	
	public T findOrThrow(ID id) throws HibernateException {
		T result = find(id);
		if(result == null) {
			throw new UnresolvableObjectException(id, clazz.getName());
		}
		return result;
	}
	
	@SuppressWarnings("unchecked")
	public List<T> findAll() {
		return getSession().createQuery("from " + clazz.getName()).list();
	}
	
	public void flush() {
		getSession().flush();
	}
	
	public void save(T entity) {
		getSession().saveOrUpdate(entity);
	}
	
	/** mainly useful for testing */
	public void saveFlushEvict(T entity) {
		save(entity);
		flush();
		evict(entity);
	}
	
	protected Class<T> getDomainClass() {
		return clazz;
	}

}
