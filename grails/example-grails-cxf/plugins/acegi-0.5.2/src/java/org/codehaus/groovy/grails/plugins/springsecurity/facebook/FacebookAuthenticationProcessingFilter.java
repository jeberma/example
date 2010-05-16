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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.codehaus.groovy.grails.plugins.springsecurity.SecurityRequestHolder;
import org.springframework.security.Authentication;
import org.springframework.security.AuthenticationException;
import org.springframework.security.ui.AbstractProcessingFilter;
import org.springframework.security.ui.FilterChainOrder;
import org.springframework.security.ui.webapp.AuthenticationProcessingFilter;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.w3c.dom.Document;

import com.google.code.facebookapi.FacebookWebappHelper;

/**
 * Intercepts j_spring_facebook_security_check to trigger Facebook login.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
public class FacebookAuthenticationProcessingFilter extends AbstractProcessingFilter {

	private String _apiKey;
	private String _secretKey;
	private String _authenticationUrlRoot;

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.AbstractProcessingFilter#attemptAuthentication(
	 * 	javax.servlet.http.HttpServletRequest)
	 */
	@Override
	public Authentication attemptAuthentication(final HttpServletRequest request) throws AuthenticationException {

		String authToken = request.getParameter("auth_token");
		if (!StringUtils.hasText(authToken)) {
			// trigger a redirect to the Facebook login
			throw new FacebookAuthenticationRequiredException();
		}

		FacebookAuthenticationToken token = createToken(
				authToken, request, SecurityRequestHolder.getResponse(),
				_apiKey, _secretKey);

		token.setDetails(authenticationDetailsSource.buildDetails(request));

		Authentication authentication = getAuthenticationManager().authenticate(token);
		if (authentication.isAuthenticated()) {
			setLastUsername(token.getUserId(), request);
		}

		return authentication;
	}

	private void setLastUsername(final long userId, final HttpServletRequest request) {
		HttpSession session = request.getSession(false);
		if (session != null || getAllowSessionCreation()) {
			request.getSession().setAttribute(
					AuthenticationProcessingFilter.SPRING_SECURITY_LAST_USERNAME_KEY,
					String.valueOf(userId));
		}
	}

	/**
	 * Build an authentication from a login <code>auth_token</code>.
	 * @param authToken  the <code>auth_token</code>
	 * @param request  the http request
	 * @param response  the http response
	 * @param apiKey  the API key
	 * @param secretKey  the secret key
	 * @return  the auth token
	 */
	protected FacebookAuthenticationToken createToken(
			final String authToken, final HttpServletRequest request, final HttpServletResponse response,
			final String apiKey, final String secretKey) {

		try {
			FacebookWebappHelper<Document> helper = FacebookWebappHelper.newInstanceXml(
					request, response, apiKey, secretKey);

			if (helper.isLogin()) {
				String sessionKey = helper.doGetSession(authToken);
				return new FacebookAuthenticationToken(helper.getUser().longValue(), sessionKey);
			}

			return new FacebookAuthenticationToken(FacebookAuthenticationToken.Status.failure, null);
		}
		catch (RuntimeException e) {
			return new FacebookAuthenticationToken(FacebookAuthenticationToken.Status.error, e.getMessage());
		}
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.AbstractProcessingFilter#determineFailureUrl(
	 * 	javax.servlet.http.HttpServletRequest, org.springframework.security.AuthenticationException)
	 */
	@Override
	protected String determineFailureUrl(final HttpServletRequest request, final AuthenticationException failed) {
		if (failed instanceof FacebookAuthenticationRequiredException) {
			return _authenticationUrlRoot + _apiKey;
		}

		return super.determineFailureUrl(request, failed);
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.AbstractProcessingFilter#getDefaultFilterProcessesUrl()
	 */
	@Override
	public String getDefaultFilterProcessesUrl() {
		return "/j_spring_facebook_security_check";
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.SpringSecurityFilter#getOrder()
	 */
	public int getOrder() {
		return FilterChainOrder.OPENID_PROCESSING_FILTER + 1;
	}

	/**
	 * Dependency injection for the API key.
	 * @param key  the key
	 */
	public void setApiKey(final String key) {
		_apiKey = key;
	}

	/**
	 * Dependency injection for the secret key.
	 * @param key  the key
	 */
	public void setSecretKey(final String key) {
		_secretKey = key;
	}

	/**
	 * Dependency injection for the Facebook auth url root.
	 * @param authenticationUrlRoot  the url root
	 */
	public void setAuthenticationUrlRoot(String authenticationUrlRoot) {
		_authenticationUrlRoot = authenticationUrlRoot;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.AbstractProcessingFilter#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() throws Exception {
		super.afterPropertiesSet();
      Assert.notNull(_apiKey, "API key must be specified");
      Assert.notNull(_secretKey, "Secret key must be specified");
	}
}
