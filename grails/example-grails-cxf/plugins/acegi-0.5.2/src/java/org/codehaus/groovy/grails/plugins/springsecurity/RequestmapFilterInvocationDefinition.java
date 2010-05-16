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

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.security.ConfigAttributeDefinition;
import org.springframework.security.intercept.web.FilterInvocation;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

/**
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
public class RequestmapFilterInvocationDefinition extends AbstractFilterInvocationDefinition {

	private boolean _initialized;

	private String _requestMapClass;
	private String _requestMapPathFieldName;
	private String _requestMapConfigAttributeField;
	private RequestmapFilterInvocationDefinitionHelper _helper;

	@Override
	protected String determineUrl(final FilterInvocation filterInvocation) {
		HttpServletRequest request = filterInvocation.getHttpRequest();
		String requestUrl = request.getRequestURI().substring(request.getContextPath().length());
		return lowercaseAndStringQuerystring(requestUrl);
	}

	@Override
	protected void initialize() {
		if (!_initialized) {
			reset();
			_initialized = true;
		}
	}

	/**
	 * Call at startup or when <code>Requestmap</code> instances have been added, removed, or changed.
	 */
	public synchronized void reset() {
		Map<String, String> data = _helper.loadRequestmaps();
		_compiled.clear();

		for (Map.Entry<String, String> entry : data.entrySet()) {
			String pattern = entry.getKey();
			String[] tokens = StringUtils.commaDelimitedListToStringArray(entry.getValue());
			storeMapping(pattern, tokens);
		}

		if (_log.isTraceEnabled()) {
			_log.trace("configs: " + _compiled);
		}
	}

	private void storeMapping(final String pattern, final String[] tokens) {

		ConfigAttributeDefinition configAttribute = new ConfigAttributeDefinition(tokens);

		Object key = getUrlMatcher().compile(pattern);

		ConfigAttributeDefinition replaced = _compiled.put(key, configAttribute);
		if (replaced != null) {
			_log.warn("replaced rule for '" + key + "' with roles " + replaced.getConfigAttributes()
					+ " with roles " + configAttribute.getConfigAttributes());
		}
	}

	/**
	 * Dependency injection for the Requestmap class name.
	 * @param name  the class name
	 */
	public void setRequestMapClass(final String name) {
		_requestMapClass = name;
	}

	/**
	 * Dependency injection for the Requestmap config attribute (e.g. roles) field name.
	 * @param name
	 */
	public void setRequestMapConfigAttributeField(final String name) {
		_requestMapConfigAttributeField = name;
	}

	/**
	 * Dependency injection for the Requestmap path field name.
	 * @param name
	 */
	public void setRequestMapPathFieldName(final String name) {
		_requestMapPathFieldName = name;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.beans.factory.InitializingBean#afterPropertiesSet()
	 */
	@Override
	public void afterPropertiesSet() {
		super.afterPropertiesSet();
		Assert.notNull(_requestMapClass, "Requestmap class name is required");
		Assert.notNull(_requestMapPathFieldName, "Requestmap path field name is required");
		Assert.notNull(_requestMapConfigAttributeField, "Requestmap config attribute field name is required");

		_helper = new RequestmapFilterInvocationDefinitionHelper(_requestMapClass,
				_requestMapPathFieldName, _requestMapConfigAttributeField);
	}
}
