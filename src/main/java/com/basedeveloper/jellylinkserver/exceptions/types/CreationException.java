package com.basedeveloper.jellylinkserver.exceptions.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.EXPECTATION_FAILED)
public class CreationException extends Exception {
	public CreationException(String mesage) {
		super(mesage);
	}
}
