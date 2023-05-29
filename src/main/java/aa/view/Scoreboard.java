package aa.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Scoreboard extends Application {
	@FXML
	private VBox teamsListBox;
	@FXML
	private ComboBox<String> difficultyComboBox;

	@Override
	public void start(Stage stage) throws Exception {
		BorderPane borderPane = FXMLLoader.load(Scoreboard.class.getResource("/fxml/Scoreboard.fxml"));
		Scene scene = new Scene(borderPane);
		stage.setScene(scene);
		stage.show();
	}

	@FXML
	private void initialize() {
		setupDifficultyComboBox(difficultyComboBox);
	}

	private void setupDifficultyComboBox(ComboBox<String> comboBox) {
		comboBox.getItems().addAll("Easy", "Medium", "Hard");
	}

	public void backButtonHandler(MouseEvent mouseEvent) throws Exception {
		new MainMenu().start(LoginMenu.getStage());
	}
}
