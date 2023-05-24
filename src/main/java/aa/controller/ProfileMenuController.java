package aa.controller;

import aa.controller.messages.AccountManagementMessage;
import aa.model.Globals;
import aa.model.User;
import aa.utils.FormatValidation;

public class ProfileMenuController {
	public static AccountManagementMessage changeUsername(String newUsername) {
		User user = Globals.getCurrentUser();
		if (newUsername.length() == 0)
			return AccountManagementMessage.EMPTY_FIELD;
		if (!FormatValidation.checkUsernameFormat(newUsername))
			return AccountManagementMessage.INVALID_USERNAME_FORMAT;
		if (!newUsername.equals(user.getUsername()) && Globals.getUserByName(newUsername) != null)
			return AccountManagementMessage.USER_ALREADY_EXISTS;
		user.setUsername(newUsername);
		return AccountManagementMessage.SUCCESS;
	}

	public static AccountManagementMessage changePassword(String newPassword) {
		if (newPassword.length() == 0)
			return AccountManagementMessage.EMPTY_FIELD;
		Globals.getCurrentUser().setPassword(newPassword);
		return AccountManagementMessage.SUCCESS;
	}

	public static AccountManagementMessage changeAvatar() {
		if (SignupMenuController.getAvatarFilePath() == null)
			return AccountManagementMessage.NO_AVATAR;
		Globals.getCurrentUser().setAvatarURL(SignupMenuController.getAvatarFilePath());
		return AccountManagementMessage.SUCCESS;
	}

	public static void deleteAccount() {
		Globals.removeUser(Globals.getCurrentUser());
		Globals.setCurrentUser(null);
	}

	public static void logout() {
		Globals.setCurrentUser(null);
	}
}
