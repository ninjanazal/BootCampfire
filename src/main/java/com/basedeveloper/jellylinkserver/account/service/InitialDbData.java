package com.basedeveloper.jellylinkserver.account.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import jakarta.annotation.PostConstruct;

@Service
public class InitialDbData {

	private Logger logger = LoggerFactory.getLogger(InitialDbData.class);

	@PostConstruct
	public void initData() {
	}
}
