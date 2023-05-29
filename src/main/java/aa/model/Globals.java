package aa.model;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

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

	public static ArrayList<User> getScoreboard(int difficulty) {
		ArrayList<User> users = new ArrayList<>(allUsers);
		Comparator<User> comparator = new Comparator<User>() {
			@Override
			public int compare(User o1, User o2) {
				if (o1.getHighscore()[difficulty] != o2.getHighscore()[difficulty])
					return o2.getHighscore()[difficulty] - o1.getHighscore()[difficulty];
				else if (o1.getPlayingTime()[difficulty] != o2.getPlayingTime()[difficulty])
					return o1.getPlayingTime()[difficulty] - o2.getPlayingTime()[difficulty];
				return o1.getUsername().compareTo(o2.getUsername());
			}
		};
		Collections.sort(users, comparator);
		return users;
	}

	public static User getCurrentUser() {
		return currentUser;
	}
	public static void setCurrentUser(User currentUser) {
		Globals.currentUser = currentUser;
	}
}
