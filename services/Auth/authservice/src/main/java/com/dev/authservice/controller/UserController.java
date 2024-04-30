package com.dev.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.authservice.exeptions.types.RequestMissMatchExeption;
import com.dev.authservice.middleware.inc.data.CreateUserdto;
import com.dev.authservice.middleware.out.RenponseHandlerService;
import com.dev.authservice.middleware.out.data.GenericResponseDto;
import com.dev.authservice.service.IUserService;
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

	@PostMapping("/regist")
	public ResponseEntity<String> regist(@Valid @RequestBody CreateUserdto usr, BindingResult bindingResult)
			throws RequestMissMatchExeption, AuthException {

		DataValidations.ProcessBindingResults(bindingResult, "Invalid request body @{/api/auth/regist}");
		userService.registerUser(usr);

		return responseService.createJsonResponse(new GenericResponseDto(mapper, "User registed", HttpStatus.CREATED));
	}

}
