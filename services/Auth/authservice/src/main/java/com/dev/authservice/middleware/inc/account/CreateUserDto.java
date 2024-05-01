package com.dev.authservice.middleware.inc.account;

import org.springframework.lang.Nullable;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CreateUserDto {
	/**
	 * The user's gender.
	 * 
	 * @NotBlank (message = "Gender cant be blank") ensures the gender is not empty
	 *           or whitespace.
	 * @NotNull (message = "Gender cant be null") ensures the gender is not null.
	 */
	@NotBlank(message = "Gender cant be blank")
	@NotNull(message = "Gender cant be null")
	private String gender;
	/**
	 * The user's role (optional, defaults to "default").
	 */
	@Nullable
	private String role = "default";
	/**
	 * The user's email address.
	 * 
	 * @NotBlank (message = "Invalid email format") ensures the email is not empty
	 *           or whitespace.
	 * @Email (message = "Invalid email format") validates the email format.
	 */
	@NotNull(message = "Invalid email format")
	@Email(message = "Invalid email format")
	private String email;
	/**
	 * The user's name.
	 * 
	 * @NotBlank (message = "Name cannot be blank") ensures the name is not empty or
	 *           whitespace.u
	 * @NotNull (message = "Name is required") ensures the name is not null.
	 */
	@NotNull(message = "Name is required")
	@NotBlank(message = "Name cannot be blank")
	private String name;
	/**
	 * The user's age (must be a non-negative integer).
	 * 
	 * @Min(value = 1, message = "Age must be a non-negative integer") validates the
	 *            age is greater than or equal to 1.
	 * @NotNull (message = "Age is required") ensures the age is not null.
	 */
	@Min(value = 1, message = "Age must be a non-negative integer")
	@NotNull(message = "Age is required")
	private Integer age;
	/**
	 * The user's password for login.
	 * 
	 * @NotBlank (message = "Password cannot be blank") ensures the password is not
	 *           empty or whitespace.
	 * @NotNull (message = "Password is required") ensures the password is not null.
	 */
	@NotNull(message = "Password is required")
	@NotBlank(message = "Password cannot be blank")
	private String password;
}
