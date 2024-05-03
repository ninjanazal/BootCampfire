package com.dev.authservice.service.session;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dev.authservice.constants.session.SessionType;
import com.dev.authservice.entity.Session;
import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.BadSessionException;
import com.dev.authservice.repository.ISessionRepository;
import com.dev.authservice.security.ISecurityService;

@Service
public class SessionService implements ISessionService {
	@Autowired
	private ISessionRepository sessionRepository;
	@Autowired
	private ISecurityService securityService;

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

		result = sessionRepository.save(result);

		List<Session> userSessions = sessionRepository.findByOwnerUserId(userId);
		for (Session s : userSessions) {
			if (s != result && s.getType() == result.getType()) {
				sessionRepository.delete(s);
			}
		}

		return result;
	}

	@Override
	public void closeSessionByToken(String token) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'closeSessionByToken'");
	}

	@Override
	public boolean checkIfSessionIsValid(String token, String requestIp) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'checkIfSessionIsValid'");
	}

	@Override
	public Session getSessionByToken(String token) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getSessionByToken'");
	}

	@Override
	public User getSessionOwner(String token) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'getSessionOwner'");
	}

}
