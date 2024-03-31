package com.basedeveloper.jellylinkserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class JellylinkserverApplication {
	public static void main(String[] args) {
		SpringApplication.run(JellylinkserverApplication.class, args);
	}

}
