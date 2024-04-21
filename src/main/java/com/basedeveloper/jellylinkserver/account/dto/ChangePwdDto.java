package com.basedeveloper.jellylinkserver.account.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

/**
 * Data Transfer Object (DTO) representing data required for changing a
 * password.
 *
 * This class encapsulates the input fields for a password change request,
 * ensuring data integrity and validation before processing at the server-side.
 */
public class ChangePwdDto {
	/**
	 * The user's session token for authentication and authorization.
	 *
	 * @NotNull(message = "Please provide a valid token") indicates the token is
	 *                  mandatory.
	 * @NotBlank(message = "Token cannot be empty") ensures it's not empty or
	 *                   whitespace-only.
	 */
	@NotNull(message = "Please provide a valid token")
	@NotBlank(message = "Token cannot be empty")
	private String token;
	/**
	 * The user's current password for verification.
	 *
	 * @NotNull(message = "Please provide the old password") mandates its presence.
	 * @NotBlank(message = "Old Password cannot be empty") prevents empty or
	 *                   whitespace-only values.
	 */
	@NotNull(message = "Please provide the old password")
	@NotBlank(message = "Old Password cannot be empty")
	private String oldPassword;

	/**
	 * The new password the user wants to set.
	 *
	 * @NotNull(message = "Please provide the new password") enforces its presence.
	 * @NotBlank(message = "New Password cannot be empty") disallows empty or
	 *                   whitespace-only entries.
	 */
	@NotNull(message = "Please provide the new password")
	@NotBlank(message = "New Password cannot be empty")
	private String newPassword;

	// region Set/Get
	public String getToken() {
		return token;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public String getOldPassword() {
		return oldPassword;
	}

	public void setOldPassword(String oldPassword) {
		this.oldPassword = oldPassword;
	}


	public String getNewPassword() {
		return newPassword;
	}

	public void setNewPassword(String newPassword) {
		this.newPassword = newPassword;
	}
	// endregion
}
