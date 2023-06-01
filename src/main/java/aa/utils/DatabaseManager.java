package aa.utils;

import java.io.File;
import java.io.IOException;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Arrays;

import org.apache.commons.io.FileUtils;

import com.google.gson.Gson;

import aa.model.Game;
import aa.model.Globals;
import aa.model.User;

public class DatabaseManager {

	private static final String USER_DATABASE_FILENAME = "src/main/resources/database/users.json";
	private static final String GAME_DATABASE_PATH = "src/main/resources/database/games/";

	// File Operations
	private static void writeToFile(String filename, String content) {
		File file = new File(filename);
		try {
			FileUtils.writeStringToFile(file, content, Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.err.println("FATAL: writing to file " + filename + " failed");
			e.printStackTrace();
		}
	}

	private static String readAllFromFile(String filename) {
		File file = new File(filename);
		if (!file.exists())
			return null;
		try {
			return FileUtils.readFileToString(file, Charset.forName("UTF-8"));
		} catch (IOException e) {
			System.err.println("FATAL: reading from file " + filename + " failed");
			e.printStackTrace();
			return null;
		}
	}

	// private static void serializeObject(String filename, Object object) {
	// 	try {
	// 		OutputStream file = new FileOutputStream(filename);
	// 		ObjectOutputStream serializer = new ObjectOutputStream(file);
	// 		serializer.writeObject(object);
	// 		serializer.close();
	// 		file.close();
	// 	} catch (IOException ex) {
	// 		ex.printStackTrace();
	// 		System.out.println("FATAL: Exception while serializing");
	// 		System.exit(1);
	// 	}
	// }

	// private static Object deserializeObject(String filename) {
	// 	try {
	// 		InputStream file = new FileInputStream(filename);
	// 		ObjectInputStream deserializer = new ObjectInputStream(file);
	// 		Object object = deserializer.readObject();
	// 		deserializer.close();
	// 		file.close();
	// 		return object;
	// 	} catch (Exception ex) {
	// 		ex.printStackTrace();
	// 		System.out.println("FATAL: Exception while deserializing");
	// 		System.exit(1);
	// 	}
	// 	return null;
	// }

	// Users
	public static void loadUsers() {
		String json = readAllFromFile(USER_DATABASE_FILENAME);
		User[] users = (json == null ? null : new Gson().fromJson(json, User[].class));
		Globals.setAllUsers((users == null ? new ArrayList<>() : new ArrayList<>(Arrays.asList(users))));
		for (User user : Globals.getAllUsers())
			user.getGameSettings().setOwner(user);
	}

	public static void saveUsers() {
		String json = new Gson().toJson(Globals.getAllUsers());
		writeToFile(USER_DATABASE_FILENAME, json);
	}

	// Game
	public static void saveGame() {
		String json = new Gson().toJson(Globals.getCurrentGame());
		writeToFile(GAME_DATABASE_PATH + Globals.getCurrentUser().getUsername() + ".save", json);
	}

	public static Game loadGame() {
		String json = readAllFromFile(GAME_DATABASE_PATH + Globals.getCurrentUser().getUsername() + ".save");
		if (json == null) return null;
		return new Gson().fromJson(json, Game.class);
	}
}
