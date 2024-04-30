package com.dev.authservice.middleware.out.data.exeptions;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;

import com.dev.authservice.middleware.out.data.ResponseDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public class RequestMissMatchResponseDto implements ResponseDto {
	/**
	 * It provides an ObjectMapper instance for converting objects to JSON format.
	 */
	private final ObjectMapper mapper;

	List<FieldError> errors;
	String mString;

	public RequestMissMatchResponseDto(ObjectMapper mapper, List<FieldError> errors, String mString) {
		this.mapper = mapper;
		this.errors = errors;
		this.mString = mString;
	}

	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		json.put("message", mString);

		ArrayNode errorsArrayNode = mapper.createArrayNode();
		for (FieldError error : errors) {
			ObjectNode errorNode = mapper.createObjectNode();

			errorNode.put("field", error.getField());
			errorNode.put("message", error.getDefaultMessage());

			errorsArrayNode.add(errorNode);
		}

		json.set("errors", errorsArrayNode);

		try {
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException e) {
			return e.getMessage();
		}
	}

	@Override
	public HttpStatus getCode() {
		return HttpStatus.BAD_REQUEST;
	}

}
