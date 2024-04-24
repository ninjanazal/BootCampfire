package com.basedeveloper.jellylinkserver.exceptions.types;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class SearchException extends Exception {
	public SearchException(String message, String what) {
		super(String.format("%s -> %s", message, what));
	}
}