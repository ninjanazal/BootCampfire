package com.dev.server.models.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
}
