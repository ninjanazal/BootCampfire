package com.dev.server.exeptions.types;

import org.springframework.http.HttpStatus;

import lombok.Getter;

/**
 * This class represents an exception thrown when invalid data is encountered
 * during processing.
 * It extends the base `Exception` class and provides additional information
 * about the error.
 */
@Getter
public class InvalidDataException extends Exception {
	/**
	 * A custom error message associated with the invalid data encountered.
	 */
	private String message;
	/**
	 * The HTTP status code associated with this exception, suitable for returning
	 * in a response.
	 */
	private HttpStatus httpStatus;

	/**
	 * This constructor creates an `InvalidDataException` instance with a provided
	 * message and HTTP status code.
	 *
	 * @param msg    The custom error message describing the invalid data issue.
	 * @param status The HTTP status code to associate with the exception (e.g.,
	 *               BAD_REQUEST).
	 */
	public InvalidDataException(String msg, HttpStatus status) {
		super(msg);

		this.message = msg;
		this.httpStatus = status;
	}
}
