package com.dev.authservice.controller;

import org.springframework.web.bind.annotation.RestController;

import com.dev.authservice.entity.Session;
import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.BadSessionException;
import com.dev.authservice.exeptions.types.InvalidDataException;
import com.dev.authservice.exeptions.types.RequestMissMatchExeption;
import com.dev.authservice.middleware.inc.session.LoginDto;
import com.dev.authservice.middleware.out.RenponseHandlerService;
import com.dev.authservice.middleware.out.data.responses.LoginUserResponseDto;
import com.dev.authservice.middleware.out.data.responses.LogoutUserResponseDto;
import com.dev.authservice.middleware.out.data.responses.ValidationSesisonResponseDto;
import com.dev.authservice.service.session.ISessionService;
import com.dev.authservice.service.user.IUserService;
import com.dev.authservice.tools.DataValidations;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
	public ResponseEntity<String> login(@Valid @RequestBody LoginDto loginDto, BindingResult bindingResult)
			throws RequestMissMatchExeption, AuthException, BadSessionException {

		DataValidations.ProcessBindingResults(bindingResult, "Invalid request body @{/api/session/login}");

		User lggedUser = userService.loginUser(loginDto.getEmail(), loginDto.getPassword());
		Session result = sessionService.createSessionForUser(lggedUser.getId().toString(), loginDto.getSessionType());

		return responseService.createJsonResponse(new LoginUserResponseDto(mapper, lggedUser, result));
	}

	/**
	 * This method handles user logout requests received through a GET request on
	 * the "/logout" endpoint.
	 * It expects a valid `LogoutDto` object containing the user's logout token as a
	 * query parameter.
	 * It throws exceptions for various error conditions during logout processing.
	 *
	 * @param logoutDto     A `LogoutDto` object containing the logout token (query
	 *                      parameter).
	 * @param bindingResult A `BindingResult` object used for parameter validation.
	 * @return A `ResponseEntity<String>` containing a JSON response with logout
	 *         status and message.
	 * @throws RequestMissMatchExeption If the request method (GET) doesn't match
	 *                                  the expected method.
	 * @throws InvalidDataException     If the provided logout token is invalid.
	 * @throws BadSessionException      If an error occurs while closing the user's
	 *                                  session.
	 */
	@GetMapping("/logout")
	public ResponseEntity<String> logout(
			@RequestParam(value = "token", required = true) String token)
			throws RequestMissMatchExeption, InvalidDataException, BadSessionException {

		Session lSession = sessionService.getSessionByToken(token);
		User loutUser = userService.getUserByToken(lSession.getOwnerUserId());
		sessionService.closeSessionByToken(lSession.getId().toHexString());

		return responseService.createJsonResponse(
				new LogoutUserResponseDto(mapper, "Logout succecefully", loutUser.getName(), lSession.getType()));
	}

	/**
	 * This method handles session validation requests received through a GET
	 * request on the "/validate" endpoint.
	 * It expects a valid token as a required query parameter named "token".
	 * It throws a `BadSessionException` if an error occurs during session
	 * retrieval.
	 *
	 * @param token The session token as a String retrieved from the "token" query
	 *              parameter.
	 * @return A `ResponseEntity<String>` containing a JSON response indicating
	 *         session validity and status code.
	 * @throws BadSessionException If an error occurs while retrieving the session
	 *                             using the provided token.
	 */
	@GetMapping("/validate")
	public ResponseEntity<String> validateSession(
			@RequestParam(value = "token", required = true) String token)
			throws BadSessionException {

		Session lSession = sessionService.getSessionByToken(token);
		boolean isValid = sessionService.isSessionValid(lSession);

		ValidationSesisonResponseDto sesisonResponseDto = new ValidationSesisonResponseDto(
				mapper, isValid,
				isValid ? HttpStatus.OK : HttpStatus.NOT_ACCEPTABLE);

		if (!isValid) {
			sessionService.closeSessionByToken(lSession.getId().toHexString());
		}
		return responseService.createJsonResponse(sesisonResponseDto);
	}
}
