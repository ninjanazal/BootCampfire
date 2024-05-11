package com.dev.server.models.dto.response;

import org.springframework.http.HttpStatus;

import com.dev.server.models.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;

/**
 * This class represents a response data object (DTO) used for responses related
 * to password change operations.
 * It implements the `ResponseDto` interface (likely providing a common
 * structure for response objects).
 */
@AllArgsConstructor
public class ChangePwdResponseDto implements ResponseDto {
	/**
	 * An ObjectMapper instance used for JSON serialization and deserialization.
	 */
	private final ObjectMapper mapper;
	/**
	 * A boolean flag indicating whether the session associated with the request was
	 * valid.
	 */
	private boolean isSessionValid;

	/**
	 * This method converts the DTO object into a JSON string representation.
	 *
	 * @return A JSON string representing the password change response data.
	 */
	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		String msg = "Invalid session, failed to change.";
		if (isSessionValid) {
			msg = "Password changed successfully";
		}

		json.put("message", msg);

		ObjectNode dataNode = mapper.createObjectNode();
		dataNode.put("session_valid", isSessionValid);

		json.set("properies", dataNode);

		try {
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException ex) {
			return ex.getMessage();
		}

	}

	/**
	 * This method returns the HTTP status code associated with the response.
	 *
	 * @return The HTTP status code (likely OK for successful password change,
	 *         FORBIDDEN for failure).
	 */
	@Override
	public HttpStatus getCode() {
		return isSessionValid ? HttpStatus.OK : HttpStatus.FORBIDDEN;
	}
}
