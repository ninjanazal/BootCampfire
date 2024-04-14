package com.basedeveloper.jellylinkserver.account.entity;

import com.basedeveloper.jellylinkserver.account.constants.AccountConsts;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	private String id;

	@Column(name = "role", nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountConsts.Role role;

	@Column(name = "gender", nullable = false)
	@Enumerated(EnumType.STRING)
	private AccountConsts.Gender gender;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "age", nullable = false)
	private Integer age;

	@Column(name = "hashed_password", nullable = false)
	private String hsh_scrt;

	// #region Get/Set
	public String getId() {
		return id;
	}

	public void setId(String data) {
		id = data;
	}

	public AccountConsts.Role getRole() {
		return role;
	}

	public void setRole(AccountConsts.Role data) {
		role = data;
	}

	public AccountConsts.Gender getGender() {
		return gender;
	}

	public void setGender(AccountConsts.Gender data) {
		gender = data;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String data) {
		email = data;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getAge() {
		return age;
	}

	public void setAge(Integer data) {
		age = data;
	}

	public String getHshScrt() {
		return hsh_scrt;
	}

	public void setHshScrt(String data) {
		hsh_scrt = data;
	}
	// endregion
}
