package com.basedeveloper.jellylinkserver.account.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.basedeveloper.jellylinkserver.account.entity.Session;
import com.basedeveloper.jellylinkserver.account.entity.User;

public interface SessionRepositoryInterface extends JpaRepository<Session, String> {
	/**
	 * Finds all sessions associated with a specific user 
	 *
	 * @param user The user to search for
	 * @return Result list.
	 */
	List<Session> findByOwnerUser(User user);

	/**
	 * Finds all sessions associated with a specific user ip 
	 * @param userIp The user ip to search for .
	 * @return Result list.
	 */
	List<Session> findByUserIp(String address);
}
