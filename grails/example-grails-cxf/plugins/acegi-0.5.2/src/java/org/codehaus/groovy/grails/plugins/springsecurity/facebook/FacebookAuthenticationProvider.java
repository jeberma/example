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
package org.codehaus.groovy.grails.plugins.springsecurity.facebook;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.AuthenticationServiceException;
import org.springframework.security.BadCredentialsException;
import org.springframework.security.providers.AuthenticationProvider;
import org.springframework.security.userdetails.UserDetails;
import org.springframework.security.userdetails.UserDetailsService;
import org.springframework.util.Assert;

/**
 * Finalizes the authentication process by populating the local authorities for the authenticated user.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
public class FacebookAuthenticationProvider implements AuthenticationProvider, InitializingBean {

	private UserDetailsService _userDetailsService;

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.providers.AuthenticationProvider#authenticate(
	 * 	org.springframework.security.Authentication)
	 */
	public Authentication authenticate(final Authentication authentication) throws AuthenticationException {

		if (!supports(authentication.getClass()) ||
				!(authentication instanceof FacebookAuthenticationToken)) {
			return null;
		}

		FacebookAuthenticationToken token = (FacebookAuthenticationToken)authentication;
		FacebookAuthenticationToken.Status status = token.getStatus();

		switch (status) {
			case success:
				UserDetails userDetails = _userDetailsService.loadUserByUsername(String.valueOf(token.getUserId()));
				return new FacebookAuthenticationToken(userDetails.getAuthorities(),
						token.getUserId(), token.getSessionKey());
			case failure:
				throw new BadCredentialsException("Log in failed - identity could not be verified");
			case error:
				throw new AuthenticationServiceException("Error message from server: " + token.getErrorMessage());
		}

		// unreachable
		return null;
	}

	/**
	 * Dependency injection for the user detail service.
	 * @param userDetailsService  the service
	 */
	public void setUserDetailsService(final UserDetailsService userDetailsService) {
		_userDetailsService = userDetailsService;
	}

   /**
    * {@inheritDoc}
    * @see org.springframework.security.providers.AuthenticationProvider#supports(java.lang.Class)
    */
   @SuppressWarnings("unchecked")
	public boolean supports(final Class authentication) {
       return FacebookAuthenticationToken.class.isAssignableFrom(authentication);
   }

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(_userDetailsService, "The userDetailsService must be set");
	}
}
