package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basedeveloper.jellylinkserver.account.model.Gender;

public interface GenderRepository extends JpaRepository<Gender, Long> {
	boolean existsByDescription(String description);
}
