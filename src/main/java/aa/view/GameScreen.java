package aa.view;

import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

public class GameScreen extends Application {
	@FXML
	private Label timerText;
	@FXML
	private ProgressBar freezeBar;
	@FXML
	private Label windText;
	@FXML
	private Label player1RemainingBallsText;
	@FXML
	private Label player2RemainingBallsText;

	@Override
	public void start(Stage stage) throws Exception {
		Pane pane = FXMLLoader.load(GameScreen.class.getResource("/fxml/GameScreen.fxml"));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setFullScreenExitHint("");
		// stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);	// TODO: disable exiting fullscreen
		stage.setFullScreen(true);
		stage.show();
	}

	@FXML
	private void initialize() {

	}
}
