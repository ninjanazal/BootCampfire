package com.dev.authservice.tools;

import java.util.regex.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

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

	// public static ResponseEntity<String>
	// ProcessValidationErrorResponse(BindingResult bindingResult, ObjectMapper
	// mapper)
	// throws JsonProcessingException {

	// ObjectNode json = mapper.createObjectNode();

	// try {
	// return new
	// ResponseEntity<String>(createValidationErrorResponse(bindingResult, mapper),
	// HttpStatus.BAD_REQUEST);
	// } catch (Exception e) {
	// json.put("message", e.getMessage());
	// return new ResponseEntity<String>(mapper.writeValueAsString(json),
	// HttpStatus.BAD_REQUEST);
	// }
	// }

	// private static String createValidationErrorResponse(BindingResult
	// bindingResult, ObjectMapper mapper)
	// throws JsonProcessingException {
	// ObjectNode json = mapper.createObjectNode();

	// json.put("message", "Validation errors");
	// ArrayNode errors = mapper.createArrayNode();

	// bindingResult.getFieldErrors().forEach(error -> {
	// ObjectNode errorNode = mapper.createObjectNode();

	// errorNode.put("field", error.getField());
	// errorNode.put("message", error.getDefaultMessage());

	// errors.add(errorNode);
	// });
	// json.set("errors", errors);
	// return mapper.writeValueAsString(json);
	// }

}
