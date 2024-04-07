package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basedeveloper.jellylinkserver.account.entity.User;

public interface UserRepositoryInterface extends JpaRepository<User, String> {
	boolean existsByEmail(String email);
	// Search user for the email
	User findByEmail(String email);
}
