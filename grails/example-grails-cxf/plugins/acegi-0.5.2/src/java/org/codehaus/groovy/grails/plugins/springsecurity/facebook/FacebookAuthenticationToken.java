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

import org.springframework.security.GrantedAuthority;
import org.springframework.security.providers.AbstractAuthenticationToken;

/**
 * Authentication token with Facebook-specific extra information.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
public class FacebookAuthenticationToken extends AbstractAuthenticationToken {

	private static final long serialVersionUID = 1022970403466610153L;

	private Status _status;
	private long _userId;
	private String _sessionKey;
	private String _errorMessage;

	/**
	 * Token type.
	 */
	public static enum Status {
		/** successful authentication. */
		success,
		/** failed authentication. */
		failure,
		/** authentication error. */
		error
	}

	/**
	 * Created by the OpenIDAuthenticationProvider on successful authentication.
	 * @param authorities  roles
	 * @param userId
	 * @param sessionKey
	 */
	public FacebookAuthenticationToken(final GrantedAuthority[] authorities,
			final long userId, final String sessionKey) {
		super(authorities);
		_status = Status.success;
		_userId = userId;
		_sessionKey = sessionKey;
		setAuthenticated(true);
	}

	/**
	 * Created by {@link FacebookAuthenticationProcessingFilter} from Facebook login info,
	 * but before loading roles.
	 * @param userId  the UID
	 * @param sessionKey  the session key
	 */
	public FacebookAuthenticationToken(final long userId, final String sessionKey) {
		super(new GrantedAuthority[0]);
		_status = Status.success;
		_userId = userId;
		_sessionKey = sessionKey;
		setAuthenticated(false);
	}

	/**
	 * Create a failure token.
	 * @param status  a non-success token
	 * @param errorMessage  the error message
	 */
	public FacebookAuthenticationToken(final Status status, final String errorMessage) {
		super(new GrantedAuthority[0]);
		_status = status;
		_errorMessage = errorMessage;
		setAuthenticated(false);
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.providers.AbstractAuthenticationToken#getCredentials()
	 */
	public Object getCredentials() {
		// we don't have access to password
		return null;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.providers.AbstractAuthenticationToken#getPrincipal()
	 */
	public Object getPrincipal() {
		return _userId;
	}

	/**
	 * The Facebook UID.
	 * @return  the uid
	 */
	public long getUserId() {
		return _userId;
	}

	/**
	 * The status.
	 * @return  the status
	 */
	public Status getStatus() {
		return _status;
	}

	/**
	 * The login session key.
	 * @return  the key
	 */
	public String getSessionKey() {
		return _sessionKey;
	}

	/**
	 * Get the error message (if status is <code>error</code>).
	 * @return  the message
	 */
	public String getErrorMessage() {
		return _errorMessage;
	}
}
