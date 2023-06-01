package aa.view;

import java.util.ArrayList;

import aa.controller.GameController;
import aa.controller.MainMenuController;
import aa.model.Game;
import aa.model.GameSettings;
import aa.model.Globals;
import aa.model.User;
import aa.model.gameobjects.Needle;
import aa.utils.GameConstants;
import aa.view.animations.RotationAnimation;
import javafx.application.Application;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.paint.Color;
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

	// Pause Menu
	@FXML
	private VBox pauseMenu;
	@FXML
	private Button saveButton;
	@FXML
	private CheckBox soundCheckBox;
	@FXML
	private ComboBox<String> musicComboBox;
	@FXML
	private Label player1ShootKeyText;
	@FXML
	private Label player2ShootKeyText;
	@FXML
	private Label freezeKeyText;

	@FXML
	private VBox gameOverMenu;
	@FXML
	private Label gameOverText;

	private Game game = Globals.getCurrentGame();
	private User user = Globals.getCurrentUser();
	private GameSettings settings = user.getGameSettings();
	private GameController controller;
	private RotationAnimation rotationAnimation;

	private Circle centralDisk;
	private Group rotatingObjects;
	private ArrayList<Needle> needles = new ArrayList<>();
	private Circle[][] stationaryBalls = new Circle[2][3];
	private Label[][] remainingBallsLabels = new Label[2][3];

	private MediaPlayer beepPlayer = new MediaPlayer(
		new Media(GameScreen.class.getResource("/audio/beep.wav").toExternalForm()));
	private MediaPlayer musicPlayer = new MediaPlayer(
		new Media(GameConstants.MUSIC_TRACKS[game.getMusicTrackIndex()]));

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
	public ProgressBar getFreezeBar() {
		return freezeBar;
	}
	public VBox getPauseMenu() {
		return pauseMenu;
	}
	public MediaPlayer getBeepPlayer() {
		return beepPlayer;
	}
	public MediaPlayer getMusicPlayer() {
		return musicPlayer;
	}

	@Override
	public void start(Stage stage) throws Exception {
		pane = FXMLLoader.load(GameScreen.class.getResource("/fxml/GameScreen.fxml"));
		Scene scene = new Scene(pane);
		stage.setScene(scene);
		stage.setFullScreenExitHint("");
		stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
		stage.setFullScreen(true);
		stage.show();
		pane.requestFocus();
	}

	@FXML
	private void initialize() {
		Globals.setCurrentGameScreen(this);
		controller = new GameController();
		pane.setStyle("-fx-background-color: #f2e1b8;");
		addKeyListeners();
		setupCentralDisk();
		loadInitialArrangement();
		setupAudioPlayers();
		updateAudioPlayers();
		setupPauseMenu();
		setupHUD();
		updateHUD();
		controller.startGameTimer();
		if (game.getPhase() > 1) controller.handlePhases();
	}

	private void setupAudioPlayers() {
		musicPlayer.setCycleCount(MediaPlayer.INDEFINITE);
		musicPlayer.play();
	}

	private void updateAudioPlayers() {
		musicPlayer.setMute(!settings.hasSound());
	}

	private void setupPauseMenu() {
		player1ShootKeyText.setText("Player 1 Shoot Key: " + settings.getControls()[0].getName());
		player2ShootKeyText.setText("Player 2 Shoot Key: " + settings.getControls()[1].getName());
		freezeKeyText.setText("Freeze Key: " + settings.getControls()[2].getName());
		musicComboBox.getItems().addAll("track #1", "track #2", "track #3");
		musicComboBox.getSelectionModel().select(0);
		soundCheckBox.setSelected(settings.hasSound());
		soundCheckBox.selectedProperty().addListener((observable, oldState, newState) -> {
			settings.setHasSound(newState.booleanValue());
			updateAudioPlayers();
		});
		musicComboBox.getSelectionModel().selectedIndexProperty().addListener((observable, oldValue, newValue) -> {
			game.setMusicTrackIndex(newValue.intValue());
			musicPlayer.stop();
			musicPlayer = new MediaPlayer(new Media(GameConstants.MUSIC_TRACKS[game.getMusicTrackIndex()]));
			musicPlayer.play();
			updateAudioPlayers();
		});
		if (user.isGuest()) {
			saveButton.setVisible(false);
			saveButton.setManaged(false);
		}
		if (game.getPlayersCount() == 1) {
			player2ShootKeyText.setVisible(false);
			player2ShootKeyText.setManaged(false);
		}
	}

	private void setupHUD() {
		player2RemainingBallsText.setVisible(game.getPlayersCount() == 2);
		scoreText.setLayoutX(GameConstants.getScreenWidth() / 2 - 50);
		scoreText.setLayoutY(GameConstants.getScreenHeight() / 2 - 50);
		scoreText.toFront();
		for (int playerIndex = 0; playerIndex < game.getPlayersCount(); playerIndex++) {
			for (int i = 0; i < 3; i++) {
				stationaryBalls[playerIndex][i] = new Circle(game.getShootX()[playerIndex],
					GameConstants.getScreenHeight() / 2 +
					(GameConstants.SHOOT_STARTING_DISTANCE + i * (2 * GameConstants.MIN_BALL_RADIUS + 10)) *
					(playerIndex == 0 ? 1 : -1), GameConstants.MIN_BALL_RADIUS);
				if (playerIndex == 1) stationaryBalls[playerIndex][i].setFill(GameConstants.PLAYER2_COLOR);
				pane.getChildren().add(stationaryBalls[playerIndex][i]);
				remainingBallsLabels[playerIndex][i] = new Label("00");
				remainingBallsLabels[playerIndex][i].setTextFill(Color.WHITE);
				remainingBallsLabels[playerIndex][i].setStyle("-fx-font-size: 20px");
				remainingBallsLabels[playerIndex][i].setLayoutY(
					stationaryBalls[playerIndex][i].getLayoutBounds().getMinY() + 7);
				pane.getChildren().add(remainingBallsLabels[playerIndex][i]);
			}
		}
	}

	public void updateHUD() {
		player1RemainingBallsText.setText(Integer.toString(game.getRemainingBallsCount()[0]));
		player1RemainingBallsText.setStyle(getRemainingBallsCounterStyle(0));
		player2RemainingBallsText.setText(Integer.toString(game.getRemainingBallsCount()[1]));
		player2RemainingBallsText.setStyle(getRemainingBallsCounterStyle(1));
		if (!game.isFreezed()) freezeBar.setProgress(game.getFreezeBarPercent() / 100.0);
		windText.setText("Wind: " + game.getWindAngle());
		scoreText.setText(String.format("%02d", game.getScore()));
		for (int playerIndex = 0; playerIndex < game.getPlayersCount(); playerIndex++) {
			for (int i = 0; i < 3; i++) {
				stationaryBalls[playerIndex][i].setCenterX(game.getShootX()[playerIndex]);
				remainingBallsLabels[playerIndex][i].setLayoutX(
					stationaryBalls[playerIndex][i].getLayoutBounds().getMinX() + 7);
				remainingBallsLabels[playerIndex][i].setText(String.format("%02d", 
					game.getRemainingBallsCount()[playerIndex] - i));
				if (i >= game.getRemainingBallsCount()[playerIndex]) {
					stationaryBalls[playerIndex][i].setVisible(false);
					remainingBallsLabels[playerIndex][i].setVisible(false);
				}
			}
		}
	}
	
	private String getRemainingBallsCounterStyle(int playerIndex) {
		if (game.getRemainingBallsCount()[playerIndex] * 4 <= game.getInitialBallsCount()[playerIndex])
			return "-fx-background-color: green";
		if (game.getRemainingBallsCount()[playerIndex] * 2 <= game.getInitialBallsCount()[playerIndex])
			return "-fx-background-color: yellow";
		return "-fx-background-color:red";
	}

	public void updateTimerText() {
		timerText.setText(String.format("%02d:%02d",
			game.getRemainingSeconds() / 60, game.getRemainingSeconds() % 60));
	}

	public void updateWindText() {
		windText.setText("Wind: " + game.getWindAngle());
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
			if (game.isPaused()) return;
			KeyCode key = event.getCode();
			if (key == settings.getControls()[0])
				controller.shootKeyHandler(0);
			else if (key == settings.getControls()[1])
				controller.shootKeyHandler(1);
			else if (key == settings.getControls()[2])
				controller.freezeKeyHandler();
			else if (key == KeyCode.ESCAPE)
				controller.pauseKeyHandler();
			else if (key == KeyCode.RIGHT)
				controller.moveStationaryBalls(0, 1);
			else if (key == KeyCode.LEFT)
				controller.moveStationaryBalls(0, -1);
			else if (key == KeyCode.D)
				controller.moveStationaryBalls(1, 1);
			else if (key == KeyCode.A)
				controller.moveStationaryBalls(1, -1);
		});
	}

	private void loadInitialArrangement() {
		if (game.getSavedArrangement() == null) {
			double[] arrangementAngles = GameConstants.ARRANGEMENTS[settings.getArrangementIndex()];
			for (double angle : arrangementAngles)
				addNeedle(angle, 0);
		}
		else {
			for (double angle : game.getSavedArrangement())
				addNeedle(angle, 0);
			rotationAnimation.getRotation().setAngle(game.getSavedPivotAngle());
		}
	}

	public void addNeedle(double angle, int playerIndex) {
		Needle needle = new Needle(angle, centralDisk);
		if (playerIndex == 1) needle.setColor(GameConstants.PLAYER2_COLOR);
		needles.add(needle);
		rotatingObjects.getChildren().add(needle.getGroup());
		controller.checkBallCollisions();
		controller.checkWin();
	}

	public void updateNeedles() {
		for (Needle needle : needles) {
			needle.getGroup().setVisible(game.getVisibilityState());
			needle.getBall().setRadius(game.getCurrentBallRadius());
		}
	}

	public void resumeButtonHandler(MouseEvent mouseEvent) throws Exception {
		controller.resumeGame();
	}

	public void saveButtonHandler(MouseEvent mouseEvent) throws Exception {
		controller.saveGame();
	}

	public void restartButtonHandler(MouseEvent mouseEvent) throws Exception {
		musicPlayer.stop();
		MainMenuController.startGame(game.getPlayersCount());
		new GameScreen().start(LoginMenu.getStage());
	}

	public void exitButtonHandler(MouseEvent mouseEvent) throws Exception {
		musicPlayer.stop();
		new MainMenu().start(LoginMenu.getStage());
	}

	public void gotoScoreboardButtonHandler(MouseEvent mouseEvent) throws Exception {
		new Scoreboard().start(LoginMenu.getStage());
	}

	public void displayWin() {
		pane.setStyle("-fx-background-color: lightgreen;");
		gameOverText.setText("You Won");
		gameOverMenu.setVisible(true);
		gameOverMenu.setManaged(true);
		musicPlayer.stop();
	}

	public void displayLose() {
		pane.setStyle("-fx-background-color: pink;");
		gameOverText.setText("You Lost");
		gameOverMenu.setVisible(true);
		gameOverMenu.setManaged(true);
		musicPlayer.stop();
	}
}
