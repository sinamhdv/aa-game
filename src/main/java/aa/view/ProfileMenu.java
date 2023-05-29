package aa.view;

import aa.controller.ProfileMenuController;
import aa.controller.SignupMenuController;
import aa.controller.messages.AccountManagementMessage;
import aa.model.Globals;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class ProfileMenu extends Application {
	@FXML
	private TextField usernameTextField;
	@FXML
	private PasswordField passwordField;
	@FXML
	private Label errorText;
	@FXML
	private HBox avatarsBox;
	@FXML
	private ImageView avatarImage;
	@FXML
	private Label displayUsernameLabel;

	private RadioButton[] avatarRadioButtons = new RadioButton[6];

	public void changeUsernameButtonHandler(MouseEvent mouseEvent) {
		updateErrorText(ProfileMenuController.changeUsername(usernameTextField.getText()));
		updateProfileInfo();
	}

	public void changePasswordButtonHandler(MouseEvent mouseEvent) {
		updateErrorText(ProfileMenuController.changePassword(passwordField.getText()));
	}

	public void changeAvatarButtonHandler(MouseEvent mouseEvent) {
		if (avatarRadioButtons[4].isSelected())
			SignupMenuController.pickRandomAvatar();
		updateErrorText(ProfileMenuController.changeAvatar());
		updateProfileInfo();
	}

	public void deleteAccountButtonHandler(MouseEvent mouseEvent) throws Exception {
		ProfileMenuController.deleteAccount();
		new LoginMenu().start(LoginMenu.getStage());
	}
	
	public void logoutButtonHandler(MouseEvent mouseEvent) throws Exception {
		ProfileMenuController.logout();
		new LoginMenu().start(LoginMenu.getStage());
	}

	public void backButtonHandler(MouseEvent mouseEvent) throws Exception {
		new MainMenu().start(LoginMenu.getStage());
	}

	private void updateErrorText(AccountManagementMessage message) {
		errorText.setText(message.getErrorString());
		if (message != AccountManagementMessage.SUCCESS)
			errorText.setTextFill(Color.RED);
		else
			errorText.setTextFill(Color.GREEN);
	}

	private void updateProfileInfo() {
		avatarImage.setImage(new Image(Globals.getCurrentUser().getAvatarURL()));
		displayUsernameLabel.setText(Globals.getCurrentUser().getUsername());
	}

	@FXML
	private void initialize() {
		SignupMenu.handleAvatarSelection(avatarRadioButtons, avatarsBox);
		updateProfileInfo();
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(ProfileMenu.class.getResource("/fxml/ProfileMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}
}
