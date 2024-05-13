package com.dev.server.controller;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.dev.server.constants.data.DataType;
import com.dev.server.dto.exceptions.InvalidPayloadResponseDto;
import com.dev.server.dto.response.GenericResponseDto;
import com.dev.server.entity.User;
import com.dev.server.exeptions.types.BadSessionException;
import com.dev.server.exeptions.types.InvalidDataException;
import com.dev.server.service.dataBlock.IDataBlockService;
import com.dev.server.service.response.RenponseHandlerService;
import com.dev.server.service.session.ISessionService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/data")
public class DataController {

	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private RenponseHandlerService responseService;

	@Autowired
	private ISessionService sessionService;

	@Autowired
	private IDataBlockService dataBlockService;

	@PostMapping("/update")
	public ResponseEntity<String> postMethodName(
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "type", required = true) String type,
			@RequestBody String payload) throws BadSessionException, InvalidDataException, IOException {

		sessionService.validateSession(token);
		User owner = sessionService.getSessionOwner(token);
		
		try {
			JsonNode dataJsonNode = mapper.readTree(payload);
			DataType dataType = DataType.valueOf(type.toUpperCase());
			
			dataBlockService.UpdateData(dataJsonNode, dataType, owner.getId().toHexString());

			return responseService.createJsonResponse(new GenericResponseDto(mapper, String.format("Data updated on [%s]", dataType.name()), HttpStatus.OK));
			
		} catch (JsonProcessingException e) {
			return responseService
					.createJsonResponse(new InvalidPayloadResponseDto(mapper, "Payload needs to be a valid json object"));
		} catch (IllegalArgumentException e) {
			throw new InvalidDataException(String.format("The provided token is invalid %s", token),
					HttpStatus.BAD_REQUEST);
		}

	}

}
