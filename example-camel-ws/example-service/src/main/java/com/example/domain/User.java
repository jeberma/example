package com.example.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * A sample entity.
 */
@Entity
@Table(name="users")
public class User {
	
	private Long id;
	
	private String username;
	
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	public Long getId() {
		return id;
	}
	
	@SuppressWarnings("unused")
	private void setId(Long value) {
		id = value;
	}
	
	@Column(nullable=false, unique=true)
	public String getUsername() {
		return username;
	}
	
	public void setUsername(String value) {
		username = value;
	}
	
}