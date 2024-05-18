package com.dev.server.service.session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dev.server.constants.session.SessionType;
import com.dev.server.entity.Session;
import com.dev.server.entity.User;
import com.dev.server.exeptions.types.BadSessionException;
import com.dev.server.exeptions.types.InvalidDataException;
import com.dev.server.repository.ISessionRepository;
import com.dev.server.service.user.IUserService;
import com.dev.server.tools.DateTimeActions;

/**
 * This class implements the `ISessionService` interface and provides methods
 * for managing user sessions.
 */
@Service
@EnableCaching
public class SessionService implements ISessionService {

	@Autowired
	private ISessionRepository sessionRepository;

	@Autowired
	private IUserService userService;

	@Autowired
	private CacheManager cacheManager;

	@Override
	public Session createSessionForUser(String userId, String sTypeName) throws BadSessionException {

		Session result = new Session();

		try {
			result.setType(SessionType.valueOf(sTypeName.toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new BadSessionException(String.format("Wrong value for the Session type @ %s", sTypeName),
					HttpStatus.BAD_REQUEST);
		}

		result.setOwnerUserId(userId);
		result.setExpirationDate(DateTimeActions.GenerateExpirationDateFromNow());

		List<Session> userSessions = sessionRepository.findByOwnerUserId(userId);
		Cache sCache = cacheManager.getCache("sessions");
		Cache uCache = cacheManager.getCache("users");

		for (Session s : userSessions) {
			if (s.getType() == result.getType()) {
				if (sCache != null) {
					sCache.evictIfPresent(s.getId().toHexString());
				}
				if(uCache != null) {
					uCache.evictIfPresent(s.getId().toHexString());
				}
				sessionRepository.delete(s);
			}
		}

		result = sessionRepository.save(result);

		if (sCache != null) {
			sCache.put(result.getId().toHexString(), result);
		}
		return result;
	}

	@Override
	@CacheEvict(value = "sessions", key = "#token")
	public void closeSessionByToken(String token) throws BadSessionException {
		Optional<Session> fSession = sessionRepository.findById(new ObjectId(token));
		if (fSession.isPresent()) {
			sessionRepository.delete(fSession.get());

			Cache uCache = cacheManager.getCache("users");
			if (uCache != null) {
				uCache.evictIfPresent(token);
			}

			return;
		}
		throw new BadSessionException("Couldnt close session, token not found", HttpStatus.NOT_FOUND);
	}

	@Override
	@Cacheable(value = "sessions", key = "#token")
	public Session getSessionByToken(String token) throws BadSessionException {
		if (!token.isEmpty()) {
			Optional<Session> fSession = sessionRepository.findById(new ObjectId(token));
			if (fSession.isPresent()) {
				return fSession.get();
			}
		}
		throw new BadSessionException("Not a valid session tokem", HttpStatus.NOT_FOUND);
	}

	@Override
	@Cacheable(value = "users", key = "#token")
	public User getSessionOwner(String token) throws InvalidDataException, BadSessionException {
		if (!token.isEmpty()) {
			Optional<Session> fSession = sessionRepository.findById(new ObjectId(token));
			if (fSession.isPresent()) {
				User result = userService.getUserByToken(fSession.get().getOwnerUserId());
				return result;
			}
		}
		throw new BadSessionException("Not a valid session tokem", HttpStatus.NOT_FOUND);
	}

	@Override
	public boolean isTokenValid(String token) throws BadSessionException {
		Session lSession = getSessionByToken(token);
		return isSessionValid(lSession);
	}

	@Override
	public boolean isSessionValid(Session session) {
		return session.getExpirationDate().isAfter(LocalDateTime.now());
	}

	@Override
	public void validateSession(String token) throws BadSessionException {
		boolean isValid = isTokenValid(token);
		if (isValid) {
			return;
		}
		closeSessionByToken(token);
		throw new BadSessionException("Invalid session token", HttpStatus.BAD_REQUEST);
	}

	@Override
	public void canSessionWrite(String token) throws BadSessionException {
		Session lSession = getSessionByToken(token);
		if (lSession.getType() == SessionType.READ) {
			throw new BadSessionException("Read Only session, cant edit data", HttpStatus.BAD_REQUEST);
		}
	}
}
