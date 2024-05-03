package com.dev.authservice.exeptions.types;

import org.springframework.http.HttpStatus;

import lombok.Getter;

@Getter
public class InvalidDataException extends Exception {
	/**
	 * This field stores a custom error message associated with the request
	 * mismatch.
	 */
	private String message;

	private HttpStatus httpStatus;

	public InvalidDataException(String msg, HttpStatus status){
		super(msg);

		this.message = msg;
		this.httpStatus = status;
	}
}
