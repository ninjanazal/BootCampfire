package com.basedeveloper.jellylinkserver.account.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.basedeveloper.jellylinkserver.account.entity.Gender;
import com.basedeveloper.jellylinkserver.account.entity.Role;
import com.basedeveloper.jellylinkserver.account.repository.GenderRepository;
import com.basedeveloper.jellylinkserver.account.repository.RoleRepository;

import jakarta.annotation.PostConstruct;

@Service
public class InitialDbData {
	private final List<Gender> initGenders = new ArrayList<Gender>() {
		{
			add(new Gender("male"));
			add(new Gender("female"));
			add(new Gender("other"));
		}
	};

	private final List<Role> initRoles = new ArrayList<Role>() {
		{
			add(new Role("default"));
			add(new Role("admin"));
		}
	};

	private Logger logger = LoggerFactory.getLogger(InitialDbData.class);

	@Autowired
	private GenderRepository genderRepository;
	@Autowired
	private RoleRepository roleRepository;

	
	@PostConstruct
	public void initData() {
		// Add Initial gender data
		for (Gender gender : initGenders) {
			if (!genderRepository.existsByDescription(gender.getDescription())) {
				logger.info(String.format("Added gender %s", gender.getDescription()));
				genderRepository.save(gender);
			}
		}

		for (Role role : initRoles) {
			if (!roleRepository.existsByDescription(role.getDescription())) {
				logger.info(String.format("Added role %s", role.getDescription()));
				roleRepository.save(role);
			}
		}
	}
}
