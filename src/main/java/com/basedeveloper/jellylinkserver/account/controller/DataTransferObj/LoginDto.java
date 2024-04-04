package com.basedeveloper.jellylinkserver.account.controller.DataTransferObj;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginDto {
	@NotNull(message = "Invalid email format")
	@Email(message = "Invalid email format")
	private String email;

	@NotNull(message = "Password is required")
	@NotBlank(message = "Password cannot be blank")
	private String password;

	@Nullable
	private String sessionType = "read";

	// #region Set/Get
	public void setEmail(String email) {
		this.email = email;
	}

	public String getEmail() {
		return email;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getPassword() {
		return password;
	}

	public String getSessionType() {
		return sessionType;
	}

	public void setSessionType(String sessionType) {
		this.sessionType = sessionType;
	}
	// endregion
}
