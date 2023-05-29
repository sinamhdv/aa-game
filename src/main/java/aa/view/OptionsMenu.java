package aa.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class OptionsMenu extends Application {
	@FXML
	private ComboBox<String> difficultyComboBox;
	@FXML
	private ComboBox<String> arrangementComboBox;
	@FXML
	private CheckBox muteCheckBox;
	@FXML
	private CheckBox grayscaleCheckBox;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(OptionsMenu.class.getResource("/fxml/OptionsMenu.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void initialize() {
		initDifficultyComboBox();
		initArrangementComboBox();
		// TODO: customizing controls
		loadUserSettings();
	}

	private void initDifficultyComboBox() {
		difficultyComboBox.getItems().addAll("Easy", "Medium", "Hard");
	}

	private void initArrangementComboBox() {
		arrangementComboBox.getItems().addAll("arrangement #1", "arrangement #2", "arrangement #3");
	}

	private void loadUserSettings() {
		// TODO: implement this
	}

	public void backButtonHandler(MouseEvent mouseEvent) throws Exception {
		new MainMenu().start(LoginMenu.getStage());
	}
}
