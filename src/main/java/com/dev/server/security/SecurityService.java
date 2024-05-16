package com.dev.server.security;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class SecurityService implements ISecurityService {
	@Autowired
	private PasswordEncoder encoder;

	@Override
	public String EncodeData(String data) {
		return encoder.encode(data);
	}

	@Override
	public boolean ValidateData(String rawData, String hashedData) {
		return encoder.matches(rawData, hashedData);
	}

	@Override
	public String AnonymizeIpAddress(String address) {
		if (address == null || address.isEmpty()) {
			return null;
		}

		int lastDotIndex = address.lastIndexOf('.'); // IPv4
		int lastColonIndex = address.lastIndexOf(':'); // IPv6

		if (lastDotIndex > 0) {
			// Handle IPv4 addresses
			return address.substring(0, lastDotIndex);
		} else if (lastColonIndex > 0 && lastColonIndex < address.length() - 1) {
			// Handle IPv6 addresses (assuming at least 8 colon-separated parts)
			return address.substring(0, lastColonIndex);
		} else {
			// Invalid format, return the original address
			return address;
		}
	}

	@Override
	public boolean ValidateAnonymizedIp(String a, String b) {
		String bAnonymized = AnonymizeIpAddress(b);
		return a.equals(bAnonymized);
	}

	@Override
	public String GenerateSessionToken() {
		String uuidString = UUID.randomUUID().toString();
		return uuidString.replaceAll("-", "");
	}

}
