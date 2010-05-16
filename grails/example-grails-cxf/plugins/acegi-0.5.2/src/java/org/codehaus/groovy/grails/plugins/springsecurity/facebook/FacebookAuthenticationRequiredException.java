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

import org.springframework.security.AuthenticationException;

/**
 * Used to tigger a redirect to the Facebook login page.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
public class FacebookAuthenticationRequiredException extends AuthenticationException {

	private static final long serialVersionUID = -1162739729449839527L;

	/**
	 * Default constructor.
	 */
	public FacebookAuthenticationRequiredException() {
		super("External Authentication Required");
	}
}
