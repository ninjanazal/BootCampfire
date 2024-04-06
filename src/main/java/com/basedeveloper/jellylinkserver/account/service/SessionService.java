package com.basedeveloper.jellylinkserver.account.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basedeveloper.jellylinkserver.account.entity.Session;
import com.basedeveloper.jellylinkserver.account.entity.SessionType;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.account.repository.SessionRepository;
import com.basedeveloper.jellylinkserver.account.repository.SessionTypeRepository;
import com.basedeveloper.jellylinkserver.account.tools.DateTimeTools;
import com.basedeveloper.jellylinkserver.exceptions.CreationException;
import com.basedeveloper.jellylinkserver.security.SecurityService;

@Service
public class SessionService implements SessionServiceInterface {

	@Autowired
	SessionRepository sessionRepository;

	@Autowired
	SessionTypeRepository sessionTypeRepository;

	@Autowired
	SecurityService securityService;

	@Override
	public Session createSessionForUser(User user, SessionType sessionType, String ipAddress) throws CreationException {
		Session createSession = new Session();
		List<Session> userSessions = sessionRepository.findByUserToken(user.getUserToken());

		for (Session session : userSessions) {
			if (session.getSessionType().getDescription() == sessionType.getDescription()) {
				sessionRepository.delete(session);
			}
		}
		createSession.setSessionType(sessionType);
		createSession.setUserToken(user.getUserToken());
		createSession.setUserIp(securityService.AnonymizeIpAddress(ipAddress));

		String sessionToken;
		do {
			sessionToken = securityService.GenerateSessionToken();
		} while (sessionRepository.existsBySessionToken(sessionToken));

		createSession.setSessionToken(sessionToken);
		createSession.setExpirationDate(DateTimeTools.GenerateExpirationDateFromNow());

		return sessionRepository.save(createSession);
	}

	@Override
	public SessionType getSessionTypeByDescription(String description) throws CreationException {
		SessionType foundSessionType = sessionTypeRepository.findByDescription(description);
		if (foundSessionType == null) {
			throw new CreationException("Session type invalid name");
		}
		return foundSessionType;
	}

}
