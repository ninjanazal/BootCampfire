package com.dev.authservice.middleware.out.data.responses;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import com.dev.authservice.middleware.out.data.ResponseDto;
import com.dev.authservice.tools.DateTimeActions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class LoginUserResponseDto implements ResponseDto {

	private final ObjectMapper mapper;

	private String message;

	private String token;

	private String expirationDateTime;

	/**
	 * Constructor to initialize the object with an `ObjectMapper`, a message, a
	 * token, and a `LocalDateTime` object representing the expiration date/time.
	 *
	 * @param mapper  The `ObjectMapper` for JSON conversion.
	 * @param message A message string to be included in the response.
	 * @param token   The generated session token.
	 * @param time    A `LocalDateTime` object representing the session expiration
	 *                date/time.
	 */
	public LoginUserResponseDto(ObjectMapper mapper, String msg, String tkn, LocalDateTime time) {
		this.mapper = mapper;
		this.message = msg;
		this.token = tkn;
		this.expirationDateTime = DateTimeActions.FormatDateToString(time);
	}

	/**
	 * Implements the `toJsonData` method from the `ResponseDto` interface (likely).
	 * This method converts the object data (message, token, expiration date) into a
	 * JSON string representation.
	 *
	 * @return A String containing the JSON representation of the response data.
	 */
	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		json.put("message", message);

		ObjectNode sessionNode = mapper.createObjectNode();
		sessionNode.put("token", token);
		sessionNode.put("expiration_date", expirationDateTime);

		json.set("session", sessionNode);

		try {
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	/**
	 * Implements the `getCode` method from the `ResponseDto` interface (likely).
	 * This method returns the HTTP status code associated with the response
	 * (typically OK for successful login).
	 *
	 * @return An `HttpStatus` object representing the HTTP status code (OK in this
	 *         case).
	 */
	@Override
	public HttpStatus getCode() {
		return HttpStatus.OK;
	}

}
