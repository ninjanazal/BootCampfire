package com.dev.authservice.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.dev.authservice.entity.User;
import com.dev.authservice.middleware.inc.data.CreateUserdto;
import com.dev.authservice.service.UserService;
import com.fasterxml.jackson.core.JsonProcessingException;

import jakarta.security.auth.message.AuthException;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/auth")
public class UserController {
	// @Autowired
	// private UserService userService;

	@PostMapping("/regist")
	public ResponseEntity<String> regist(@Valid @RequestBody CreateUserdto usr, BindingResult bindingResult)
			throws JsonProcessingException, AuthException {

		// User result = userService.registerUser(usr);
		return new ResponseEntity<>("", HttpStatus.OK);
	}

}
