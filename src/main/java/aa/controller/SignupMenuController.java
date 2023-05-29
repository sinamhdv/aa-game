package aa.controller;

import java.util.Random;

import aa.controller.messages.AccountManagementMessage;
import aa.model.Globals;
import aa.model.User;
import aa.utils.FormatValidation;

public class SignupMenuController {
	public static final String[] avatarImagesPath = new String[6];
	private static String avatarFilePath;

	static {
		for (int i = 0; i < 4; i++)
			avatarImagesPath[i] = SignupMenuController.class.getResource("/images/avatars/" + i + ".jpg").toExternalForm();
		for (int i = 4; i < 6; i++)
			avatarImagesPath[i] = SignupMenuController.class.getResource("/images/avatars/" + i + ".png").toExternalForm();
	}

	public static String getAvatarFilePath() {
		return avatarFilePath;
	}
	public static void setAvatarFilePath(String avatarFilePath) {
		SignupMenuController.avatarFilePath = avatarFilePath;
	}

	public static AccountManagementMessage signup(String username, String password) {
		if (username.length() == 0 || password.length() == 0)
			return AccountManagementMessage.EMPTY_FIELD;
		if (Globals.getUserByName(username) != null)
			return AccountManagementMessage.USER_ALREADY_EXISTS;
		if (!FormatValidation.checkUsernameFormat(username))
			return AccountManagementMessage.INVALID_USERNAME_FORMAT;
		if (avatarFilePath == null)
			return AccountManagementMessage.NO_AVATAR;
		System.out.println("DBG: " + avatarFilePath);
		new User(username, password, avatarFilePath);
		return AccountManagementMessage.SUCCESS;
	}

	public static void pickDefaultAvatar(int index) {
		SignupMenuController.setAvatarFilePath(avatarImagesPath[index]);
	}

	public static void pickRandomAvatar() {
		pickDefaultAvatar(new Random().nextInt(4));
	}
}
