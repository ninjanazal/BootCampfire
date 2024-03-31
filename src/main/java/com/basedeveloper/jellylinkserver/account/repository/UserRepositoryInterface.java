package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.basedeveloper.jellylinkserver.account.model.User;

public interface UserRepositoryInterface extends JpaRepository<User, Long> {
	boolean existsByEmail(String email);
}
