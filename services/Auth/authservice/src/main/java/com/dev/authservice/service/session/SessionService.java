package com.dev.authservice.service.session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dev.authservice.constants.session.SessionType;
import com.dev.authservice.entity.Session;
import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.BadSessionException;
import com.dev.authservice.exeptions.types.InvalidDataException;
import com.dev.authservice.repository.ISessionRepository;
import com.dev.authservice.security.ISecurityService;
import com.dev.authservice.service.user.IUserService;
import com.dev.authservice.tools.DateTimeActions;

import jakarta.servlet.http.HttpServletRequest;

/**
 * This class implements the `ISessionService` interface and provides methods
 * for managing user sessions.
 */
@Service
public class SessionService implements ISessionService {

	@Autowired
	private ISessionRepository sessionRepository;

	@Autowired
	private ISecurityService securityService;

	@Autowired
	private IUserService userService;

	@Override
	public Session createSessionForUser(String userId, String sTypeName, String ipAddress) throws BadSessionException {

		Session result = new Session();

		try {
			result.setType(SessionType.valueOf(sTypeName.toUpperCase()));
		} catch (IllegalArgumentException e) {
			throw new BadSessionException(String.format("Wrong value for the Session type @ %s", sTypeName),
					HttpStatus.BAD_REQUEST);
		}

		result.setOwnerUserId(userId);
		result.setUserIp(securityService.AnonymizeIpAddress(ipAddress));
		result.setExpirationDate(DateTimeActions.GenerateExpirationDateFromNow());

		List<Session> userSessions = sessionRepository.findByOwnerUserId(userId);
		for (Session s : userSessions) {
			if (s.getType() == result.getType()) {
				sessionRepository.delete(s);
			}
		}

		return sessionRepository.save(result);
	}

	@Override
	public void closeSessionByToken(String token) throws BadSessionException {
		Optional<Session> fSession = sessionRepository.findById(new ObjectId(token));
		if (fSession.isPresent()) {
			sessionRepository.delete(fSession.get());
			return;
		}
		throw new BadSessionException("Couldnt close session, token not found", HttpStatus.NOT_FOUND);
	}

	@Override
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
	public boolean isSessionValid(Session session, HttpServletRequest servletRequest) {
		return session.getExpirationDate().isAfter(LocalDateTime.now())
				&& securityService.ValidateAnonymizedIp(session.getUserIp(), servletRequest.getRemoteAddr());
	}
}
