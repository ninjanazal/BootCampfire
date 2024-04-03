package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basedeveloper.jellylinkserver.account.entity.SessionType;

public interface SessionTypeRepositoryInterface extends JpaRepository<SessionType, Long> {
	// Search if a description exists on the database
	boolean existsByDescription(String description);
	// Finds the object by the provided descriptionc
	SessionType findByDescription(String description);
}
