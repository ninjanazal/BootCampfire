package com.dev.server.dto.exceptions;

import org.springframework.http.HttpStatus;

import com.dev.server.dto.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class InvalidPayloadResponseDto implements ResponseDto{
		private final ObjectMapper mapper;

		private String message;

		@Override
		public String toJsonData() {
			ObjectNode json = mapper.createObjectNode();
			json.put("message", message);
			try{
				return mapper.writeValueAsString(json);
			}catch (JsonProcessingException exception){
				return exception.getMessage();
			}
		}

		@Override
		public HttpStatus getCode() {
			return HttpStatus.BAD_REQUEST;
		}

}
