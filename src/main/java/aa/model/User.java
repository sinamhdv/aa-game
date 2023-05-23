package aa.model;

import java.util.HashMap;

public class User {
	private static HashMap<String, User> allUsers;

	private String username;
	private String password;

	public User(String username, String password) {
		this.username = username;
		this.password = password;
	}

	public boolean checkPassword(String password) {
		return this.password == password;
	}

	public String getUsername() {
		return username;
	}
	public String getPassword() {
		return password;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public void setPassword(String password) {
		this.password = password;
	}

	public static User byName(String username) {
		return allUsers.get(username);
	}
}
