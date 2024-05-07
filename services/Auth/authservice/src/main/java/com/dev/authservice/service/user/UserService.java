package com.dev.authservice.service.user;

import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import com.dev.authservice.constants.user.Gender;
import com.dev.authservice.constants.user.Role;
import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.InvalidDataException;
import com.dev.authservice.middleware.inc.account.ChangePwdDto;
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
@EnableCaching
public class UserService implements IUserService {

	@Autowired
	private IUserRepository userRepository;

	@Autowired
	private SecurityService securityService;

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
	public User changeUserPassword(ChangePwdDto changePwdDto, User usr) throws AuthException {
		if (securityService.ValidateData(changePwdDto.getOldPassword(), usr.getHsh_scrt())) {
			usr.setHsh_scrt(securityService.EncodeData(changePwdDto.getNewPassword()));
			return userRepository.save(usr);
		}

		throw new AuthException("Wrong password");
	}

	@Override
	@Cacheable(key = "#token", value = "User")
	public User getUserByToken(String token) throws InvalidDataException {
		if (!token.isEmpty()) {
			Optional<User> result = userRepository.findById(new ObjectId(token));
			if (result.isPresent()) {
				return result.get();
			}
		}

		throw new InvalidDataException("Not a valid user token", HttpStatus.BAD_REQUEST);
	}

	@Override
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
