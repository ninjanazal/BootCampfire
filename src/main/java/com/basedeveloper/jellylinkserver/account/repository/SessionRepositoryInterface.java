package com.basedeveloper.jellylinkserver.account.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.basedeveloper.jellylinkserver.account.entity.Session;

public interface SessionRepositoryInterface extends JpaRepository<Session, Long>{
	Session findBySessionToken(String sessionToken);
}
