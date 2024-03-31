package com.basedeveloper.jellylinkserver.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements SecurityServiceInterface {
	@Autowired
	private PasswordEncoder passwordEncoder;

	public String EncodeData(String data) {
		return passwordEncoder.encode(data);
	}
}
