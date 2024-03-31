package com.basedeveloper.jellylinkserver.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basedeveloper.jellylinkserver.account.model.User;
import com.basedeveloper.jellylinkserver.account.service.UserService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	UserService userService;

	@PostMapping("regist")
	public ResponseEntity<String> regist(@RequestBody User usr) {
		try {
			User createdUser = userService.registerUser(usr);
			return new ResponseEntity<>(String.format("Created user %s", createdUser.getName()), HttpStatus.CREATED);

		} catch (Exception e) {
			return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
		}

	}
}
