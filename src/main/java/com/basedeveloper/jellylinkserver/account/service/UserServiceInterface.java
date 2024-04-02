package com.basedeveloper.jellylinkserver.account.service;

import com.basedeveloper.jellylinkserver.account.controller.DataTransferObj.UserDto;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.exceptions.CreationException;

public interface UserServiceInterface {
	public User registerUser(UserDto usrdDto) throws CreationException;
}
