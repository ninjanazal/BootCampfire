package com.dev.server.middleware.out.data;

import org.springframework.http.HttpStatus;

/**
 * This interface defines a contract for ResponseDto objects.
 * ResponseDto objects are used to represent data that is sent back
 * from a server to a client in response to a request.
 */
public interface ResponseDto {
	/**
	 * This method converts the ResponseDto object into a JSON string.
	 * This allows the data in the ResponseDto to be easily transmitted
	 * and parsed by the client.
	 * 
	 * @return A JSON string representation of the ResponseDto object.
	 */
	public String toJsonData();

	/**
	 * This method gets the HTTP status code associated with the response.
	 * The HTTP status code indicates whether the request was successful
	 * or not (e.g., 200 for success, 404 for not found).
	 * 
	 * @return The HTTP status code for the response.
	 */
	public HttpStatus getCode();
}
