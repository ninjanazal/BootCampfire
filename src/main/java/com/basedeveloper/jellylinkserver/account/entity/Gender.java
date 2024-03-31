package com.basedeveloper.jellylinkserver.account.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "genders")
public class Gender {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "description", nullable = false, unique = true)
	private String description;

	public Gender(Long genderId, String genderDesc) {
		id = genderId;
		description = genderDesc;
	}

	//region Get/Set
	public Long getId() {
		return id;
	}

	public void setId(Long data) {
		id = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDesc(String data) {
		description = data;
	}
	//endregion
}
