package com.basedeveloper.jellylinkserver.account.service.user;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basedeveloper.jellylinkserver.account.constants.AccountConsts;
import com.basedeveloper.jellylinkserver.account.dto.ChangePwdDto;
import com.basedeveloper.jellylinkserver.account.dto.CreateUserDto;
import com.basedeveloper.jellylinkserver.account.dto.LoginDto;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.account.repository.UserRepository;
import com.basedeveloper.jellylinkserver.account.tools.ValidationTools;
import com.basedeveloper.jellylinkserver.exceptions.types.AuthException;
import com.basedeveloper.jellylinkserver.exceptions.types.CreationException;
import com.basedeveloper.jellylinkserver.security.SecurityService;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	UserRepository userRepo;

	@Autowired
	SecurityService securityService;

	public User registerUser(CreateUserDto dto) throws AuthException, CreationException {
		User createdUser = new User();

		try {
			AccountConsts.Gender userGender = AccountConsts.Gender.valueOf(dto.getGender().toUpperCase());
			createdUser.setGender(userGender);
		} catch (IllegalArgumentException e) {
			throw new CreationException("Failed to create User, wrong gender description");
		}

		try {
			AccountConsts.Role userRole = AccountConsts.Role.valueOf(dto.getRole().toUpperCase());
			createdUser.setRole(userRole);
		} catch (IllegalArgumentException e) {
			throw new CreationException("Failed to create User, wrong role description");
		}

		// Validate Email
		if (!ValidationTools.ValidEmail(dto.getEmail())) {
			throw new CreationException("Failed to create User, invalid email format");
		}
		if (userRepo.existsByEmail(dto.getEmail())) {
			throw new CreationException("Failed to create User, email already in use");
		}

		createdUser.setEmail(dto.getEmail());
		createdUser.setHshScrt(securityService.EncodeData(dto.getPassword()));
		createdUser.setName(dto.getName());
		createdUser.setAge(dto.getAge());

		String userToken;
		do {
			userToken = UUID.randomUUID().toString();
		} while (userRepo.existsById(userToken));
		createdUser.setId(userToken);

		return userRepo.save(createdUser);
	}

	public User loginUser(LoginDto dto) throws AuthException {
		if (ValidationTools.ValidEmail(dto.getEmail())) {
			User foundUser = userRepo.findByEmail(dto.getEmail());
			if (foundUser != null && securityService.ValidateData(dto.getPassword(), foundUser.getHshScrt())) {
				return foundUser;
			}
		}
		throw new AuthException("Failed to authenticate user, password or email invalid");
	}

	@Override
	public boolean changeUserPassword(ChangePwdDto changePwdDto, User user) throws AuthException {
		if (!securityService.ValidateData(changePwdDto.getOldPassword(), user.getHshScrt())) {
			throw new AuthException("Wrong password, faild to update");
		}
		user.setHshScrt(securityService.EncodeData(changePwdDto.getNewPassword()));
		userRepo.save(user);
		return true;
	}
}
