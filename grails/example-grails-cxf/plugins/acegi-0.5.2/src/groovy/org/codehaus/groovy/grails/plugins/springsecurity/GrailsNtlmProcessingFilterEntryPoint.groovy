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

import org.springframework.security.AuthenticationException
import org.springframework.security.BadCredentialsException
import org.springframework.security.ui.ntlm.NtlmBaseException
import org.springframework.security.ui.ntlm.NtlmBeginHandshakeException
import org.springframework.security.ui.ntlm.NtlmProcessingFilter
import org.springframework.security.ui.ntlm.NtlmProcessingFilterEntryPoint

import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

/**
 * @author Martin Vlcek
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
class GrailsNtlmProcessingFilterEntryPoint extends NtlmProcessingFilterEntryPoint {

	private final String STATE_ATTR = NtlmProcessingFilter.@STATE_ATTR
	private final Integer BEGIN = NtlmProcessingFilter.@BEGIN

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.ntlm.NtlmProcessingFilterEntryPoint#commence(
	 * 	javax.servlet.ServletRequest, javax.servlet.ServletResponse,
	 * 	org.springframework.security.AuthenticationException)
	 */
	@Override
	void commence(ServletRequest req, ServletResponse res, AuthenticationException authException) throws IOException, ServletException {

		// start authentication, if necessary and forceIdentification in NtlmProcessingFilter is false
		if (!(authException instanceof NtlmBaseException
				|| authException instanceof BadCredentialsException)) {

			req.session.setAttribute STATE_ATTR, BEGIN

			HttpServletResponse response = (HttpServletResponse)res

			response.setHeader 'WWW-Authenticate', new NtlmBeginHandshakeException().message
			response.setHeader 'Connection', 'Keep-Alive'
			response.status = HttpServletResponse.SC_UNAUTHORIZED
			response.contentLength = 0
			response.flushBuffer()
		}
		else {
			super.commence(req, res, authException)
		}
	}
}
