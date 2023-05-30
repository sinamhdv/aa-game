package aa.view;

import java.util.ArrayList;

import aa.controller.GameController;
import aa.model.Game;
import aa.model.GameSettings;
import aa.model.Globals;
import aa.model.User;
import aa.model.gameobjects.Needle;
import aa.utils.GameConstants;
import aa.view.animations.RotationAnimation;
import aa.view.animations.ShootingAnimation;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
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
	@FXML
	private Label scoreText;

	private Game game = Globals.getCurrentGame();
	private User user = Globals.getCurrentUser();
	private GameSettings settings = user.getGameSettings();
	private GameController controller;
	private RotationAnimation rotationAnimation;

	private Circle centralDisk;
	private Group rotatingObjects;
	private ArrayList<Needle> needles = new ArrayList<>();
	private Circle[][] stationaryBalls = new Circle[2][3];

	public Pane getPane() {
		return pane;
	}
	public Circle getCentralDisk() {
		return centralDisk;
	}
	public RotationAnimation getRotationAnimation() {
		return rotationAnimation;
	}
	public ArrayList<Needle> getNeedles() {
		return needles;
	}

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
		Globals.setCurrentGameScreen(this);
		controller = new GameController();
		addKeyListeners();
		setupCentralDisk();
		loadInitialArrangement();
		setupHUD();
		updateHUD();
	}

	private void setupHUD() {
		scoreText.setLayoutX(GameConstants.getScreenWidth() / 2 - 50);
		scoreText.setLayoutY(GameConstants.getScreenHeight() / 2 - 50);
		scoreText.toFront();
		for (int playerIndex = 0; playerIndex < game.getPlayersCount(); playerIndex++) {
			for (int i = 0; i < 3; i++) {
				stationaryBalls[playerIndex][i] = new Circle(game.getShootX()[playerIndex],
					0, GameConstants.MIN_BALL_RADIUS);
				pane.getChildren().add(stationaryBalls[playerIndex][i]);
			}
		}
	}

	private void updateHUD() {
		player1RemainingBallsText.setText(Integer.toString(game.getRemainingBallsCount()[0]));
		player2RemainingBallsText.setText(Integer.toString(game.getRemainingBallsCount()[1]));
		freezeBar.setProgress(game.getFreezeBarPercent() / 100.0);
		windText.setText("Wind: " + game.getWindAngle());
		scoreText.setText(String.format("%2s", Integer.toString(game.getScore())).replace(' ', '0'));
		for (int playerIndex = 0; playerIndex < game.getPlayersCount(); playerIndex++) {
			for (int i = 0; i < 3; i++) {
				stationaryBalls[playerIndex][i].setCenterX(game.getShootX()[playerIndex]);
				stationaryBalls[playerIndex][i].setCenterY(GameConstants.getScreenHeight() / 2 +
					(GameConstants.SHOOT_STARTING_DISTANCE + i * (2 * GameConstants.MIN_BALL_RADIUS + 10)) *
					(playerIndex == 0 ? 1 : -1));
				if (i >= game.getRemainingBallsCount()[playerIndex])
					stationaryBalls[playerIndex][i].setVisible(false);
			}
		}
	}

	private void setupCentralDisk() {
		centralDisk = new Circle(GameConstants.getScreenWidth() / 2, GameConstants.getScreenHeight() / 2, 100);
		rotatingObjects = new Group(centralDisk);
		pane.getChildren().add(rotatingObjects);
		rotationAnimation = new RotationAnimation(rotatingObjects, centralDisk);
		rotationAnimation.play();
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

	private void loadInitialArrangement() {
		double[] arrangementAngles = GameConstants.ARRANGEMENT1;
		for (double angle : arrangementAngles)
			addNeedle(angle);
	}

	public void addNeedle(double angle) {
		Needle needle = new Needle(angle, centralDisk);
		needles.add(needle);
		rotatingObjects.getChildren().add(needle.getGroup());
		controller.checkBallCollisions();
	}

	private void shootKeyHandler(int playerIndex) {
		if (game.getPlayersCount() == 1 && playerIndex > 0) return;
		if (game.getRemainingBallsCount()[playerIndex] == 0) return;
		new ShootingAnimation(playerIndex, game.getWindAngle()).play();
		game.setScore(game.getScore() + game.getPhase());
		game.getRemainingBallsCount()[playerIndex]--;
		game.setFreezeBarPercent(Math.min(100, game.getFreezeBarPercent() + GameConstants.FREEZE_BAR_BOOST));
		updateHUD();
		controller.handlePhases();
	}

	private void freezeKeyHandler() {
		System.out.println("freeze");
	}

	private void pauseKeyHandler() {
		System.out.println("pause");
	}
}
