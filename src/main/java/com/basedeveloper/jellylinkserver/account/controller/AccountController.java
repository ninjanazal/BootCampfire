package com.basedeveloper.jellylinkserver.account.controller;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basedeveloper.jellylinkserver.account.controller.DataTransferObj.UserDto;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.account.service.UserService;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	UserService userService;

	@PostMapping("regist")
	public ResponseEntity<Map<String, String>> regist(@RequestBody UserDto usr) {
		Map<String,String> responseData = new HashMap<>();
		responseData.put("message", "Created user");
		try {
			System.out.println(usr);
			//User createdUser = userService.registerUser(usr);
			return new ResponseEntity<>(responseData, HttpStatus.CREATED);

		} catch (Exception e) {
			responseData.put("message", "Failed to create");
			return new ResponseEntity<>(responseData, HttpStatus.BAD_REQUEST);
		}

	}
}
