package com.dev.authservice.middleware.out.data.responses;

import org.springframework.http.HttpStatus;

import com.dev.authservice.middleware.out.data.ResponseDto;
import com.fasterxml.jackson.databind.ObjectMapper;

public class LogoutUserResponseDto implements ResponseDto {

	private final ObjectMapper mapper;

	private String message;

	private String userName;

	public LogoutUserResponseDto(ObjectMapper mapper, String msg, String usrName) {
		this.mapper = mapper;
		this.message = msg;
		this.userName = usrName;
	}

	@Override
	public String toJsonData() {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'toJsonData'");
	}

	@Override
	public HttpStatus getCode() {
		return HttpStatus.OK;
	}

}
