package org.openapplicant.user.service.impl.facade;

import java.util.List;

import org.openapplicant.common.api.EntityNotFoundException;
import org.openapplicant.common.api.FindOptions;
import org.openapplicant.common.api.InvalidEntityException;
import org.openapplicant.common.api.PaginatedList;
import org.openapplicant.user.service.api.IUserService;
import org.openapplicant.user.service.api.UserType;
import org.openapplicant.user.service.impl.service.UserService;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class LocalUserServiceFacade implements IUserService {
	
	private UserService userService;
	
	public void setUserService(UserService value) {
		userService = value;
	}

	@Override
	public List<String> addAuthoritiesToUser(String userId,
			List<String> authorities) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public UserType authenticate(String username, String password) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void changePasswordForUser(String userId, String password)
			throws EntityNotFoundException {
		// TODO Auto-generated method stub
	}

	@Override
	public String createUser(UserType user) throws InvalidEntityException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void deleteUser(String userId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	@Transactional(readOnly=true)
	public PaginatedList<UserType> findAllUsers(FindOptions options) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public UserType findUser(String userId) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	@Transactional(readOnly=true)
	public boolean isUsernameUnique(String username) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<String> removeAuthoritiesFromUser(String userId,
			List<String> authorities) throws EntityNotFoundException {
		// TODO Auto-generated method stub
		return null;
	}
}
