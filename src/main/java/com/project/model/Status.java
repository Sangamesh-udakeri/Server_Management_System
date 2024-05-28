package com.project.model;

public enum Status {

	SERVER_UP("SERVER_UP"),
	SERVER_DOWN("SERVER_DOWN");
	private final String status;
	private Status(String status) {
		this.status=status;
	}
	public String getStatus() {
		return this.status;
	}
	
}
