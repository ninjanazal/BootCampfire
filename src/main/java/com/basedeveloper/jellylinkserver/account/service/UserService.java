package com.basedeveloper.jellylinkserver.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basedeveloper.jellylinkserver.account.model.User;
import com.basedeveloper.jellylinkserver.account.repository.GenderRepository;
import com.basedeveloper.jellylinkserver.account.repository.UserRepository;
import com.basedeveloper.jellylinkserver.exceptions.AuthException;
import com.basedeveloper.jellylinkserver.security.SecurityService;

@Service
@Transactional
public class UserService {

	@Autowired
	UserRepository userRepo;

	@Autowired
	GenderRepository genderRepo;

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
