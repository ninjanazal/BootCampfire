package com.basedeveloper.jellylinkserver.account.service;

import org.springframework.stereotype.Service;

import com.basedeveloper.jellylinkserver.account.entity.Session;
import com.basedeveloper.jellylinkserver.account.entity.SessionType;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.exceptions.CreationException;


@Service
public class SessionService implements SessionServiceInterface{



	@Override
	public Session createSessionForUser(User user, SessionType sessionType) throws CreationException {
		
		throw new UnsupportedOperationException("Unimplemented method 'createSessionForUser'");
	}
	
}
