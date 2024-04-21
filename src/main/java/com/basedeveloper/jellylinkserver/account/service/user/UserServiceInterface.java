package com.basedeveloper.jellylinkserver.account.service.user;

import com.basedeveloper.jellylinkserver.account.dto.ChangePwdDto;
import com.basedeveloper.jellylinkserver.account.dto.CreateUserDto;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.exceptions.types.AuthException;
import com.basedeveloper.jellylinkserver.exceptions.types.CreationException;

public interface UserServiceInterface {
	/**
	 * Registers a new user in the system.
	 *
	 * @param usrdDto Data Transfer Object containing user information for
	 *                registration.
	 * @return The newly created User object, fully populated with data upon
	 *         successful registration.
	 * @throws CreationException If any errors occur during user creation, providing
	 *                           details for handling.
	 */
	public User registerUser(CreateUserDto usrdDto) throws AuthException, CreationException;

	/**
	 * Changes a user's password while ensuring session validity.
	 *
	 * @param changePwdDto Data Transfer Object containing necessary details for
	 *                     password change.
	 * @param User      The user's current.
	 * @return True if the password change is successful, false otherwise.
	 * @throws CreationException If any errors hinder password change, allowing for
	 *                           proper exception handling.
	 */
	public boolean changeUserPassword(ChangePwdDto changePwdDto, User usr) throws AuthException;
}
