package com.reyco.rabbitmq.core.domain;

public class LogEntity extends BaseEntity {
	private Integer userId;
	
	private String username;

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

	@Override
	public String toString() {
		return "LogEntity [userId=" + userId + ", username=" + username + "]";
	}
}
