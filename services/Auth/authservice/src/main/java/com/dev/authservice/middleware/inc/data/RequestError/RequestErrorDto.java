package com.dev.authservice.middleware.inc.data.RequestError;

import org.springframework.http.HttpStatus;

import com.dev.authservice.middleware.out.data.ResponseDto;

public class RequestErrorDto implements ResponseDto{

	@Override
	public String toJsonData() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'toJsonData'");
	}

	@Override
	public HttpStatus getCode() {
		return HttpStatus.BAD_REQUEST;
	}
 
}
