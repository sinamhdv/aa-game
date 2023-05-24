package aa.model;

import aa.utils.DatabaseManager;

public class User {
	private String username;
	private String password;
	private String avatarURL;
	private final int[] highscore = new int[3];
	private final int[] playingTime = new int[3];

	public User(String username, String password, String avatarURL) {
		this.username = username;
		this.password = password;
		this.avatarURL = avatarURL;
		Globals.addUser(this);
	}

	public boolean checkPassword(String password) {
		return this.password.equals(password);
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
	public void setHighscore(int index, int value) {
		highscore[index] = value;
		DatabaseManager.saveUsers();
	}
	public void setPlayingTime(int index, int value) {
		playingTime[index] = value;
		DatabaseManager.saveUsers();
	}
	public String getAvatarURL() {
		return avatarURL;
	}
	public void setAvatarURL(String avatarURL) {
		this.avatarURL = avatarURL;
		DatabaseManager.saveUsers();
	}
}
