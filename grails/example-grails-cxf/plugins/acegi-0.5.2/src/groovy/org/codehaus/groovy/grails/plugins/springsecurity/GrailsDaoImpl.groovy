/**
/* Copyright 2006-2009 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.codehaus.groovy.grails.plugins.springsecurity

import org.apache.log4j.Logger

import org.springframework.security.GrantedAuthority
import org.springframework.security.GrantedAuthorityImpl
import org.springframework.security.userdetails.UserDetails
import org.springframework.security.userdetails.UserDetailsService
import org.springframework.security.userdetails.UsernameNotFoundException
import org.springframework.dao.DataAccessException

/**
 * {@link UserDetailsService} with {@link GrailsDomainClass} Data Access Object.
 * @author Tsuyoshi Yamamoto
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
class GrailsDaoImpl extends GrailsWebApplicationObjectSupport implements UserDetailsService {

	private final Logger logger = Logger.getLogger(getClass())

	def authenticateService

	// dependency-injected fields
	String loginUserDomainClass
	String usernameFieldName
	String passwordFieldName
	String enabledFieldName
	String relationalAuthoritiesField
	String authorityFieldName
	String authoritiesMethodName

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.userdetails.UserDetailsService#loadUserByUsername(java.lang.String)
	 */
	UserDetails loadUserByUsername(String username) throws UsernameNotFoundException, DataAccessException {
		return loadUserByUsername(username, true)
	}

	/**
	 * Load a user by username, optionally not loading roles.
	 * @param username  the login name
	 * @param loadRoles  if <code>true</code> load roles from the database
	 * @return the user if found, otherwise throws {@link UsernameNotFoundException}
	 * @throws UsernameNotFoundException  if the user isn't found
	 * @throws DataAccessException  if there's a database problem
	 */
	UserDetails loadUserByUsername(String username, boolean loadRoles)
			throws UsernameNotFoundException, DataAccessException {

		if (authenticateService.securityConfig.security.useNtlm) {
			username = username.toLowerCase()
		}

		GrailsWebApplicationObjectSupport.SessionContainer container = setUpSession()
		try {
			def user = loadDomainUser(username, container.session)
			GrantedAuthority[] authorities = loadAuthorities(user, username, loadRoles)
			return createUserDetails(
					username, user."$passwordFieldName", user."$enabledFieldName",
					authorities, user)
		}
		finally {
			releaseSession(container)
		}
    }

	protected GrantedAuthority[] loadAuthorities(user, String username, boolean loadRoles) {
		if (!loadRoles) {
			return []
		}

		if (relationalAuthoritiesField) {
			return createRolesByRelationalAuthorities(user, username)
		}

		if (authoritiesMethodName) {
			return createRolesByAuthoritiesMethod(user, username)
		}

		logger.error("User [${username}] has no GrantedAuthority")
		throw new UsernameNotFoundException("User has no GrantedAuthority")
	}

	protected def loadDomainUser(username, session) throws UsernameNotFoundException, DataAccessException {

		List users = session.createQuery(
				"from $loginUserDomainClass where $usernameFieldName=:username")
				.setString('username', username)
				.setCacheable(true)
				.list()

		if (users.empty) {
			logger.error "User not found: $username"
			throw new UsernameNotFoundException("User not found", username)
		}

		return users[0]
    }

	/**
	 * Create the {@link UserDetails} instance. Subclasses can override to inherit core functionality
	 * but determine the concrete class without reimplementing the entire class.
	 * @param username the username
	 * @param password the password
	 * @param enabled set to <code>true</code> if the user is enabled
	 * @param authorities the authorities that should be granted to the caller
	 * @param user  the user domain instance
	 * @return  the instance
	 */
	protected UserDetails createUserDetails(
			String username, String password, boolean enabled,
			GrantedAuthority[] authorities, Object user) {
		new GrailsUserImpl(
				username, password, enabled,
				true, true, true, authorities, user)
	}

	protected GrantedAuthority[] createRolesByAuthoritiesMethod(user, String username) {
		Set authorities = user."$authoritiesMethodName"()
		assertNotEmpty authorities, username

		authorities.collect { roleName -> new GrantedAuthorityImpl(roleName) } as GrantedAuthority[]
	}

	protected GrantedAuthority[] createRolesByRelationalAuthorities(user, String username) {
		// get authorities from LoginUser [LoginUser]--M:M--[Authority]

		Set authorities = user."$relationalAuthoritiesField"
		assertNotEmpty authorities, username

		authorities.collect { item -> new GrantedAuthorityImpl(item."$authorityFieldName") } as GrantedAuthority[]
	}

	protected void assertNotEmpty(Collection authorities, String username) {
		if (authorities == null || authorities.empty) {
			logger.error("User [${username}] has no GrantedAuthority")
			throw new UsernameNotFoundException("User has no GrantedAuthority")
		}
	}

	protected Logger getLog() {
		return logger
	}
}
