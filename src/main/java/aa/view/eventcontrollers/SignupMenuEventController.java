package aa.view.eventcontrollers;

import aa.controller.SignupMenuController;
import aa.controller.messages.SignupMenuMessage;
import aa.view.LoginMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;

public class SignupMenuEventController {
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorText;

	public void signupButtonClicked(MouseEvent mouseEvent) throws Exception {
		SignupMenuMessage message = SignupMenuController.signup(
			usernameTextField.getText(),
			passwordField.getText()
		);
		errorText.setText(message.getErrorString());
		if (message != SignupMenuMessage.SUCCESS) {
			errorText.setTextFill(Color.RED);
			return;
		}
		errorText.setTextFill(Color.GREEN);
	}

	public void backButtonClicked(MouseEvent mouseEvent) throws Exception {
		new LoginMenu().start(LoginMenu.getStage());
	}
}
