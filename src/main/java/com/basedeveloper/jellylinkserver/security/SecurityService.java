package com.basedeveloper.jellylinkserver.security;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService {
	private final PasswordEncoder passwordEncoder;

	public SecurityService(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	public String EncodeData(String data) {
		return passwordEncoder.encode(data);
	}
}
