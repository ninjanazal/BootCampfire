package com.dev.server;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;

import com.dev.server.security.SecurityService;

/**
 * This class contains unit tests for the SecurityService class, specifically
 * for the method AnonymizeIpAddress. The tests cover anonymization of both
 * IPv4 and IPv6 addresses, as well as handling of null, empty, and invalid
 * IP address formats.
 */
public class SecurityServiceTests {

	@InjectMocks
	private SecurityService service;

	/**
	 * Sets up the test environment before each test method execution.
	 * Initializes mocks annotated with @Mock.
	 */
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	/**
	 * Tests the anonymization of a valid IPv4 address.
	 * The last octet should be removed.
	 */
	@Test
	public void testAnonymizeIpAddressIPV4() {
		String ip = "192.168.1.1";
		String anonymizedIp = service.AnonymizeIpAddress(ip);
		assertEquals("192.168.1", anonymizedIp);
	}

	/**
	 * Tests the anonymization of a valid IPv6 address.
	 * The last segment should be removed.
	 */
	@Test
	public void testAnonymizeIpAddressIPV6() {
		String ip = "2001:0db8:85a3:0000:0000:8a2e:0370:7334";
		String anonymizedIp = service.AnonymizeIpAddress(ip);
		assertEquals("2001:0db8:85a3:0000:0000:8a2e:0370", anonymizedIp);
	}

	/**
	 * Tests the handling of null and empty IP address strings.
	 * The method should return null for both cases.
	 */
	@Test
	public void testAnonymizeNullOrEmpty() {
		String ip = null;
		String anonymizedIp = service.AnonymizeIpAddress(ip);
		assertNull(anonymizedIp);

		ip = "";
		anonymizedIp = service.AnonymizeIpAddress(ip);
		assertNull(anonymizedIp);
	}

	/**
	 * Tests the handling of an invalid IP address format.
	 * The method should return the original invalid address.
	 */
	@Test
	public void testAnonymizeInvalidFormat() {
		String ip = "invalid_address";
		String anonymizedIp = service.AnonymizeIpAddress(ip);
		assertEquals(ip, anonymizedIp);
	}
}
