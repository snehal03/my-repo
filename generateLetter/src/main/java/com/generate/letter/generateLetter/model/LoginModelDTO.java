package com.generate.letter.generateLetter.model;

import javax.validation.constraints.NotNull;

public class LoginModelDTO {

	@NotNull(message = "username cannot be null")
	private String username;
	@NotNull(message = "password cannot be null")
	private String password;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "LoginModelDTO [username=" + username + ", password=" + password + "]";
	}

}
