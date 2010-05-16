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
package org.codehaus.groovy.grails.plugins.springsecurity;

import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.security.intercept.web.FilterInvocationDefinitionSource;
import org.springframework.security.util.AntUrlPathMatcher;
import org.springframework.security.util.UrlMatcher;
import org.springframework.util.Assert;

/**
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
public abstract class AbstractFilterInvocationDefinition
      implements FilterInvocationDefinitionSource, InitializingBean {

	private UrlMatcher _urlMatcher;
	private boolean _rejectIfNoRule;
	private boolean _stripQueryStringFromUrls = true;

	protected static final ConfigAttributeDefinition DENY =
		new ConfigAttributeDefinition(Collections.emptyList());

	protected final Map<Object, ConfigAttributeDefinition> _compiled =
		new HashMap<Object, ConfigAttributeDefinition>();

	protected final Logger _log = Logger.getLogger(getClass());

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.intercept.ObjectDefinitionSource#getAttributes(java.lang.Object)
	 */
	public ConfigAttributeDefinition getAttributes(Object object) {
		if (object == null || !supports(object.getClass())) {
			throw new IllegalArgumentException("Object must be a FilterInvocation");
		}

		FilterInvocation filterInvocation = (FilterInvocation)object;

		String url = determineUrl(filterInvocation);

		ConfigAttributeDefinition configAttribute = findConfigAttribute(url);
		if (configAttribute == null && _rejectIfNoRule) {
			return DENY;
		}

		return configAttribute;
	}

	protected abstract String determineUrl(FilterInvocation filterInvocation);

	private ConfigAttributeDefinition findConfigAttribute(final String url) {

		initialize();

		ConfigAttributeDefinition configAttribute = null;
		Object configAttributePattern = null;

		for (Map.Entry<Object, ConfigAttributeDefinition> entry : _compiled.entrySet()) {
			Object pattern = entry.getKey();
			if (_urlMatcher.pathMatchesUrl(pattern, url)) {
				// TODO  this assumes Ant matching, not valid for regex
				if (configAttribute == null || _urlMatcher.pathMatchesUrl(configAttributePattern, (String)pattern)) {
					configAttribute = entry.getValue();
					configAttributePattern = pattern;
					if (_log.isTraceEnabled()) {
						_log.trace("new candidate for '" + url + "': '" + pattern
								+ "':" + configAttribute.getConfigAttributes());
					}
				}
			}
		}

		if (_log.isTraceEnabled()) {
			if (configAttribute == null) {
				_log.trace("no config for '" + url + "'");
			}
			else {
				_log.trace("config for '" + url + "' is '" + configAttributePattern
						+ "':" + configAttribute.getConfigAttributes());
			}
		}

		return configAttribute;
	}

	protected void initialize() {
		// override if necessary
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.intercept.ObjectDefinitionSource#supports(java.lang.Class)
	 */
	@SuppressWarnings("unchecked")
	public boolean supports(final Class clazz) {
		return FilterInvocation.class.isAssignableFrom(clazz);
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.intercept.ObjectDefinitionSource#getConfigAttributeDefinitions()
	 */
	@SuppressWarnings("unchecked")
	public Collection getConfigAttributeDefinitions() {
		return null;
	}

	/**
	 * Dependency injection for the url matcher.
	 * @param urlMatcher  the matcher
	 */
	public void setUrlMatcher(final UrlMatcher urlMatcher) {
		_urlMatcher = urlMatcher;
		_stripQueryStringFromUrls = _urlMatcher instanceof AntUrlPathMatcher;
	}

	/**
	 * Dependency injection for whether to reject if there's no matching rule.
	 * @param reject  if true, reject access unless there's a pattern for the specified resource
	 */
	public void setRejectIfNoRule(final boolean reject) {
		_rejectIfNoRule = reject;
	}

	protected String lowercaseAndStringQuerystring(final String url) {

		String fixed = url;

		if (getUrlMatcher().requiresLowerCaseUrl()) {
			fixed = fixed.toLowerCase();
		}

		if (_stripQueryStringFromUrls) {
			int firstQuestionMarkIndex = fixed.indexOf("?");
			if (firstQuestionMarkIndex != -1) {
				fixed = fixed.substring(0, firstQuestionMarkIndex);
			}
		}

		return fixed;
	}

	protected UrlMatcher getUrlMatcher() {
		return _urlMatcher;
	}

	/**
	 * For debugging.
	 * @return  an unmodifiable map of {@link AnnotationFilterInvocationDefinition}ConfigAttributeDefinition
	 * keyed by compiled patterns
	 */
	public Map<Object, ConfigAttributeDefinition> getConfigAttributeMap() {
		return Collections.unmodifiableMap(_compiled);
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	public void afterPropertiesSet() {
		Assert.notNull(_urlMatcher, "url matcher is required");
	}
}
