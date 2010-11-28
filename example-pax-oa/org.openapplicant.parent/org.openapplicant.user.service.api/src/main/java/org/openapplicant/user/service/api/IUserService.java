package org.openapplicant.user.service.api;

import java.util.List;

import org.openapplicant.common.api.EntityNotFoundException;
import org.openapplicant.common.api.FindOptions;
import org.openapplicant.common.api.InvalidEntityException;
import org.openapplicant.common.api.PaginatedList;

public interface IUserService {

	String createUser(UserType user) throws InvalidEntityException;
	
	PaginatedList<UserType> findAllUsers(FindOptions options);
	
	UserType findUser(String userId) throws EntityNotFoundException;
	
	UserType authenticate(String username, String password);
	
	void changePasswordForUser(String userId, String password) throws EntityNotFoundException;
	
	List<String> addAuthoritiesToUser(String userId, List<String> authorities) throws EntityNotFoundException;
	
	List<String> removeAuthoritiesFromUser(String userId, List<String> authorities) throws EntityNotFoundException;
	
	void deleteUser(String userId);
	
	boolean isUsernameUnique(String username);
}
