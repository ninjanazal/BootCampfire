package com.basedeveloper.jellylinkserver.account.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "sessions")
public class Session {

	@Id
	private String id;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "session_type_id", nullable = false)
	private SessionType sessionType;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "owner_user_id", nullable = false)
	private User ownerUser;

	@Column(name = "user_ip")
	private String userIp;

	@Column(name = "expiration_date")
	private LocalDateTime expirationDate;


	
	// #region Set/Get
	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public User getOwnerUser() {
		return ownerUser;
	}

	public void setOwnerUser(User user) {
		this.ownerUser = user;
	}

	public SessionType getSessionType() {
		return sessionType;
	}

	public void setSessionType(SessionType sessionType) {
		this.sessionType = sessionType;
	}


	public String getUserIp() {
		return userIp;
	}

	public void setUserIp(String userIp) {
		this.userIp = userIp;
	}

	public LocalDateTime getExpirationDate() {
		return expirationDate;
	}

	public void setExpirationDate(LocalDateTime expirationDate) {
		this.expirationDate = expirationDate;
	}

	// endregion

}
