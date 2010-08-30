package com.example.domain;

import java.util.UUID;

public class UserCredentialsBuilder {
	
	private String username = UUID.randomUUID().toString();
	
	private String password = "secret";
	
	public UserCredentialsBuilder username(String value) {
		username = value;
		return this;
	}
	
	public UserCredentialsBuilder password(String value) {
		password = value;
		return this;
	}
	
	public UserCredentials build() {
		return new UserCredentials(username, password);
	}

}
