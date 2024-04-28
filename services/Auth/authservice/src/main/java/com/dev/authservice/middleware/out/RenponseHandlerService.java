package com.dev.authservice.middleware.out;

import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.dev.authservice.middleware.out.data.ResponseDto;

@Service
public class RenponseHandlerService {

	public ResponseEntity<String> createResponse(ResponseDto responseDto) {
		String jsonData = responseDto.toJsonData();

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.APPLICATION_JSON);

		ResponseEntity<String> result = new ResponseEntity<String>(jsonData, headers, responseDto.getCode());

		return result;
	}
}
