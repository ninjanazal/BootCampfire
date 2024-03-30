package com.basedeveloper.jellylinkserver.account.model;

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
	private Integer id;
	private String description;

	public Gender(Integer genderId, String genderDesc) {
		id = genderId;
		description = genderDesc;
	}

	public Integer getGenderId() {
		return id;
	}

	public void setGenderId(Integer data) {
		id = data;
	}

	public String getGenderDescription() {
		return description;
	}

	public void setGenderDesc(String data) {
		description = data;
	}
}
