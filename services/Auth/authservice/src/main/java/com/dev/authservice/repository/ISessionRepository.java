package com.dev.authservice.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.authservice.entity.Session;

public interface ISessionRepository extends MongoRepository<Session, ObjectId> {
	/**
	 * Finds all sessions associated with a specific userId
	 *
	 * @param user The user to search for
	 * @return Result list.
	 */
	List<Session> findByOwnerUserId(String userId);

}
