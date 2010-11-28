package org.openapplicant.user.service.impl.domain;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

import javax.persistence.Column;
import javax.persistence.Embedded;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Table;

import org.apache.commons.lang.StringUtils;
import org.hibernate.annotations.CollectionOfElements;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.jasypt.digest.StandardStringDigester;
import org.openapplicant.common.domain.PersonName;

@Entity
@Table(name="user")
public class User {
	
	private static final StandardStringDigester digester = new StandardStringDigester();
	static {
		digester.setAlgorithm("SHA-1");
		digester.setIterations(1000);
	}
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="user_id")
	private Long id;
	
	@Column(nullable=false, unique=true)
	private String uuid = UUID.randomUUID().toString();
	
	@CollectionOfElements
	@JoinTable(
			name="user_authorities",
			joinColumns=@JoinColumn(name="user_id")
	)
	@Column(name="authority", nullable=false)
	@Fetch(FetchMode.SUBSELECT)
	private Set<String> authorities = new HashSet<String>();
	
	@Column(nullable=false, unique=true)
	private String username;
	
	private String password;
	
	@Column(nullable=false)
	private String email;
	
	@Embedded
	private PersonName personName;

	public Long getId() {
		return id;
	}

	public String getUuid() {
		return uuid;
	}

	public Set<String> getAuthoritiesUnmodifiable() {
		return Collections.unmodifiableSet(authorities);
	}
	
	public void addAuthorities(Collection<String> values) {
		for(String each : values) {
			addAuthority(each);
		}
	}
	
	public void addAuthority(String value) {
		if(StringUtils.isNotBlank(value)) {
			authorities.add(value);
		}
	}
	
	public void removeAuthorities(Collection<String> values) {
		for(String each : values) {
			removeAuthority(each);
		}
	}
	
	public void removeAuthority(String value) {
		if(StringUtils.isNotBlank(value)) {
			authorities.remove(value);
		}
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}
	
	public void changePassword(String value) {
		password = digester.digest(StringUtils.defaultString(value));
	}
	
	public boolean passwordMatches(String value) {
		return digester.matches(value, password);
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PersonName getPersonName() {
		return personName;
	}

	public void setPersonName(PersonName personName) {
		this.personName = personName;
	}
}
