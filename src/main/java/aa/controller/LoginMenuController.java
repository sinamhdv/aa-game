package aa.controller;

import aa.controller.messages.LoginMenuMessage;
import aa.model.User;

public class LoginMenuController {
	public static LoginMenuMessage login(String username, String password) {
		User user = User.byName(username);
		if (user == null || !user.checkPassword(password))
			return LoginMenuMessage.INVALID_CREDENTIALS;
		// TODO: set user as currentUser
		return LoginMenuMessage.SUCCESS;
	}
}
