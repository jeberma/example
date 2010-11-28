package org.openapplicant.common.persistence.hbm;

import java.io.Serializable;
import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.UnresolvableObjectException;
import org.openapplicant.common.api.FindOptions;
import org.openapplicant.common.api.PaginatedList;

public class HibernateGenericDao<T, ID extends Serializable> {
	
	private Class<T> clazz;
	
	private SessionFactory sessionFactory;
	
	public HibernateGenericDao(Class<T> clazz) {
		this.clazz = clazz;
	}
	
	public void setSessionFactory(SessionFactory value) {
		sessionFactory = value;
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
	public PaginatedList<T> findAll(FindOptions findOptions) {
		List<T> records = getSession()
							.createQuery("from " + clazz.getName())
							.setMaxResults(findOptions.getPerPage())
							.setFirstResult((findOptions.getPage() -1) * findOptions.getPerPage())
							.list();
		 
		long count = count();
		PaginatedList<T> result = new PaginatedList<T>();
		result.setList(records);
		result.setCurrentPage(findOptions.getPage());
		result.setTotalEntries(count);
		result.setTotalPages(count / findOptions.getPerPage());
		return result;
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
	
	protected Class<T> getEntityClass() {
		return clazz;
	}
	
	protected Session getSession() {
		return sessionFactory.getCurrentSession();
	}

}
