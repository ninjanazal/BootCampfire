package com.dev.authservice.middleware.out;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dev.authservice.middleware.out.data.ResponseDto;

/**
 * This class is a Spring `@Service` bean that provides utility methods for
 * creating HTTP response entities.
 * It's used in conjunction with controllers to build and return responses to
 * client requests.
 */
@Service
public class RenponseHandlerService {
	/**
	 * This method creates a `ResponseEntity<String>` object based on a provided
	 * `ResponseDto` object.
	 * 
	 * @param responseDto The `ResponseDto` object containing the response data and
	 *                    status code.
	 * @return A `ResponseEntity<String>` object representing the HTTP response.
	 */
	public ResponseEntity<String> createJsonResponse(ResponseDto responseDto) {
		String jsonData = responseDto.toJsonData();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<String> result = new ResponseEntity<String>(jsonData, headers, responseDto.getCode());

		return result;
	}
}
