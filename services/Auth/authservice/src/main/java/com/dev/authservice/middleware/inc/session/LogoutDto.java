package com.dev.authservice.middleware.inc.session;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * This class represents a Data Transfer Object (DTO) used for logout requests.
 * It contains a single field for the logout token.
 */
@Data
@NoArgsConstructor
public class LogoutDto {
	/**
	 * The logout token as a String. This field is mandatory and cannot be null or
	 * blank.
	 */
	@NotNull(message = "Token is required")
	@NotBlank(message = "Token cannot be black")
	private String toke;
}
