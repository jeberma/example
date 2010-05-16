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
import org.springframework.security.providers.UsernamePasswordAuthenticationToken
import org.springframework.security.providers.dao.DaoAuthenticationProvider
import org.springframework.security.ui.ntlm.NtlmUsernamePasswordAuthenticationToken
import org.springframework.security.userdetails.UserDetails

/**
 * @author Martin Vlcek
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
class GrailsDaoAuthenticationProvider extends DaoAuthenticationProvider {

	@Override
	protected void additionalAuthenticationChecks(UserDetails userDetails,
            UsernamePasswordAuthenticationToken authentication) throws AuthenticationException {

        // don't check password in case of NTLM authentication
        if (!(authentication instanceof NtlmUsernamePasswordAuthenticationToken)) {
            super.additionalAuthenticationChecks(userDetails, authentication)
        }
    }
}
