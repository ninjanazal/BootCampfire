package com.dev.authservice.middleware.out.data.exeptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.dev.authservice.middleware.out.data.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RequestMissMatchResponseDto implements ResponseDto {

	private final ObjectMapper mapper;
	/**
	 * A list of `FieldError` objects containing details about validation errors (if
	 * any).
	 */
	List<FieldError> errors;
	/**
	 * A custom message string to be included in the response.
	 */
	String mString;

	/**
	 * Constructor to initialize the object with an `ObjectMapper`, a list of
	 * `FieldError`s, and a custom message.
	 *
	 * @param mapper  The `ObjectMapper` for JSON conversion.
	 * @param errors  A list of `FieldError` objects representing validation errors
	 *                (optional).
	 * @param message A custom message string to be included in the response.
	 */
	public RequestMissMatchResponseDto(ObjectMapper mapper, List<FieldError> errors, String mString) {
		this.mapper = mapper;
		this.errors = errors;
		this.mString = mString;
	}

	/**
	 * Implements the `toJsonData` method from the `ResponseDto` interface (likely).
	 * This method converts the object data (message and errors) into a JSON string
	 * representation.
	 *
	 * @return A String containing the JSON representation of the response data.
	 */
	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		json.put("message", mString);

		ArrayNode errorsArrayNode = mapper.createArrayNode();
		for (FieldError error : errors) {
			ObjectNode errorNode = mapper.createObjectNode();

			errorNode.put("field", error.getField());
			errorNode.put("message", error.getDefaultMessage());

			errorsArrayNode.add(errorNode);
		}

		json.set("errors", errorsArrayNode);

		try {
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	/**
	 * Implements the `getCode` method from the `ResponseDto` interface (likely).
	 * This method returns the HTTP status code associated with the response
	 * (typically BAD_REQUEST for request mismatches).
	 *
	 * @return An `HttpStatus` object representing the HTTP status code (BAD_REQUEST
	 *         in this case).
	 */
	@Override
	public HttpStatus getCode() {
		return HttpStatus.BAD_REQUEST;
	}
}
