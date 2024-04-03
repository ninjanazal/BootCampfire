package com.basedeveloper.jellylinkserver.account.controller.DataTransferObj;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class UserDto {

	@NotBlank(message = "Gender cant be blank")
	@NotNull(message = "Gender cant be null")
	private String gender;

	@Nullable
	private String role = "default";
	
	@NotNull(message = "Invalid email format")
	@Email(message = "Invalid email format")
	private String email;
	
	@NotNull(message = "Name is required")
	@NotBlank(message = "Name cannot be blank")
	private String name;
	
	@Min(value = 1, message = "Age must be a non-negative integer")
	@NotNull(message = "Age is required")
	private Integer age;

	@NotNull(message = "Password is required")
	@NotBlank(message = "Password cannot be blank")
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
