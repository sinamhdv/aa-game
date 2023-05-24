package aa.controller.messages;

public enum AccountManagementMessage {
	INVALID_CREDENTIALS("This username and password do not match"),
	INVALID_USERNAME_FORMAT("The username can only contain letters, digits, and underscores"),
	USER_ALREADY_EXISTS("This username already exists"),
	NO_AVATAR("Please select an avatar"),
	EMPTY_FIELD("Please enter the required values"),
	SUCCESS("Success"),
	;

	private final String errorString;

	private AccountManagementMessage(String errorString) {
		this.errorString = errorString;
	}
	
	public String getErrorString() {
		return errorString;
	}
}
