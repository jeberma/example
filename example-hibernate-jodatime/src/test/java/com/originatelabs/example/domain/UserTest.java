package com.originatelabs.example.domain;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.joda.time.DateTime;
import org.junit.Ignore;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.AbstractJUnit4SpringContextTests;

@ContextConfiguration(locations="/spring.xml")
public class UserTest extends AbstractJUnit4SpringContextTests {
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Test
	public void save() {
		Session session = sessionFactory.openSession();
		Transaction tx = session.beginTransaction();
		
		User user = new User();
		session.save(user);
		session.flush();
		session.evict(user);
		
		user = (User)session.get(User.class, user.getId());
		
		System.out.println(user.getTs());
		System.out.println(user.getTs2());
		System.out.println(user.getCal());
		
		tx.commit();
		session.close();
	}
	
	@Ignore
	@Test
	public void foo() {
		DateTime dt = new DateTime("1951-12-13T21:39:45.618-08:00");
		System.out.println(dt.toDate());
		System.out.println(dt.toDate().getTime());
	}

}
