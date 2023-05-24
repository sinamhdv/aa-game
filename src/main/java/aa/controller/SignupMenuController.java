package aa.controller;

import aa.controller.messages.SignupMenuMessage;
import aa.model.Globals;
import aa.model.User;
import aa.utils.FormatValidation;

public class SignupMenuController {
	public static SignupMenuMessage signup(String username, String password) {
		if (Globals.getUserByName(username) != null)
			return SignupMenuMessage.USER_ALREADY_EXISTS;
		if (!FormatValidation.checkUsernameFormat(username))
			return SignupMenuMessage.INVALID_USERNAME_FORMAT;
		// TODO: add a new user to the database
		return SignupMenuMessage.SUCCESS;
	}
}
