package com.dev.server.entity;


import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dev.server.constants.data.DataType;

import lombok.Data;

@Data
@Document(collection = "data")
public class DataBlock {
	@Id
	private ObjectId id;
	private DataType type;
	private String ownerUserId;
	private String nodeData;
}
