package com.dev.server.service.session;

import com.dev.server.entity.Session;
import com.dev.server.entity.User;
import com.dev.server.exeptions.types.BadSessionException;
import com.dev.server.exeptions.types.InvalidDataException;


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
	 * @return A `Session` object representing the newly created session.
	 * @throws BadSessionException If the session type is invalid or an error occurs
	 *                             during session creation.
	 */
	public Session createSessionForUser(String userId, String sTypeName) throws BadSessionException;

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

	/**
	 * This method checks whether a provided `Session Token` is currently valid.
	 *
	 * @param tokken The `token` string to be validated.
	 * @return True if the session's expiration date is after the current date and
	 *         time, false otherwise.
	 */
	public boolean isTokenValid(String token)throws BadSessionException;


	/**
	 * This method checks whether a provided `Session` object is currently valid.
	 *
	 * @param session The `Session` object to be validated.
	 * @return True if the session's expiration date is after the current date and
	 *         time, false otherwise.
	 */
	public boolean isSessionValid(Session session);


	public void validateSession(String token) throws BadSessionException;
}
