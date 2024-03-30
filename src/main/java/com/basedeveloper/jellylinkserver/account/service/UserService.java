package com.basedeveloper.jellylinkserver.account.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.basedeveloper.jellylinkserver.account.repo.UserRepo;

@Service
@Transactional
public class UserService {
	
	@Autowired
	UserRepo userRepo;

}
