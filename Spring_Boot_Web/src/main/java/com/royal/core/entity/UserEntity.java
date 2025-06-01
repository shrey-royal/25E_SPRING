package com.royal.core.entity;

public class UserEntity {
	private String name;
	private String email;
	
	public UserEntity() {}
	
	public UserEntity(String name, String email) {
		this.name = name;
		this.email = email;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}

	@Override
	public String toString() {
		return "UserEntity [name=" + name + ", email=" + email + "]";
	}
}
