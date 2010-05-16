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

import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

import org.springframework.beans.factory.FactoryBean
import org.springframework.beans.factory.InitializingBean
import org.springframework.beans.factory.annotation.Required
import org.springframework.security.ui.logout.LogoutFilter
import org.springframework.security.ui.logout.LogoutHandler

/**
 * Configures a {@link LogoutFilter} given a list of {@link LogoutHandler}s.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
class LogoutFilterFactoryBean implements FactoryBean, InitializingBean {

	private List<LogoutHandler> _handlers
	private LogoutFilter _logoutFilter
	private String _logoutSuccessUrl

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.FactoryBean#getObject()
	 */
	LogoutFilter getObject() {
		return _logoutFilter
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.FactoryBean#getObjectType()
	 */
	Class<LogoutFilter> getObjectType() {
		return LogoutFilter
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.FactoryBean#isSingleton()
	 */
	boolean isSingleton() {
		return true
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	void afterPropertiesSet() {
		_logoutFilter = new FixRedirectLogoutFilter(_logoutSuccessUrl, _handlers as LogoutHandler[])
	}

	/**
	 * Dependency injection for the logout success url.
	 * @param logoutSuccessUrl  the url
	 */
	@Required
	void setLogoutSuccessUrl(String logoutSuccessUrl) {
		_logoutSuccessUrl = logoutSuccessUrl
	}

	/**
	 * Dependency injection for the handlers.
	 * @param handlers  the handlers
	 */
	@Required
	void setHandlers(List<LogoutHandler> handlers) {
		_handlers = handlers
	}
}

/**
 * Overrides the default redirect behavior to use {@link RedirectUtils#sendRedirect(HttpServletRequest, HttpServletResponse, String)}.
 */
class FixRedirectLogoutFilter extends LogoutFilter {

	FixRedirectLogoutFilter(String logoutSuccessUrl, LogoutHandler[] handlers) {
		super(logoutSuccessUrl, handlers)
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.ui.logout.LogoutFilter#sendRedirect(
	 * 	javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.String)
	 */
	@Override
	protected void sendRedirect(
			HttpServletRequest request,
			HttpServletResponse response,
			String url) throws IOException {
		RedirectUtils.sendRedirect(request, response, url)
	}
}
