package com.dev.authservice.service;

import jakarta.security.auth.message.AuthException;
import com.dev.authservice.entity.User;
import com.dev.authservice.middleware.inc.data.CreateUserdto;

public interface IUserService {
	/**
	 * Registers a new user in the system.
	 *
	 * @param usrdDto Data Transfer Object containing user information for
	 *                registration.
	 * @return The newly created User object, fully populated with data upon
	 *         successful registration.
	*/
	public User registerUser(CreateUserdto usrdDto) throws AuthException;

	/**
	 * Changes a user's password while ensuring session validity.
	 *
	 * @param changePwdDto Data Transfer Object containing necessary details for
	 *                     password change.
	 * @param User         The user's current.
	 * @return True if the password change is successful, false otherwise.
	 */
	public boolean changeUserPassword(CreateUserdto changePwdDto, User usr) throws AuthException;
}