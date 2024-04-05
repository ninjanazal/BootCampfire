package com.basedeveloper.jellylinkserver.account.entity;

import org.hibernate.annotations.GenericGenerator;

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
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "genderSeq")
	@GenericGenerator(name = "genderSeq", strategy = "increment")
	private Long id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "role_id", nullable = false)
	private Role role;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "gender_id", nullable = false)
	private Gender gender;

	@Column(name = "user_token", unique = true, nullable = false)
	private String userToken;

	@Column(name = "email", nullable = false)
	private String email;

	@Column(name = "name", nullable = false)
	private String name;

	@Column(name = "age", nullable = false)
	private Integer age;

	@Column(name = "hashed_password", nullable = false)
	private String hsh_scrt;

	// #region Get/Set
	public Long getId() {
		return id;
	}

	public void setId(Long data) {
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

	public void setUserToken(String userToken) {
		this.userToken = userToken;
	}

	public String getUserToken() {
		return userToken;
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
