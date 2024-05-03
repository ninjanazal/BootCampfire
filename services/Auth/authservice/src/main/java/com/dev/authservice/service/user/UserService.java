package com.dev.authservice.service.user;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dev.authservice.constants.user.Gender;
import com.dev.authservice.constants.user.Role;
import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.InvalidDataException;
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
	public User registerUser(CreateUserDto usrdDto) throws InvalidDataException {
		User result = new User();

		if (!DataValidations.ValidEmail(usrdDto.getEmail())) {
			throw new InvalidDataException(String.format("Failed to validade %s", usrdDto.getEmail()),
					HttpStatus.BAD_REQUEST);
		}
		if (userRepository.existsByEmail(usrdDto.getEmail())) {
			throw new InvalidDataException(String.format("email already in use %s", usrdDto.getEmail()),
					HttpStatus.BAD_REQUEST);
		}

		try {
			result.setGender(Gender.valueOf(usrdDto.getGender().toUpperCase()));
			result.setRole(Role.valueOf(usrdDto.getRole().toUpperCase()));
		} catch (IllegalArgumentException exception) {
			throw new InvalidDataException(String.format("Wrong value for the Gender or the Role @ %s ; %s",
					usrdDto.getGender(),
					usrdDto.getRole()),
					HttpStatus.BAD_REQUEST);
		}

		result.setEmail(usrdDto.getEmail());
		result.setHsh_scrt(securityService.EncodeData(usrdDto.getPassword()));
		result.setName(usrdDto.getName());
		result.setAge(usrdDto.getAge());

		return userRepository.save(result);
	}

	@Override
	public boolean changeUserPassword(CreateUserDto changePwdDto, User usr) throws AuthException {
		return true;

	}

	public User loginUser(String usrEmail, String usrPwd) throws AuthException {
		if (DataValidations.ValidEmail(usrEmail)) {
			Optional<User> fUser = userRepository.findByEmail(usrEmail);
			if (fUser.isPresent() && securityService.ValidateData(usrPwd, fUser.get().getHsh_scrt())) {
				return fUser.get();
			}
		}
		throw new AuthException(String.format("Failed to authenticate user [%s]", usrEmail));
	}

}
