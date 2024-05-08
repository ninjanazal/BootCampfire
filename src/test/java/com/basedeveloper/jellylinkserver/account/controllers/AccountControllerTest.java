package com.basedeveloper.jellylinkserver.account.controllers;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.basedeveloper.jellylinkserver.account.service.session.SessionService;
import com.basedeveloper.jellylinkserver.account.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@ExtendWith(SpringExtension.class)
@SpringBootTest
public class AccountControllerTest {
	@MockBean
	private UserService userService;
	@MockBean
	private SessionService sessionService;

	@Autowired
	private ObjectMapper mapper;

	// @Test
	public void testRegistEndpoint() throws Exception {
	}
}
