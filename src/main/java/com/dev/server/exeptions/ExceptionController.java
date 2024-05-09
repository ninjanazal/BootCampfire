package com.dev.server.exeptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dev.server.exeptions.types.BadSessionException;
import com.dev.server.exeptions.types.InvalidDataException;
import com.dev.server.exeptions.types.RequestMissMatchExeption;
import com.dev.server.middleware.out.RenponseHandlerService;
import com.dev.server.middleware.out.data.exeptions.RequestMissMatchResponseDto;
import com.dev.server.middleware.out.data.responses.GenericResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * This class is a Spring MVC `@ControllerAdvice` that provides centralized
 * exception handling
 * for the application. It extends `ResponseEntityExceptionHandler` to leverage
 * built-in
 * functionalities for handling exceptions that result in HTTP response
 * entities.
 */
@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
	/**
	 * This field is injected using the `@Autowired` annotation (assuming Spring
	 * framework).
	 * The `RenponseHandlerService` likely provides utility methods for building
	 * response objects.
	 */
	@Autowired
	private RenponseHandlerService responseService;

	@Autowired
	private ObjectMapper mapper;

	/**
	 * This method serves as a generic exception handler for any `Exception` thrown
	 * by the application.
	 * It catches all exceptions and returns a formatted error response with an
	 * internal server error status code (500).
	 *
	 * @param ex The `Exception` object that was thrown.
	 * @return A `ResponseEntity<String>` containing a generic error message and
	 *         internal server error status code.
	 */
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {

		return responseService.createJsonResponse(
				new GenericResponseDto(mapper, "An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
	}

	/**
	 * This method handles the `RequestMissMatchExeption` specifically.
	 * It extracts details about the request mismatch from the exception object and
	 * returns a more informative error response.
	 * 
	 * @param exeption The `RequestMissMatchExeption` object containing details
	 *                 about the request mismatch.
	 * @return A `ResponseEntity<String>` containing details about the validation
	 *         errors and a custom message.
	 */
	@ExceptionHandler(RequestMissMatchExeption.class)
	public ResponseEntity<String> handleRequestMissMatch(RequestMissMatchExeption exeption) {
		return responseService.createJsonResponse(
				new RequestMissMatchResponseDto(mapper, exeption.getBindingResult().getFieldErrors(),
						exeption.getMessage()));
	}

	/**
	 * Handles InvalidDataException, which occurs when there's an issue with the
	 * data provided in the request.
	 * 
	 * @param exception The InvalidDataException that was thrown.
	 * @return A ResponseEntity containing a JSON error response with the exception
	 *         message and HTTP status code.
	 */
	@ExceptionHandler(InvalidDataException.class)
	public ResponseEntity<String> handleInvalidDataException(InvalidDataException exception) {
		return responseService.createJsonResponse(
				new GenericResponseDto(mapper, exception.getMessage(), exception.getHttpStatus()));
	}

	/**
	 * Handles BadSessionException, which occurs when there's an issue with the
	 * user's session (e.g., expired or invalid).
	 * 
	 * @param exception The BadSessionException that was thrown.
	 * @return A ResponseEntity containing a JSON error response with the exception
	 *         message and HTTP status code.
	 */
	@ExceptionHandler(BadSessionException.class)
	public ResponseEntity<String> handleBadSessionExeption(BadSessionException exception) {
		return responseService.createJsonResponse(
				new GenericResponseDto(mapper, exception.getMessage(), exception.getHttpStatus()));
	}

}
