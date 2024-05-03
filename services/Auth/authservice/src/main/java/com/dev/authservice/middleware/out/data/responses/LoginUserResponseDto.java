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

	public LoginUserResponseDto(ObjectMapper mapper, String msg, String tkn, LocalDateTime time) {
		this.mapper = mapper;
		this.message = msg;
		this.token = tkn;
		this.expirationDateTime = DateTimeActions.FormatDateToString(time);
	}

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

	@Override
	public HttpStatus getCode() {
		return HttpStatus.OK;
	}

}
