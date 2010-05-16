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
package org.codehaus.groovy.grails.plugins.springsecurity.kerberos

import org.springframework.security.Authentication
import org.springframework.security.AuthenticationException
import org.springframework.security.GrantedAuthority
import org.springframework.security.providers.jaas.JaasAuthenticationProvider
import org.springframework.security.providers.jaas.JaasAuthenticationToken

/**
 * Kerberos {@link AuthenticationProvider}.
 *
 * @author <a href='mailto:mmornati@byte-code.com'>Marco Mornati</a>
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
class GrailsKerberosAuthenticationProvider extends JaasAuthenticationProvider {

	def authenticateService
	def userDetailsService

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.providers.jaas.JaasAuthenticationProvider#authenticate(
	 * 	org.springframework.security.Authentication)
	 */
	@Override
	Authentication authenticate(Authentication auth) throws AuthenticationException {

		Authentication authToken = super.authenticate(auth)

		if (authToken instanceof JaasAuthenticationToken) {
			String username = authToken.principal
			boolean retrieveDatabaseRoles = authenticateService.securityConfig.security.kerberosRetrieveDatabaseRoles
			def dbDetails = userDetailsService.loadUserByUsername(username, retrieveDatabaseRoles)
			def authorities = mergeDatabaseRoles(dbDetails, authToken.authorities)
			dbDetails.authorities = authorities
			authToken = new JaasAuthenticationToken(
					dbDetails, authToken.credentials,
					dbDetails.authorities, authToken.loginContext);
		}

		return authToken
	}

	private GrantedAuthority[] mergeDatabaseRoles(details, GrantedAuthority[] authorities) {
		List merged = []
		if (authorities) {
			merged.addAll(authorities as List)
		}

		if (details.authorities) {
			merged.addAll(details.authorities as List)
		}

		return merged as GrantedAuthority[]
	}
}
