package com.dev.server.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.server.entity.Session;
import com.dev.server.entity.User;
import com.dev.server.exeptions.types.BadSessionException;
import com.dev.server.exeptions.types.InvalidDataException;
import com.dev.server.exeptions.types.RequestMissMatchExeption;
import com.dev.server.middleware.inc.account.ChangePwdDto;
import com.dev.server.middleware.inc.account.CreateUserDto;
import com.dev.server.middleware.out.RenponseHandlerService;
import com.dev.server.middleware.out.data.responses.ChangePwdResponseDto;
import com.dev.server.middleware.out.data.responses.RegistUserResponseDto;
import com.dev.server.service.session.ISessionService;
import com.dev.server.service.user.IUserService;
import com.dev.server.tools.DataValidations;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	@Autowired
	private ObjectMapper mapper;

	@Autowired
	private RenponseHandlerService responseService;

	@Autowired
	private IUserService userService;

	@Autowired
	private ISessionService sessionService;

	/**
	 * This method handles POST requests to the "/regist" endpoint. It's likely used
	 * for user registration.
	 *
	 * @param usr           A `@Valid` and `@RequestBody` `CreateUserdto` object
	 *                      containing user registration data.
	 * @param bindingResult A `BindingResult` object used to capture any validation
	 *                      errors from the `@Valid` annotation.
	 * @return A `ResponseEntity<String>` object representing the HTTP response to
	 *         the registration request.
	 * @throws RequestMissMatchExeption If there's a request method mismatch (likely
	 *                                  expecting POST).
	 * @throws AuthException            If user registration fails due to
	 *                                  authentication-related issues.
	 */
	@PostMapping("/regist")
	public ResponseEntity<String> regist(@Valid @RequestBody CreateUserDto dto, BindingResult bindingResult)
			throws RequestMissMatchExeption, InvalidDataException {

		DataValidations.ProcessBindingResults(bindingResult, "Invalid request body @{/api/auth/regist}");
		User value = userService.registerUser(dto);

		return responseService.createJsonResponse(new RegistUserResponseDto(mapper, value));
	}

	/**
	 * This method handles password change requests received through a POST request
	 * on the "/changepassword" endpoint.
	 * It expects a valid `ChangePwdDto` object containing password change details
	 * in the request body.
	 * It throws various exceptions for error conditions during password change
	 * processing.
	 *
	 * @param dto            A `ChangePwdDto` object containing password change
	 *                       details (request body).
	 * @param bindingResult  A `BindingResult` object used for parameter validation.
	 * @param servletRequest The `HttpServletRequest` object (might be used for IP
	 *                       address validation or other request details).
	 * @return A `ResponseEntity<String>` containing a JSON response indicating
	 *         success or failure of the password change operation.
	 * @throws RequestMissMatchExeption If the request method (POST) doesn't match
	 *                                  the expected method.
	 * @throws BadSessionException      If an error occurs while retrieving or
	 *                                  validating the session.
	 * @throws InvalidDataException     If the provided password change data is
	 *                                  invalid.
	 * @throws AuthException            If an error occurs during user
	 *                                  authentication or password update.
	 */
	@PostMapping("/changepassword")
	public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePwdDto dto, BindingResult bindingResult)
			throws RequestMissMatchExeption, BadSessionException, InvalidDataException, AuthException {

		DataValidations.ProcessBindingResults(bindingResult, "Invalid request body @{/api/auth/regist}");

		Session lSession = sessionService.getSessionByToken(dto.getToken());
		boolean isValid = sessionService.isSessionValid(lSession);

		if (!isValid) {
			sessionService.closeSessionByToken(lSession.getId().toHexString());
			return responseService.createJsonResponse(new ChangePwdResponseDto(mapper, false));
		}

		User fUser = userService.getUserByToken(lSession.getOwnerUserId());
		fUser = userService.changeUserPassword(dto, fUser);

		return responseService.createJsonResponse(new ChangePwdResponseDto(mapper, true));

	}
}
