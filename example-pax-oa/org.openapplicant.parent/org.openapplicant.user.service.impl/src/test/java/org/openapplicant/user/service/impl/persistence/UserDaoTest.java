package org.openapplicant.user.service.impl.persistence;

import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.openapplicant.user.service.impl.domain.User;
import org.openapplicant.user.service.impl.domain.UserBuilder;
import org.openapplicant.user.service.impl.test.AbstractTransactionalContextBaseTest;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends AbstractTransactionalContextBaseTest {
	
	@Autowired
	private UserDao userDao;
	
	@Test
	public void save_should_cascade_authorities() {
		User user = new UserBuilder().build();
		user.addAuthority("ROLE_USER");
		userDao.saveFlushEvict(user);
		User found = userDao.find(user.getId());
		assertTrue(found.getAuthoritiesUnmodifiable().contains("ROLE_USER"));
	}

}
