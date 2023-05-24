package aa.controller.messages;

public enum SignupMenuMessage {
	INVALID_USERNAME_FORMAT("The username can only contain letters, digits, and underscores"),
	USER_ALREADY_EXISTS("This username already exists"),
	NO_AVATAR("Please select an avatar"),
	EMPTY_FIELD("Please enter both username and password"),
	SUCCESS("Success"),
	;

	private final String errorString;

	private SignupMenuMessage(String errorString) {
		this.errorString = errorString;
	}
	
	public String getErrorString() {
		return errorString;
	}
}
