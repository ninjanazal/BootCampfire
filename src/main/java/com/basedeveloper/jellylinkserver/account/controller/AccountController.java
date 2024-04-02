package com.basedeveloper.jellylinkserver.account.controller;

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
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	UserService userService;

	@PostMapping("regist")
	public ResponseEntity<String> regist(@RequestBody UserDto usr) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();

		try {
			User registed = userService.registerUser(usr);
			json.put("message", "Create user");

			ObjectNode userNode = mapper.createObjectNode();
			userNode.put("name", registed.getName());
			userNode.put("token", registed.getUserToken());
			userNode.put("email", registed.getEmail());

			json.set("user", userNode);

		} catch (Exception e) {
			json.put("message", e.getMessage());
			return new ResponseEntity<String>(mapper.writeValueAsString(json), HttpStatus.BAD_REQUEST);
		}

		return new ResponseEntity<String>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json),
				HttpStatus.CREATED);
	}
}
