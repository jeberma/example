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

import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.security.Authentication
import org.springframework.security.AuthenticationException
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter

/**
 * Extends the default {@link AuthenticationProcessingFilter} to override the <code>sendRedirect()</code>
 * logic and always send absolute redirects.
 *
 * @author Tsuyoshi Yamamoto
 */
class GrailsAuthenticationProcessingFilter extends AuthenticationProcessingFilter {

	/**
	 * Dependency injection for the authentication service.
	 */
	def authenticateService

	/**
	 * Dependency injection for the Ajax auth fail url.
	 */
	String ajaxAuthenticationFailureUrl

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.AbstractProcessingFilter#doFilterHttp(
	 * 	javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * 	javax.servlet.FilterChain)
	 */
	@Override
	void doFilterHttp(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws IOException, ServletException {

		SecurityRequestHolder.set request, response
		try {
			super.doFilterHttp(request, response, chain)
		}
		finally {
			SecurityRequestHolder.reset()
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.AbstractProcessingFilter#sendRedirect(
	 * 	javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * 	java.lang.String)
	 */
	@Override
	protected void sendRedirect(
			HttpServletRequest request,
			HttpServletResponse response,
			String url) throws IOException {
		RedirectUtils.sendRedirect(request, response, url);
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.AbstractProcessingFilter#determineFailureUrl(
	 * 	javax.servlet.http.HttpServletRequest, org.springframework.security.AuthenticationException)
	 */
	@Override
	protected String determineFailureUrl(HttpServletRequest request, AuthenticationException failed) {
		String url = super.determineFailureUrl(request, failed)
		if (url == authenticationFailureUrl && authenticateService.isAjax(request)) {
			url = ajaxAuthenticationFailureUrl ?: authenticationFailureUrl
		}
		return url
	}
}
