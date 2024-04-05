package com.basedeveloper.jellylinkserver.account.service;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basedeveloper.jellylinkserver.account.controller.DataTransferObj.LoginDto;
import com.basedeveloper.jellylinkserver.account.controller.DataTransferObj.UserDto;
import com.basedeveloper.jellylinkserver.account.entity.Gender;
import com.basedeveloper.jellylinkserver.account.entity.Role;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.account.repository.GenderRepository;
import com.basedeveloper.jellylinkserver.account.repository.RoleRepository;
import com.basedeveloper.jellylinkserver.account.repository.UserRepository;
import com.basedeveloper.jellylinkserver.account.tools.ValidationTools;
import com.basedeveloper.jellylinkserver.exceptions.AuthException;
import com.basedeveloper.jellylinkserver.exceptions.CreationException;
import com.basedeveloper.jellylinkserver.security.SecurityService;

@Service
public class UserService implements UserServiceInterface {

	@Autowired
	UserRepository userRepo;

	@Autowired
	GenderRepository genderRepo;

	@Autowired
	RoleRepository roleRepo;

	@Autowired
	SecurityService securityService;

	public User registerUser(UserDto dto) throws AuthException {
		User createdUser = new User();

		// Validate Gender
		Gender userGender = genderRepo.findByDescription(dto.getGender());
		if (userGender == null) {
			throw new CreationException("Failed to create User, wrong gender description");
		}
		createdUser.setGender(userGender);

		// Validate Role
		Role userRole = roleRepo.findByDescription(dto.getRole());
		if (userRole == null) {
			throw new CreationException("Failed to create User, wrong role description");
		}
		createdUser.setRole(userRole);

		// Validate Email
		if (!ValidationTools.ValidEmail(dto.getEmail()) || userRepo.existsByEmail(dto.getEmail())) {
			throw new CreationException("Failed to create User, Email allready in use");
		}
		createdUser.setEmail(dto.getEmail());
		createdUser.setHshScrt(securityService.EncodeData(dto.getPassword()));

		createdUser.setName(dto.getName());
		createdUser.setAge(dto.getAge());

		String userToken = UUID.randomUUID().toString();
		while (userRepo.existsByUserToken(userToken)) {
			userToken = UUID.randomUUID().toString();
		}
		createdUser.setUserToken(userToken);

		createdUser = userRepo.save(createdUser);
		return createdUser;
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
}
