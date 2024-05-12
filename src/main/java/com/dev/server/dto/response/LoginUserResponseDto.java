package com.dev.server.dto.response;

import org.springframework.http.HttpStatus;

import com.dev.server.dto.ResponseDto;
import com.dev.server.entity.Session;
import com.dev.server.entity.User;
import com.dev.server.tools.DateTimeActions;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;

/**
 * This class represents a response data object (DTO) used for responses related
 * to successful user login.
 * It implements the `ResponseDto` interface (likely providing a common
 * structure for response objects).
 */
@AllArgsConstructor
public class LoginUserResponseDto implements ResponseDto {
	/**
	 * An ObjectMapper instance used for JSON serialization and deserialization.
	 */
	private final ObjectMapper mapper;
	/**
	 * The User object representing the logged-in user.
	 */
	private User luser;
	/**
	 * The Session object representing the user's session.
	 */
	private Session lSession;

	/**
	 * This method converts the DTO object into a JSON string representation.
	 *
	 * @return A JSON string representing the login response data.
	 */
	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		json.put("message", String.format(
				"Created session of type %s to %s", lSession.getType().name(), luser.getName()));

		ObjectNode sessionNode = mapper.createObjectNode();
		sessionNode.put("token", lSession.getId().toHexString());
		sessionNode.put("expiration_date", DateTimeActions.FormatDateToString(lSession.getExpirationDate()));

		json.set("session", sessionNode);

		try {
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	/**
	 * This method returns the HTTP status code associated with the response (likely
	 * OK for successful login).
	 *
	 * @return The HTTP status code (likely HttpStatus.OK).
	 */
	@Override
	public HttpStatus getCode() {
		return HttpStatus.OK;
	}

}
