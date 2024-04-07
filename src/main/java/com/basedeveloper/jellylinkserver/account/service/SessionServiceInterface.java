package com.basedeveloper.jellylinkserver.account.service;

import com.basedeveloper.jellylinkserver.account.entity.Session;
import com.basedeveloper.jellylinkserver.account.entity.SessionType;
import com.basedeveloper.jellylinkserver.account.entity.User;
import com.basedeveloper.jellylinkserver.exceptions.CreationException;
import com.basedeveloper.jellylinkserver.exceptions.SearchException;

public interface SessionServiceInterface {
	/**
	 * Creates a new session for the given user with the specified session type.
	 * 
	 * @param user        The user for whom the session is being created.
	 * @param sessionType The type of session to create
	 * @param ipAddress   The ip of the session owner
	 * @return The newly created session object.
	 * @throws CreationException If an error occurs while creating the session.
	 * 
	 */
	public Session createSessionForUser(User user, SessionType sessionType, String ipAddress) throws CreationException;

	/**
	 * Retrieves the session type by its descriptive text.
	 * 
	 * @param description The textual description of the session type (e.g.,
	 *                    "Read-only access").
	 * @return The corresponding session type object.
	 * @throws SearchException If no session type is found with the given
	 *                         description.
	 */
	public SessionType getSessionTypeByDescription(String description) throws SearchException;

	/**
	 * Closes a session based on the provided session token.
	 *
	 * This method attempts to find a session object in your storage mechanism
	 * (e.g., database) that matches the given session token. If a matching session
	 * is found, it is marked as closed or invalidated.
	 *
	 * @param token The unique identifier for the session to close.
	 * @throws SearchException If an error occurs while searching for the session.
	 */
	public void closeSessionByToken(String token) throws SearchException;

	/**
	 * Checks if a session is valid based on the provided session token.
	 *
	 * This method attempts to find a session object in your storage mechanism
	 * (e.g., database) that matches the given session token. If a matching session
	 * is found, it verifies its validity based on certain criteria (e.g., not
	 * expired,
	 * not marked as invalid).
	 *
	 * @param token The unique identifier for the session to check.
	 * @return True if the session is valid, false otherwise.
	 * @throws SearchException If an error occurs while searching for the session.
	 */
	public boolean checkIfSessionIsValid(String token) throws SearchException;

}
