package com.dev.server.repository;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.MongoRepository;

import com.dev.server.entity.DataBlock;

public interface IDataBlockRepository extends MongoRepository<DataBlock, ObjectId>{
	List<DataBlock> findByOwnerUserId(String userId);
}
