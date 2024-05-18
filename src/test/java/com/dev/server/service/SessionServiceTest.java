package com.dev.server.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;

import com.dev.server.constants.session.SessionType;
import com.dev.server.entity.Session;
import com.dev.server.exeptions.types.BadSessionException;
import com.dev.server.repository.ISessionRepository;
import com.dev.server.service.session.SessionService;
import com.dev.server.service.user.UserService;

public class SessionServiceTest {
	@InjectMocks
	private SessionService sessionService;

	@InjectMocks
	private UserService userService;

	@Mock
	private ISessionRepository sessionRepository;

	@Mock
	private CacheManager cacheManager;

	/**
	 * Sets up the test environment before each test method execution.
	 * Initializes mocks annotated with @Mock.
	 */
	@BeforeEach
	public void setup() {
		MockitoAnnotations.openMocks(this);
	}

	@AfterEach
	public void resetAfter() {
		reset(cacheManager, sessionRepository);
	}

	@Test
	public void testCreateSessionForUser() throws BadSessionException {
		String userId = "user123";
		String sessionType = "READ";

		when(sessionRepository.findByOwnerUserId(userId)).thenReturn(new ArrayList<>());
		// Mock dependencies
		Cache mockSCache = Mockito.mock(Cache.class);
		Cache mockUCache = Mockito.mock(Cache.class);

		when(cacheManager.getCache("sessions")).thenReturn(mockSCache);
		when(cacheManager.getCache("users")).thenReturn(mockUCache);

		Session expectedSession = new Session() {
			{
				setId(new ObjectId());
				setType(SessionType.valueOf(sessionType.toUpperCase()));
				setOwnerUserId(userId);
				setExpirationDate(LocalDateTime.parse("2007-12-03T10:15:30"));
			}
		};

		when(sessionRepository.save(any(Session.class))).thenReturn(expectedSession);

		// Call the service method
		Session createdSession = sessionService.createSessionForUser(userId, sessionType);

		// Assertions
		assertNotNull(createdSession);
		assertEquals(expectedSession, createdSession);
		verify(sessionRepository, times(1)).save(any(Session.class));
		verify(mockSCache, times(1)).put(any(String.class), eq(createdSession));
	}

	@Test
	public void testInvalidCreateSessionForUser() throws BadSessionException {
		String userId = "user123";
		String sessionType = "NOTAVALID";

		assertThrows(BadSessionException.class,
				() -> sessionService.createSessionForUser(userId, sessionType));
	}
}
