package aa.controller;

import java.util.Random;

import aa.model.Game;
import aa.model.Globals;
import aa.model.User;
import aa.model.gameobjects.Needle;
import aa.utils.DatabaseManager;
import aa.utils.GameConstants;
import aa.view.GameScreen;
import aa.view.animations.FreezeBarAnimation;
import aa.view.animations.ShootingAnimation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Shape;
import javafx.util.Duration;

public class GameController {
	private Game game = Globals.getCurrentGame();
	private GameScreen gameScreen = Globals.getCurrentGameScreen();

	private Timeline invisibilityTimer;
	private Timeline gameTimer;
	private Timeline resizingTimer;
	private Timeline windTimer;

	public GameController() {
		Globals.setCurrentGameController(this);
	}

	public void handlePhases() {
		if (game.getPhase() >= 2 && game.getLastStartedPhase() <= 1) {
			// random turn reversal
			new Timeline(new KeyFrame(Duration.seconds(new Random().nextDouble(
				GameConstants.MIN_TURN_REVERSAL_INTERVALS, GameConstants.MAX_TURN_REVERSAL_INTERVALS
			)), event -> { reverseRotation(); })).play();
			startBallResizing();
			game.setLastStartedPhase(2);
		}
		if (game.getPhase() >= 3 && game.getLastStartedPhase() == 2) {
			startBallsInvisibilityTimer();
			game.setLastStartedPhase(3);
		}
		if (game.getPhase() >= 4 && game.getLastStartedPhase() == 3) {
			startWind();
			game.setLastStartedPhase(4);
		}
	}

	private void reverseRotation() {
		game.setRotationDirection(-game.getRotationDirection());
		new Timeline(new KeyFrame(Duration.seconds(new Random().nextDouble(
				GameConstants.MIN_TURN_REVERSAL_INTERVALS, GameConstants.MAX_TURN_REVERSAL_INTERVALS
		)), event -> {
				reverseRotation();
		})).play();
	}

	private void startBallsInvisibilityTimer() {
		invisibilityTimer = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
			game.setVisibilityState(!game.getVisibilityState());
			gameScreen.updateNeedles();
		}));
		invisibilityTimer.setCycleCount(Timeline.INDEFINITE);
		invisibilityTimer.play();
	}

	public void checkBallCollisions() {
		for (int i = 0; i < gameScreen.getNeedles().size(); i++) {
			for (int j = i + 1; j < gameScreen.getNeedles().size(); j++) {
				Shape intersect = Shape.intersect(gameScreen.getNeedles().get(i).getBall(),
					gameScreen.getNeedles().get(j).getBall());
				if (intersect.getBoundsInLocal().getWidth() > -0.5) {
					gameScreen.getNeedles().get(i).setColor(Color.RED);
					gameScreen.getNeedles().get(j).setColor(Color.RED);
					loseGame();
					return;
				}
			}
		}
	}

	public void checkWin() {
		if (game.isPaused()) return;
		if (game.getRemainingBallsCount()[0] != 0 || game.getRemainingBallsCount()[1] != 0)
			return;
		stopTheTime();
		updateScore();
		gameScreen.displayWin();
	}

	public void loseGame() {
		if (game.isPaused()) return;
		stopTheTime();
		updateScore();
		gameScreen.displayLose();
	}

	private void updateScore() {
		User user = Globals.getCurrentUser();
		for (int i = 0; i <= user.getGameSettings().getDifficulty(); i++) {
			if (game.getScore() > user.getHighscore()[i] ||
				(game.getScore() == user.getHighscore()[i] &&
				GameConstants.GAME_TIMEOUT - game.getRemainingSeconds() < user.getPlayingTime()[i])) {
				user.setHighscore(i, game.getScore());
				user.setPlayingTime(i, GameConstants.GAME_TIMEOUT - game.getRemainingSeconds());
			}
		}
	}

	public void startGameTimer() {
		gameScreen.updateTimerText();
		gameTimer = new Timeline(new KeyFrame(Duration.millis(1000), event -> {
			if (game.getRemainingSeconds() == 0) {
				loseGame();
				return;
			}
			game.setRemainingSeconds(game.getRemainingSeconds() - 1);
			gameScreen.updateTimerText();
		}));
		gameTimer.setCycleCount(Timeline.INDEFINITE);
		gameTimer.play();
	}

	private void startWind() {
		windTimer = new Timeline(new KeyFrame(
			Duration.millis(Globals.getCurrentUser().getGameSettings().getWindChangingIntervals()),
		event -> {
			game.setWindAngle(new Random().nextInt(-15, 16));
			gameScreen.updateWindText();
		}));
		windTimer.setCycleCount(Timeline.INDEFINITE);
		windTimer.play();
	}

	private void startBallResizing() {
		resizingTimer = new Timeline(new KeyFrame(Duration.millis(1000),
			event -> {
				game.toggleCurrentBallRadius();
				gameScreen.updateNeedles();
				checkBallCollisions();
			}));
		resizingTimer.setCycleCount(Timeline.INDEFINITE);
		resizingTimer.play();
	}

	public void shootKeyHandler(int playerIndex) {
		if (game.getPlayersCount() == 1 && playerIndex > 0) return;
		if (game.getRemainingBallsCount()[playerIndex] == 0) return;
		new ShootingAnimation(playerIndex, game.getWindAngle()).play();
		game.setScore(game.getScore() + game.getPhase());
		game.getRemainingBallsCount()[playerIndex]--;
		if (!game.isFreezed())
			game.setFreezeBarPercent(Math.min(100, game.getFreezeBarPercent() + GameConstants.FREEZE_BAR_BOOST));
		gameScreen.updateHUD();
		handlePhases();
	}

	public void freezeKeyHandler() {
		if (game.getFreezeBarPercent() < 100) return;
		game.startFreeze();
		game.setFreezeBarPercent(0);
		new FreezeBarAnimation().play();
	}

	public void endFreeze(ActionEvent event) {
		game.endFreeze();
	}

	private void stopTheTime() {
		game.setPaused(true);
		gameScreen.getRotationAnimation().pause();
		if (invisibilityTimer != null) invisibilityTimer.pause();
		if (windTimer != null) windTimer.pause();
		if (resizingTimer != null) resizingTimer.pause();
		if (gameTimer != null) gameTimer.pause();
	}

	public void pauseKeyHandler() {
		stopTheTime();
		gameScreen.getPauseMenu().setVisible(true);
		gameScreen.getPauseMenu().setManaged(true);
		gameScreen.getPane().requestFocus();
	}

	public void resumeGame() {
		game.setPaused(false);
		gameScreen.getRotationAnimation().play();
		if (invisibilityTimer != null) invisibilityTimer.play();
		if (windTimer != null) windTimer.play();
		if (resizingTimer != null) resizingTimer.play();
		if (gameTimer != null) gameTimer.play();
		gameScreen.getPauseMenu().setVisible(false);
		gameScreen.getPauseMenu().setManaged(false);
		gameScreen.getPane().requestFocus();
	}

	public void saveGame() {
		if (Globals.getCurrentUser().isGuest()) return;
		double[] arrangement = new double[gameScreen.getNeedles().size()];
		int i = 0;
		for (Needle needle : gameScreen.getNeedles())
			arrangement[i++] = needle.getAngle();
		game.setSavedArrangement(arrangement);
		game.setSavedPivotAngle(gameScreen.getRotationAnimation().getRotation().getAngle());
		DatabaseManager.saveGame();
	}

	public void moveStationaryBalls(int playerIndex, int direction) {
		if (game.getPhase() < 4) return;
		int newX = game.getShootX()[playerIndex] + direction * GameConstants.LEFT_RIGHT_MOVEMENT_STEP;
		if (newX < 50 || newX > GameConstants.getScreenWidth() - 50) return;
		game.getShootX()[playerIndex] = newX;
		gameScreen.updateHUD();
	}
}
