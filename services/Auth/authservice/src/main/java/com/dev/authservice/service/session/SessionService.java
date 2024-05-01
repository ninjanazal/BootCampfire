package com.dev.authservice.service.session;

import org.springframework.stereotype.Service;

import com.dev.authservice.entity.Session;
import com.dev.authservice.entity.User;

@Service
public class SessionService implements ISessionService {

	@Override
	public Session createSessionForUser(User user, String sTypeName, String ipAddress) {
		// TODO Auto-generated method stub
		throw new UnsupportedOperationException("Unimplemented method 'createSessionForUser'");
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
