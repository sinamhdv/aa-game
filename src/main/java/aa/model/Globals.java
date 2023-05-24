package aa.model;

import java.util.ArrayList;

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
	}

	public static User getCurrentUser() {
		return currentUser;
	}
	public static void setCurrentUser(User currentUser) {
		Globals.currentUser = currentUser;
	}
}
