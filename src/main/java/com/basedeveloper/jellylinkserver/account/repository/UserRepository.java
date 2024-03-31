package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.basedeveloper.jellylinkserver.account.model.User;


@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	boolean existsByEmail(String email);
}
