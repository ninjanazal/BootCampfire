package com.dev.server.repository;

import java.util.List;
import java.util.Optional;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.server.entity.DataBlock;

public interface IDataBlockRepository extends MongoRepository<DataBlock, ObjectId>{
	Optional<List<DataBlock>> findByOwnerUserId(String userId);
}
