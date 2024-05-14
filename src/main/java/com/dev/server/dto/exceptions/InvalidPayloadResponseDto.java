package com.dev.server.dto.exceptions;

import org.springframework.http.HttpStatus;

import com.dev.server.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidPayloadResponseDto implements ResponseDto {
	// ObjectMapper for JSON serialization/deserialization.
	private final ObjectMapper mapper;

	// The message to be included in the JSON response.
	private String message;

	/**
	 * Converts the object to JSON data.
	 * 
	 * @return JSON representation of the response data.
	 */
	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		json.put("message", message);
		try {
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException exception) {
			return exception.getMessage();
		}
	}

	/**
	 * Get the HTTP status code associated with this response.
	 * 
	 * @return HTTP status code.
	 */
	@Override
	public HttpStatus getCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
