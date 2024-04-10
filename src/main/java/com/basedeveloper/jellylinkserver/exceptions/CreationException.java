package com.basedeveloper.jellylinkserver.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class CreationException extends RuntimeException {
	public CreationException(String mesage) {
		super(mesage);
	}
}
