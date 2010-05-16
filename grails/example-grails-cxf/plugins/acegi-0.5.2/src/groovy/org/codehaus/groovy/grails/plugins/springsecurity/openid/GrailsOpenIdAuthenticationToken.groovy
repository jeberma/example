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
package org.codehaus.groovy.grails.plugins.springsecurity.openid

import org.springframework.security.GrantedAuthorityimport org.springframework.security.providers.openid.OpenIDAuthenticationStatusimport org.springframework.security.providers.openid.OpenIDAuthenticationToken
import org.springframework.security.userdetails.UserDetails
/**
 * Subclass that holds the user domain instance.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
class GrailsOpenIdAuthenticationToken extends OpenIDAuthenticationToken {

	private UserDetails _userDetails

 	/**
 	 * Full constructor.
 	 * @param userDetails  the details
 	 * @param status  the status
 	 * @param identityUrl  the url
 	 */
	GrailsOpenIdAuthenticationToken(
			UserDetails userDetails,
			OpenIDAuthenticationStatus status,
			String identityUrl) {
		super(userDetails.authorities, status, identityUrl)
		_userDetails = userDetails
	}

 	/**
 	 * {@inheritDoc}
 	 * @see org.springframework.security.providers.openid.OpenIDAuthenticationToken#getPrincipal()
 	 */
	@Override
	Object getPrincipal() {
		return _userDetails
	}
}
