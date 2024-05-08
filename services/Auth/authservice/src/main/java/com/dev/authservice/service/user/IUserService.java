package com.dev.authservice.service.user;

import jakarta.security.auth.message.AuthException;
import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.InvalidDataException;
import com.dev.authservice.middleware.inc.account.ChangePwdDto;
import com.dev.authservice.middleware.inc.account.CreateUserDto;

public interface IUserService {
	/**
	 * This method implements the `registerUser` method from the `IUserService`
	 * interface.
	 * It takes a `CreateUserdto` object containing user registration details and
	 * throws an
	 * `AuthException` if registration fails.
	 *
	 * @param usrdDto The `CreateUserdto` object containing user registration data.
	 * @return A `User` object representing the newly registered user.
	 * @throws AuthException If user registration fails due to invalid email,
	 *                       existing email,
	 *                       or other issues.
	 */
	public User registerUser(CreateUserDto usrdDto) throws InvalidDataException;

	/**
	 * This method overrides a method from the parent class (likely an interface) to
	 * find a user based on a session token.
	 * It throws an `InvalidDataException` if the token is invalid or the user
	 * cannot be found.
	 *
	 * @param token The session token as a String.
	 * @return A `User` object representing the found user.
	 * @throws InvalidDataException If the token is invalid or the user cannot be
	 *                              found.
	 */
	public User getUserByToken(String token) throws InvalidDataException;

	/**
	 * This method overrides a method from the parent class (likely an interface) to
	 * handle user login.
	 * It attempts to authenticate a user based on their email and password.
	 * It throws an `AuthException` if the login fails due to invalid credentials or
	 * other authentication issues.
	 *
	 * @param usrEmail The user's email address as a String.
	 * @param usrPwd   The user's password as a String.
	 * @return A `User` object representing the logged-in user if authentication is
	 *         successful.
	 * @throws AuthException If the login fails due to invalid credentials or other
	 *                       issues.
	 */
	public User loginUser(String usrEmail, String usrPwd) throws AuthException;

	/**
	 * This method overrides a method from the parent class (likely an interface) to
	 * handle user password changes.
	 * It attempts to change the password for the provided `User` object based on
	 * the information in the `CreateUserDto` object.
	 * It throws an `AuthException` if the password change fails due to
	 * authorization issues.
	 *
	 * @param ChangePwdDto A `ChangePwdDto` object likely containing new password
	 *                     information.
	 * @param usr          The `User` object for whom the password change is
	 *                     requested.
	 * @throws AuthException If the password change is not authorized for the
	 *                       provided user.
	 */
	public User changeUserPassword(ChangePwdDto changePwdDto, User usr) throws AuthException;

}
