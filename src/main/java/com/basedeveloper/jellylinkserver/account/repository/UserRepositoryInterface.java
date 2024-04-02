package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basedeveloper.jellylinkserver.account.entity.User;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);
	boolean existsByUserToken(String token);
}
