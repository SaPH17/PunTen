package helpers;

import database.Database;
import models.User;

public class Session {
	private User user;
	private static Session session;
	
	private Session() { }
	
	public static Session getSession(){
		if(session == null) {
			session = new Session();
		}
		
		return session;
	}
	
	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}
	
	public void logout() {
		user = null;
	}
}
