package com.example.domain;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;
import org.apache.commons.lang.builder.ToStringBuilder;

public class UserCredentials {

	private String username;
	
	private String password;
	
	public UserCredentials() {	
	}
	
	public UserCredentials(String username, String password) {
		this.username = username;
		this.password = password;
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
	
	@Override
	public String toString() {
		return new ToStringBuilder(this)
					.append("username", username)
					.toString();
	}
	
	@Override
	public boolean equals(Object other) {
		if(!(other instanceof UserCredentials)) {
			return false;
		}
		UserCredentials rhs = (UserCredentials) other;
		return new EqualsBuilder()
					.append(username, rhs.username)
					.append(password, rhs.password)
					.isEquals();
	}
	
	@Override
	public int hashCode() {
		return new HashCodeBuilder(17, 47)
					.append(username)
					.append(password)
					.hashCode();
	}
}
