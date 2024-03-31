package com.basedeveloper.jellylinkserver.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.account.repository.GenderRepositoryInterface;
import com.basedeveloper.jellylinkserver.account.repository.UserRepositoryInterface;
import com.basedeveloper.jellylinkserver.exceptions.AuthException;
import com.basedeveloper.jellylinkserver.security.SecurityService;

@Service
@Transactional
public class UserService implements UserServiceInterface {

	@Autowired
	UserRepositoryInterface userRepo;

	@Autowired
	GenderRepositoryInterface genderRepo;

	@Autowired
	SecurityService securityService;

	public User registerUser(User usr) throws AuthException {
		if (userRepo.existsByEmail(usr.getEmail())) {
			throw new AuthException("Email allready in use");
		}

		// if(genderRepo.existsByDescription(usr.getGender())

		usr.setHshScrt(securityService.EncodeData(usr.getHshScrt()));
		User savedUser = userRepo.save(usr);
		return savedUser;
	}

}
