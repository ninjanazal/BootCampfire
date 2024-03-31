package com.basedeveloper.jellylinkserver.account.service;

import com.basedeveloper.jellylinkserver.account.model.User;
import com.basedeveloper.jellylinkserver.exceptions.AuthException;

public interface UserServiceInterface {
	public User registerUser(User usr) throws AuthException;
}
