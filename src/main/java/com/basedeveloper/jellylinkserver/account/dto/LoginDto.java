package com.basedeveloper.jellylinkserver.account.dto;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class LoginDto {
	/**
	 * The user's email address for login.
	 * 
	 * @NotBlank (message = "Invalid email format") ensures the email is not null,
	 *           empty, or whitespace.
	 * @Email (message = "Invalid email format") validates the email format.
	 */

	@NotNull(message = "Invalid email format")
	@Email(message = "Invalid email format")
	private String email;

	/**
	 * The user's password for login.
	 * 
	 * @NotBlank (message = "Password cannot be blank") ensures the password is not
	 *           null, empty, or whitespace.
	 * @NotNull (message = "Password is required") ensures the password is not null.
	 */
	@NotNull(message = "Password is required")
	@NotBlank(message = "Password cannot be blank")
	private String password;

	/**
	 * The type of session requested (optional, defaults to "read").
	 */
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
