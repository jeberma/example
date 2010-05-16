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

import org.springframework.security.AccessDeniedException
import org.springframework.security.Authentication
import org.springframework.security.ConfigAttribute
import org.springframework.security.ConfigAttributeDefinition
import org.springframework.security.vote.AbstractAccessDecisionManagerimport org.springframework.security.vote.AccessDecisionVoterimport org.springframework.security.vote.AuthenticatedVoter

/**
 * Uses the affirmative-based logic for roles, i.e. any in the list will grant access, but allows
 * an authenticated voter to 'veto' access. This allows specification of roles and
 * <code>IS_AUTHENTICATED_FULLY</code> on one line in SecurityConfig.groovy.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
class AuthenticatedVetoableDecisionManager extends AbstractAccessDecisionManager {

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.vote.AbstractAccessDecisionManager#decide(
	 * 	org.springframework.security.Authentication, java.lang.Object,
	 * 	org.springframework.security.ConfigAttributeDefinition)
	 */
	void decide(Authentication authentication, Object object, ConfigAttributeDefinition config)
            throws AccessDeniedException {

		boolean authenticatedVotersGranted = checkAuthenticatedVoters(authentication, object, config)
		boolean otherVotersGranted = checkOtherVoters(authentication, object, config)

		if (!authenticatedVotersGranted && !otherVotersGranted) {
			checkAllowIfAllAbstainDecisions()
		}
	}

	/**
	 * Allow any {@link AuthenticatedVoter} to veto. If any voter denies,
	 * throw an exception; if any grant, return <code>true</code>;
	 * otherwise return <code>false</code> if all abstain.
	 */
	private boolean checkAuthenticatedVoters(authentication, object, config) {
		boolean grant = false
		for (AccessDecisionVoter voter in decisionVoters) {
			if (voter instanceof AuthenticatedVoter) {
				int result = voter.vote(authentication, object, config)
				switch (result) {
					case AccessDecisionVoter.ACCESS_GRANTED:
						grant = true
						break
					case AccessDecisionVoter.ACCESS_DENIED:
						deny()
						break
					default: // abstain
						break
				}
			}
		}
		return grant
	}

	/**
	 * Check the other (non-{@link AuthenticatedVoter}) voters. If any voter grants,
	 * return true. If any voter denies, throw exception. Otherwise return <code>false</code>
	 * to indicate that all abstained.
	 */
	private boolean checkOtherVoters(authentication, object, config) {
		int denyCount = 0
		for (AccessDecisionVoter voter in decisionVoters) {
			if (voter instanceof AuthenticatedVoter) {
				continue
			}

			int result = voter.vote(authentication, object, config)
			switch (result) {
            	case AccessDecisionVoter.ACCESS_GRANTED:
            		return true
            	case AccessDecisionVoter.ACCESS_DENIED:
            		denyCount++
            		break
				default: // abstain
            		break
            }
        }

        if (denyCount) {
            deny()
        }

        // all abstain
        return false
	}

	private void deny() {
		throw new AccessDeniedException(messages.getMessage(
				"AbstractAccessDecisionManager.accessDenied",
				"Access is denied"))
	}
}
