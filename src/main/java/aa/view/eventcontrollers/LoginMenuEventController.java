package aa.view.eventcontrollers;

import aa.controller.LoginMenuController;
import aa.controller.messages.LoginMenuMessage;
import aa.view.LoginMenu;
import aa.view.MainMenu;
import aa.view.SignupMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginMenuEventController {
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorText;

	public void submitButtonClicked(MouseEvent mouseEvent) throws Exception {
		LoginMenuMessage message = LoginMenuController.login(
			usernameTextField.getText(),
			passwordField.getText()
		);
		if (message != LoginMenuMessage.SUCCESS) {
			errorText.setText(message.getErrorString());
			return;
		}
		new MainMenu().start(LoginMenu.getStage());
	}

	public void guestButtonClicked(MouseEvent mouseEvent) throws Exception {
		System.out.println("guest mode");
	}

	public void gotoSignupMenuClicked(MouseEvent mouseEvent) throws Exception {
		new SignupMenu().start(LoginMenu.getStage());
	}

	@FXML
	private void initialize() {
		
	}
}
