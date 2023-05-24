package aa.model;

import java.util.ArrayList;

import aa.utils.DatabaseManager;

public class Globals {
	private static ArrayList<User> allUsers;
	private static User currentUser;

	public static User getUserByName(String username) {
		for (User user : allUsers)
			if (user.getUsername().equals(username))
				return user;
		return null;
	}
	public static ArrayList<User> getAllUsers() {
		return allUsers;
	}
	public static void setAllUsers(ArrayList<User> allUsers) {
		Globals.allUsers = allUsers;
	}
	public static void addUser(User user) {
		allUsers.add(user);
		DatabaseManager.saveUsers();
	}
	public static void removeUser(User user) {
		allUsers.remove(user);
		DatabaseManager.saveUsers();
	}

	public static User getCurrentUser() {
		return currentUser;
	}
	public static void setCurrentUser(User currentUser) {
		Globals.currentUser = currentUser;
	}
}
