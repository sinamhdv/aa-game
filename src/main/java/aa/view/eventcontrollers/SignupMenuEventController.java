package aa.view.eventcontrollers;

import aa.controller.SignupMenuController;
import aa.controller.messages.SignupMenuMessage;
import aa.view.LoginMenu;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;

public class SignupMenuEventController {
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorText;
	@FXML
	private HBox avatarsBox;
	private RadioButton[] avatarRadioButtons = new RadioButton[6];

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

	@FXML
	private void initialize() {
		ToggleGroup toggleGroup = new ToggleGroup();
		for (int i = 0; i < avatarRadioButtons.length; i++) {
			RadioButton radio = new RadioButton();
			radio.setToggleGroup(toggleGroup);
			ImageView image;
			try {
				image = new ImageView(
					SignupMenuEventController.class.getResource("/images/avatars/" + i + ".jpg").toExternalForm());
			} catch (NullPointerException ex) {
				image = new ImageView(
					SignupMenuEventController.class.getResource("/images/avatars/" + i + ".png").toExternalForm());
			}
			image.setFitHeight(50);
			image.setFitWidth(50);
			radio.setGraphic(image);
			avatarRadioButtons[i] = radio;
			avatarsBox.getChildren().add(radio);
		}
	}
}
