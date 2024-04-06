package com.basedeveloper.jellylinkserver.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basedeveloper.jellylinkserver.account.controller.DataTransferObj.LoginDto;
import com.basedeveloper.jellylinkserver.account.controller.DataTransferObj.UserDto;
import com.basedeveloper.jellylinkserver.account.entity.Session;
import com.basedeveloper.jellylinkserver.account.entity.SessionType;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.account.service.SessionService;
import com.basedeveloper.jellylinkserver.account.service.UserService;
import com.basedeveloper.jellylinkserver.account.tools.DateTimeTools;
import com.basedeveloper.jellylinkserver.account.tools.ValidationTools;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	UserService userService;

	@Autowired
	SessionService sessionService;

	@PostMapping("regist")
	public ResponseEntity<String> regist(@Valid @RequestBody UserDto usr, BindingResult bindingResult)
			throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();

		if (bindingResult.hasErrors()) {
			return ValidationTools.ProcessValidationErrorResponse(bindingResult);
		}

		try {
			User registed = userService.registerUser(usr);
			json.put("message", "Created user");

			ObjectNode userNode = mapper.createObjectNode();
			userNode.put("name", registed.getName());
			userNode.put("email", registed.getEmail());

			json.set("data", userNode);

		} catch (Exception e) {
			json.put("message", e.getMessage());
			return new ResponseEntity<String>(mapper.writeValueAsString(json), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json),
				HttpStatus.CREATED);
	}

	@PostMapping("login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult,
			HttpServletRequest httpServletRequest)
			throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();

		if (bindingResult.hasErrors()) {
			return ValidationTools.ProcessValidationErrorResponse(bindingResult);
		}

		try {
			User validUser = userService.loginUser(loginDto);
			SessionType sessionType = sessionService.getSessionTypeByDescription(loginDto.getSessionType());
			Session createdSession = sessionService.createSessionForUser(validUser, sessionType,
					httpServletRequest.getRemoteAddr());

			json.put("message", String.format("Created %s session", createdSession.getSessionType().getDescription()));
			ObjectNode sessionData = mapper.createObjectNode();
			sessionData.put("token", createdSession.getSessionToken());
			sessionData.put("expiration_date", DateTimeTools.FormatDateToString(createdSession.getExpirationDate()));

			json.set("session", sessionData);

		} catch (Exception e) {
			json.put("message", e.getMessage());
			return new ResponseEntity<String>(mapper.writeValueAsString(json), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json),
				HttpStatus.OK);
	}
}
