package com.example.persistence;

import com.example.domain.User;

import org.hibernate.Session;
import org.springframework.orm.hibernate3.HibernateCallback;
import org.springframework.stereotype.Repository;

/**
 * A sample dao.
 */
@Repository
public class UserDao extends AbstractDao<User,Long> {
	
	public UserDao() {
		super(User.class);
	}
	
	public User findByUsername(final String username) {
		return (User) getCurrentSession()
						.createQuery("from " + User.class.getName() + " where username=:username")
						.setParameter("username", username)
						.uniqueResult();
	}
	
}