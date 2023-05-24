package aa.controller;

import aa.controller.messages.SignupMenuMessage;
import aa.model.Globals;
import aa.utils.FormatValidation;

public class SignupMenuController {
	private static String avatarFilePath;
	public static String getAvatarFilePath() {
		return avatarFilePath;
	}
	public static void setAvatarFilePath(String avatarFilePath) {
		SignupMenuController.avatarFilePath = avatarFilePath;
	}

	public static SignupMenuMessage signup(String username, String password) {
		if (Globals.getUserByName(username) != null)
			return SignupMenuMessage.USER_ALREADY_EXISTS;
		if (!FormatValidation.checkUsernameFormat(username))
			return SignupMenuMessage.INVALID_USERNAME_FORMAT;
		System.out.println(avatarFilePath);
		// new User(username, password);
		return SignupMenuMessage.SUCCESS;
	}
}
