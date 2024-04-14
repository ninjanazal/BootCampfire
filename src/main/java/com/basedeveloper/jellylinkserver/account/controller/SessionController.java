package com.basedeveloper.jellylinkserver.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.basedeveloper.jellylinkserver.account.dto.LoginDto;
import com.basedeveloper.jellylinkserver.account.entity.Session;
import com.basedeveloper.jellylinkserver.account.entity.SessionType;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.account.service.session.SessionService;
import com.basedeveloper.jellylinkserver.account.service.user.UserService;
import com.basedeveloper.jellylinkserver.account.tools.DateTimeTools;
import com.basedeveloper.jellylinkserver.account.tools.ValidationTools;
import com.basedeveloper.jellylinkserver.exceptions.SearchException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/session")
public class SessionController {
	@Autowired
	UserService userService;

	@Autowired
	SessionService sessionService;

	@PostMapping("/login")
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
			sessionData.put("token", createdSession.getId());
			sessionData.put("expiration_date", DateTimeTools.FormatDateToString(createdSession.getExpirationDate()));

			json.set("session", sessionData);

		} catch (Exception e) {
			json.put("message", e.getMessage());
			return new ResponseEntity<String>(mapper.writeValueAsString(json), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json),
				HttpStatus.OK);
	}

	@GetMapping("/logout")
	public ResponseEntity<Void> logout(@RequestParam(name = "token", required = true) String token) {
		if (token == null || token.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		try {
			sessionService.closeSessionByToken(token);
			return new ResponseEntity<>(HttpStatus.NO_CONTENT);

		} catch (SearchException e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}

	@GetMapping("/validate")
	public ResponseEntity<Void> validate(@RequestParam(name = "token", required = true) String token, HttpServletRequest httpServletRequest) {
		if (token == null || token.isEmpty()) {
			return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
		}

		try {
			boolean isvalidTime = sessionService.checkIfSessionIsValid(token, httpServletRequest.getRemoteAddr());
			HttpStatus responseCode = HttpStatus.OK;
			if (!isvalidTime) {
				responseCode = HttpStatus.UNAUTHORIZED;
				sessionService.closeSessionByToken(token);
			}
			return new ResponseEntity<>(responseCode);

		} catch (Exception e) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
	}
}
