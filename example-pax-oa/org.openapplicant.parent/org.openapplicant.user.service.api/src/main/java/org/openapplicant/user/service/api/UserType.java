package org.openapplicant.user.service.api;

import java.util.List;

import org.openapplicant.common.api.PersonNameType;

public class UserType {
	
	private String id;
	
	private Long version;
	
	private List<String> authorities;
	
	private String username;
	
	private String password;
	
	private String email;
	
	private PersonNameType personName;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Long getVersion() {
		return version;
	}

	public void setVersion(Long version) {
		this.version = version;
	}

	public List<String> getAuthorities() {
		return authorities;
	}

	public void setAuthorities(List<String> authorities) {
		this.authorities = authorities;
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

	public void setPassword(String password) {
		this.password = password;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public PersonNameType getPersonName() {
		return personName;
	}

	public void setPersonName(PersonNameType personName) {
		this.personName = personName;
	}
}
