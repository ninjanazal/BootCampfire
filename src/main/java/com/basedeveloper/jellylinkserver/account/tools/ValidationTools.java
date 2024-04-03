package com.basedeveloper.jellylinkserver.account.tools;

import java.util.regex.*;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

public final class ValidationTools {
	public static boolean ValidEmail(String email) {
		if (email == null || email.isEmpty()) {
			return false;
		}

		String emailRegex = "^[\\w!#$%&'*+/=?^`{|}~-]+(?:\\.[\\w!#$%&'*+/=?^`{|}~-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,}$";
		Pattern pattern = Pattern.compile(emailRegex);
		return pattern.matcher(email).matches();
	}

	
	public static ResponseEntity<String> ProcessValidationErrorResponse(BindingResult bindingResult)
			throws JsonProcessingException {

		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();

		try {
			return new ResponseEntity<String>(createValidationErrorResponse(bindingResult),
					HttpStatus.BAD_REQUEST);
		} catch (Exception e) {
			json.put("message", e.getMessage());
			return new ResponseEntity<String>(mapper.writeValueAsString(json), HttpStatus.BAD_REQUEST);
		}
	}


	private static String createValidationErrorResponse(BindingResult bindingResult) throws JsonProcessingException {
		ObjectMapper mapper = new ObjectMapper();
		ObjectNode json = mapper.createObjectNode();

		json.put("message", "Validation errors");
		ArrayNode errors = mapper.createArrayNode();

		bindingResult.getFieldErrors().forEach(error -> {
			ObjectNode errorNode = mapper.createObjectNode();

			errorNode.put("field", error.getField());
			errorNode.put("message", error.getDefaultMessage());

			errors.add(errorNode);
		});
		json.set("errors", errors);
		return mapper.writeValueAsString(json);
	}

}
