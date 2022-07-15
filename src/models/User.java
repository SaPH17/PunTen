package models;

import database.Database;

public class User {
	private String username, password;
	private int profilePicture;
	
	public User(String username, String password) {
		this.username = username;
		this.password = password;
		this.profilePicture = 0;
	}

	public User(String username, String password, int profilePicture) {
		super();
		this.username = username;
		this.password = password;
		this.profilePicture = profilePicture;
	}
	
	public String getStringFormat() {
		return this.profilePicture + "," + this.username + "," + this.password  ;
	}

	public int getProfilePicture() {
		return profilePicture;
	}

	public void setProfilePicture(int profilePicture) {
		this.profilePicture = profilePicture;
	}

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
	
}
