package com.dev.authservice.service.session;

import com.dev.authservice.entity.Session;
import com.dev.authservice.entity.User;
import com.dev.authservice.exeptions.types.BadSessionException;
import com.dev.authservice.exeptions.types.InvalidDataException;

public interface ISessionService {
	/**
	 * This method implements the `createSessionForUser` method from the
	 * `ISessionService` interface.
	 * It creates a new session for a user and throws a `BadSessionException` if
	 * something goes wrong.
	 *
	 * @param userId    The user ID for whom the session is being created.
	 * @param sTypeName The type of session as a String (needs to be a valid
	 *                  `SessionType` enum value).
	 * @param ipAddress The user's IP address.
	 * @return A `Session` object representing the newly created session.
	 * @throws BadSessionException If the session type is invalid or an error occurs
	 *                             during session creation.
	 */
	public Session createSessionForUser(String userId, String sTypeName, String ipAddress) throws BadSessionException;

	/**
	 * This method closes a user session based on the provided token.
	 * It throws a `BadSessionException` if the session cannot be found or an error
	 * occurs during deletion.
	 *
	 * @param token The session token as a String.
	 * @throws BadSessionException If the session with the provided token is not
	 *                             found or cannot be deleted.
	 */
	public void closeSessionByToken(String token) throws BadSessionException;

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
	public boolean checkIfSessionIsValid(String token, String requestIp);

	/**
	 * Retrieves a Session object representing an active session based on a provided
	 * token.
	 *
	 * @param token The session token associated with the session to retrieve.
	 * @return The Session object corresponding to the token, if found.
	 * @throws SearchException If the session cannot be found or retrieved for any
	 *                         reason.
	 */
	public Session getSessionByToken(String token) throws BadSessionException;

	/**
	 * Retrieves the user associated with a given session token.
	 * 
	 * @param token The session token to be used for lookup.
	 * @return The User object representing the owner of the session, or throws an
	 *         exception if the token is invalid.
	 * @throws InvalidDataException Thrown if the provided token is empty or doesn't
	 *                              correspond to a valid session, user side.
	 * @throws BadSessionException  Thrown if the provided token is empty or doesn't
	 *                              correspond to a valid session, session side.
	 */
	public User getSessionOwner(String token) throws InvalidDataException, BadSessionException;
}
