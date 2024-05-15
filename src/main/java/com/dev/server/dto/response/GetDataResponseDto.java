package com.dev.server.dto.response;

import org.springframework.http.HttpStatus;

import com.dev.server.dto.ResponseDto;
import com.dev.server.entity.DataBlock;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import lombok.AllArgsConstructor;

/**
 * A DTO (Data Transfer Object) representing a response for retrieving data.
 * Implements the ResponseDto interface.
 */
@AllArgsConstructor
public class GetDataResponseDto implements ResponseDto {
	// ObjectMapper for JSON serialization/deserialization.
	private final ObjectMapper mapper;

	// The data block containing the retrieved data.
	private DataBlock data;

	/**
	 * Converts the data block to JSON data.
	 * 
	 * @return JSON representation of the retrieved data.
	 */
	@Override
	public String toJsonData() {
		
		try {
			JsonNode blockData = mapper.readTree(data.getNodeData());
			return mapper.writeValueAsString(blockData);
		} catch (JsonProcessingException ex) {
			return ex.getMessage();
		}
	}

	/**
	 * Get the HTTP status code associated with this response.
	 * 
	 * @return HTTP status code OK.
	 */
	@Override
	public HttpStatus getCode() {
		return HttpStatus.OK;
	}

}
