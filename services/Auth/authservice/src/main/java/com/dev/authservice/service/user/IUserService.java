package com.dev.authservice.service.user;

import jakarta.security.auth.message.AuthException;
import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.InvalidDataException;
import com.dev.authservice.middleware.inc.account.CreateUserDto;

public interface IUserService {
	/**
	 * Registers a new user in the system.
	 *
	 * @param usrdDto Data Transfer Object containing user information for
	 *                registration.
	 * @return The newly created User object, fully populated with data upon
	 *         successful registration.
	*/
	public User registerUser(CreateUserDto usrdDto) throws InvalidDataException;

	/**
	 * Changes a user's password while ensuring session validity.
	 *
	 * @param changePwdDto Data Transfer Object containing necessary details for
	 *                     password change.
	 * @param User         The user's current.
	 * @return True if the password change is successful, false otherwise.
	 */
	public boolean changeUserPassword(CreateUserDto changePwdDto, User usr) throws AuthException;



	public User loginUser(String usrEmail, String usrPwd) throws AuthException;
}
