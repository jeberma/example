package org.openapplicant.user.service.impl.domain;

import static org.junit.Assert.*;

import org.junit.Test;

public class UserTest {
	
	@Test
	public void changePassword_should_encrypt_a_password() {
		User user = new UserBuilder().build();
		user.changePassword("foo");
		assertFalse(user.getPassword().equals("foo"));
		assertTrue(user.passwordMatches("foo"));
	}

}
