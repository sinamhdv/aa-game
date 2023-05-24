package aa.controller;

import aa.controller.messages.AccountManagementMessage;
import aa.model.Globals;
import aa.model.User;

public class LoginMenuController {
	public static AccountManagementMessage login(String username, String password) {
		User user = Globals.getUserByName(username);
		if (user == null || !user.checkPassword(password))
			return AccountManagementMessage.INVALID_CREDENTIALS;
		Globals.setCurrentUser(user);
		return AccountManagementMessage.SUCCESS;
	}

	public static void setGuestMode() {
		Globals.setCurrentUser(null);
	}
}
