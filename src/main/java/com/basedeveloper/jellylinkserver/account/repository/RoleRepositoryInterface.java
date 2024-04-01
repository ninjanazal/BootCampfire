package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basedeveloper.jellylinkserver.account.entity.Role;

public interface RoleRepositoryInterface extends JpaRepository<Role, Long> {
	boolean existsByDescription(String description);
}
