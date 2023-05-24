package aa.controller;

import aa.controller.messages.AccountManagementMessage;
import aa.model.Globals;

public class ProfileMenuController {
	public static AccountManagementMessage changeUsername(String newUsername) {
		System.out.println(newUsername);
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
