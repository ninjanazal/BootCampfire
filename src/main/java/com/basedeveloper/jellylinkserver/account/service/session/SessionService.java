package com.basedeveloper.jellylinkserver.account.service.session;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basedeveloper.jellylinkserver.account.constants.SessionConsts;
import com.basedeveloper.jellylinkserver.account.entity.Session;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.account.repository.SessionRepository;
import com.basedeveloper.jellylinkserver.account.tools.DateTimeTools;
import com.basedeveloper.jellylinkserver.exceptions.types.CreationException;
import com.basedeveloper.jellylinkserver.exceptions.types.SearchException;
import com.basedeveloper.jellylinkserver.security.SecurityService;

@Service
public class SessionService implements SessionServiceInterface {

	@Autowired
	SessionRepository sessionRepository;
	@Autowired
	SecurityService securityService;

	@Override
	public Session createSessionForUser(User user, String sTypeName, String ipAddress) throws CreationException {
		Session createSession = new Session();
		List<Session> userSessions = sessionRepository.findByOwnerUser(user);
		SessionConsts.SessionType sType;
		try {
			sType = SessionConsts.SessionType.valueOf(sTypeName.toUpperCase());
		} catch (IllegalArgumentException e) {
			throw new CreationException("Failed to parse the session type, wrong session type");
		}

		for (Session session : userSessions) {
			if (session.getSessionType() == sType) {
				sessionRepository.delete(session);
			}
		}
		createSession.setSessionType(sType);
		createSession.setOwnerUser(user);
		createSession.setUserIp(securityService.AnonymizeIpAddress(ipAddress));

		String sessionToken;
		do {
			sessionToken = securityService.GenerateSessionToken();
		} while (sessionRepository.existsById(sessionToken));

		createSession.setId(sessionToken);
		createSession.setExpirationDate(DateTimeTools.GenerateExpirationDateFromNow());

		return sessionRepository.save(createSession);
	}

	@Override
	public void closeSessionByToken(String token) throws SearchException {
		Optional<Session> toCloseSession = sessionRepository.findById(token);

		if (toCloseSession.isEmpty()) {
			throw new SearchException("Session with token not found", token);
		}

		sessionRepository.delete(toCloseSession.get());
	}

	@Override
	public boolean checkIfSessionIsValid(String token, String requestIp) throws SearchException {
		Optional<Session> toValidateSession = sessionRepository.findById(token);
		if (toValidateSession.isEmpty()) {
			throw new SearchException("Session with token not found", token);
		}
		LocalDateTime nowDateTime = LocalDateTime.now();
		boolean validIp = securityService.ValidateAnonymizedIp(toValidateSession.get().getUserIp(), requestIp);
		if (toValidateSession.get().getExpirationDate().isAfter(nowDateTime) && validIp) {
			return true;
		}
		throw new SearchException("Invalid Session with token", token);
	}

	@Override
	public Session getSessionByToken(String token) throws SearchException {
		Optional<Session> foundSession = sessionRepository.findById(token);
		if (foundSession.isEmpty()) {
			throw new SearchException("Session with token not found", token);
		}

		return foundSession.get();
	}

	@Override
	public User getSessionOwner(String token) throws SearchException {
		Optional<Session> foundSession = sessionRepository.findById(token);
		if (foundSession.isEmpty()) {
			throw new SearchException("Session with token not found", token);
		}

		return foundSession.get().getOwnerUser();
	}

}
