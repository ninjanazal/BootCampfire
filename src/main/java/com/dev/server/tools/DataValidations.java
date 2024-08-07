package com.dev.server.tools;

import java.util.regex.*;

import org.springframework.validation.BindingResult;

import com.dev.server.exeptions.types.RequestMissMatchExeption;

public final class DataValidations {
	// Pre-compiled regular expression pattern for efficient email address
	// validation
	/**
	 * Regular expression to match a valid email address format:
	 * - Starts with one or more word characters, special characters, or symbols:
	 * `[\\w!#$%&'*+/=?^`{|}~-]+`
	 * - Can have one or more dots (.) separating these characters:
	 * `(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*`
	 * - Followed by "@" symbol
	 * - Then one or more alphanumeric characters, hyphens, or dots for the domain
	 * name: `(?:[a-zA-Z0-9-]+\\.)+`
	 * - Ends with a top-level domain (TLD) of at least two letters: `[a-zA-Z]{2,}`
	 */
	private static final Pattern emailPattern = Pattern
			.compile("^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$");

	/**
	 * This static method validates an email address based on the pre-compiled
	 * `emailPattern`.
	 * 
	 * @param email The email address to be validated.
	 * @return True if the email address matches the valid email format, False
	 *         otherwise.
	 */
	public static boolean ValidEmail(String email) {
		if (email == null || email.isBlank()) {
			return false;
		}

		return emailPattern.matcher(email).matches();
	}

	/**
	 * This static method likely processes validation errors captured in a
	 * `BindingResult` object.
	 * It throws a `RequestMissMatchExeption` if there are any validation errors.
	 * 
	 * @param bindingResult   The `BindingResult` object containing validation
	 *                        errors (if any).
	 * @param exceptionString An optional String to be included in the exception
	 *                        message of `RequestMissMatchExeption`.
	 * @throws RequestMissMatchExeption If the `BindingResult` object has validation
	 *                                  errors.
	 */
	public static void ProcessBindingResults(BindingResult bindingResult, String exeptionString)
			throws RequestMissMatchExeption {
		if (bindingResult.hasErrors()) {
			throw new RequestMissMatchExeption(bindingResult, exeptionString);
		}
	}

}
