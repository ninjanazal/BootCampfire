package com.basedeveloper.jellylinkserver.security;

public interface SecurityServiceInterface {
	/**
	 * Encodes a string using a hashing algorithm.
	 * 
	 * @param data The data to be encoded (e.g., a password).
	 * @return The encoded data (hash) as a string.
	 */
	public String EncodeData(String data);

	/**
	 * Validates a string against its encoded (hashed) counterpart.
	 * 
	 * @param rawData    The original, unencoded data.
	 * @param hashedData The encoded (hashed) data to compare against.
	 * @return True if the raw data matches the hashed data, false otherwise.
	 */
	public boolean ValidateData(String rawData, String hashedData);

	/**
	 * Anonymizes an IP address by keeping only a specific portion.
	 * 
	 * @param address The IP address to anonymize.
	 * @return The anonymized IP address (e.g., keeping only the first three
	 *         octets).
	 */
	public String AnonymizeIpAddress(String address);

	/**
	 * Generates a unique session token as a string.
	 *
	 * This method typically uses a cryptographically secure random number generator
	 * (like UUID) to create a random string that is highly unlikely to collide
	 * (generate the same token twice).
	 * 
	 * @return A unique session token string.
	 */
	public String GenerateSessionToken();
}
