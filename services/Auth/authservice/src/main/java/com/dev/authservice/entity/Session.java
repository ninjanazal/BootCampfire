package com.dev.authservice.entity;

import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import lombok.Data;

@Data
@Document(collation = "sessions")
public class Session {
	@Id
	private ObjectId id;
	private String ownerUserId;
	private LocalDateTime expirationDate;
}
