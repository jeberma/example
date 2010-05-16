package com.example.persistence;

import static org.junit.Assert.*;

import java.util.UUID;

import javax.annotation.Resource;

import org.junit.Test;

import com.example.domain.User;
import com.example.test.TransactionalContextTestBase;

/**
 * Sample transactional context test.
 */
public class UserDaoTest extends TransactionalContextTestBase {
	
	@Resource
	private UserDao userDao;
	
	@Test
	public void save() {
		User user = new User();
		user.setUsername(UUID.randomUUID().toString());

		userDao.saveFlushEvict(user);
		
		User found = userDao.find(user.getId());
		assertEquals(user.getUsername(), found.getUsername());
	}
	
	@Test
	public void findByUsername() {
		User user = new User();
		user.setUsername(UUID.randomUUID().toString());

		userDao.saveFlushEvict(user);
		
		User found = userDao.findByUsername(user.getUsername());
		assertNotNull(found);
	}
}
