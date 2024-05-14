package com.dev.server.exeptions.types;

import lombok.Getter;

@Getter
public class InvalidSessionTypeException extends Exception {
	private String message;

	public InvalidSessionTypeException(String msg) {
		super(msg);
		message = msg;
	}
}
