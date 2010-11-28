package org.openapplicant.user.service.impl.service;

import org.openapplicant.user.service.impl.persistence.UserDao;
import org.springframework.stereotype.Service;

@Service
public class UserService {
	
	private UserDao userDao;
	
	public void setUserDao(UserDao value) {
		userDao = value;
	}

}
