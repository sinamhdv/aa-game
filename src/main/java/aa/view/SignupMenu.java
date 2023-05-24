package aa.view;

import java.io.File;

import aa.controller.SignupMenuController;
import aa.controller.messages.AccountManagementMessage;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class SignupMenu extends Application {
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
		if (avatarRadioButtons[4].isSelected())
			SignupMenuController.pickRandomAvatar();
		AccountManagementMessage message = SignupMenuController.signup(
			usernameTextField.getText(),
			passwordField.getText()
		);
		errorText.setText(message.getErrorString());
		if (message != AccountManagementMessage.SUCCESS) {
			errorText.setTextFill(Color.RED);
			return;
		}
		errorText.setTextFill(Color.GREEN);
	}

	public void backButtonClicked(MouseEvent mouseEvent) throws Exception {
		new LoginMenu().start(LoginMenu.getStage());
	}

	public static void chooseAvatarFile(MouseEvent mouseEvent) {
		FileChooser fileChooser = new FileChooser();
		File file = fileChooser.showOpenDialog(LoginMenu.getStage());
		if (file != null)
			SignupMenuController.setAvatarFilePath("file:" + file.getAbsolutePath());
		else
			SignupMenuController.setAvatarFilePath(null);
	}

	@FXML
	private void initialize() {
		handleAvatarSelection(avatarRadioButtons, avatarsBox);
	}

	public static void handleAvatarSelection(RadioButton[] avatarRadioButtons, HBox avatarsBox) {
		SignupMenuController.setAvatarFilePath(null);

		ToggleGroup toggleGroup = new ToggleGroup();
		for (int i = 0; i < avatarRadioButtons.length; i++) {
			RadioButton radio = new RadioButton();
			radio.setToggleGroup(toggleGroup);
			ImageView image;
			image = new ImageView(SignupMenuController.avatarImagesPath[i]);
			image.setFitHeight(50);
			image.setFitWidth(50);
			radio.setGraphic(image);
			avatarRadioButtons[i] = radio;
			avatarsBox.getChildren().add(radio);
		}
		for (int i = 0; i < 4; i++) {
			int index = i;
			avatarRadioButtons[i].setOnMouseClicked(event -> { SignupMenuController.pickDefaultAvatar(index); });
		}
		avatarRadioButtons[4].setSelected(true);
		avatarRadioButtons[5].setOnMouseClicked(SignupMenu::chooseAvatarFile);
	}

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(SignupMenu.class.getResource("/fxml/SignupMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}
}
