package aa.view;

import aa.model.GameSettings;
import aa.model.Globals;
import aa.model.User;
import aa.utils.GameConstants;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Circle;
import javafx.stage.Stage;

public class GameScreen extends Application {
	@FXML
	private Pane pane;
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

	private Circle centralDisk;

	private User user = Globals.getCurrentUser();
	private GameSettings settings = user.getGameSettings();

	@Override
	public void start(Stage stage) throws Exception {
		pane = FXMLLoader.load(GameScreen.class.getResource("/fxml/GameScreen.fxml"));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setFullScreenExitHint("");
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);	// TODO: disable exiting fullscreen
		stage.setFullScreen(true);
		stage.show();
		pane.requestFocus();
	}

	@FXML
	private void initialize() {
		centralDisk = new Circle(GameConstants.getScreenWidth() / 2, GameConstants.getScreenHeight() / 2, 100);
		pane.getChildren().add(centralDisk);
		addKeyListeners();
	}

	private void addKeyListeners() {
		pane.setOnKeyPressed(event -> {
			KeyCode key = event.getCode();
			if (key == settings.getControls()[0])
				shootKeyHandler(0);
			else if (key == settings.getControls()[1])
				shootKeyHandler(1);
			else if (key == settings.getControls()[2])
				freezeKeyHandler();
			else if (key == KeyCode.ESCAPE)
				pauseKeyHandler();
		});
	}

	private void shootKeyHandler(int playerIndex) {
		System.out.println("shoot " + playerIndex);
	}

	private void freezeKeyHandler() {
		System.out.println("freeze");
	}

	private void pauseKeyHandler() {
		System.out.println("pause");
	}
}
