package com.basedeveloper.jellylinkserver.exceptions.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.UNAUTHORIZED)
public class AuthException extends Exception {
	public AuthException(String message) {
		super(message);
	}
}
