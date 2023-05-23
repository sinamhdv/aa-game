package aa.utils;

public class FormatValidation {
	public static boolean checkUsernameFormat(String username) {
		return username.matches("\\w+");
	}
}
