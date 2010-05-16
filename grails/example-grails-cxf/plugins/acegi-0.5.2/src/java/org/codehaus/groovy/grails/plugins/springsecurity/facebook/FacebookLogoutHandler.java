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

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.Authentication;
import org.springframework.security.ui.logout.LogoutHandler;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * Removes cookies at logout.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
public class FacebookLogoutHandler implements LogoutHandler, InitializingBean {

	private String _apiKey;

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.logout.LogoutHandler#logout(
	 * 	javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse,
	 * 	org.springframework.security.Authentication)
	 */
	public void logout(final HttpServletRequest request, final HttpServletResponse response,
			final Authentication authentication) {

		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			String path = StringUtils.hasLength(request.getContextPath()) ? request.getContextPath() : "/";
			for (Cookie cookie : cookies) {
				if (cookie.getName().startsWith(_apiKey)) {
					cancelCookie(cookie.getName(), path, response);
				}
			}
		}
	}

	private void cancelCookie(final String name, final String path, final HttpServletResponse response) {
      Cookie cookie = new Cookie(name, null);
      cookie.setMaxAge(0);
      cookie.setPath(path);
      response.addCookie(cookie);
	}

	/**
	 * Dependency injection for the API key.
	 * @param key  the key
	 */
	public void setApiKey(final String key) {
		_apiKey = key;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
      Assert.notNull(_apiKey, "API key must be specified");
	}
}
