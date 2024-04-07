package com.basedeveloper.jellylinkserver.account.entity;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "users")
public class User {
	@Id
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gender_id", nullable = false)
	private Gender gender;

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

	public Role getRole() {
		return role;
	}

	public void setRole(Role data) {
		role = data;
	}

	public Gender getGender() {
		return gender;
	}

	public void setGender(Gender data) {
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
