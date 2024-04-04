package com.basedeveloper.jellylinkserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements SecurityServiceInterface {
	@Autowired
	private PasswordEncoder passwordEncoder;

	@Override
	public String EncodeData(String data) {
		return passwordEncoder.encode(data);
	}

	@Override
	public boolean ValidateData(String rawData, String hashedData) {
		return passwordEncoder.matches(rawData, hashedData);
	}
}
