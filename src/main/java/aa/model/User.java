package aa.model;

import java.util.ArrayList;

import aa.utils.DatabaseManager;

public class User {
	private static ArrayList<User> allUsers;

	private String username;
	private String password;
	private final int[] highscore = new int[3];
	private final int[] playingTime = new int[3];

	public User(String username, String password) {
		this.username = username;
		this.password = password;
		allUsers.add(this);
		DatabaseManager.saveUsers();
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
		DatabaseManager.saveUsers();
	}
	public void setPassword(String password) {
		this.password = password;
		DatabaseManager.saveUsers();
	}
	public int[] getHighscore() {
		return highscore;
	}
	public int[] getPlayingTime() {
		return playingTime;
	}

	public static User byName(String username) {
		for (User user : allUsers)
			if (user.username == username)
				return user;
		return null;
	}
	public static ArrayList<User> getAllUsers() {
		return allUsers;
	}
	public static void setAllUsers(ArrayList<User> allUsers) {
		User.allUsers = allUsers;
	}
}
