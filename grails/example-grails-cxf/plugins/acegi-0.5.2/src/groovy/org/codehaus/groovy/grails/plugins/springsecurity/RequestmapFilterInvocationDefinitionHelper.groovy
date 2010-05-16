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

import org.codehaus.groovy.grails.commons.ApplicationHolder as AH

/**
 * Helper class for RequestmapFilterInvocationDefinition to perform Requestmap dynamic query.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
class RequestmapFilterInvocationDefinitionHelper {

	private String _requestMapClass // 'Requestmap'
	private String _requestMapPathFieldName // 'url'
	private String _requestMapConfigAttributeField // 'configAttribute'

	/**
	 * Constructor.
	 * @param requestMapClass  Requestmap class name
	 * @param requestMapPathFieldName  Requestmap path pattern field name
	 * @param requestMapConfigAttributeField  Requestmap config attribute (roles) field name
	 */
	RequestmapFilterInvocationDefinitionHelper(String requestMapClass,
			String requestMapPathFieldName, String requestMapConfigAttributeField) {
		_requestMapClass = requestMapClass
		_requestMapPathFieldName = requestMapPathFieldName
		_requestMapConfigAttributeField = requestMapConfigAttributeField
	}

	/**
	 * Load all Requestmaps and build a map with the relevant data.
	 * @return keys are url patterns, values are roles
	 */
	Map<String, String> loadRequestmaps() {
		Class requestmapClass = AH.application.getClassForName(_requestMapClass)
		Map<String, String> data = [:]
		def requestmaps = requestmapClass.list()
		for (requestmap in requestmaps) {
			String urlPattern = requestmap."${_requestMapPathFieldName}"
			String configAttribute = requestmap."${_requestMapConfigAttributeField}"
			data[urlPattern] = configAttribute
		}

		return data
	}
}
