package com.dev.authservice.service.user;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.authservice.constants.user.Gender;
import com.dev.authservice.constants.user.Role;
import com.dev.authservice.entity.User;
import com.dev.authservice.middleware.inc.account.CreateUserDto;
import com.dev.authservice.repository.IUserRepository;
import com.dev.authservice.security.SecurityService;
import com.dev.authservice.tools.DataValidations;

import jakarta.security.auth.message.AuthException;

/**
 * This class implements the `IUserService` interface and provides methods for
 * user management.
 */
@Service
public class UserService implements IUserService {
	/**
	 * Injected instance of the `IUserRepository` interface, likely used for
	 * interacting
	 * with the user data store (e.g., database).
	 */
	@Autowired
	private IUserRepository userRepository;
	/**
	 * Injected instance of the `SecurityService`, likely used for password
	 * encryption
	 * or other security-related tasks.
	 */
	@Autowired
	private SecurityService securityService;

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
	@Override
	public User registerUser(CreateUserDto usrdDto) throws AuthException {
		User result = new User();

		if (!DataValidations.ValidEmail(usrdDto.getEmail())) {
			throw new AuthException(String.format("Failed to validade %s", usrdDto.getEmail()));
		}
		if (userRepository.existsByEmail(usrdDto.getEmail())) {
			throw new AuthException(String.format("email already in use %s", usrdDto.getEmail()));
		}

		result.setGender(Gender.valueOf(usrdDto.getGender().toUpperCase()));
		result.setRole(Role.valueOf(usrdDto.getRole().toUpperCase()));
		result.setEmail(usrdDto.getEmail());
		result.setHsh_scrt(securityService.EncodeData(usrdDto.getPassword()));
		result.setName(usrdDto.getName());
		result.setAge(usrdDto.getAge());

		return userRepository.insert(result);
	}

	@Override
	public boolean changeUserPassword(CreateUserDto changePwdDto, User usr) throws AuthException {
		return true;

	}

}
