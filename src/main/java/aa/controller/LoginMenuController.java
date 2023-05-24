package aa.controller;

import aa.controller.messages.LoginMenuMessage;
import aa.model.Globals;
import aa.model.User;

public class LoginMenuController {
	public static LoginMenuMessage login(String username, String password) {
		User user = Globals.getUserByName(username);
		if (user == null || !user.checkPassword(password))
			return LoginMenuMessage.INVALID_CREDENTIALS;
		Globals.setCurrentUser(user);
		return LoginMenuMessage.SUCCESS;
	}

	public static void setGuestMode() {
		Globals.setCurrentUser(null);
	}
}
