package com.basedeveloper.jellylinkserver.account.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "roles")
public class Role {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@Column(name = "description", nullable = false)
	private String description;

	public Role(Long roleId, String roleDesc) {
		id = roleId;
		description = roleDesc;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long data) {
		id = data;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String data) {
		description = data;
	}
}
