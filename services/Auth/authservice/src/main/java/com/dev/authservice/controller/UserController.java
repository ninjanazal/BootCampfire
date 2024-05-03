package com.dev.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.InvalidDataException;
import com.dev.authservice.exeptions.types.RequestMissMatchExeption;
import com.dev.authservice.middleware.inc.account.CreateUserDto;
import com.dev.authservice.middleware.out.RenponseHandlerService;
import com.dev.authservice.middleware.out.data.responses.RegistUserResponseDto;
import com.dev.authservice.service.user.IUserService;
import com.dev.authservice.tools.DataValidations;
import com.fasterxml.jackson.databind.ObjectMapper;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {
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
	private IUserService userService;

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

	// @PostMapping("/changepwd")
	// public ResponseEntity<String> postMethodName(@Valid @RequestBody ChangePwdDto dto, BindingResult bindingResult)
	// 		throws RequestMissMatchExeption {

	// 	DataValidations.ProcessBindingResults(bindingResult, "Invalid request body @{/api/auth/changepwd}");

	// 	return entity;
	// }

}
