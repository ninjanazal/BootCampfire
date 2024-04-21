package com.basedeveloper.jellylinkserver.account.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.basedeveloper.jellylinkserver.account.dto.ChangePwdDto;
import com.basedeveloper.jellylinkserver.account.dto.CreateUserDto;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.account.service.session.SessionService;
import com.basedeveloper.jellylinkserver.account.service.user.UserService;
import com.basedeveloper.jellylinkserver.account.tools.ValidationTools;
import com.basedeveloper.jellylinkserver.exceptions.types.AuthException;
import com.basedeveloper.jellylinkserver.exceptions.types.CreationException;
import com.basedeveloper.jellylinkserver.exceptions.types.SearchException;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/account")
public class AccountController {

	@Autowired
	private UserService userService;

	@Autowired
	private SessionService sessionService;

	@Autowired
	private ObjectMapper mapper;

	@PostMapping("/regist")
	public ResponseEntity<String> regist(@Valid @RequestBody CreateUserDto usr, BindingResult bindingResult)
			throws JsonProcessingException, AuthException, CreationException {

		ObjectNode json = mapper.createObjectNode();

		if (bindingResult.hasErrors()) {
			return ValidationTools.ProcessValidationErrorResponse(bindingResult, mapper);
		}

		User registed = userService.registerUser(usr);

		json.put("message", "Created user");
		ObjectNode userNode = mapper.createObjectNode();
		userNode.put("name", registed.getName());
		userNode.put("email", registed.getEmail());
		json.set("data", userNode);

		return new ResponseEntity<String>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json),
				HttpStatus.CREATED);
	}

	@PostMapping("/changepassword")
	public ResponseEntity<String> changePassword(@Valid @RequestBody ChangePwdDto changePwdDto,
			HttpServletRequest httpServletRequest,
			BindingResult bindingResult) throws JsonProcessingException, SearchException, AuthException {

		ObjectNode json = mapper.createObjectNode();

		if (bindingResult.hasErrors()) {
			return ValidationTools.ProcessValidationErrorResponse(bindingResult, mapper);
		}

		sessionService.checkIfSessionIsValid(changePwdDto.getToken(), httpServletRequest.getRemoteAddr());
		User foundUser = sessionService.getSessionOwner(changePwdDto.getToken());

		userService.changeUserPassword(changePwdDto, foundUser);

		json.put("message", "Changed password for " + foundUser.getEmail());

		return new ResponseEntity<String>(mapper.writerWithDefaultPrettyPrinter().writeValueAsString(json),
				HttpStatus.ACCEPTED);
	}
}
