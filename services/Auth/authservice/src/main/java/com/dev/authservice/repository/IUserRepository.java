package com.dev.authservice.repository;


import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.authservice.entity.User;

/**
 * This interface extends `MongoRepository` from Spring Data MongoDB
 * and provides additional methods specific to user management.
 * 
 * `MongoRepository` provides basic CRUD operations for interacting with
 * a MongoDB collection named after the entity class (`User` in this case).
 * It uses the entity's identifier type (`String` here) for record lookup.
 */
public interface IUserRepository extends MongoRepository<User, ObjectId> {
	/**
	 * This method checks if a user exists in the repository based on their email
	 * address.
	 * 
	 * @param email The email address to search for.
	 * @return True if a user with the provided email exists, False otherwise.
	 */
	boolean existsByEmail(String email);

	/**
	 * This method finds a user by their email address.
	 * 
	 * @param email The email address to search for.
	 * @return The `User` object if found, null otherwise.
	 */
	User findByEmail(String email);

}
