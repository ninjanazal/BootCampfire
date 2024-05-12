package com.dev.server.dto.response;

import org.springframework.http.HttpStatus;

import com.dev.server.dto.ResponseDto;
import com.dev.server.entity.DataBlock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public class ValidDataBlockUpdate implements ResponseDto {
	/**
	 * An ObjectMapper instance used for JSON serialization and deserialization.
	 */
	private final ObjectMapper mapper;

	private DataBlock uBlock;

	@Override
	public String toJsonData() {
		ObjectNode json = mapper.createObjectNode();
		json.put("message", "DataBlock updated successfully");
		json.put("type", uBlock.getType().name());

		try{
			return mapper.writeValueAsString(json);
		} catch (JsonProcessingException exception){
			return exception.getMessage();
		}
	}

	@Override
	public HttpStatus getCode() {
		return HttpStatus.OK;
	}

}
