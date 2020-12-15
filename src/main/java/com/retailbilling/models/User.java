package com.retailbilling.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@Entity
@Table(name = "user")
public class User {

	@Id
	@Column(name = "user_id")
	private String userId;

	@NotBlank(message = "user name is mandatory")
	private String name;

	@NotBlank(message = "user role is mandatory")
	private String role;

	@NotBlank(message = "account created on field is mandatory")
	@Column(name = "account_created_on")
	private String accountCreatedOn;
	
	public User() {
		super();
	}

	public User(String userId, @NotNull @NotEmpty String name, @NotNull @NotEmpty String role,
			@NotNull @NotEmpty String accountCreatedOn) {
		super();
		this.userId = userId;
		this.name = name;
		this.role = role;
		this.accountCreatedOn = accountCreatedOn;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getAccountCreatedOn() {
		return accountCreatedOn;
	}

	public void setAccountCreatedOn(String accountCreatedOn) {
		this.accountCreatedOn = accountCreatedOn;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", name=" + name + ", role=" + role + ", accountCreatedOn=" + accountCreatedOn
				+ "]";
	}
}
