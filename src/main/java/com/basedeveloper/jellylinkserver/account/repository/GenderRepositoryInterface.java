package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basedeveloper.jellylinkserver.account.entity.Gender;

public interface GenderRepositoryInterface extends JpaRepository<Gender, Long> {
	boolean existsByDescription(String description);
}