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

	public RegistUserResponseDto(ObjectMapper mapper, User user) {
		this.mapper = mapper;
		this.user = user;
	}

	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		json.put("message", "User registed");

		ObjectNode userNode = mapper.createObjectNode();
		userNode.put("id", user.getId().toString());
		userNode.put("name", user.getName());
		userNode.put("email", user.getEmail());

		json.set("user", userNode);

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
