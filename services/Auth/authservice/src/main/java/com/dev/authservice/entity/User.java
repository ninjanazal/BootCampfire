package com.dev.authservice.entity;

import java.io.Serializable;

import org.bson.types.ObjectId;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.index.Indexed;
import org.springframework.data.mongodb.core.mapping.Document;

import com.dev.authservice.constants.user.Gender;
import com.dev.authservice.constants.user.Role;

import lombok.Data;

@Data
@Document(collection = "users")
public class User implements Serializable{
	@Id
	private ObjectId id;
	private Role role;
	private Gender gender;
	@Indexed(unique = true)
	private String email;
	private String name;
	private Integer age;
	private String hsh_scrt;
}
