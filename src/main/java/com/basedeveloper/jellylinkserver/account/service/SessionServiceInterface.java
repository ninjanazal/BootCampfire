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

}
