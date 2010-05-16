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

import org.springframework.security.GrantedAuthority
import org.springframework.security.GrantedAuthorityImpl
import org.springframework.security.context.SecurityContextHolder as SCH
import org.springframework.util.StringUtils as STU

import grails.util.GrailsUtil

/**
 * Helper methods.
 * @author Tsuyoshi Yamamoto
 */
class AuthorizeTools {

	/**
	 * Extract the role names from authorities.
	 * @param  authorities  the authorities
	 * @return  the names
	 */
	static Set<String> authoritiesToRoles(authorities) {
		def roles = [] as Set
		authorities.each { authority ->
			if (null == authority.authority) {
				throw new IllegalArgumentException(
						"Cannot process GrantedAuthority objects which return null from getAuthority() - attempting to process "
						+ authority)
			}
			roles.add(authority.authority)
		}

		return roles
	}

	/**
	 * Get the current user's authorities.
	 * @return  a list of authorities (empty if not authenticated).
	 */
	static List getPrincipalAuthorities() {
		def currentUser = SCH.context.authentication
		if (null == currentUser) {
			return Collections.emptyList()
		}

		if (!currentUser.authorities) {
			return Collections.emptyList()
		}

		return Arrays.asList(currentUser.authorities)
	}

	/**
	 * Split the role names and create {@link GrantedAuthority}s for each.
	 * @param authorizationsString  comma-delimited role names
	 * @return authorities (possibly empty)
	 */
	static Set<GrantedAuthority> parseAuthoritiesString(String authorizationsString) {
		def requiredAuthorities = [] as Set
		STU.commaDelimitedListToStringArray(authorizationsString).each { auth ->
			requiredAuthorities << new GrantedAuthorityImpl(auth)
		}

		return requiredAuthorities
	}

	static Set<String> retainAll(granted, required) {
		def grantedRoles = authoritiesToRoles(granted)
		def requiredRoles = authoritiesToRoles(required)
		grantedRoles.retainAll(requiredRoles)

		return rolesToAuthorities(grantedRoles, granted)
	}

	static Set<String> rolesToAuthorities(grantedRoles, granted) {
		def target = new HashSet()
		grantedRoles.each { role ->
			def auth = granted.find { authority -> authority.authority == role }
			if (auth != null) {
				target.add(auth.authority)
			}
		}

		return target
	}

	static boolean ifAllGranted(role) {
		def granted = getPrincipalAuthorities()
		return granted.containsAll(parseAuthoritiesString(role))
	}

	static boolean ifNotGranted(role) {
		def granted = getPrincipalAuthorities()
		Set grantedCopy = retainAll(granted, parseAuthoritiesString(role))
		return grantedCopy.empty
	}

	static boolean ifAnyGranted(role) {
		def granted = getPrincipalAuthorities()
		Set grantedCopy = retainAll(granted, parseAuthoritiesString(role))
		return !grantedCopy.empty
	}

	static ConfigObject getSecurityConfig() {

		GroovyClassLoader classLoader = new GroovyClassLoader(AuthorizeTools.getClassLoader())

		def slurper = new ConfigSlurper(GrailsUtil.environment)
		ConfigObject userConfig
		try {
			userConfig = slurper.parse(classLoader.loadClass('SecurityConfig'))
		}
		catch (e) {
			// ignored, use defaults
		}

		ConfigObject config
		ConfigObject defaultConfig = slurper.parse(classLoader.loadClass('DefaultSecurityConfig'))
		if (userConfig) {
			config = defaultConfig.merge(userConfig)
		}
		else {
			config = defaultConfig
		}

		return config
	}
}
