package com.dev.authservice.exeptions.types;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class BadSessionException extends Exception {
	/**
	 * This field stores a custom error message associated with the request
	 * mismatch.
	 */
	private String message;

	private HttpStatus httpStatus;

	/**
	 * Constructor for `SessionException`.
	 * 
	 * @param bindingResult The `BindingResult` object containing validation errors
	 *                      (if any).
	 * @param msg           The custom error message describing the request
	 *                      mismatch.
	 */
	public BadSessionException(String msg, HttpStatus status) {
		super(msg);

		message = msg;
		httpStatus = status;
	}
}
