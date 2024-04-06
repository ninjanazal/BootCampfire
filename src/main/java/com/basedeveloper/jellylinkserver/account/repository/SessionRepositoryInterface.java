package com.basedeveloper.jellylinkserver.account.repository;

import java.util.*;
import org.springframework.data.jpa.repository.JpaRepository;

import com.basedeveloper.jellylinkserver.account.entity.Session;

public interface SessionRepositoryInterface extends JpaRepository<Session, Long> {
	/**
	 * Checks if a session exists based on the provided session token.
	 *
	 * This method retrieves sessions from your storage mechanism (e.g., database)
	 * and searches for a session object that has a matching session token.
	 *
	 * @param sessionToken The unique identifier for the session to check.
	 * @return True if a session exists with the given token, false otherwise.
	 */
	boolean existsBySessionToken(String sessionToken);

	/**
	 * Retrieves a session object based on the provided session token.
	 *
	 * This method searches your session storage for a session that has a matching
	 * session token. If found, the entire session object is returned.
	 *
	 * @param sessionToken The unique identifier for the session to find.
	 * @return The session object if found, null otherwise.
	 */
	Session findBySessionToken(String sessionToken);

	/**
	 * Finds all sessions associated with a specific user token (not implemented).
	 *
	 * This method is currently not implemented as the provided information
	 * doesn't include user tokens as part of the session model. You'll need to
	 * adjust this based on your specific session data structure.
	 *
	 * @param userToken The user token to search for (not implemented).
	 * @return An empty list (not implemented).
	 */
	List<Session> findByUserToken(String userToken);

	/**
	 * Finds all sessions associated with a specific user token (not implemented).
	 *
	 * This method is currently not implemented as the provided information
	 * doesn't include user tokens as part of the session model. You'll need to
	 * adjust this based on your specific session data structure.
	 *
	 * @param userToken The user token to search for (not implemented).
	 * @return An empty list (not implemented).
	 */
	List<Session> findByUserIp(String address);
}
