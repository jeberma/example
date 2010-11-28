package org.openapplicant.user.service.impl.domain;

import java.util.UUID;

public class UserBuilder {
	
	public User build() {
		User result = new User();
		result.setEmail(UUID.randomUUID().toString() + "@gmail.com");
		result.setUsername(UUID.randomUUID().toString());
		return result;
	}

}
