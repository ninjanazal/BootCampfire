package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basedeveloper.jellylinkserver.account.entity.Role;

public interface RoleRepositoryInterface extends JpaRepository<Role, Long> {
	// Search if a description exists ont the database
	boolean existsByDescription(String description);
	// Finds the object by the provided description
	Role findByDescription(String description);
}
