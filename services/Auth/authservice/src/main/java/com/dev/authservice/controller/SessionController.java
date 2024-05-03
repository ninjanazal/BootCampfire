package com.dev.authservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dev.authservice.entity.Session;
import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.BadSessionException;
import com.dev.authservice.exeptions.types.RequestMissMatchExeption;
import com.dev.authservice.middleware.inc.session.LoginDto;
import com.dev.authservice.middleware.out.RenponseHandlerService;
import com.dev.authservice.middleware.out.data.responses.LoginUserResponseDto;
import com.dev.authservice.service.session.ISessionService;
import com.dev.authservice.service.user.IUserService;
import com.dev.authservice.tools.DataValidations;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.security.auth.message.AuthException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@RestController
@RequestMapping("/api/session")
public class SessionController {
	@Autowired
	private ObjectMapper mapper;
	/**
	 * This field is injected using the `@Autowired` annotation (assuming Spring
	 * framework).
	 * The `RenponseHandlerService` likely provides utility methods for building
	 * response objects.
	 */
	@Autowired
	private RenponseHandlerService responseService;

	@Autowired
	private ISessionService sessionService;

	@Autowired
	private IUserService userService;

	@PostMapping("/login")
	public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult,
			HttpServletRequest httpServletRequest) throws RequestMissMatchExeption, AuthException, BadSessionException {

		DataValidations.ProcessBindingResults(bindingResult, "Invalid request body @{/api/session/login}");
		User lggedUser = userService.loginUser(loginDto.getEmail(), loginDto.getPassword());

		Session result = sessionService.createSessionForUser(lggedUser.getId().toString(), loginDto.getSessionType(),
				httpServletRequest.getRemoteAddr());

		return responseService.createJsonResponse(
				new LoginUserResponseDto(mapper, String.format("Session created for %s", lggedUser.getName()),
						result.getId().toString(), result.getExpirationDate()));
	}
}
