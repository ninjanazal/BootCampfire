package com.dev.server.middleware.out.data.responses;

import org.springframework.http.HttpStatus;

import com.dev.server.middleware.out.data.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;

/**
 * This class represents a response data object (DTO) used for responses related
 * to session validation.
 * It implements the `ResponseDto` interface (likely providing a common
 * structure for response objects).
 */
@AllArgsConstructor
public class ValidationSesisonResponseDto implements ResponseDto {
	/**
	 * An ObjectMapper instance used for JSON serialization and deserialization.
	 */
	private final ObjectMapper mapper;
	/**
	 * A boolean flag indicating whether the session is valid.
	 */
	private boolean isSessionValid;
	/**
	 * The HTTP status code associated with the response.
	 */
	private HttpStatus status;

	/**
	 * This method converts the DTO object into a JSON string representation.
	 *
	 * @return A JSON string representing the session validation response data.
	 */
	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		json.put("message", String.format("The session is currently %svalid", isSessionValid ? "" : "not "));

		try {
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException ex) {
			return ex.getMessage();
		}
	}

	/**
	 * This method returns the HTTP status code associated with the response.
	 *
	 * @return The HTTP status code (likely set based on the validation result).
	 */
	@Override
	public HttpStatus getCode() {
		return status;
	}

}
