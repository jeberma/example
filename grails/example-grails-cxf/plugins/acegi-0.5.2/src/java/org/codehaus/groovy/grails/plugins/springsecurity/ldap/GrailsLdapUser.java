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
package org.codehaus.groovy.grails.plugins.springsecurity.ldap;

import javax.naming.directory.Attributes;

import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUser;
import org.codehaus.groovy.grails.plugins.springsecurity.GrailsUserImpl;
import org.springframework.security.GrantedAuthority;
import org.springframework.security.userdetails.ldap.LdapUserDetails;

/**
 * A {@link GrailsUser} for use in LDAP authentication.
 *
 * @author <a href='mailto:beckwithb@studentsonly.com'>Burt Beckwith</a>
 */
public class GrailsLdapUser extends GrailsUserImpl implements GrailsUser, LdapUserDetails {

	private static final long serialVersionUID = -1557817722745366207L;

	private final Attributes _attributes;
	private final String _dn;

	/**
	 * Constructor from {@link LdapUserDetails}.
	 * @param details  the original details
	 * @param domainClass  the domain instance
	 */
	@SuppressWarnings("deprecation") // just passing along the core impl
	public GrailsLdapUser(final LdapUserDetails details, final Object domainClass) {
		super(details.getUsername(), details.getPassword(), details.isEnabled(),
				details.isAccountNonExpired(), details.isCredentialsNonExpired(),
				details.isAccountNonLocked(), details.getAuthorities(), domainClass);
		_attributes = details.getAttributes();
		_dn = details.getDn();
	}

	/**
	 * Full constructor.
	 * @param username  the username
	 * @param password  the password
	 * @param enabled  whether the user is enabled
	 * @param accountNonExpired  whether the user's account is expired
	 * @param credentialsNonExpired  whether the user's credentials are locked
	 * @param accountNonLocked  whether the user's account is locked
	 * @param authorities  authorities
	 * @param attributes  attributes
	 * @param dn  distinguished name
	 * @param domainClass  the domain instance
	 */
	public GrailsLdapUser(final String username, final String password, final boolean enabled,
			final boolean accountNonExpired, final boolean credentialsNonExpired,
			final boolean accountNonLocked, final GrantedAuthority[] authorities,
			final Attributes attributes, final String dn, final Object domainClass) {
		super(username, password, enabled, accountNonExpired, credentialsNonExpired,
				accountNonLocked, authorities, domainClass);
		_attributes = attributes;
		_dn = dn;
	}

	/**
	 * {@inheritDoc}
	 */
	public Attributes getAttributes() {
		return _attributes;
	}

	/**
	 * {@inheritDoc}
	 * @see org.springframework.security.userdetails.ldap.LdapUserDetails#getDn()
	 */
	public String getDn() {
		return _dn;
	}
}
