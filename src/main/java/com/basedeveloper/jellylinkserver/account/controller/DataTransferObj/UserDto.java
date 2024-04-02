package com.basedeveloper.jellylinkserver.account.controller.DataTransferObj;

import jakarta.validation.constraints.NotBlank;;


public class UserDto {
	private String role;
	private String gender;
	private String email;
	@NotBlank(message = "Name cannot be empty")
	private String name;
	private Integer age;
	private String password;

	// region Set/Get
	public void setRole(String role) {
		this.role = role;
	}

	public String getRole() {
		return role;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getGender() {
		return gender;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setAge(Integer age) {
		this.age = age;
	}

	public Integer getAge() {
		return age;
	}

	public void setPassword(String psw) {
		this.password = psw;
	}

	public String getPassword() {
		return password;
	}

	// endregion

}
