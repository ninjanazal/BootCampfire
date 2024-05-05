package com.dev.authservice.middleware.out.data.responses;

import org.springframework.http.HttpStatus;

import com.dev.authservice.entity.User;
import com.dev.authservice.middleware.out.data.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RegistUserResponseDto implements ResponseDto {

	private final ObjectMapper mapper;

	private User user;

	/**
	 * Constructor to initialize the object with an `ObjectMapper` and a `User`
	 * object representing the registered user.
	 *
	 * @param mapper The `ObjectMapper` for JSON conversion.
	 * @param user   The `User` object containing details of the registered user.
	 */
	public RegistUserResponseDto(ObjectMapper mapper, User user) {
		this.mapper = mapper;
		this.user = user;
	}

	/**
	 * Implements the `toJsonData` method from the `ResponseDto` interface (likely).
	 * This method converts the object data (message and user details) into a JSON
	 * string representation.
	 *
	 * @return A String containing the JSON representation of the response data.
	 */
	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		json.put("message", "User registed");

		ObjectNode userNode = mapper.createObjectNode();
		userNode.put("id", user.getId().toHexString());
		userNode.put("name", user.getName());
		userNode.put("email", user.getEmail());

		json.set("user", userNode);

		try {
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	/**
	 * Implements the `getCode` method from the `ResponseDto` interface (likely).
	 * This method returns the HTTP status code associated with the response
	 * (typically OK for successful registration).
	 *
	 * @return An `HttpStatus` object representing the HTTP status code (OK in this
	 *         case).
	 */
	@Override
	public HttpStatus getCode() {
		return HttpStatus.OK;
	}

}
