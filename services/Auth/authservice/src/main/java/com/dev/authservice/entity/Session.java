package com.dev.authservice.entity;

import java.io.Serializable;
import java.time.LocalDateTime;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dev.authservice.constants.session.SessionType;

import lombok.Data;

@Data
@Document(collection = "sessions")
public class Session implements Serializable{
	@Id
	private ObjectId id;
	private String ownerUserId;
	private LocalDateTime expirationDate;
	private SessionType type;
}
