package com.dev.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import com.dev.server.security.SecurityService;

@SpringBootTest
public class SecurityServiceTests {

	private SecurityService service = new SecurityService();

	@Test
	public void testAnonymizeIpAddressIPV4() {
		String ip = "192.168.1.1";
		String anonymizedIp = service.AnonymizeIpAddress(ip);
		assertEquals("192.168.1", anonymizedIp);
	}

	@Test
	public void testAnonymizeIpAddressIPV6() {
		String ip = "2001:0db8:85a3:0000:0000:8a2e:0370:7334";
		String anonymizedIp = service.AnonymizeIpAddress(ip);
		assertEquals("2001:0db8:85a3:0000:0000:8a2e:0370", anonymizedIp);
	}

	@Test
	public void testAnonymizeNullOrEmpty() {
		String ip = null;
		String anonymizedIp = service.AnonymizeIpAddress(ip);
		assertNull(anonymizedIp);

		ip = "";
		anonymizedIp = service.AnonymizeIpAddress(ip);
		assertNull(anonymizedIp);
	}

	@Test
	public void testAnonymizeInvalidFormat() {
		String ip = "invalid_address";
		String anonymizedIp = service.AnonymizeIpAddress(ip);
		assertEquals(ip, anonymizedIp); // Should return original for invalid format
	}
}
