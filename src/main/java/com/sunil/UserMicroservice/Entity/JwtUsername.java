package com.sunil.UserMicroservice.Entity;

public class JwtUsername {

    String username;
    public JwtUsername(String username) {
        this.username=username;
    }
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}

}
