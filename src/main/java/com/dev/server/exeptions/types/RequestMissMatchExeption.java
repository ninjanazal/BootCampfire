package com.dev.server.exeptions.types;

import org.springframework.validation.BindingResult;

import lombok.Getter;

/**
 * This class defines a custom exception named `RequestMissMatchExeption`.
 * It extends the built-in `Exception` class and is specifically designed to
 * handle
 * situations where there's a mismatch between the expected request format and
 * the actual request received by the application.
 */
@Getter
public class RequestMissMatchExeption extends Exception {
	/**
	 * It contains details about any validation errors encountered during request
	 * processing.
	 */
	private BindingResult bindingResult;
	/**
	 * This field stores a custom error message associated with the request
	 * mismatch.
	 */
	private String message;

	/**
	 * Constructor for `RequestMissMatchExeption`.
	 * 
	 * @param bindingResult The `BindingResult` object containing validation errors
	 *                      (if any).
	 * @param msg           The custom error message describing the request
	 *                      mismatch.
	 */
	public RequestMissMatchExeption(BindingResult bResult, String msg) {
		super(msg);

		bindingResult = bResult;
		message = msg;
	}
}
