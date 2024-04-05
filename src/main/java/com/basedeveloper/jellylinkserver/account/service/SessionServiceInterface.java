package com.basedeveloper.jellylinkserver.account.service;

import com.basedeveloper.jellylinkserver.account.entity.Session;
import com.basedeveloper.jellylinkserver.account.entity.SessionType;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.exceptions.CreationException;

public interface SessionServiceInterface {
	public Session createSessionForUser(User user, SessionType sessionType) throws CreationException;
}
