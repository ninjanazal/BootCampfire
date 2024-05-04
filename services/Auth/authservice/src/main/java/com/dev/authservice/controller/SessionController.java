package com.dev.authservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dev.authservice.entity.Session;
import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.BadSessionException;
import com.dev.authservice.exeptions.types.RequestMissMatchExeption;
import com.dev.authservice.middleware.inc.session.LoginDto;
import com.dev.authservice.middleware.inc.session.LogoutDto;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@RestController
@RequestMapping("/api/session")
public class SessionController {
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private RenponseHandlerService responseService;

	@Autowired
	private ISessionService sessionService;

	@Autowired
	private IUserService userService;

	/**
	 * This method handles POST requests to the "/login" endpoint, likely used for
	 * user login.
	 *
	 * @param loginDto           A `@Valid` and `@RequestBody` `LoginDto` object
	 *                           containing login credentials.
	 * @param bindingResult      A `BindingResult` object containing any validation
	 *                           errors from `@Valid`.
	 * @param httpServletRequest The `HttpServletRequest` object providing access to
	 *                           request details.
	 * @return A `ResponseEntity<String>` object representing the HTTP response to
	 *         the login request.
	 * @throws RequestMissMatchExeption If there's a request method mismatch (likely
	 *                                  expecting POST).
	 * @throws AuthException            If user login fails due to authentication
	 *                                  issues (e.g., invalid credentials).
	 * @throws BadSessionException      If an error occurs during session creation.
	 */
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

	@GetMapping("/logout")
	public ResponseEntity<String> logout(@Valid @RequestParam LogoutDto logoutDto, BindingResult bindingResult)
			throws RequestMissMatchExeption {
		DataValidations.ProcessBindingResults(bindingResult, "Invalid querry params @{/api/session/logout}");
		
	}
}
