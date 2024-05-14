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

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@RestController
@RequestMapping("/api/data")
public class DataController {
	/**
	 * Autowired ObjectMapper for JSON serialization/deserialization.
	 */
	@Autowired
	private ObjectMapper mapper;
	/**
	 * Autowired ResponseHandlerService for creating JSON responses.
	 */
	@Autowired
	private RenponseHandlerService responseService;
	/**
	 * Autowired ISessionService for session management.
	 */
	@Autowired
	private ISessionService sessionService;
	/**
	 * Autowired IDataBlockService for data block operations.
	 */
	@Autowired
	private IDataBlockService dataBlockService;

	/**
	 * Handles HTTP POST requests to update data.
	 * 
	 * @param token   The session token for authentication. Required.
	 * @param type    The type of data to be updated. Required.
	 * @param payload The payload containing data to be updated. Expected to be a
	 *                valid JSON object.
	 * @return A JSON response indicating the result of the update operation.
	 * @throws BadSessionException  If the session is invalid or expired.
	 * @throws InvalidDataException If the provided data type is invalid.
	 * @throws IOException          If there is an I/O error while processing the
	 *                              request.
	 */
	@PostMapping("/update")
	public ResponseEntity<String> update(
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "type", required = true) String type,
			@RequestBody String payload) throws BadSessionException, InvalidDataException, IOException {

		sessionService.validateSession(token);
		sessionService.canSessionWrite(token);

		User owner = sessionService.getSessionOwner(token);

		try {
			JsonNode dataJsonNode = mapper.readTree(payload);
			DataType dataType = DataType.valueOf(type.toUpperCase());

			dataBlockService.UpdateData(dataJsonNode, dataType, owner.getId().toHexString());

			return responseService.createJsonResponse(
					new GenericResponseDto(mapper, String.format("Data updated for [%s]", dataType.name()), HttpStatus.OK));

		} catch (JsonProcessingException e) {
			return responseService
					.createJsonResponse(new InvalidPayloadResponseDto(mapper, "Payload needs to be a valid json object"));
		} catch (IllegalArgumentException e) {
			throw new InvalidDataException(String.format("The provided token is invalid %s", token),
					HttpStatus.BAD_REQUEST);
		}
	}

	/**
	 * Handles HTTP GET requests to reset data.
	 * 
	 * @param token The session token for authentication. Required.
	 * @param type  The type of data to be reset. Required.
	 * @return A JSON response indicating the result of the reset operation.
	 * @throws BadSessionException  If the session is invalid or expired.
	 * @throws InvalidDataException If the provided data type is invalid.
	 */
	@GetMapping("/reset")
	public ResponseEntity<String> reset(
			@RequestParam(value = "token", required = true) String token,
			@RequestParam(value = "type", required = true) String type) throws BadSessionException, InvalidDataException {

		sessionService.validateSession(token);
		sessionService.canSessionWrite(token);

		try {
			DataType dataType = DataType.valueOf(type.toUpperCase());
			User owner = sessionService.getSessionOwner(token);
			dataBlockService.resetData(owner.getId().toHexString(), dataType);

			return responseService.createJsonResponse(
					new GenericResponseDto(mapper, String.format("Data Reseted for [%s]", dataType.name()), HttpStatus.OK));

		} catch (IllegalArgumentException exc) {
			throw new InvalidDataException(String.format("Invalid type param [%s]", type),
					HttpStatus.BAD_REQUEST);
		}
	}
}
