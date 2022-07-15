package helpers;

import database.Database;
import models.User;

public class Session {
	private User user;
	private static Session session;
	
	private Session() {
//		user = new User("Jho123Star", "manisbanget", 1);
//		user = Database.getInstance().getUsers().get(0);
	}
	
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
