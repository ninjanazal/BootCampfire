package com.dev.authservice.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.dev.authservice.constants.user.Gender;
import com.dev.authservice.constants.user.Role;
import com.dev.authservice.entity.User;
import com.dev.authservice.middleware.inc.data.CreateUserdto;
import com.dev.authservice.repository.IUserRepository;
import com.dev.authservice.security.SecurityService;
import com.dev.authservice.tools.DataValidations;

import jakarta.security.auth.message.AuthException;

@Service
public class UserService implements IUserService {

	@Autowired
	private IUserRepository userRepository;
	@Autowired
	private SecurityService securityService;

	@Override
	public User registerUser(CreateUserdto usrdDto) throws AuthException {
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
	public boolean changeUserPassword(CreateUserdto changePwdDto, User usr) throws AuthException {
		return true;

	}

}
