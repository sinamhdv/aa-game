package aa.view;

import aa.controller.LoginMenuController;
import aa.controller.messages.AccountManagementMessage;
import aa.utils.DatabaseManager;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class LoginMenu extends Application {
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorText;

	private static Stage stage;

	public void submitButtonClicked(MouseEvent mouseEvent) throws Exception {
		AccountManagementMessage message = LoginMenuController.login(
			usernameTextField.getText(),
			passwordField.getText()
		);
		if (message != AccountManagementMessage.SUCCESS) {
			errorText.setText(message.getErrorString());
			return;
		}
		new MainMenu().start(LoginMenu.getStage());
	}

	public void guestButtonClicked(MouseEvent mouseEvent) throws Exception {
		LoginMenuController.setGuestMode();
		new MainMenu().start(LoginMenu.getStage());
	}

	public void gotoSignupMenuClicked(MouseEvent mouseEvent) throws Exception {
		new SignupMenu().start(LoginMenu.getStage());
	}

	public static Stage getStage() {
		return stage;
	}

	public static void main(String[] args) {
		DatabaseManager.loadUsers();
		launch(args);
	}

	@Override
	public void start(Stage stage) throws Exception {
		LoginMenu.stage = stage;
		BorderPane borderPane = FXMLLoader.load(LoginMenu.class.getResource("/fxml/LoginMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}
}
