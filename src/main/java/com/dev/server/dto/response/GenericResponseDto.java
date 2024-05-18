package com.dev.server.dto.response;

import org.springframework.http.HttpStatus;

import com.dev.server.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;

/**
 * This class implements the `ResponseDto` interface and provides a generic
 * structure for response data.
 */
@AllArgsConstructor
public class GenericResponseDto implements ResponseDto {
	/**
	 * It provides an ObjectMapper instance for converting objects to JSON format.
	 */
	private final ObjectMapper mapper;

	/**
	 * This field stores a message associated with the response.
	 */
	private String msg;

	/**
	 * This field stores the HTTP status code for the response.
	 */
	private HttpStatus code;

	/**
	 * This method overrides the `toJsonData` method from the `ResponseDto`
	 * interface.
	 * It converts the `GenericResponseDto` object into a JSON string.
	 * 
	 * @return A JSON string representation of the `GenericResponseDto` object.
	 */
	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		json.put("message", msg);
		try {
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	/**
	 * This method overrides the `getCode` method from the `ResponseDto` interface.
	 * It returns the HTTP status code associated with the response object.
	 * 
	 * @return The HTTP status code for the response.
	 */
	@Override
	public HttpStatus getCode() {
		return code;
	}
}
