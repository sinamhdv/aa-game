package aa.controller.messages;

public enum LoginMenuMessage {
	SUCCESS("Success"),
	INVALID_CREDENTIALS("Username and password didn't match"),
	;

	private String errorString;

	private LoginMenuMessage(String errorString) {
		this.errorString = errorString;
	}

	public String getErrorString() {
		return errorString;
	}
}
