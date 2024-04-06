package com.basedeveloper.jellylinkserver.security;

import java.util.UUID;

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

	@Override
	public String AnonymizeIpAddress(String address) {
		if (address == null || address.isEmpty()) {
			return address;
		}

		int lastDotIndex = address.lastIndexOf('.'); // IPv4
		int lastColonIndex = address.lastIndexOf(':'); // IPv6

		if (lastDotIndex > 0) {
			// Handle IPv4 addresses
			return address.substring(0, lastDotIndex) + ".[anonymized]";
		} else if (lastColonIndex > 0 && lastColonIndex < address.length() - 1) {
			// Handle IPv6 addresses (assuming at least 8 colon-separated parts)
			return address.substring(0, lastColonIndex) + ":[anonymized]";
		} else {
			// Invalid format, return the original address
			return address;
		}
	}

	@Override
	public String GenerateSessionToken() {
		String uuidString = UUID.randomUUID().toString();
		return uuidString.replaceAll("-", "");
	}
}
