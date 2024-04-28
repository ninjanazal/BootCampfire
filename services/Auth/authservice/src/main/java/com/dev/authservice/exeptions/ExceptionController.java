package com.dev.authservice.exeptions;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.dev.authservice.middleware.out.RenponseHandlerService;
import com.dev.authservice.middleware.out.data.GenericResponseDto;

@ControllerAdvice
public class ExceptionController extends ResponseEntityExceptionHandler {
	@Autowired
	private RenponseHandlerService responseService;

	// Generic Exception handling
	@ExceptionHandler(Exception.class)
	public ResponseEntity<String> handleException(Exception ex) {

		return responseService.createResponse(
				new GenericResponseDto("An error occurred: " + ex.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR));
	}
}
